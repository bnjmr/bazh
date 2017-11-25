package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/11/2017.
 */

public class EventOnOpenAttachmentFile {

    String fileUri;

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public EventOnOpenAttachmentFile(String fileUri) {

        this.fileUri = fileUri;
    }
}
