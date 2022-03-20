package com.herokuapp.agendatoday.Controllers;

import com.herokuapp.agendatoday.Entities.Tasks;
import com.herokuapp.agendatoday.Entities.User;
import com.herokuapp.agendatoday.dao.TasksRepository;
import com.herokuapp.agendatoday.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping("/app/list")
public class ListController {

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

    //return true if task belongs to user
    public boolean taskBelongsToUser(Tasks task){
        User user = getLoggedInUser();
        return user.getTasks().contains(task);
    }

    @GetMapping()
    public String list(Model mvcModel){

        //retrieve the logged-in user
        User loggedInUser = getLoggedInUser();

        //retrieve user ID to find corresponding tasks
        int userId = loggedInUser.getId();
        List<Tasks> tasksList = tasksRepository.findTasksByUser_id(userId);

        //add tasks to the Model
        mvcModel.addAttribute("tasks", tasksList);

        //add date to the Model
        LocalDate date = LocalDate.now();
        String dateFormatted = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(date);
        mvcModel.addAttribute("date", dateFormatted);

        return "List/list";
    }

    //list operations
    @GetMapping("/delete")
    public String delete(@RequestParam("task") int taskID){
        //if task does not belong to user return error
        if (!(taskBelongsToUser(tasksRepository.getById(taskID)))) {
            return "redirect:/app/list?deletionError";
        }
        //delete from database with JPA
        tasksRepository.deleteById(taskID);

        //return to list
        return "redirect:/app/list";
    }
    @GetMapping("/add")
    public String add(Model mvcModel){

        mvcModel.addAttribute("newTask", new Tasks());
        return "List/add";
    }
    @PostMapping("/saveTask")
    public String saveTask(Tasks task){

        //get logged-in user and establish the relationship between the task and that user
        User loggedInUser = getLoggedInUser();
        task.setUser(loggedInUser);

        //save the task in tasks schema
        tasksRepository.save(task);

        return "redirect:/app/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("task") int taskID, Model mvcModel){

        //retrieve task from data base
        Tasks task = tasksRepository.getById(taskID);

        //if task does not belong to user return error
        if (!(taskBelongsToUser(task))) {
            return "redirect:/app/list?editError";
        }

        //id needed in editTask
        mvcModel.addAttribute("taskId", taskID);

        //this attribute will autofill the form for edit page
        mvcModel.addAttribute("taskExisting", task);

        return "List/edit";
    }

    @PostMapping("/editTask")
    public String edit(@RequestParam("taskId") int taskId, Tasks task){
        //retrieve old task from data base
        Tasks oldTask = tasksRepository.getById(taskId);

        //set new task as old task
        oldTask.setTask(task.getTask());

        //save old task
        tasksRepository.save(oldTask);

        return "redirect:/app/list";
    }
}
