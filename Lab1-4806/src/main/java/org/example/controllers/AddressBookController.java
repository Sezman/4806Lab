package org.example.controllers;

import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.repositories.AddressBookRepository;
import org.example.repositories.BuddyInfoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addressbooks")
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

    @GetMapping // NEW: List all to make seeding easy
    public List<AddressBook> getAllAddressBooks() {
        return (List<AddressBook>) addressBookRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<AddressBook> getAddressBook(@PathVariable Long id) {
        return addressBookRepo.findById(Math.toIntExact(id)); // FIXED: Use Long directly (update repo if needed)
    }

    @PostMapping("/{id}/buddies")
    public AddressBook addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("AddressBook not found: " + id)); // FIXED: Better error
        ab.addBuddy(buddy);
        buddyRepo.save(buddy);
        return addressBookRepo.save(ab);
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) { // FIXED: Long for consistency
        AddressBook ab = addressBookRepo.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("AddressBook not found: " + id));
        BuddyInfo buddy = buddyRepo.findById(buddyId)
                .orElseThrow(() -> new RuntimeException("Buddy not found: " + buddyId));
        ab.removeBuddy(buddy);
        buddyRepo.delete(buddy);
        return addressBookRepo.save(ab);
    }
}