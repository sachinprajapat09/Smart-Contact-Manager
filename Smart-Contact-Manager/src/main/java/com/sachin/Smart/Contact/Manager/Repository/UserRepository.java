package com.sachin.Smart.Contact.Manager.Repository;
import com.sachin.Smart.Contact.Manager.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {

    

    User findByEmail(String email);
    
}
