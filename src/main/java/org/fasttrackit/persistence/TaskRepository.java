package org.fasttrackit.persistence;

import org.fasttrackit.config.DatabaseConfiguration;
import org.fasttrackit.domain.Task;
import org.fasttrackit.transfer.CreateTaskRequest;
import org.fasttrackit.transfer.UpdateTaskRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO (data access object)
public class TaskRepository {

    public void createTask(CreateTaskRequest request) throws SQLException {

       //preventing SQL injection using placeholders and PreparedStatement
        String sql="INSERT INTO task (description,deadline) VALUES (?, ?) ";

        //try-with-resources
        try(   PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)){


            preparedStatement.setString(1,request.getDescription());
            preparedStatement.setDate(2, Date.valueOf(request.getDeadline()));

            preparedStatement.executeUpdate();
        }
    }

    public void deleteTask(long id) throws SQLException {
        String sql="DELETE FROM task WHERE id=?";

        try(PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)){

            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();


        }
    }

    public void updateTask(long id, UpdateTaskRequest request) throws SQLException {
        String sql="UPDATE task SET done=? WHERE id=?";

        try(PreparedStatement preparedStatement = DatabaseConfiguration.getConnection().prepareStatement(sql)){

            preparedStatement.setBoolean(1,request.isDone());

            preparedStatement.setLong(2,id);

            preparedStatement.executeUpdate();


        }


    }


    public List<Task> getTasks() throws SQLException {
        String sql="SELECT id, description, deadline, done FROM task";

        List<Task> task = new ArrayList<>();


        try(Statement statement= DatabaseConfiguration.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);

           while (resultSet.next()){
               Task task1=new Task();
               task1.setId(resultSet.getLong("id"));
               task1.setDescription(resultSet.getString("description"));
               task1.setDeadline(resultSet.getDate("deadline").toLocalDate());
               task1.setDone(resultSet.getBoolean("done"));

               task.add(task1);

           }

        }

        return task;
    }



}
