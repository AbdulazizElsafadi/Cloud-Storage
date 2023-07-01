package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NotesData;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;
    private final FileService fileService;

    public HomeController(NoteService noteService, CredentialsService credentialsService, EncryptionService encryptionService, FileService fileService) {
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String homeView(Model model, Authentication authentication) {
        model.addAttribute("note", new NotesData());

        int userid = ((UserData) authentication.getDetails()).getUserid();
        model.addAttribute("notes", noteService.getUserNotes(userid));
        model.addAttribute("credentials", credentialsService.getUserCredentials(userid));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getFilesForUser(userid));

        return "home";
    }
}