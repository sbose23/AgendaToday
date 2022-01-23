package com.herokuapp.agendatoday.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class ListController {
    @GetMapping("/list")
    public String list(){
        return "List/list";
    }
    //list stuff
}
