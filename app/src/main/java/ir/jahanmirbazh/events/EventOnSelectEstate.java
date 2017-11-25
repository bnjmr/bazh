package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Database.ModelEstate;

/**
 * Created by FCC on 8/2/2017.
 */

public class EventOnSelectEstate {

    ModelEstate modelEstate;

    public EventOnSelectEstate(ModelEstate modelEstate) {
        this.modelEstate = modelEstate;
    }

    public ModelEstate getModelEstate() {
        return modelEstate;
    }

    public void setModelEstate(ModelEstate modelEstate) {
        this.modelEstate = modelEstate;
    }
}
