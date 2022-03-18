package com.herokuapp.agendatoday.api;

import com.herokuapp.agendatoday.Entities.Tasks;
import com.herokuapp.agendatoday.Entities.User;
import com.herokuapp.agendatoday.dao.TasksRepository;
import com.herokuapp.agendatoday.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class apiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TasksRepository tasksRepository;

    //retrieve logged in user
    public User getLoggedInUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userRepository.findByUsername(userName);
    }

    @GetMapping("/tasks")
    public List<String> tasks(){

        //retrieve logged-in user
        User loggedInUser = getLoggedInUser();

        int userId = loggedInUser.getId();
        List<Tasks> tasksList = tasksRepository.findTasksByUser_id(userId);
        List<String> tasks = new ArrayList<>();
        for (Tasks task: tasksList){
            tasks.add(task.getTask());
        }
        return tasks;
    }
}
