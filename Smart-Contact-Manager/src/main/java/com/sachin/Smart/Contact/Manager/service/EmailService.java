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

             try{
                    System.out.println("Mail sending start");
                SimpleMailMessage   mail= new SimpleMailMessage();

                  mail.setTo(to);
                  mail.setSubject(subject);
                  mail.setText(messega);

                  // yaha javamail sender ko mail ka obj ka data send  ho raha he 
                 javaMailSender.send(mail);
                  System.out.println("Mail sent successfully");
             } catch (Exception e) {

            System.out.println("MAIL ERROR");
            e.printStackTrace();
        }
                   
        }
    
}
