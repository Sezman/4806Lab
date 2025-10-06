package org.example;

import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.repositories.AddressBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Test
    void testCreateAddressBook() {
        ResponseEntity<AddressBook> response =
                restTemplate.postForEntity("/addressbooks", null, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testAddBuddy() {
        // first create an address book
        AddressBook ab = restTemplate.postForObject("/addressbooks", null, AddressBook.class);

        BuddyInfo buddy = new BuddyInfo("John", "555-5555");

        ResponseEntity<AddressBook> response =
                restTemplate.postForEntity("/addressbooks/" + ab.getId() + "/buddies",
                        buddy, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBuddies()).isNotEmpty();
        assertThat(response.getBody().getBuddies().get(0).getName()).isEqualTo("John");
    }

    @Test
    void testGetAddressBook() {
        AddressBook ab = restTemplate.postForObject("/addressbooks", null, AddressBook.class);
        ResponseEntity<AddressBook> response =
                restTemplate.getForEntity("/addressbooks/" + ab.getId(), AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(ab.getId());
    }
}
