package com.sachin.Smart.Contact.Manager.Controll;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import  com.sachin.Smart.Contact.Manager.Entity.Contact;
import com.sachin.Smart.Contact.Manager.Entity.User;
import com.sachin.Smart.Contact.Manager.Repository.ContactRepository;
import com.sachin.Smart.Contact.Manager.Repository.UserRepository;
import java.nio.file.Path;




@Controller
@RequestMapping("/user")
public class UserControll {

   @Autowired
     private UserRepository userrepo;

     @Autowired
     private ContactRepository contactRepository;


 
    @RequestMapping("/index")
     public String deshboard(Model model , Principal principal){
        User user = userrepo.findByEmail(principal.getName());

           model.addAttribute("user", user);
           model.addAttribute("contacts", user.getContacts());

        

         
        return "usertemplates/dashboard";
     }

     // contect add
   
     @GetMapping("/add-contact")
      public String  addContect(Model model , Principal principal){

           User user = userrepo.findByEmail(principal.getName());
             model.addAttribute("user", user);
           model.addAttribute("contact",new Contact());

          return "usertemplates/addContact";

      }
// add contect 
@PostMapping("/process-contact")
public String save(@ModelAttribute Contact contact,
                   Principal principal,
                   @RequestParam("profileImage") MultipartFile file) {

    try {

        // Login User
        User user = userrepo.findByEmail(principal.getName());

        contact.setUser(user);
        user.getContacts().add(contact);

        // Image Folder
        String uploadDir = "src/main/resources/static/images/";

        // Folder Create if not exists
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Image Check
        if (!file.isEmpty()) {

            // Image Name
            String fileName = file.getOriginalFilename();

            // Image Path
            Path filePath = uploadPath.resolve(fileName);

            // Save Image
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Save Image Name in DB
            contact.setImg(fileName);

        } else {

            // Default Image
            contact.setImg(null);
        }

        // Save Contact
        contactRepository.save(contact);
        userrepo.save(user);

        return "usertemplates/add_contact_success";

    } catch (Exception e) {
        e.printStackTrace();
        return "usertemplates/addContact";
    }
}

       //view  contect 

       @GetMapping("/show-contacts/0")
        public String view(Model model, Principal principal ){

               // user name find 
            User user = userrepo.findByEmail(principal.getName());

             // user contact find 
             List<Contact> contacts=  contactRepository.findByUser(user);

            model.addAttribute("user",user);
            model.addAttribute("contacts",contacts);

             return "usertemplates/show_contacts";
            
          
         
          
        }

        // delet cpontect

         @GetMapping("/delete/{cid}")
         public String delet(@PathVariable("cid") Integer cid,
                             HttpSession session){

                
            // Contact ko is se nikal na 
            Optional<Contact> cOptional = contactRepository.findById(cid);
               //check
               if(cOptional.isPresent()){

                        // contect get
                   
                       Contact contact = cOptional.get();

                        contactRepository.deleteById(cid);
                      //      session.setAttribute("message", "Contact deleted successfully...");
                        


               }else{
                      //  session.setAttribute("message", "Contact not found...");  
               } 

               return "redirect:/user/show-contacts/0";
}
               


         // update tha contect 

      @GetMapping("/update-contact/{cid}")
public String updatecontect(@PathVariable Integer cid,
                            Model model,
                            Principal principal){

    Contact contact = contactRepository.findById(cid).get();

    User user = userrepo.findByEmail(principal.getName());

    model.addAttribute("contact", contact);
    model.addAttribute("user", user);

    return "usertemplates/updatecontact";
}
            

            // update tha contact save database 
@PostMapping("/process-update")
public String saveUdContact(@ModelAttribute Contact contact,
                            Principal principal,
                            @RequestParam("profileImage") MultipartFile file) {

    try {

        Contact oldContact = contactRepository.findById(contact.getCid()).get();

        User user = userrepo.findByEmail(principal.getName());
        contact.setUser(user);

        String uploadDir = "src/main/resources/static/images/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (!file.isEmpty()) {

            String fileName = file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(),
                       filePath,
                       StandardCopyOption.REPLACE_EXISTING);

            contact.setImg(fileName);

        } else {

            // Agar image change nahi ki to purani image rakho
            contact.setImg(oldContact.getImg());
        }

        contactRepository.save(contact);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return "redirect:/user/show-contacts/0";
}

        // profile 

        @GetMapping("/profile")
        
        public String  profile(Model model,Principal principal){

           String name =   principal.getName();
              
                User user = userrepo.findByEmail(name);
            model.addAttribute("user",user);

            return "usertemplates/profile";

        }

          // get profile data with use for tha update  
        @GetMapping("/edit-profile")
         public String  edit(Model model,Principal principal){
                 String name = principal.getName();
                   
                  User user = userrepo.findByEmail(name);

                  model.addAttribute("user",user);
               
            return "usertemplates/edit-user-Profile";
         }

                 // update user profile save in tha database 

             
                  @PostMapping("/update-profile-save")
                        public String save(@ModelAttribute User user,
                                            @RequestParam(value = "userImage", required = false) MultipartFile image)
                                            throws IOException {

                         // Logged-in user
                        User dbUser = userrepo.findById(user.getId()).get();

                        // Update data
                        dbUser.setName(user.getName());
                       
                        dbUser.setAbout(user.getAbout());

                     // Image update
                         if (image != null && !image.isEmpty()) {

                           String uploadDir = "src/main/resources/static/images/";
                             Path uploadPath = Paths.get(uploadDir);

                     // Folder create if not exists
                         if (!Files.exists(uploadPath)) {
                             Files.createDirectories(uploadPath);
                              }

                        String fileName = image.getOriginalFilename();

                         Path filePath = uploadPath.resolve(fileName);

                          Files.copy(image.getInputStream(),
                             filePath,
                             StandardCopyOption.REPLACE_EXISTING);

                             dbUser.setImgUrl(fileName);
                            }

                                // Save user
                             userrepo.save(dbUser);

                                 return "redirect:/user/profile";

      }
    


        // seting // 
    
         @GetMapping("/settings")
public String setting(Model model, Principal principal) {

    User user = userrepo.findByEmail(principal.getName());

    model.addAttribute("user", user);

    return "usertemplates/settings";
}


           // change passworde 

           @PostMapping("/change-password")
           public String chnagepas(@RequestParam("oldPassword") String oldPassword,
                                    @RequestParam("newPassword") String newPassword,
                                Principal principal){

                                    User user = userrepo.findByEmail(principal.getName());
                                     
                                       if(!user.getPassword().equals(oldPassword)){
                                            return "redirect:/user/settings";
                                       } 
                                user.setPassword(newPassword);
                                userrepo.save(user);

                                return"usertemplates/passworde-change-don";
                                
                       

                  
           }

        @GetMapping("/search/{keyword}")
            @ResponseBody
                public List<Contact> search(@PathVariable String keyword,
         Principal principal) {

    User user = userrepo.findByEmail(principal.getName());
    return contactRepository.findByNameContainingAndUser(keyword, user);
}
}

    