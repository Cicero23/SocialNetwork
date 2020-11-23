package socialnetwork.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDate date;

    public Prietenie(LocalDate date) {
        this.date = date;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDate getDate() {
        return date;
    }

}
