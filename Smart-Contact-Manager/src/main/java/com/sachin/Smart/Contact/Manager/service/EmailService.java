package com.sachin.Smart.Contact.Manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

     @Autowired
      private JavaMailSender javaMailSender;

        public void sendmail(String to,String subject,String  messega){

                SimpleMailMessage   mail= new SimpleMailMessage();

                  mail.setTo(to);
                  mail.setSubject(subject);
                  mail.setText(messega);

                  // yaha javamail sender ko mail ka obj ka data send  ho raha he 
                 javaMailSender.send(mail);
                   
        }
    
}
