package ir.jahanmirbazh.events;

import android.content.Context;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelEstate;

/**
 * Created by FCC on 8/1/2017.
 */

public class EventOnSuccessGetEstates {

    Context context;
    ModelEstate[] modelEstates;

    public EventOnSuccessGetEstates() {
    }

    public EventOnSuccessGetEstates(Context context, ModelEstate[] modelEstates) {
        this.context = context;
        this.modelEstates = modelEstates;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.clearT_Estate();

        for (ModelEstate estate : modelEstates) {
            databaseManager.insertEstatest(estate);
        }
        databaseManager = null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ModelEstate[] getModelEstates() {
        return modelEstates;
    }

    public void setModelEstates(ModelEstate[] modelEstates) {
        this.modelEstates = modelEstates;
    }
}
