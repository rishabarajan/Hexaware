// PetDAOImpl.java
package dao;

import java.sql.*;
import java.util.*;
import entity.Pet;
import util.DBConnUtil;
import java.sql.SQLException;

public class PetDAOImpl implements PetDAO {
    private Connection conn;

    public PetDAOImpl() {
        conn = DBConnUtil.getConnection("db.properties");
    }

    
    @Override
    public List<Pet> getAllPets() throws SQLException {
        List<Pet> petList = new ArrayList<>();
        String query = "SELECT * FROM pets";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pet pet = new Pet(
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("breed"),
                    rs.getString("type"),
                    rs.getBoolean("availableforadoption"),
                    rs.getObject("ownerid") != null ? rs.getInt("ownerid") : null
                );
                petList.add(pet);
            }
        }
        return petList;
    }


    @Override
    public void addPet(Pet pet) throws SQLException {
        String query = "INSERT INTO pets (name, age, breed, type, availableforadoption, ownerid) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pet.getName());
            ps.setInt(2, pet.getAge());
            ps.setString(3, pet.getBreed());

            // Optional fields – set default if not available
            ps.setString(4, pet.getType() != null ? pet.getType() : "unknown");
            ps.setBoolean(5, true); // assuming newly added pet is available for adoption
            ps.setObject(6, null);  // ownerid is null (not yet adopted)

            ps.executeUpdate();
        }
    }
        public boolean updatePetAdoptionStatus(String name, boolean available, Integer ownerId) throws SQLException {
            String query = "UPDATE pets SET availableforadoption = ?, ownerid = ? WHERE name = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setBoolean(1, available);
                ps.setObject(2, ownerId);
                ps.setString(3, name);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            }
    }
        public boolean deletePetByName(String name) throws SQLException {
            String query = "DELETE FROM pets WHERE name = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, name);
                int rowsDeleted = ps.executeUpdate();
                return rowsDeleted > 0;
            }
        }

}
