package org.example.controllers;

import org.example.AddressBook;
import org.example.repositories.AddressBookRepository;
import org.hibernate.Hibernate; // NEW: Add this import (IntelliJ auto-suggests)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ViewController {

    private final AddressBookRepository addressBookRepo;

    public ViewController(AddressBookRepository addressBookRepo) {
        this.addressBookRepo = addressBookRepo;
    }

    @GetMapping("/addressbooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("AddressBook not found: " + id));
        Hibernate.initialize(ab.getBuddies()); // Load lazy buddies
        model.addAttribute("addressBook", ab);
        model.addAttribute("buddy", new org.example.BuddyInfo()); // 👈 add this line
        return "addressbook";
    }


    @GetMapping("/")
    public String home(Model model) {
        List<AddressBook> books = (List<AddressBook>) addressBookRepo.findAll();
        model.addAttribute("addressBooks", books);
        model.addAttribute("newBook", new AddressBook()); // for the creation form
        return "index";
    }

    @PostMapping("/addressbooks/create")
    public String createAddressBook(@ModelAttribute("newBook") AddressBook addressBook) {
        AddressBook saved = addressBookRepo.save(addressBook);
        return "redirect:/addressbooks/" + saved.getId() + "/view";
    }



    @PostMapping("/addressbooks/{id}/addBuddy")
    public String addBuddy(@PathVariable Long id,
                           @ModelAttribute("buddy") org.example.BuddyInfo buddy,
                           RedirectAttributes redirectAttributes) {

        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("AddressBook not found: " + id));

        // Link the buddy to the address book
        ab.addBuddy(buddy);

        // Save via repo cascade or directly if needed
        addressBookRepo.save(ab);

        redirectAttributes.addFlashAttribute("message", "Buddy added!");
        return "redirect:/addressbooks/" + id + "/view";
    }

}