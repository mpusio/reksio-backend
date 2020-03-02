package com.reksio.restbackend.files;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/storage")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @ApiOperation("Upload file to AWS S3 Bucket")
    @PostMapping("/upload-file")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @ApiOperation(value = "Delete file from AWS S3 Bucket", code = 204)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-file")
    public void deleteFile(@RequestPart(value = "url") String fileUrl) {
        this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}