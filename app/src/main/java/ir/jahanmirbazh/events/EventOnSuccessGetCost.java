package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelCost;

/**
 * Created by FCC on 8/22/2017.
 */

public class EventOnSuccessGetCost {

    List<ModelCost> modelCosts;
    DatabaseManager databaseManager;

    public List<ModelCost> getModelCosts() {
        return modelCosts;
    }

    public void setModelCosts(List<ModelCost> modelCosts) {
        this.modelCosts = modelCosts;
    }

    public EventOnSuccessGetCost(List<ModelCost> modelCosts) {

        this.modelCosts = modelCosts;
        databaseManager = new DatabaseManager();
        databaseManager.clearT_Cost();
        for (ModelCost modelCost : modelCosts) {
            databaseManager.insertCost(modelCost);
        }
        databaseManager = null;
    }
}
