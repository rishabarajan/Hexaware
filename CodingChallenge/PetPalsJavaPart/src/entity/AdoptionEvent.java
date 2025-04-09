package entity;

import java.util.ArrayList;
import java.util.List;

public class AdoptionEvent {
    private List<IAdoptable> participants;

    public AdoptionEvent() {
        this.participants = new ArrayList<>();
    }

    public void registerParticipant(IAdoptable participant) {
        participants.add(participant);
        System.out.println("Participant registered: " + participant);
    }

    public void hostEvent() {
        System.out.println("Adoption Event Hosted with Participants:");
        for (IAdoptable participant : participants) {
            participant.adopt();
        }
    }

    public List<IAdoptable> getParticipants() {
        return participants;
    }
}
