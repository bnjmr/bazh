package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/20/2017.
 */

public class EventOnUploadProgress {

    int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public EventOnUploadProgress(int progress) {

        this.progress = progress;
    }
}
