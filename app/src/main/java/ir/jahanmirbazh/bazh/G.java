package ir.jahanmirbazh.bazh;

import android.support.multidex.MultiDexApplication;

import com.alexbbb.uploadservice.UploadService;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import ir.jahanmirbazh.Database.DatabaseManager;

/**
 * Created by FCC on 7/30/2017.
 */

public class G extends MultiDexApplication {



    public static Gson gson;
//    public static Uploader uploader = new Uploader();


    private DatabaseManager databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        ActiveAndroid.initialize(this);
        UploadService.NAMESPACE = "ir.jahanmirbazh.bazh";


        buildGSON();

        initDateForLoadCost();

    }

    private void initDateForLoadCost() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        int day0 = calendar.get(Calendar.DAY_OF_MONTH);
        int m0 = calendar.get(Calendar.MONTH) + 1;
        int y0 = calendar.get(Calendar.YEAR);

        V.StartDate = df.format(getDate(y0, m0, day0));
        V.EndDate = df.format(getDate(y0, m0, day0));
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void buildGSON() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        gson = builder.create();
    }
}
