package com.sachin.Smart.Contact.Manager.Controll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.sachin.Smart.Contact.Manager.Entity.User;
import com.sachin.Smart.Contact.Manager.Repository.UserRepository;
import jakarta.validation.Valid;

@Controller
public class HamoControll {

     @Autowired
     private UserRepository userRepository;

     // hamo page
     
     @GetMapping("/")
     public String home(Model model){
        model.addAttribute("titel", "Home - Smart Contact Manager");
        return "home";
     }


     // singup
     @GetMapping("/signup")
     public String sin(Model model){

        model.addAttribute("user", new User());
         return "signup";

        
     }

     //save data
     @PostMapping("/register")
     public String save(
        @Valid
        @ModelAttribute("user") User user,
        BindingResult result,
         Model model){
   
             // Validation Check
            if(result.hasErrors()){
                return "signup";
            }

            User oldUser = userRepository.findByEmail(user.getEmail());

             if(oldUser != null){
               model.addAttribute("msg", "Email already exists");
                return "signup";
             }

             
             // Default Values
              user.setRole("ROLE_USER");
        user.setEnable(true);
        user.setImgUrl("default.png");


        //save user
                userRepository.save(user);
                return "login";
        }

     

    
   // login 

   @GetMapping("/login")
   public String login(){
      return "login";
   }
    
}
