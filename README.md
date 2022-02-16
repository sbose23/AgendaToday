# AgendaToday
This web app provides a very simple to-do list anyone can use on the go.

# Main Features
1. User Sign Up
2. User Login
3. User Task Page (Add, Delete, Edit Task)

# Database Schema
The schema is set up with two tables: 
1. A "users" table - This table contains the columns "id" (unique auto generated INT), "username" (provided by user), and "password" (encrypted password provided by user).
2. A "tasks" table - This table contains the columns "id" (unique auto generated INT), "task" (task provided by user), 
   and "user_id" (foreign key mapping to the user which "owns" this task.

Mapping:
There is a OneToMany relationship from "users" to "tasks" and a ManyToOne relationship from "tasks" to "users".

![Schema](https://imgur.com/0AVpImg)
