package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileData;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequestMapping("/file")
@Controller
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        Integer userid = ((UserData) authentication.getDetails()).getUserid();
        try {

            if (fileService.checkFileDuplication(file)) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "File Name already exists!");

            } else if (file.getOriginalFilename().equals("")) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "Empty file names are not allowed!");
            } else {

                Integer fileId = fileService.addFile(file, userid);
                redirectAttributes.addFlashAttribute("messageCheck", true);

                if (fileId > 0)
                    redirectAttributes.addFlashAttribute("messageText", "File is Added Successfully");
                else
                    redirectAttributes.addFlashAttribute("messageText", "There was a Problem Uploading the File");
            }
        } catch (IOException ioException) {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "There was a Problem Uploading the File");
        }
        return "redirect:/home";
    }

    @GetMapping("/{fileId}")
    public String deleteFile(@PathVariable int fileId, Model model, RedirectAttributes redirectAttributes) {

        int rowsAffected = fileService.deleteFile(fileId);
        if (rowsAffected > 0) {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "File is Deleted Successfully");
        } else {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "There was a Problem Deleting the File");
        }

        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Integer fileId) {
        FileData file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

}

