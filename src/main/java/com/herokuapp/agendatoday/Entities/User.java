package com.herokuapp.agendatoday.Entities;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;

@Component
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 64)
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "user")
    private Collection<Tasks> tasks;

    public Collection<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Tasks> tasks) {
        this.tasks = tasks;
    }
}
