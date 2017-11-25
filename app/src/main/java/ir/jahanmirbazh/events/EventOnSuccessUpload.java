package ir.jahanmirbazh.events;

/**
 * Created by FCC on 8/29/2017.
 */

public class EventOnSuccessUpload {

    String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public EventOnSuccessUpload(String filename) {

        this.filename = filename;
    }
}
