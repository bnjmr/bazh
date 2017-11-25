package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 8/15/2017.
 */

@Table(name = "T_Payment ")
public class ModelPayment extends Model {

    @Expose
    @Column(name = "PaymentId")
    int PaymentId;

    @Expose
    @Column(name = "Amount")
    int Amount;

    @Expose
    @Column(name = "RegisterDateTime")
    String RegisterDateTime;

    @Expose
    @Column(name = "PaymentType")
    int PaymentType;

    @Expose
    @Column(name = "Bank")
    String Bank;

    @Expose
    @Column(name = "Payer")
    String Payer;

    @Column(name = "EstateId")
    int EstateId= V.currentEstate.getEstateId();

    @Expose
    @Column(name = "Description")
    String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getEstateId() {
        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }

    public int getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(int paymentId) {
        PaymentId = paymentId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getRegisterDateTime() {
        return RegisterDateTime;
    }

    public void setRegisterDateTime(String registerDateTime) {
        RegisterDateTime = registerDateTime;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String payer) {
        Payer = payer;
    }
}
