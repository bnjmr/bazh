package ir.jahanmirbazh.events;

/**
 * Created by FCC on 9/7/2017.
 */

public class EventOnStartDownloadAttach {
    String url;
    int DetailId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDetailId() {
        return DetailId;
    }

    public void setDetailId(int detailId) {
        DetailId = detailId;
    }

    public EventOnStartDownloadAttach(String url, int detailId) {

        this.url = url;
        DetailId = detailId;
    }
}
