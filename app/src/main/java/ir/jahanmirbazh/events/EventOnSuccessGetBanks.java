package ir.jahanmirbazh.events;

import java.util.List;

import ir.jahanmirbazh.Gson.ModelBank;

/**
 * Created by FCC on 9/18/2017.
 */

public class EventOnSuccessGetBanks {

    List<ModelBank> modelBanks;

    public List<ModelBank> getModelBanks() {
        return modelBanks;
    }

    public void setModelBanks(List<ModelBank> modelBanks) {
        this.modelBanks = modelBanks;
    }

    public EventOnSuccessGetBanks(List<ModelBank> modelBanks) {

        this.modelBanks = modelBanks;
    }
}
