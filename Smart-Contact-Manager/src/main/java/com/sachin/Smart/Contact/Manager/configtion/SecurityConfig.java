package com.sachin.Smart.Contact.Manager.configtion;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.sachin.Smart.Contact.Manager.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  
      private final  CustomUserDetailsService costomuserdailaservice;

      public SecurityConfig (CustomUserDetailsService  costomuserdailaservice){
        this.costomuserdailaservice = costomuserdailaservice;
      }

// passworde encode 
        @Bean
        public PasswordEncoder passwordEncoder(){
             return NoOpPasswordEncoder.getInstance();
        }


         // user id passworde mech 
          @Bean
         public DaoAuthenticationProvider daoAuthenticationProvider(){
             
               DaoAuthenticationProvider provider = new DaoAuthenticationProvider(costomuserdailaservice);

                provider.setPasswordEncoder(passwordEncoder());

                 return provider;
         }

         //   Security Configuration

            @Bean
          public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

                 http  
                               // CSRF Disable
                        .csrf(csrf->csrf.disable())
                        // Authorization Rules
                        .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/","/login","/register","/signup","/forgot-password","/send-otp", "/verify-otp","/change-password")      
                         .permitAll()

                         // admin
                         .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                           
                        // user 
                            .requestMatchers("/user/**")
                        .hasRole("USER")

                            .anyRequest()
                         .authenticated()
                        
                        )

                          // Login Configuration  

                          .formLogin(form-> form
                             .loginPage("/login")
                              .defaultSuccessUrl("/user/index",true)
                              .permitAll()
                          )


                          // logout

                          .logout(logout ->logout
                              .logoutSuccessUrl("/")
                               .permitAll()
                          );
                            return http.build();
          }

        }

    





