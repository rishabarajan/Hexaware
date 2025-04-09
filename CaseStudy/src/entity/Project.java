package entity;



public class Project {
    private int id;
    private String projectName;
    private String description;
    private String startDate;
    private String status;

    public Project() {}

    public Project(String projectName, String description, String startDate, String status) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.status = status;
    }


    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
