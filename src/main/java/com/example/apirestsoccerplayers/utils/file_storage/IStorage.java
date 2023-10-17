package com.example.apirestsoccerplayers.utils.file_storage;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorage {
    public void saveFile(MultipartFile file) throws IOException;
    public Resource loadFile(String name) throws MalformedURLException;
}
