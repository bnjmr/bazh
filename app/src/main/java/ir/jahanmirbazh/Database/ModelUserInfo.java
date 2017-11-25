package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by FCC on 7/30/2017.
 */
@Table(name = "T_UserInfo")
public class ModelUserInfo extends Model {


    @Column(name = "Token")
    private String Token = "";

    @Column(name = "Name")
    private String Name = "";

    @Column(name = "Mobile")
    private String Mobile = "";

    @Column(name = "oldToken")
    String oldToken = "";

    @Column(name = "newToken")
    String newToken = "";

    public String getOldToken() {
        return oldToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getToken() {
        if (Token != null && !Token.equals("")) {
            return Token;
        } else {
            return "";
        }
    }

    public String getMobile() {

        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Mobile;
    }

    public void setPhone(String Mobile) {
        Mobile = Mobile;
    }

    public void setToken(String token) {
        Token = token;
    }
}
