package com.herokuapp.agendatoday.api;

import com.herokuapp.agendatoday.Entities.Tasks;
import com.herokuapp.agendatoday.Entities.User;
import com.herokuapp.agendatoday.dao.TasksRepository;
import com.herokuapp.agendatoday.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
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

    @PostMapping("/addTask")
    public String addTask(@RequestParam("task") String task){
        //retrieve logged-in user
        User loggedInUser = getLoggedInUser();

        //create new task
        Tasks newTask = new Tasks();

        //set details
        newTask.setTask(task);
        newTask.setUser(loggedInUser);

        //save task
        tasksRepository.save(newTask);

        return "Added task [" + task + "] to tasks belonging to user [" + loggedInUser.getUsername() + "]";
    }

    @DeleteMapping("/deleteTask")
    public String deleteTask(@RequestParam("task") String task){

        //retrieve logged-in user
        User loggedInUser = getLoggedInUser();
        String username = loggedInUser.getUsername();

        //find task object by task name
        Tasks taskToDelete = tasksRepository.findTasksByTaskEquals(task);

        //if task object is not present in user's task return this response
        if (!(loggedInUser.getTasks().contains(taskToDelete))) {
            return "Task [" + task + "] is not in the tasks belonging to user [" + username + "]";
        }
        //else delete task and return success message
        tasksRepository.delete(taskToDelete);
        return "Deleted task [" + task + "] from the tasks belonging to user [" + username + "]";
    }
}
