package entity;

public class Pet {
    private String name;
    private int age;
    private String breed;

   
    private String type;
    private boolean availableForAdoption;
    private Integer ownerId; 

   
    public Pet(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

   
    public Pet(String name, int age, String breed, String type, boolean availableForAdoption, Integer ownerId) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.type = type;
        this.availableForAdoption = availableForAdoption;
        this.ownerId = ownerId;
    }

   
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isAvailableForAdoption() { return availableForAdoption; }
    public void setAvailableForAdoption(boolean availableForAdoption) { this.availableForAdoption = availableForAdoption; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Breed: " + breed +
               ", Type: " + type + ", Available: " + availableForAdoption +
               ", Owner ID: " + (ownerId != null ? ownerId : "None");
    }
}
