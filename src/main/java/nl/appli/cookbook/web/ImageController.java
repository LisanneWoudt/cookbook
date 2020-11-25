package nl.appli.cookbook.web;

import nl.appli.cookbook.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "images/")
@MultipartConfig(maxFileSize = 1024*1024*1024, maxRequestSize = 1024*1024*1024)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "upload")
    public void uploadImage(
            @RequestParam(name = "name") String filename,
            @RequestPart(name = "file") MultipartFile imageFile) throws IOException {
        imageService.uploadImage(filename, imageFile);
    }

    @RequestMapping(method = GET, value = "download")
    public byte[] downloadImage(@RequestParam String image) {
        return imageService.downloadImage(image);
    }


    @RequestMapping(method = GET, value = "download/thumbnail")
    public byte[] downloadThumbnailImage(@RequestParam String image) {
        return imageService.downloadImage(image + "_thumbnail");
    }


}
