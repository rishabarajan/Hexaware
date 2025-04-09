package entity;

public class Participant {
    private int participantId;
    private String participantName;
    private String participantType; // e.g., "Shelter", "Adopter"
    private int eventId;

    public Participant() {}

    public Participant(int participantId, String participantName, String participantType, int eventId) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.participantType = participantType;
        this.eventId = eventId;
    }

    // Getters and Setters
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Participant [participantId=" + participantId + ", participantName=" + participantName
                + ", participantType=" + participantType + ", eventId=" + eventId + "]";
    }
}
