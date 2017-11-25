package ir.jahanmirbazh.events;

import android.content.Context;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelBill;

/**
 * Created by FCC on 8/13/2017.
 */

public class EventOnSuccessGetBill {

    Context context;
    List<ModelBill> modelBills;
    boolean busy;

    public EventOnSuccessGetBill(Context context, List<ModelBill> modelBills) {
        this.context = context;
        this.modelBills = modelBills;
        busy = false;
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.clearT_Bill();
        if (!busy) {
            busy = true;
            for (ModelBill modelBill : modelBills) {
                databaseManager.insertBill(modelBill);
            }
            busy = false;
        }

        databaseManager = null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ModelBill> getModelBills() {
        return modelBills;
    }

    public void setModelBills(List<ModelBill> modelBills) {
        this.modelBills = modelBills;
    }
}
