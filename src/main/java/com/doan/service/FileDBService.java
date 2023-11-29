package com.doan.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileDBService {
    public void init();
//    public void save(MultipartFile file) throws IOException;
    public Resource load(String filename);
    public Stream<Path> loadAll();
    public void store(MultipartFile file);
}
