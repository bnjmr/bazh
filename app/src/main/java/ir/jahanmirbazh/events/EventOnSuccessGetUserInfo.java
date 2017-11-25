package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelUserInfo;
import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 7/30/2017.
 */

public class EventOnSuccessGetUserInfo {

    ModelUserInfo userInfo;

    public EventOnSuccessGetUserInfo(ModelUserInfo userInfo) {
        this.userInfo = userInfo;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.clearT_UserInfo();
        databaseManager.insertUserInfo(userInfo);
        V.userInfo = userInfo;
        databaseManager = null;

    }

    public EventOnSuccessGetUserInfo() {
    }

    public ModelUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ModelUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
