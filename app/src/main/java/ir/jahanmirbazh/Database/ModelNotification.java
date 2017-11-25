package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by FCC on 8/28/2017.
 */

@Table(name = "T_Notification")
public class ModelNotification extends Model {

    @Expose
    @Column(name = "RegisterDateTime")
    String RegisterDateTime;

    @Expose
    @Column(name = "Text")
    String Text;

    @Expose
    @Column(name = "IsSeen")
    boolean IsSeen;

    public String getRegisterDateTime() {
        return RegisterDateTime;
    }

    public void setRegisterDateTime(String registerDateTime) {
        RegisterDateTime = registerDateTime;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }
}
