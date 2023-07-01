package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialsData;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {
    private final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public String saveOrEditNote(Authentication authentication, @ModelAttribute CredentialsData credential, Model model, RedirectAttributes redirectAttributes) {
        UserData user = (UserData) authentication.getDetails();
//        System.out.println("user: " + user);
        credential.setUserid(user.getUserid());


//        System.out.println("credential id: " + credential.getCredentialid());

        if (credential.getCredentialid() == null) {

            if (credentialsService.checkCredentialUsernameDuplication(credential)) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "Username already exists in Credentials!");
            } else {
                int credentialid = credentialsService.createCredential(credential);

                if (credentialid > 0) {
                    redirectAttributes.addFlashAttribute("messageCheck", true);
                    redirectAttributes.addFlashAttribute("messageText", "Credential is Added Successfully");
                } else {
                    redirectAttributes.addFlashAttribute("messageCheck", true);
                    redirectAttributes.addFlashAttribute("messageText", "There was a Problem adding New Credential");
                }
            }


        } else {
            int rowsEffect = credentialsService.editCredential(credential);
            if (rowsEffect > 0) {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "Credential is Modified Successfully");
            } else {
                redirectAttributes.addFlashAttribute("messageCheck", true);
                redirectAttributes.addFlashAttribute("messageText", "There was a Problem Modifying the Credential");
            }
        }


        return "redirect:/home";
    }

    @GetMapping("/{credentialid}")
    public String deleteCredential(@PathVariable int credentialid, Model model, RedirectAttributes redirectAttributes) {
//        System.out.println("credential id: " + credentialid);

        int rowsAffected = credentialsService.deleteCredential(credentialid);
        if (rowsAffected > 0) {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "Credential is Deleted Successfully");
        } else {
            redirectAttributes.addFlashAttribute("messageCheck", true);
            redirectAttributes.addFlashAttribute("messageText", "There was a Problem Deleting the Credential");
        }

        return "redirect:/home";
    }
}
