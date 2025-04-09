// DonationDAOImpl.java
package dao;

import java.sql.*;
import entity.CashDonation;
import util.DBConnUtil;

public class DonationDAOImpl implements DonationDAO {
    private Connection conn;

    public DonationDAOImpl() {
        conn = DBConnUtil.getConnection("db.properties");
    }

    @Override
    public void addCashDonation(CashDonation donation) throws SQLException {
        String query = "INSERT INTO donations (donor_name, amount, donation_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, donation.getDonorName());
            ps.setDouble(2, donation.getAmount());
            ps.setDate(3, java.sql.Date.valueOf(donation.getDonationDate()));
            ps.executeUpdate();
        }
    
    }
}
