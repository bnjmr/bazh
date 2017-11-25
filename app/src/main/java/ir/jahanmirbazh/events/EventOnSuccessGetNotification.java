package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Database.ModelNotification;

/**
 * Created by FCC on 8/28/2017.
 */

public class EventOnSuccessGetNotification {

    List<ModelNotification> modelNotifications;

    public List<ModelNotification> getModelNotifications() {
        return modelNotifications;
    }

    public void setModelNotifications(List<ModelNotification> modelNotifications) {
        this.modelNotifications = modelNotifications;
    }

    public EventOnSuccessGetNotification(List<ModelNotification> modelNotifications) {

        this.modelNotifications = modelNotifications;
    }
}
