package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/19/2017.
 */

public class EventOnSuccessGetPayUrl {

    String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public EventOnSuccessGetPayUrl(String url) {

        Url = url;
    }
}
