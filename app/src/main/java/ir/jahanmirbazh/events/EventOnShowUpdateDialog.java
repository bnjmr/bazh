package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Gson.getUpdate;

/**
 * Created by FCC on 11/4/2017.
 */

public class EventOnShowUpdateDialog {

    getUpdate update;

    public getUpdate getUpdate() {
        return update;
    }

    public void setUpdate(getUpdate update) {
        this.update = update;
    }

    public EventOnShowUpdateDialog(getUpdate update) {

        this.update = update;
    }
}
