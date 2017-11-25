package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelTicket;

/**
 * Created by FCC on 8/23/2017.
 */

public class EventOnSuccessGetTicket {

    List<ModelTicket> modelTickets;
    DatabaseManager databaseManager;

    public List<ModelTicket> getModelTickets() {
        return modelTickets;
    }

    public void setModelTickets(List<ModelTicket> modelTickets) {
        this.modelTickets = modelTickets;
    }

    public EventOnSuccessGetTicket(List<ModelTicket> modelTickets) {

        this.modelTickets = modelTickets;
        databaseManager = new DatabaseManager();
        databaseManager.clearT_Ticket();
        for (ModelTicket modelTicket : modelTickets) {
            databaseManager.insertTicket(modelTicket);
        }
        databaseManager = null;
    }
}
