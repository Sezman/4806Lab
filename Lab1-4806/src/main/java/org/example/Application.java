package org.example;

import org.example.repositories.AddressBookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.example.repositories.BuddyInfoRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepo, BuddyInfoRepository buddyRepo) {
        return (args) -> {
            // Create a new address book
            AddressBook addressBook = new AddressBook();

            // Create buddies
            BuddyInfo alice = new BuddyInfo("Alice", "555-1234");
            BuddyInfo bob = new BuddyInfo("Bob", "555-5678");

            // Add buddies to the address book
            addressBook.addBuddy(alice);
            addressBook.addBuddy(bob);

            // Save everything
            addressBookRepo.save(addressBook);

            System.out.println("Saved AddressBook with buddies:");
            System.out.println(addressBook);
        };
    }
}
