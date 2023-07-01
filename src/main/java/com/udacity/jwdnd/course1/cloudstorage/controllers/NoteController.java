package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NotesData;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String saveOrEditNote(Authentication authentication, @ModelAttribute NotesData note, Model model, RedirectAttributes redirectAttributes) {

        UserData user = (UserData) authentication.getDetails();
//        System.out.println("note: " + note);
        note.setUserid(user.getUserid());

        if (note.getNoteid() == null) {
            int noteid = noteService.createNote(note);

            if (noteid > 0) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "Note is Added Successfully");
            } else {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "There was a Problem adding the Note");
            }

        } else {
            int rowsEffect = noteService.editNote(note);
            if (rowsEffect > 0) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "Note is Modified Successfully");
            } else {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "There was a Problem Modifying the Note");
            }
        }

        return "redirect:/home";
    }

    @GetMapping("/{noteid}")
    public String deleteNote(@PathVariable int noteid, Model model, RedirectAttributes redirectAttributes) {
//        System.out.println("note id: " + noteid);

        int rowsAffected = noteService.deleteNote(noteid);
        if (rowsAffected > 0) {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "Note is Deleted Successfully");
        } else {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "There was a Problem Deleting the Note");
        }

        return "redirect:/home";
    }


}
