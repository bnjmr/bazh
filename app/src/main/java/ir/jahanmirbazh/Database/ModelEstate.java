package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import static ir.jahanmirbazh.bazh.V.ShowCosts;

/**
 * Created by FCC on 8/1/2017.
 */

@Table(name = "T_Estate")
public class ModelEstate extends Model {

    @Expose
    @Column(name = "EstateId")
    int EstateId;

    @Expose
    @Column(name = "Name")
    String Name;

    @Expose
    @Column(name = "Debt")
    int Debt;

    @Expose
    @Column(name = "Credit")
    int Credit;

    @Expose
    @Column(name = "OwnerType")
    String OwnerType;

    @Expose
    @Column(name = "NotificationsCount")
    int NotificationsCount;

    @Expose
    @Column(name = "BillsCount")
    int BillsCount;

    @Expose
    @Column(name = "TicketsCount")
    int TicketsCount;

    @Expose
    @Column(name = "NewsCount")
    int NewsCount;

    public int getNotificationsCount() {
        return NotificationsCount;
    }

    public void setNotificationsCount(int notificationsCount) {
        NotificationsCount = notificationsCount;
    }

    public int getBillsCount() {
        return BillsCount;
    }

    public void setBillsCount(int billsCount) {
        BillsCount = billsCount;
    }

    public int getTicketsCount() {
        return TicketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        TicketsCount = ticketsCount;
    }

    public int getNewsCount() {
        return NewsCount;
    }

    public void setNewsCount(int newsCount) {
        NewsCount = newsCount;
    }

    public ModelEstate() {
    }

    public ModelEstate(int estateId, String name, int debt, int credit, String ownerType, boolean showCost) {
        EstateId = estateId;
        Name = name;
        Debt = debt;
        Credit = credit;
        OwnerType = ownerType;
        ShowCosts = showCost;
    }

    public int getEstateId() {
        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDebt() {
        return Debt;
    }

    public void setDebt(int debt) {
        Debt = debt;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public String getOwnerType() {
        return OwnerType;
    }

    public void setOwnerType(String ownerType) {
        OwnerType = ownerType;
    }

}
