// DonationDAO.java
package dao;

import java.sql.SQLException;
import entity.CashDonation;

public interface DonationDAO {
    void addCashDonation(CashDonation donation) throws SQLException;
}
