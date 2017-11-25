package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelPayment;

/**
 * Created by FCC on 8/16/2017.
 */

public class EventOnSuccessGetPayments {

    List<ModelPayment> modelPayments;

    public List<ModelPayment> getModelPayments() {
        return modelPayments;


    }

    public void setModelPayments(List<ModelPayment> modelPayments) {
        this.modelPayments = modelPayments;
    }

    public EventOnSuccessGetPayments(List<ModelPayment> modelPayments) {
        this.modelPayments = modelPayments;


        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.clearT_Payment();

        for (ModelPayment payment : modelPayments) {
            databaseManager.insertPayment(payment);
        }
        databaseManager = null;

    }
}
