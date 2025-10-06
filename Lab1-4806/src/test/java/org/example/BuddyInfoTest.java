package org.example;

import org.example.repositories.BuddyInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BuddyInfoTest {

    @Autowired
    private BuddyInfoRepository repo;

    @BeforeEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void constructorAndGetters() {
        BuddyInfo b = new BuddyInfo("Alice", "555-1234");
        assertThat(b.getName()).isEqualTo("Alice");
        assertThat(b.getPhoneNumber()).isEqualTo("555-1234");
    }

    @Test
    public void rejectsEmptyName() {
        try {
            new BuddyInfo("", "1");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("name");
        }
    }

    @Test
    public void persistAndRetrieveBuddyInfo() {
        repo.save(new BuddyInfo("Alice", "123-4567"));
        repo.save(new BuddyInfo("Bob", "987-6543"));

        List<BuddyInfo> results = repo.findAll();

        assertThat(results).hasSize(2);
        assertThat(results).extracting(BuddyInfo::getName).contains("Alice", "Bob");
    }
}
