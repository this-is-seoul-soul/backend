package com.seouldata.auth.domain.auth.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String saveFile(MultipartFile profile) throws IOException {
        String originalFilename = profile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(profile.getSize());
        metadata.setContentType(profile.getContentType());

        amazonS3.putObject(bucket, originalFilename, profile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

}
