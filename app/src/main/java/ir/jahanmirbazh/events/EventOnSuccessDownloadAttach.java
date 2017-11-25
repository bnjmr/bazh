package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/7/2017.
 */

public class EventOnSuccessDownloadAttach {

    String fileUri;

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public EventOnSuccessDownloadAttach(String fileUri) {

        this.fileUri = fileUri;
    }
}
