package test;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Task;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectRepositoryTest {

    static ProjectRepositoryImpl repo;

    @BeforeAll
    public static void setup() {
        repo = new ProjectRepositoryImpl();
    }

    @Test
    @Order(1)
    @DisplayName("Create Employee - Success Scenario")
    public void testCreateEmployeeSuccess() {
        Employee emp = new Employee("Rishab", "Developer", "Male", 50000, 1);
         boolean result = repo.createEmployee(emp);
        assertTrue(result, "Employee should be created successfully");
    }

    @Test
    @Order(2)
    @DisplayName(" Create Task - Success Scenario")
    public void testCreateTaskSuccess() {
        Task task = new Task("Unit Testing", 2, 2, "Assigned"); 
        boolean result = repo.createTask(task);
        assertTrue(result, "Task should be created successfully");
    }

    @Test
    @Order(3)
    @DisplayName("Get All Tasks by Employee and Project")
    public void testGetAllTasksByEmployeeAndProject() {
        List<Task> tasks = repo.getAllTasks(2,2); 
        assertNotNull(tasks, "Tasks list should not be null");
        assertTrue(tasks.size() >= 0, "Tasks list should be zero or more");
    }

    @Test
    @Order(4)
    @DisplayName("Search Tasks - Invalid Employee/Project Returns Empty List")
    public void testSearchThrowsExceptionWhenEmployeeOrProjectNotFound() {
        List<Task> tasks = repo.getAllTasks(999, 999); 
        assertEquals(0, tasks.size(), "Should return empty list for non-existing emp/project");
    }
}
