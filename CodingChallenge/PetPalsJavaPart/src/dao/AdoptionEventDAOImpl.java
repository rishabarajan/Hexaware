// AdoptionEventDAOImpl.java
package dao;

import java.sql.*;
import java.util.*;
import entity.AdoptionEvent;
import entity.Participant;
import util.DBConnUtil;

public class AdoptionEventDAOImpl implements AdoptionEventDAO {
    private Connection conn;

    public AdoptionEventDAOImpl() {
        conn = DBConnUtil.getConnection("db.properties");
    }

    @Override
    public List<AdoptionEvent> getAllEvents() throws SQLException {
        List<AdoptionEvent> eventList = new ArrayList<>();
        String query = "SELECT * FROM adoption_events";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AdoptionEvent event = new AdoptionEvent(); // assume default constructor
                // Set values here
                eventList.add(event);
            }
        }
        return eventList;
    }

    @Override
    public void registerParticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO participants (name, event_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, participant.getParticipantName());
            ps.setInt(2, participant.getEventId());
            ps.executeUpdate();
        }
    }
}
