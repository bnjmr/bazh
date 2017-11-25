package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;

public class ActivitySplash extends AppCompatActivity {

    @Bind(R.id.imgBuild)
    ImageView imgBuild;

    @Bind(R.id.imgCloudL)
    ImageView imgCloudL;

    @Bind(R.id.imgCloudR)
    ImageView imgCloudR;

    @Bind(R.id.imgLogo)
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        final Animation right2left = AnimationUtils.loadAnimation(this, R.anim.right2left);
        final Animation left2right = AnimationUtils.loadAnimation(this, R.anim.left2right);
        final Animation up2down = AnimationUtils.loadAnimation(this, R.anim.up2down);
        final Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fadein);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgCloudL.startAnimation(left2right);
                        imgCloudR.startAnimation(right2left);
                        imgLogo.startAnimation(up2down);

                        imgLogo.setVisibility(View.VISIBLE);
                        imgCloudL.setVisibility(View.VISIBLE);
                        imgCloudR.setVisibility(View.VISIBLE);

                    }
                },500);
                imgBuild.startAnimation(fade_in);

                imgBuild.setVisibility(View.VISIBLE);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplash.this, ActivityFirstPage.class));

            }
        },2200);



    }
}
