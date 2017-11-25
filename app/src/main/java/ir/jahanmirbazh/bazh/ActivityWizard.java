package ir.jahanmirbazh.bazh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterViewPagerWizard;

public class ActivityWizard extends AppCompatActivity {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewpagertab;


    AdapterViewPagerWizard adapterViewPagerWizard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        ButterKnife.bind(this);


        adapterViewPagerWizard = new AdapterViewPagerWizard(getSupportFragmentManager(),ActivityWizard.this);
        viewpager.setAdapter(adapterViewPagerWizard);
        viewpagertab.setViewPager(viewpager);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
