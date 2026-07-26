package com.sachin.Smart.Contact.Manager.service;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sachin.Smart.Contact.Manager.Entity.User;

public class CustomUserDetails implements UserDetails {

     private User user;

     public CustomUserDetails(User user){
           this.user= user;
     }

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
         return List.of(simpleGrantedAuthority);
         
     }

     @Override
     public @Nullable String getPassword() {
           return user.getPassword();
       
     }

     @Override
     public String getUsername() {
        return user.getEmail();
     }   
    
}
