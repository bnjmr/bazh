package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by FCC on 8/16/2017.
 */

@Table(name = "T_News")
public class ModelNews extends Model {

    @Expose
    @Column(name = "NewsId")
    private
    int NewsId;

    @Expose
    @Column(name = "EstateId")
    int EstateId;

    @Expose
    @Column(name = "Title")
    private
    String Title;

    @Expose
    @Column(name = "StartDate")
    private String StartDate;

    @Expose
    @Column(name = "IsSeen")
    private
    boolean IsSeen;


    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }

    public int getNewsId() {
        return NewsId;
    }

    public void setNewsId(int newsId) {
        NewsId = newsId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRegisterDateTime() {
        return StartDate;
    }

    public void setRegisterDateTime(String StartDate) {
        StartDate = StartDate;
    }

    public int getEstateId() {
        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }
}
