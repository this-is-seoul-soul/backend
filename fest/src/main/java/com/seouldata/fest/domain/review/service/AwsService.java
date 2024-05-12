package com.seouldata.fest.domain.review.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsService {

    String saveFile(MultipartFile multipartFile) throws IOException;

}
