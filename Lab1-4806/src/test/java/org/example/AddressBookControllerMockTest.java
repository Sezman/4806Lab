package org.example;

import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.controllers.AddressBookController;
import org.example.repositories.AddressBookRepository;
import org.example.repositories.BuddyInfoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository addressBookRepo;

    @MockBean
    private BuddyInfoRepository buddyRepo;

    @Test
    void testCreateAddressBook() throws Exception {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("Test", "111-2222"));
        Mockito.when(addressBookRepo.save(any(AddressBook.class))).thenReturn(ab);

        mockMvc.perform(post("/addressbooks"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAddressBook() throws Exception {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("Sam", "999-8888"));
        Mockito.when(addressBookRepo.findById(1)).thenReturn(Optional.of(ab));

        mockMvc.perform(get("/addressbooks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddBuddy() throws Exception {
        AddressBook ab = new AddressBook();
        Mockito.when(addressBookRepo.findById(1)).thenReturn(Optional.of(ab));
        Mockito.when(addressBookRepo.save(any(AddressBook.class))).thenReturn(ab);
        Mockito.when(buddyRepo.save(any(BuddyInfo.class))).thenReturn(new BuddyInfo("Jane", "555-5555"));

        String json = """
                {
                  "name": "Jane",
                  "phoneNumber": "555-5555"
                }
                """;

        mockMvc.perform(post("/addressbooks/1/buddies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
