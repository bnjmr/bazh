package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.classes.WebService;

import static ir.jahanmirbazh.bazh.V.setting;
import static ir.jahanmirbazh.bazh.V.userInfo;

/**
 * Created by FCC on 7/30/2017.
 */

public class ActivityStarter extends AppCompatActivity {

    DatabaseManager db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        Bundle bundle = getIntent().getExtras();


        db = new DatabaseManager(ActivityStarter.this);
//        configDatabase();
        db.configDb();
//        ActiveAndroid.initialize(this);

        userInfo = db.readUserInfo();
        setting = db.readSetting();
        U.updateFirebaseToken(this);

        if (userInfo != null && setting != null && setting.isLogin()) {
            Logger.d("onCreate", userInfo.getName() + " isLogin");

            startActivity(new Intent(ActivityStarter.this, ActivitySplash.class));


            finish();
        } else {
            WebService.sendFirebaseToken(this, 2);
            if (setting == null) {
                startActivity(new Intent(ActivityStarter.this, ActivityWizard.class));
                finish();
            } else {
                if (setting.isShowWizard()) {
                    startActivity(new Intent(ActivityStarter.this, ActivityLogin.class));
                    finish();
                } else {
                    startActivity(new Intent(ActivityStarter.this, ActivityWizard.class));
                    finish();
                }
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
