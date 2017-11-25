package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/3/2017.
 */

public class EventOnSuccessGetTicketReply {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventOnSuccessGetTicketReply(String message) {

        this.message = message;
    }
}
