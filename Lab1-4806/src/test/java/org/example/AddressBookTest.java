package org.example;

import org.example.repositories.AddressBookRepository;
import org.example.repositories.BuddyInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AddressBookTest {

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyInfoRepo;

    @Test
    public void addFindRemove() {
        AddressBook ab = new AddressBook();
        BuddyInfo alice = new BuddyInfo("Alice", "111");
        ab.addBuddy(alice);
        ab.addBuddy(new BuddyInfo("Bob", "222"));

        assertThat(ab.size()).isEqualTo(2);
        assertThat(ab.findByName("alice")).isEqualTo(alice);
        assertThat(ab.removeBuddy(alice)).isTrue();
        assertThat(ab.size()).isEqualTo(1);
    }

    @Test
    public void persistAddressBookWithBuddies() {
        BuddyInfo alice = new BuddyInfo("Alice", "123-4567");
        BuddyInfo bob = new BuddyInfo("Bob", "555-5678");

        AddressBook ab = new AddressBook();
        ab.addBuddy(alice);
        ab.addBuddy(bob);

        // Persist using repository
        addressBookRepo.save(ab);

        // Load back
        AddressBook persistedAB = addressBookRepo.findAll().iterator().next();

        assertThat(persistedAB.size()).isEqualTo(2);
        assertThat(persistedAB.getBuddies())
                .extracting(BuddyInfo::getName)
                .contains("Alice", "Bob");
    }
}
