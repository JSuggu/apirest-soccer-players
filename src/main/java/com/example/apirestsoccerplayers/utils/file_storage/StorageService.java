package com.example.apirestsoccerplayers.utils.file_storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService implements IStorage{
    private final Path rootFolder = Paths.get("src/files");

    public void saveFile(MultipartFile file) throws IOException{
        Files.copy(file.getInputStream(), rootFolder.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
    }

    public Resource loadFile(String name) throws MalformedURLException{
        Path file = rootFolder.resolve(name);
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }
}
