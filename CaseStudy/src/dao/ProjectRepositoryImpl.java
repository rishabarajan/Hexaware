package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ProjectRepositoryImpl implements IProjectRepository {

    private Connection conn;

    public ProjectRepositoryImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public boolean createEmployee(Employee emp) {
        String query = "INSERT INTO employee(name, designation, gender, salary, project_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDesignation());
            stmt.setString(3, emp.getGender());
            stmt.setDouble(4, emp.getSalary());
            stmt.setInt(5, emp.getProjectId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createProject(Project pj) {
        String query = "INSERT INTO project(project_name, description, start_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pj.getProjectName());
            stmt.setString(2, pj.getDescription());
            stmt.setString(3, pj.getStartDate());
            stmt.setString(4, pj.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createTask(Task task) {
        String query = "INSERT INTO task(task_name, project_id, employee_id, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, task.getTaskName());
            stmt.setInt(2, task.getProjectId());
            stmt.setInt(3, task.getEmployeeId());
            stmt.setString(4, task.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignProjectToEmployee(int projectId, int employeeId) {
        String query = "UPDATE employee SET project_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignTaskInProjectToEmployee(int taskId, int projectId, int employeeId) {
        String query = "UPDATE task SET project_id = ?, employee_id = ? WHERE task_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, employeeId);
            stmt.setInt(3, taskId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(int userId) {
        String query = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProject(int projectId) {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteTask(int taskId) {
        String query = "DELETE FROM task WHERE task_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Task> getAllTasks(int empId, int projectId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task WHERE employee_id = ? AND project_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empId);
            stmt.setInt(2, projectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
     
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
   
    
}
