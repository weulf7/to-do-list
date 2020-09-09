package org.fasttrackit.web;

import org.fasttrackit.config.ObjectMapperConfiguration;
import org.fasttrackit.domain.Task;
import org.fasttrackit.service.TaskService;
import org.fasttrackit.transfer.CreateTaskRequest;
import org.fasttrackit.transfer.UpdateTaskRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {

    private TaskService taskService = new TaskService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);


        CreateTaskRequest request = ObjectMapperConfiguration.getObjectMapper()
                .readValue(req.getReader(), CreateTaskRequest.class);


        try {
            taskService.createTask(request);
        } catch (SQLException e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);


        try {
            List<Task> task = taskService.getTask();


            ObjectMapperConfiguration.getObjectMapper().writeValue(resp.getWriter(), task);

        } catch (SQLException e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idAsString = req.getParameter("id");

        UpdateTaskRequest request = ObjectMapperConfiguration.getObjectMapper()
                .readValue(req.getReader(), UpdateTaskRequest.class);

        try {
            taskService.updateTask(Long.parseLong(idAsString),request);
        } catch (SQLException e) {
            resp.sendError(500,e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       addCorsHeaders(resp);



        String idAsString = req.getParameter("id");


        try {
            taskService.deleteTask(Long.parseLong(idAsString));
        } catch (SQLException e) {
            resp.sendError(500,e.getMessage());
        }


    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCorsHeaders(resp);
    }

    private void addCorsHeaders(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","POST,GET,PUT,DELETE,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers","content-type");
    }
}
