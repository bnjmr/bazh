package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelNewsDetail;

/**
 * Created by FCC on 8/17/2017.
 */

public class EventOnSuccessGetNewsDetail {

    ModelNewsDetail modelNewsDetail;

    public ModelNewsDetail getModelNewsDetail() {
        return modelNewsDetail;
    }

    public void setModelNewsDetail(ModelNewsDetail modelNewsDetail) {
        this.modelNewsDetail = modelNewsDetail;
    }

    public EventOnSuccessGetNewsDetail(ModelNewsDetail modelNewsDetail) {
        this.modelNewsDetail = modelNewsDetail;

        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.deleteNewsDetailByNewsId(modelNewsDetail.getNewsId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        databaseManager.insertNewsDetail(modelNewsDetail);
        databaseManager = null;


    }
}
