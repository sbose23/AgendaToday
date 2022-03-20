# AgendaToday
This web app provides a very simple to-do list anyone can use on the go. This app has a model-view-controller architecutral pattern with Spring MVC. The view layer is supported by the Thymeleaf template engine. 

# Important Note
Since this project uses the free version of Heroku, it may take up to 30 seconds to start the container for the app.  
[Containers sleep after 30 minutes of inactivity.](https://www.heroku.com/dynos#:~:text=Sleeps%20after%2030%20mins%20of%20inactivity%2C%20otherwise%20always%20on%20depending%20on%20your%20remaining%20monthly%20free%20dyno%20hours.)


# Main Features
1. User Sign Up
2. User Login
3. User Task Page (Add, Delete, Edit Task)
4. Stateful API (WIP)

# Database Schema
The schema is set up with two tables: 
1. A "users" table - This table contains the columns "id" (unique auto generated INT), "username" (provided by user), and "password" (encrypted password provided by user).
2. A "tasks" table - This table contains the columns "id" (unique auto generated INT), "task" (task provided by user), 
   and "user_id" (foreign key mapping to the user which "owns" this task.

Mapping:
There is a OneToMany relationship from "users" to "tasks" and a ManyToOne relationship from "tasks" to "users".

![Schema](https://imgur.com/0AVpImg.png)

# Stateful API
(Work in progress)
The Stateful API for this project uses Spring REST and Basic Authentication (hence stateful). A user that has signed up for AgendaToday will be able to make API calls for adding, deleting, and viewing their tasks.  
For example, the command `curl -u username:password https://agendatoday.herokuapp.com/api/tasks` on command prompt or terminal would return a list of tasks belonging to user "username".
