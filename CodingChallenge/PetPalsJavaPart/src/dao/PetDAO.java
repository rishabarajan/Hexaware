package dao;

import java.sql.SQLException;
import java.util.List;
import entity.Pet;

public interface PetDAO {
    List<Pet> getAllPets() throws SQLException;
    void addPet(Pet pet) throws SQLException;
    boolean updatePetAdoptionStatus(String name, boolean available, Integer ownerId) throws SQLException;
    boolean deletePetByName(String name) throws SQLException;
}
