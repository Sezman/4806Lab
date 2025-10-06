package org.example.controllers;

import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.repositories.AddressBookRepository;
import org.example.repositories.BuddyInfoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/addressbooks") //rest

public class AddressBookController {

    private final AddressBookRepository addressBookRepo;
    private final BuddyInfoRepository buddyRepo;

    public AddressBookController(AddressBookRepository addressBookRepo, BuddyInfoRepository buddyRepo) {
        this.addressBookRepo = addressBookRepo;
        this.buddyRepo = buddyRepo;
    }

    @PostMapping
    public AddressBook createAddressBook() {
        return addressBookRepo.save(new AddressBook());
    }

    @GetMapping("/{id}")
    public Optional<AddressBook> getAddressBook(@PathVariable Long id) {
        return addressBookRepo.findById(Math.toIntExact(id));
    }

    @PostMapping("/{id}/buddies")
    public AddressBook addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id)).orElseThrow();
        ab.addBuddy(buddy);
        buddyRepo.save(buddy); // persist buddy
        return addressBookRepo.save(ab);
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Long id, @PathVariable Integer buddyId) {
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id)).orElseThrow();
        BuddyInfo buddy = buddyRepo.findById(Long.valueOf(buddyId)).orElseThrow();
        ab.removeBuddy(buddy);
        buddyRepo.delete(buddy);
        return addressBookRepo.save(ab);
    }
}
