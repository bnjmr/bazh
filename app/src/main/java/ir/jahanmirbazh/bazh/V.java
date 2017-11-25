package ir.jahanmirbazh.bazh;

import android.os.Environment;

import ir.jahanmirbazh.BuildConfig;
import ir.jahanmirbazh.Database.ModelEstate;
import ir.jahanmirbazh.Database.ModelSetting;
import ir.jahanmirbazh.Database.ModelUserInfo;
import ir.jahanmirbazh.enums.EnumCurrentPage;

/**
 * Created by FCC on 7/30/2017.
 */

public class V {


    public static String className = "other";
    public static ModelUserInfo userInfo;

    public static ModelSetting setting;
//        public static String URL = "http://192.168.1.44:54423/";
    public static String URL = "http://service.bazh.ir/";
    public static int currentPage = EnumCurrentPage.PAGE_HOME;
    public static ModelEstate currentEstate;
    public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "ir.jahanmirbazh.bedebestan.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
    public static boolean OnlinePayment = false;
    public static boolean ShowCosts = false;
    public static boolean isUploadingFile = false;

    public static String versionName = BuildConfig.VERSION_NAME;
    public static String StartDate;
    public static String EndDate;


    public static int NewsId;
    public static final String APP_FOLDER = Environment.getExternalStorageDirectory() + "/Bazh/";
    public static final String PATH_TICKET_FILE = APP_FOLDER + "Ticket/";


}
