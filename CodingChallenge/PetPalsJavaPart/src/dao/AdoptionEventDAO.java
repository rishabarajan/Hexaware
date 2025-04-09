// AdoptionEventDAO.java
package dao;

import java.sql.SQLException;
import java.util.List;
import entity.AdoptionEvent;
import entity.Participant;

public interface AdoptionEventDAO {
    List<AdoptionEvent> getAllEvents() throws SQLException;
    void registerParticipant(Participant participant) throws SQLException;
}
