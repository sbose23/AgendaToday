package com.herokuapp.agendatoday.dao;

import com.herokuapp.agendatoday.Entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    //return list of tasks belonging to some user_id (foreign key)
    List<Tasks> findTasksByUser_id(int user_id);

    //find a task by its name
    Tasks findTasksByTaskEquals(String task);

}