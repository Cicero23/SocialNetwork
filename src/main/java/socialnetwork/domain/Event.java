package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Event extends Entity<Long>{
    private String name;
    private String description;
    private LocalDateTime date;
    private long id_user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public Event(String name, String description, LocalDateTime date,  long id_user) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.id_user = id_user;
    }

    private List<Long> participants;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}
