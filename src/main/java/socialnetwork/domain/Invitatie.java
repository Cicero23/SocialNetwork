package socialnetwork.domain;

import java.time.LocalDate;

public class Invitatie extends Entity<Long> {
    long id_from;
    long id_to;
    LocalDate data;
    int stare;

    public Invitatie(long id_from, long id_to, LocalDate data, int stare) {
        this.id_from = id_from;
        this.id_to = id_to;
        this.data = data;
        this.stare = stare;
    }

    public long getId_from() {
        return id_from;
    }

    public void setId_from(long id_from) {
        this.id_from = id_from;
    }

    public long getId_to() {
        return id_to;
    }

    public void setId_to(long id_to) {
        this.id_to = id_to;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getStare() {
        return stare;
    }

    public void setStare(int stare) {
        this.stare = stare;
    }

    @Override
    public String toString() {
        String s = id_from + " -> " + id_to  +  " " + data.toString();
        if(stare == 1)
            s = s + " pending";
        else if(stare == 2)
            s = s + " rejected";
        else
            s= s + " approved";
        return  s;
    }
}
