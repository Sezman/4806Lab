package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) buddies.add(buddy);
    }

    public boolean removeBuddy(BuddyInfo buddy) {
        return buddies.remove(buddy);
    }

    public BuddyInfo findByName(String name) {
        for (BuddyInfo b : buddies) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }
        return null;
    }

    public int size() {
        return buddies.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddressBook:\n");
        for (BuddyInfo b : buddies) sb.append(" - ").append(b).append('\n');
        return sb.toString();
    }

    public Long getId() { return id; }
    public List<BuddyInfo> getBuddies() { return buddies; }
    public void setBuddies(List<BuddyInfo> buddies) { this.buddies = buddies; }
}
