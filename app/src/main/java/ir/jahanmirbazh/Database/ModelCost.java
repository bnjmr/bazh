package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 8/22/2017.
 */


@Table(name = "T_Cost")
public class ModelCost extends Model {

    @Expose
    @Column(name = "CostId  ")
    int CostId;

    @Column(name = "EstateId")
    int EstateId = V.currentEstate.getEstateId();

    @Expose
    @Column(name = "Title")
    String Title;

    @Expose
    @Column(name = "CostDate")
    String CostDate;

    @Expose
    @Column(name = "Amount")
    int Amount;

    public int getEstateId() {
        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }

    public int getCostId() {
        return CostId;
    }

    public void setCostId(int costId) {
        CostId = costId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCostDate() {
        return CostDate;
    }

    public void setCostDate(String costDate) {
        CostDate = costDate;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
