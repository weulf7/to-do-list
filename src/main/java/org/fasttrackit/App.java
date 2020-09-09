package org.fasttrackit;

import org.fasttrackit.config.DatabaseConfiguration;
import org.fasttrackit.persistence.TaskRepository;
import org.fasttrackit.transfer.CreateTaskRequest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        TaskRepository taskRepository=new TaskRepository();

        CreateTaskRequest request=new CreateTaskRequest();
//        request.setDeadline(LocalDate.now().plusWeeks(1));
//        request.setDescription("Learn JDBC");
////
//        taskRepository.createTask(request);

//        taskRepository.deleteTask(2);

        System.out.println(taskRepository.getTasks());
    }


}
