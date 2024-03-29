package nl.appli.cookbook.service;

import com.amazonaws.util.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Service
public class ImageService {

    private S3Wrapper s3Wrapper;

    public ImageService(@Autowired S3Wrapper s3Wrapper) {
        this.s3Wrapper = s3Wrapper;
    }

    public void uploadImage(String filename, MultipartFile multipart) throws IOException {
        s3Wrapper.uploadImage(filename, resizeImage(multipart, "preview"));
        s3Wrapper.uploadImage(filename + "_thumbnail", resizeImage((multipart), "thumbnail"));
    }

    public void uploadImages(String filename, List<MultipartFile> multipartFiles) throws IOException {
        int count = 1;
        for (MultipartFile file: multipartFiles) {
            uploadImage(filename + "_" + count, file);
            count ++;
        }
    }

    public byte[] downloadImage(String objectKey) {
        try {
            ResponseEntity<byte[]> response = s3Wrapper.download(objectKey);
            HttpHeaders httpHeaders = response.getHeaders();

            if (response.getBody() != null || httpHeaders.getContentType() != null) {
                InputStream inputStream = new ByteArrayInputStream(response.getBody());
                return IOUtils.toByteArray(inputStream);
            }
            else {
                return null;
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    void deleteImages(String key, int imgCount) {
        for (int i = 1; i <= imgCount; i++) {
            s3Wrapper.deleteObject(key + "_" + i);
        }
    }

    private InputStream resizeImage(MultipartFile multipartFile, String imageScale) throws IOException {
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        BufferedImage resizedImage;
        float compressionQuality;

        if (imageScale.equals("thumbnail")) {
            resizedImage = Scalr.resize(image, calculateThumbnailSize(image.getWidth()),
                    calculateThumbnailSize(image.getHeight()));
            compressionQuality = 0.75f;
        } else {
            resizedImage = Scalr.resize(image, calculatePreviewWidthSize(image.getWidth()),
                    calculatePreviewHeightSize(image.getHeight()));
            compressionQuality = 1f;
        }

        File resizedFile = new File(imageScale + ".jpg");
        OutputStream os = new FileOutputStream(resizedFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(compressionQuality);

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        writer.write(null, new IIOImage(resizedImage, null, null), param);
        os.close();
        ios.close();
        writer.dispose();

        return new FileInputStream(imageScale + ".jpg");
    }

    public int calculatePreviewHeightSize(int height) {
        int divideBy =  height / 1500;
        return calculateSize(divideBy, height);
    }
    public int calculatePreviewWidthSize(int width) {
        int divideBy = width / 1200;
        return calculateSize(divideBy, width);
    }
    public int calculateThumbnailSize(int size) {
        int divideBy = size / 130;
        return calculateSize(divideBy, size);
    }

    public int calculateSize(int divideBy, int size) {
        if (divideBy != 0) {
            return size / divideBy;
        }
        return size;
    }

}
