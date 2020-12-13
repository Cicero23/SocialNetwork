package socialnetwork.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActionDTO {
    private String desc;
    private LocalDate date;


    public ActionDTO(String desc, LocalDate date) {
        this.desc = desc;
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public LocalDate getDate() {
        return date;
    }
}
