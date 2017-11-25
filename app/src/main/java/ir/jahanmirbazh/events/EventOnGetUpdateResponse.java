package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Gson.getUpdate;

/**
 * Created by FCC on 11/4/2017.
 */

public class EventOnGetUpdateResponse {
    getUpdate updateResponse;
    public EventOnGetUpdateResponse(getUpdate getUpdateResponse) {
        this.updateResponse = getUpdateResponse;
    }
    public getUpdate getUpdateResponse() {
        return updateResponse;
    }
}
