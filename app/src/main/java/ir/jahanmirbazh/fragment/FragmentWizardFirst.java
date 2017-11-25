package ir.jahanmirbazh.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;

public class FragmentWizardFirst extends Fragment {

    View view;
    @Bind(R.id.imgBuild)
    ImageView imgBuild;

    @Bind(R.id.imgCloudL)
    ImageView imgCloudL;

    @Bind(R.id.imgCloudR)
    ImageView imgCloudR;

    @Bind(R.id.imgLogo)
    ImageView imgLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wizard_first, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        final Animation right2left = AnimationUtils.loadAnimation(getContext(), R.anim.right2left);
        final Animation left2right = AnimationUtils.loadAnimation(getContext(), R.anim.left2right);
        final Animation up2down = AnimationUtils.loadAnimation(getContext(), R.anim.up2down);
        final Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
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


    }
}
