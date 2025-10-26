package org.example.controllers;

import org.example.AddressBook;
import org.example.repositories.AddressBookRepository;
import org.hibernate.Hibernate; // NEW: Add this import (IntelliJ auto-suggests)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Hibernate.initialize(ab.getBuddies()); // NEW: This loads the lazy-loaded buddies list
        model.addAttribute("addressBook", ab);
        return "addressbook";
    }

    @GetMapping("/")
    public String home(Model model, RedirectAttributes redirectAttributes) {
        List<AddressBook> books = (List<AddressBook>) addressBookRepo.findAll(); // KEEP: Your cast if using CrudRepository
        if (books.isEmpty()) {
            AddressBook newBook = addressBookRepo.save(new AddressBook());
            redirectAttributes.addFlashAttribute("message", "Created first address book!");
            return "redirect:/addressbooks/" + newBook.getId() + "/view";
        }
        return "redirect:/addressbooks/" + books.get(0).getId() + "/view";
    }
}