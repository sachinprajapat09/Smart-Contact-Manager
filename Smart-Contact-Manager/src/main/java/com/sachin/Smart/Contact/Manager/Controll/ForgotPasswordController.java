package com.sachin.Smart.Contact.Manager.Controll;
import com.sachin.Smart.Contact.Manager.Entity.User;
import java.util.Random;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sachin.Smart.Contact.Manager.Repository.UserRepository;
import com.sachin.Smart.Contact.Manager.service.EmailService;

@Controller
public class ForgotPasswordController {

     @Autowired
     private UserRepository userRepository;

      @Autowired
      private EmailService emailService;

     
    @GetMapping("/forgot-password")
     
           public String forget(){
             return "forgot-password";
           }
         
    

// otp send 
     @PostMapping("/send-otp")
       public String sendotp(@RequestParam("email") String email,
                                HttpSession session
                                    ){
         
               User user = userRepository.findByEmail(email);
                 if(user == null){
                    System.out.println("Email Not Found ");
                      return"forgot-password";
                      
                 }
                 // rendom opt ganret 
                    Random random = new Random();
                       int otp = 100000 + random.nextInt(900000);

                        

                        // session me otp save karn 
                         
                     session.setAttribute("otp", otp);
                     session.setAttribute("email",email);
                     System.out.println("sendOtp Controller Hit");

                     // gmail send 

                     String subject = "Password Reset OTP";
                     String message ="Your OTP is :" + otp;
                     emailService.sendmail(email, subject, message);
                      
                      

                       return "redirect:/verify-otp";

       }

//  otp verify  code 
         @GetMapping("/verify-otp")
    public String verifyOtpPage() {
        return "verify-otp";

}

              @PostMapping("/verify-otp")
             
       public String verifyotp(@RequestParam("otp") int useropt,HttpSession session){

    Object otpObj = session.getAttribute("otp");

    if (otpObj == null) {
        System.out.println("Session OTP not found");
        return "redirect:/forgot-password";
    }

    int sessionotp = (int) otpObj;

    if(sessionotp== useropt){
        return"password_change_form";
    }else{
      return "verify-otp";
    }

}

              // pasworde change
              
               @PostMapping("/change-password")
                
                public String change(@RequestParam("newpassword") String password,
                                    HttpSession session){
                                          System.out.println("Change Password Method Called");

                    
                  // Session se email nikalo
                String gmail= (String) session.getAttribute("email");
                System.out.println("Email = " + gmail);

                 // Agar session me email nahi hai
                 if(gmail== null){
                         return "redirect:/forgot-password";

                 }
                  // Email se user find karo
                   User user = userRepository.findByEmail(gmail);
                   System.out.println(user);

                   if(user== null){
                      return "redirect:/forgot-password";
                   }


                   // pasworde save 

                   user.setPassword(password);

                   // databese me save 

                   userRepository.save(user);
                   System.out.println("Saved Successfully");
                   // session clear 
                   session.removeAttribute("email");
                   session.removeAttribute("otp");

                     // Login page par bhejo

                      return"redirect:/login";
                }

}

