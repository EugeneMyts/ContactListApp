package com.example.ContactListApp.controllers;

import com.example.ContactListApp.Contact;
import com.example.ContactListApp.ContactService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ContactService contactService;

    @GetMapping("/")
    public String mainPage(@NotNull Model model) {
        model.addAttribute("contacts", contactService.getAll());
        return "index";
    }

    @GetMapping("/contact/create")
    public String showCreateForm(@NotNull Model model) {
        model.addAttribute("contact", new Contact());
        return "create-edit";
    }

    @PostMapping("/contact/save")
    public String create(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Contact contact = contactService.get(id);
        model.addAttribute("contact", contact);
        return "create-edit";
    }

    @GetMapping("/contact/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.delete(id);
        return "redirect:/";
    }



}
