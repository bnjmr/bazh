package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by FCC on 8/16/2017.
 */

@Table(name = "T_NewsDetail")
public class ModelNewsDetail extends Model {

    @Expose
    @Column(name = "Text")
    String Text;

    @Expose
    @Column(name = "Title")
    String Title;

    @Expose
    @Column(name ="RegisterDateTime")
    String RegisterDateTime;

    @Expose
    @Column(name ="IsSeen")
    boolean IsSeen;

    @Expose
    @Column(name ="NewsId")
    int NewsId;

    public int getNewsId() {
        return NewsId;
    }

    public void setNewsId(int newsId) {
        NewsId = newsId;
    }

    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRegisterDateTime() {
        return RegisterDateTime;
    }

    public void setRegisterDateTime(String registerDateTime) {
        RegisterDateTime = registerDateTime;
    }
}
