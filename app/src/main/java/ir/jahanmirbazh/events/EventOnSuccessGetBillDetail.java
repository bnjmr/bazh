package ir.jahanmirbazh.events;

import ir.jahanmirbazh.Database.ModelBillDetail;

/**
 * Created by FCC on 8/14/2017.
 */

public class EventOnSuccessGetBillDetail
{
    ModelBillDetail billDetail;

    public EventOnSuccessGetBillDetail(ModelBillDetail billDetail) {
        this.billDetail = billDetail;
    }

    public ModelBillDetail getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(ModelBillDetail billDetail) {
        this.billDetail = billDetail;
    }
}
