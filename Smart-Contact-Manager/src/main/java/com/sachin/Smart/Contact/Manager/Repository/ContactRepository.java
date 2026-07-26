package com.sachin.Smart.Contact.Manager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sachin.Smart.Contact.Manager.Entity.Contact;
import com.sachin.Smart.Contact.Manager.Entity.User;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

        List<Contact>findByUser(User user);    
        List<Contact> findByNameContainingAndUser(String keyword, User user);
} 
