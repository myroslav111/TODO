package dao;

import model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {
    void addTask(Task task) throws SQLException;
    Task getTask(int id) throws SQLException;
    List<Task> getAllTasks() throws SQLException;
    void updateTask(Task task) throws SQLException;
    void deleteTask(int id) throws SQLException;

}
