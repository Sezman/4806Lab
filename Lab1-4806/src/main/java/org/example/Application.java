package org.example;

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
    public CommandLineRunner demo(BuddyInfoRepository repo) {
        return (args) -> {
            repo.save(new BuddyInfo("Alice", "555-1234"));
            repo.save(new BuddyInfo("Bob", "555-5678"));

            System.out.println("All buddies:");
            for (BuddyInfo b : repo.findAll()) {
                System.out.println(b);
            }


            BuddyInfo alice = repo.findByName("Alice");
            System.out.println("Found by name: " + alice);
        };
    }
}
