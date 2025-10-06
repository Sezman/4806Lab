package org.example.repositories;

import org.example.BuddyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuddyInfoRepository extends JpaRepository<BuddyInfo, Long> {
    BuddyInfo findByName(String name);
}
