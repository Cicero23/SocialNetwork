package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message extends  Entity<Long> {
    long from;
    LocalDateTime data;
    String mesaj;
    List<Long> to;

    public void setTo(List<Long> to) {
        this.to = to;
    }

    public Message(long from, LocalDateTime data, String mesaj) {

        this.from = from;
        this.data = data;
        this.mesaj = mesaj;
        this.to = new ArrayList<Long>();
    }

    public Message() {
        this.data = null;
        this.mesaj = null;
        this.to = null;
    }


    public List<Long> getTo() {
        return to;
    }



    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
}
