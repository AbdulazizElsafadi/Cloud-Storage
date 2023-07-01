package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer addFile(MultipartFile multipartFile, Integer userId) throws IOException {
        FileData file = new FileData(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getSize(), userId, multipartFile.getBytes());
        return fileMapper.createFile(file);
    }

    public FileData[] getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public FileData getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public FileData[] getFilesForUser(Integer userId) {
        return fileMapper.getUserFiles(userId);
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public boolean checkFileDuplication(MultipartFile file) {
        FileData[] allFiles = getAllFiles();

        for (int i = 0; i < allFiles.length; i++)
            if (file.getOriginalFilename().equals(allFiles[i].getFilename()))
                return true;

        return false;
    }


}
