package com.herokuapp.agendatoday.Controllers;

import com.herokuapp.agendatoday.Entities.User;
import com.herokuapp.agendatoday.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebpageController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String userLogin(){
        //if authenticated user exists redirect to app
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            return "redirect:app/list";
        }
        return "Login/login";
    }

    @GetMapping("/register")
    public String userRegister(Model mvcModel){
        mvcModel.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_registration")
    public String processRegistration(User modelUser){
        try{
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPass = encoder.encode(modelUser.getPassword());
            modelUser.setPassword(encodedPass);
            userRepo.save(modelUser);
        }
        //if user already exists
        catch (DataIntegrityViolationException e){
            return "redirect:register?alreadyExists";

        }
        //unknown error
        catch (Exception e) {
            return "redirect:register?error";
        }
        return "redirect:register?success";
    }
}
