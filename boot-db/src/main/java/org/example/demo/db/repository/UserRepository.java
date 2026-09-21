package org.example.demo.db.repository;

import org.example.demo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
    List<User> findByUsername(String username);

    List<User> findByBirthdayBetweenAndEmailContains(LocalDate birthdayAfter, LocalDate birthdayBefore, String email);
    List<User> findByBirthdayBefore(LocalDate birthday);
}
