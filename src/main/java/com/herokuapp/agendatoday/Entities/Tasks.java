package com.herokuapp.agendatoday.Entities;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name="tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length=30)
    private String task;

    //foreign key
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    public int getId() {
        return id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", user=" + user +
                '}';
    }
}
