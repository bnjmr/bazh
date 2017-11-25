package ir.jahanmirbazh.Gson;

/**
 * Created by FCC on 9/18/2017.
 */

public class ModelBank {

    int BankId;

    String Name;

    public int getBankId() {
        return BankId;
    }

    public void setBankId(int bankId) {
        BankId = bankId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ModelBank(int bankId, String name) {

        BankId = bankId;
        Name = name;
    }
}
