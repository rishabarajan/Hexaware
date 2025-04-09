package main;

import dao.PetDAO;
import dao.PetDAOImpl;
import entity.Pet;

import java.util.List;
import java.util.Scanner;

public class PetPalsApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PetDAO petDAO = new PetDAOImpl();

        while (true) {
            System.out.println("\n=== Pet Pals Menu ===");
            System.out.println("1. Add Pet");
            System.out.println("2. View All Pets");
            System.out.println("3. Update Pet Adoption Status");
            System.out.println("4. Delete Pet by Name");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter pet name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter pet age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter pet breed: ");
                        String breed = scanner.nextLine();
                        System.out.print("Enter pet type (e.g., Dog, Cat): ");
                        String type = scanner.nextLine();

                        boolean availableForAdoption = true;
                        Integer ownerId = null;

                        Pet newPet = new Pet(name, age, breed, type, availableForAdoption, ownerId);
                        petDAO.addPet(newPet);
                        System.out.println("Pet added successfully!");
                        break;

                    case 2:
                        List<Pet> petList = petDAO.getAllPets();
                        if (petList.isEmpty()) {
                            System.out.println("No pets found in the shelter.");
                        } else {
                            System.out.println("\nAvailable Pets:");
                            System.out.printf("%-15s %-5s %-15s %-10s %-10s %-10s%n",
                                    "Name", "Age", "Breed", "Type", "Available", "Owner ID");
                            System.out.println("--------------------------------------------------------------------------");

                            for (Pet pet : petList) {
                                System.out.printf("%-15s %-5d %-15s %-10s %-10s %-10s%n",
                                        pet.getName(),
                                        pet.getAge(),
                                        pet.getBreed(),
                                        pet.getType(),
                                        pet.isAvailableForAdoption() ? "Yes" : "No",
                                        pet.getOwnerId() != null ? pet.getOwnerId() : "N/A");
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter pet name to update adoption status: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Is the pet adopted? (true/false): ");
                        boolean isAdopted = scanner.nextBoolean();
                        scanner.nextLine();
                        Integer newOwnerId = null;
                        if (isAdopted) {
                            System.out.print("Enter new owner ID: ");
                            newOwnerId = scanner.nextInt();
                            scanner.nextLine();
                        }

                        boolean updated = petDAO.updatePetAdoptionStatus(updateName, !isAdopted, newOwnerId);
                        if (updated) {
                            System.out.println("Pet adoption status updated successfully.");
                        } else {
                            System.out.println("Pet not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter pet name to delete: ");
                        String deleteName = scanner.nextLine();
                        boolean deleted = petDAO.deletePetByName(deleteName);
                        if (deleted) {
                            System.out.println("Pet deleted successfully.");
                        } else {
                            System.out.println("Pet not found.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting... Thank you!");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
