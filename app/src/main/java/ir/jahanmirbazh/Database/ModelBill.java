package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import ir.jahanmirbazh.bazh.V;

@Table(name = "T_Bill")
public class ModelBill extends Model {

    @Expose
    @Column(name = "BillId")
     long BillId ;

    @Expose
    @Column(name = "EstateId")
    int EstateId = V.currentEstate.getEstateId();

    @Expose
    @Column(name = "Charge")
     String Charge ;

    @Expose
    @Column(name = "RegisterDateTime")
     String RegisterDateTime ;

    @Expose
    @Column(name = "DeadlineDate")
     String DeadlineDate ;

    @Expose
    @Column(name = "Status")
     int Status ;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getEstateId() {
        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }

    public long getBillId() {
        return BillId;
    }

    public void setBillId(long billId) {
        BillId = billId;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public String getRegisterDateTime() {
        return RegisterDateTime;
    }

    public void setRegisterDateTime(String registerDateTime) {
        RegisterDateTime = registerDateTime;
    }

    public String getDeadlineDate() {
        return DeadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        DeadlineDate = deadlineDate;
    }
}
