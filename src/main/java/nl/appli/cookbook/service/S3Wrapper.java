package nl.appli.cookbook.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Service
public class S3Wrapper {

    private final AmazonS3Client amazonS3Client;

    public S3Wrapper(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PutObjectResult uploadImage(String fileName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,
                fileName + ".jpg", inputStream, new ObjectMetadata());
        return upload(putObjectRequest, inputStream);
    }


    private PutObjectResult upload(PutObjectRequest putObjectRequest, InputStream inputStream) {
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        org.apache.tomcat.util.http.fileupload.IOUtils.closeQuietly(inputStream);
        return putObjectResult;
    }

    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key + ".jpg");

        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    void deleteObject(String key) {
        amazonS3Client.deleteObject(bucket, key + ".jpg");
        amazonS3Client.deleteObject(bucket, key + "_thumbnail.jpg");
    }
}
