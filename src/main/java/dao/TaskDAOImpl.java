package dao;

import db.DatabaseConnection;
import model.Task;


import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDao{
    @Override
    public void addTask(Task task) throws SQLException{
        String query = "INSERT INTO tasks (title, description, is_completed) VALUES (?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());
            stmt.executeUpdate();
        }
    }

    @Override
    public Task getTask(int id) throws SQLException{
        String query = "SELECT * FROM tasks WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return  new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("is_completed")
                );
            }
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() throws SQLException{
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()){
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("is_completed")
                );
                tasks.add(task);

            }
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) throws SQLException{
        String query = "UPDATE tasks SET title = ?, description = ?, is_completed = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());
            stmt.setInt(4, task.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteTask(int id) throws SQLException{
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
