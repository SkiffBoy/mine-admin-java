package com.skiffboy.mine.admin.controller.common;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.spring.SpringFileStorageProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class UploadsController {
    private static final String UPLOADS_PATH = "/uploads";
    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private SpringFileStorageProperties springFileStorageProperties;

    @GetMapping(UPLOADS_PATH + "/**")
    public void uploads(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileFullPath = request.getRequestURI().substring(request.getContextPath().length() + UPLOADS_PATH.length());
        int lastFileSeparator = FileUtil.lastIndexOfSeparator(fileFullPath) + 1;
        String filePath = fileFullPath.substring(0, lastFileSeparator);
        String fileName = fileFullPath.substring(lastFileSeparator);
        FileInfo fileInfo = new FileInfo()
                .setPlatform(springFileStorageProperties.getDefaultPlatform())
                .setPath(filePath)
                .setFilename(fileName);

        boolean exists = fileStorageService.exists(fileInfo);
        if (!exists) {
            throw new RuntimeException("File not found: " + filePath);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        fileStorageService.download(fileInfo).outputStream(outputStream);
    }
}
