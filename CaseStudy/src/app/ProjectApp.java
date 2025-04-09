package app;

import java.util.Scanner;
import dao.ProjectRepositoryImpl;
import dao.IProjectRepository;
import entity.Employee;
import entity.Project;
import entity.Task;

public class ProjectApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IProjectRepository dao = new ProjectRepositoryImpl();

        while (true) {
            System.out.println("\n--- Project Management Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Project");
            System.out.println("3. Add Task");
            System.out.println("4. Assign Project to Employee");
            System.out.println("5. Assign Task within Project to Employee");
            System.out.println("6. Delete Employee");
            System.out.println("7. Delete Task");
            System.out.println("8. List All Tasks of an Employee in a Project");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter designation: ");
                        String designation = sc.nextLine();
                        System.out.print("Enter gender (Male/Female/Other): ");
                        String gender = sc.nextLine();
                        System.out.print("Enter salary: ");
                        double salary = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Enter project ID (or 0 for none): ");
                        int projId = sc.nextInt();
                        sc.nextLine();

                        Employee emp = new Employee(name, designation, gender, salary, projId);
                        dao.createEmployee(emp);
                        break;

                    case 2:
                        System.out.print("Enter project name: ");
                        String projName = sc.nextLine();
                        System.out.print("Enter description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter start date (yyyy-mm-dd): ");
                        String startDate = sc.nextLine();
                        System.out.print("Enter status (started/dev/build/test/deployed): ");
                        String status = sc.nextLine();

                        Project project = new Project(projName, desc, startDate, status);
                        dao.createProject(project);
                        break;

                    case 3:
                        System.out.print("Enter task name: ");
                        String taskName = sc.nextLine();
                        System.out.print("Enter project ID: ");
                        int taskProjId = sc.nextInt();
                        System.out.print("Enter employee ID: ");
                        int empId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter task status (Assigned/Started/Completed): ");
                        String taskStatus = sc.nextLine();

                        Task task = new Task(taskName, taskProjId, empId, taskStatus);
                        dao.createTask(task);
                        break;

                    case 4:
                        System.out.print("Enter project ID: ");
                        int pId = sc.nextInt();
                        System.out.print("Enter employee ID: ");
                        int eId = sc.nextInt();
                        dao.assignProjectToEmployee(pId, eId);
                        break;

                    case 5:
                        System.out.print("Enter task ID: ");
                        int tId = sc.nextInt();
                        System.out.print("Enter project ID: ");
                        int tpId = sc.nextInt();
                        System.out.print("Enter employee ID: ");
                        int teId = sc.nextInt();
                        dao.assignTaskInProjectToEmployee(tId, tpId, teId);
                        break;

                    case 6:
                        System.out.print("Enter employee ID to delete: ");
                        int deleteId = sc.nextInt();
                        dao.deleteEmployee(deleteId);
                        break;

                    case 7:
                        System.out.print("Enter task ID to delete: ");
                        int delTaskId = sc.nextInt();
                        dao.deleteTask(delTaskId);
                        break;

                    case 8:
                        System.out.print("Enter employee ID: ");
                        int e = sc.nextInt();
                        System.out.print("Enter project ID: ");
                        int p = sc.nextInt();
                        dao.getAllTasks(e, p);
                        break;

                    case 9:
                        System.out.println("Exiting... Thank you!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
    }
}
