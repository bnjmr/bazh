package ir.jahanmirbazh.events;

/**
 * Created by FCC on 8/13/2017.
 */

public class EventOnPageChanged {
    int enumCurrentPage;

    public EventOnPageChanged(int enumCurrentPage) {
        this.enumCurrentPage = enumCurrentPage;
    }

    public int getEnumCurrentPage() {
        return enumCurrentPage;
    }

    public void setEnumCurrentPage(int enumCurrentPage) {
        this.enumCurrentPage = enumCurrentPage;
    }
}
