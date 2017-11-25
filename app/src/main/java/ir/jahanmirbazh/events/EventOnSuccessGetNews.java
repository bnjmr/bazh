package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelNews;

/**
 * Created by FCC on 8/16/2017.
 */

public class EventOnSuccessGetNews {

    List<ModelNews> modelNewses;

    public List<ModelNews> getModelNewses() {
        return modelNewses;
    }

    public void setModelNewses(List<ModelNews> modelNewses) {
        this.modelNewses = modelNewses;
    }

    public EventOnSuccessGetNews(List<ModelNews> modelNewses) {
        this.modelNewses = modelNewses;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.clearT_News();

        for (ModelNews modelNews : modelNewses) {
            databaseManager.insertNews(modelNews);
        }
        databaseManager = null;
    }
}
