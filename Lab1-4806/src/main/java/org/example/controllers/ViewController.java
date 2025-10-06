package org.example.controllers;

import org.example.AddressBook;
import org.example.repositories.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    private final AddressBookRepository addressBookRepo;

    public ViewController(AddressBookRepository addressBookRepo) {
        this.addressBookRepo = addressBookRepo;
    }

    @GetMapping("/addressbooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id)).orElseThrow();
        model.addAttribute("addressBook", ab);
        return "addressbook"; // maps to addressbook.html
    }
}
