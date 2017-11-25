package ir.jahanmirbazh.Database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by FCC on 8/14/2017.
 */

@Table(name = "T_BillDetail")
public class ModelBillDetail {

    @Expose
    @Column(name = "StartDate")
    String StartDate = "";

    @Expose
    @Column(name = "EndDate")
    String EndDate = "";

    @Expose
    @Column(name = "PaymentDateTime")
    String PaymentDateTime = "";

    @Expose
    @Column(name = "CloseDate")
    String CloseDate = "";

    @Expose
    @Column(name = "Amount")
    int Amount = 0;

    @Expose
    @Column(name = "Fine")
    int Fine = 0;

    @Expose
    @Column(name = "Debt")
    int Debt = 0;

    @Expose
    @Column(name = "Discount")
    int Discount = 0;

    @Expose
    @Column(name = "TotalAmount")
    int TotalAmount = 0;

    @Expose
    @Column(name = "Status")
    int Status = 0; // 1 New , 2 Paid , 3 Closed , 4 Removed

    @Expose
    @Column(name = "OnlinePayment")
    boolean OnlinePayment = false;

    @Expose
    @Column(name = "EstateCredit")
    int EstateCredit = 0;

    @Expose
    @Column(name = "Charge")
    String Charge = "";

    @Expose
    @Column(name = "RegisterDateTime")
    String RegisterDateTime = "";

    @Expose
    @Column(name = "DeadlineDate")
    String DeadlineDate = "";

    @Expose
    @Column(name = "Payer")
    String Payer;

    @Expose
    @Column(name = "PaymentType")
    String PaymentType;

    @Expose
    @Column(name = "BillPayment")
    boolean BillPayment;

    @Expose
    @Column
    String Description = "";


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public boolean isBillPayment() {
        return BillPayment;
    }

    public void setBillPayment(boolean billPayment) {
        BillPayment = billPayment;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String payer) {
        Payer = payer;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
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

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPaymentDateTime() {
        return PaymentDateTime;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        PaymentDateTime = paymentDateTime;
    }

    public String getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(String closeDate) {
        CloseDate = closeDate;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getFine() {
        return Fine;
    }

    public void setFine(int fine) {
        Fine = fine;
    }

    public int getDebt() {
        return Debt;
    }

    public void setDebt(int debt) {
        Debt = debt;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isOnlinePayment() {
        return OnlinePayment;
    }

    public void setOnlinePayment(boolean onlinePayment) {
        OnlinePayment = onlinePayment;
    }

    public int getEstateCredit() {
        return EstateCredit;
    }

    public void setEstateCredit(int estateCredit) {
        EstateCredit = estateCredit;
    }
}
