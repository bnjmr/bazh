package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 7/30/2017.
 */
@Table(name = "T_Setting")
public class ModelSetting extends Model {

    @Column(name = "isShowWizard")
    private boolean isShowWizard = false;

    @Column(name = "currentEstateId")
    private int currentEstateId = 0;

    @Column(name = "isLogin")
    private boolean isLogin = false;

    public boolean isTokenSend() {
        return isTokenSend;
    }

    public void setTokenSend(boolean tokenSend) {
        isTokenSend = tokenSend;
    }

    @Column(name = "isTokenSend")
    private boolean isTokenSend = false;

    @Column(name = "FileServerUrl")
    String FileServerUrl;


    public String getFileServerUrl() {
        return FileServerUrl;
    }

    public void setFileServerUrl(String fileServerUrl) {
        FileServerUrl = fileServerUrl;
    }

    public int getCurrentEstateId() {
        return currentEstateId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        if (!login) {
            V.setting.setCurrentEstateId(0);
            V.setting.setTokenSend(false);
            V.setting.save();

        }
        isLogin = login;
    }

    public void setCurrentEstateId(int currentEstateId) {
        this.currentEstateId = currentEstateId;
    }

    public boolean isShowWizard() {
        return isShowWizard;
    }

    public void setShowWizard(boolean showWizard) {
        isShowWizard = showWizard;
    }
}
