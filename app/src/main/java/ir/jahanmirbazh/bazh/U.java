package ir.jahanmirbazh.bazh;

import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.alexbbb.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelEstate;
import ir.jahanmirbazh.classes.PersianDate;
import ir.jahanmirbazh.enums.EnumFileType;

/**
 * Created by FCC on 7/30/2017.
 */

public class U {

    public static void tintWidget(View view, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(view.getBackground());
        DrawableCompat.setTint(wrappedDrawable.mutate(), view.getContext().getResources().getColor(color));
        view.setBackgroundDrawable(wrappedDrawable);
    }

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String convertDateToPersianDateShowInMainPage(Date date) {
        Calendar calender = DateToCalendar(date);
        int mYear = calender.get(Calendar.YEAR);
        int mMonth = calender.get(Calendar.MONTH) + 1;
        int mDay = calender.get(Calendar.DAY_OF_MONTH);
        int DayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int min = calender.get(Calendar.MINUTE);
        PersianDate jCal = new PersianDate(); // ایجاد یک نمونه از کلاس تبدیل تاریخ
        jCal.GregorianToPersian(mYear, mMonth, mDay); // دادن تاریخ کنونی سیستم به متد تبدیل تاریخ میلادی به شنسی
        int year = jCal.getYear(); // سال شمسی
        int month = jCal.getMonth(); // ماه شمسی
        int day = jCal.getDay(); // روز شمسی

//        String StringDate = "امروز ";
        String StringDate = "";

        switch (DayOfWeek) {
            case 7:
                StringDate += "شنبه";
                break;
            case 6:
                StringDate += "جمعه";
                break;
            case 5:
                StringDate += "پنج شنبه";
                break;
            case 4:
                StringDate += "چهارشنبه";
                break;
            case 3:
                StringDate += "سه شنبه";
                break;
            case 2:
                StringDate += "دوشنبه";
                break;
            case 1:
                StringDate += "یک شنبه";
                break;
        }


        StringDate += " " + day + " ";

        switch (month) {
            case 1:
                StringDate += "فروردین";
                break;
            case 2:
                StringDate += "اردیبهشت";
                break;
            case 3:
                StringDate += "خرداد";
                break;
            case 4:
                StringDate += "تیر";
                break;
            case 5:
                StringDate += "مرداد";
                break;
            case 6:
                StringDate += "شهریور";
                break;
            case 7:
                StringDate += "مهر";
                break;
            case 8:
                StringDate += "آبان";
                break;
            case 9:
                StringDate += "آذر";
                break;
            case 10:
                StringDate += "دی";
                break;
            case 11:
                StringDate += "بهمن";
                break;
            case 12:
                StringDate += "اسفند";
                break;
        }

        StringDate += " " + year;

        return StringDate;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String ConvertStringSqlDateTimeToPersianStringDate(String datetime) {
        try {
            String strDate = datetime;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dateConverted = dateFormat.parse(strDate);
            String date = convertDateToPersianDate(dateConverted);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String ConvertStringSqlDateTimeToPersianStringDateWithoutTime(String datetime) {
        try {
            String strDate = datetime;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dateConverted = dateFormat.parse(strDate);
            String date = convertDateToPersianDateWithoutTime(dateConverted);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateToPersianDate(Date date) {
        Calendar calender = DateToCalendar(date);
        int mYear = calender.get(Calendar.YEAR);
        int mMonth = calender.get(Calendar.MONTH) + 1;
        int mDay = calender.get(Calendar.DAY_OF_MONTH);
        int DayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int min = calender.get(Calendar.MINUTE);
        PersianDate jCal = new PersianDate(); // ایجاد یک نمونه از کلاس تبدیل تاریخ
        jCal.GregorianToPersian(mYear, mMonth, mDay); // دادن تاریخ کنونی سیستم به متد تبدیل تاریخ میلادی به شنسی
        int year = jCal.getYear(); // سال شمسی
        int month = jCal.getMonth(); // ماه شمسی
        int day = jCal.getDay(); // روز شمسی
        return String.valueOf(year).substring(2) + "/" + addZeroBeforeInteger(month) + "/" + addZeroBeforeInteger(day) + "  " + addZeroBeforeInteger(hour) + ":" + addZeroBeforeInteger(min);
    }

    public static String convertDateToPersianDateWithoutTime(Date date) {
        Calendar calender = DateToCalendar(date);
        int mYear = calender.get(Calendar.YEAR);
        int mMonth = calender.get(Calendar.MONTH) + 1;
        int mDay = calender.get(Calendar.DAY_OF_MONTH);
        int DayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int min = calender.get(Calendar.MINUTE);
        PersianDate jCal = new PersianDate(); // ایجاد یک نمونه از کلاس تبدیل تاریخ
        jCal.GregorianToPersian(mYear, mMonth, mDay); // دادن تاریخ کنونی سیستم به متد تبدیل تاریخ میلادی به شنسی
        int year = jCal.getYear(); // سال شمسی
        int month = jCal.getMonth(); // ماه شمسی
        int day = jCal.getDay(); // روز شمسی
        return String.valueOf(year).substring(2) + "/" + addZeroBeforeInteger(month) + "/" + addZeroBeforeInteger(day);
    }

    public static String addZeroBeforeInteger(int strInt) {
        if (strInt < 10)
            return "0" + strInt;
        return "" + strInt;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        return height;
    }

    public static void cancelNotification(int notifyId, Context context) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static int getTypeOfFile(String fileName) {
        String[] f = fileName.split("\\.");
        String extension = f[f.length - 1].toLowerCase();
        switch (extension) {
            case "png":
            case "jpg":
            case "gif":
            case "tif":
                return EnumFileType.IMAGE;
            case "avi":
            case "mov":
            case "mp4":
            case "3gp":
            case "mkv":
                return EnumFileType.VIDEO;
            case "mp3":
            case "amr":
            case "ogg":
                return EnumFileType.MUSIC;
            default:
                return EnumFileType.OTHER;
        }
    }

    public static String getFolderOfFile(String fileName) {
        String[] f = fileName.split("\\.");
        String extension = (f[f.length - 1]).toLowerCase();
        switch (extension) {
            case "png":
            case "jpg":
            case "gif":
            case "tif":
            case "jpeg":
                return "Image";
            case "avi":
            case "mov":
            case "mp4":
            case "3gp":
            case "mkv":
            case "dat":
                return "Video";
            case "mp3":
            case "amr":
                return "Music";
            default:
                return "Other";
        }
    }

    public static String getFileName(String fileName) {
        if (fileName.contains("/")) {
            String[] f = fileName.split("/");
            return f[f.length - 1];
        }
        return fileName;
    }

    public static String getFolderOfFile(int type) {
        switch (type) {
            case EnumFileType.IMAGE:
                return "Image";
            case EnumFileType.MUSIC:
                return "Music";
            case EnumFileType.VIDEO:
                return "Video";
            case EnumFileType.OTHER:
                return "Other";
        }
        return "Other";
    }

    public static void uploadMultipart(final Context context, String fileAddress, String uploadId, String path, boolean grouping, String URL) {

        String[] address = fileAddress.split("/");

        final MultipartUploadRequest request =
                new MultipartUploadRequest(context,
                        uploadId,
                        URL);

        request.addFileToUpload(fileAddress,
                "file",
                address[address.length - 1],
                "*/*");
        request.addParameter("path", path);
        if (grouping)
            request.addParameter("grouping", "true");

        request.setNotificationConfig(android.R.drawable.ic_menu_upload,
                "",
                "فایل در حال آپلود است",
                "فایل با موفقیت آپلود شد",
                "آپلود فایل ناموفق بود",
                true);
        //request.setNotificationClickIntent(new Intent(context, ActivityMain.class));
        request.setMaxRetries(2);

        try {
            request.startUpload();
        } catch (Exception exc) {
            Log.e("LOG", exc.getLocalizedMessage(), exc);
        }
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDate;
        formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void copyFile(final String inputPath, final String inputFile, final String outputPath, final String newName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                OutputStream out = null;
                try {

                    //create output directory if it doesn't exist
                    File dir = new File(outputPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }


                    in = new FileInputStream(inputPath + inputFile);
                    out = new FileOutputStream(outputPath + newName);

                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    in = null;

                    // write the output file (You have now copied the file)
                    out.flush();
                    out.close();
                    out = null;
                } catch (FileNotFoundException fnfe1) {
                    Log.e("tag", fnfe1.getMessage());
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }).start();

    }

    public static boolean isValidFormat(String format) {
        String[] vFormats = {"rar", "zip", "xls", "xlsx", "pdf", "doc", "docx", "jpg", "jpeg", "bmp", "png", "gif", "txt", "html", "htm", "css", "7zip", "mp3", "ogg", "wav", "wma", "7z", "pps", "ppt", "pptx", "xlr", "ods", "odp", "3gp", "avi", "flv", "h264", "mkv", "mov", "mp4", "mpg", "mpeg", "swf", "wmv", "odt", "wks", "wps"};
        for (int i = 0; i < vFormats.length; i++) {
            if (vFormats[i].equals(format)) {
                return true;
            }
        }

        return false;
    }

    public static void updateFirebaseToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "firebasToken", Context.MODE_PRIVATE);

        String defaultValue = "";
        String refreshedToken = sharedPref.getString("firebasToken", defaultValue);

        try {
            if (!refreshedToken.equals("")) {
                if (V.userInfo != null ) {
                    if (!V.userInfo.getNewToken().equals(refreshedToken)) {
                        V.userInfo.setOldToken(V.userInfo.getNewToken());
                        V.userInfo.setNewToken(refreshedToken);
                        V.userInfo.save();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static ModelEstate getCurrentEstate(Context context) {
        DatabaseManager databaseManager = new DatabaseManager(context);

        ModelEstate estate = new ModelEstate();
        if (V.setting != null && V.setting.getCurrentEstateId() != 0) {
            estate = databaseManager.selectEstatesByEstateId(V.setting.getCurrentEstateId());
            if (estate == null) {
                estate = databaseManager.selectEstatesByEstateId(databaseManager.selectEstates().get(0).getEstateId());
            }
        } else if (V.setting != null) {
            estate = databaseManager.selectEstatesByEstateId(databaseManager.selectEstates().get(0).getEstateId());
        }
        return estate;
    }

    public static String getDate(int minus) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        cal.add(Calendar.DATE, minus);
        String formattedDate;
        formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

}
