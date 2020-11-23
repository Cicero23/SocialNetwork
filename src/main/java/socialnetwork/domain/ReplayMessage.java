package socialnetwork.domain;


import java.time.LocalDateTime;

public class ReplayMessage extends Message {
    private Message msg;

    public ReplayMessage(long from, LocalDateTime data, String mesaj, Message msg) {
        super(from, data, mesaj);
        this.msg = msg;
    }

    public ReplayMessage(long from, LocalDateTime data, String mesaj, long id) {
        super(from,data,mesaj);
        msg = new Message();
        msg.setId(id);
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
