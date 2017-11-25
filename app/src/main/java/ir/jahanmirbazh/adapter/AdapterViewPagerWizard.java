package ir.jahanmirbazh.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ir.jahanmirbazh.R;
import ir.jahanmirbazh.fragment.FragmentShowGuide;
import ir.jahanmirbazh.fragment.FragmentWizardContactUs;
import ir.jahanmirbazh.fragment.FragmentWizardFirst;
import ir.jahanmirbazh.fragment.FragmentWizardLast;

public class AdapterViewPagerWizard extends FragmentStatePagerAdapter {
    Context context;

    public AdapterViewPagerWizard(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentWizardFirst();
            case 1:
                return FragmentShowGuide.newInstance("توضیحات سیستم", context.getResources().getString(R.string.wizard_1));
            case 2:
                return FragmentShowGuide.newInstance("مدیریت اطلاعات", context.getResources().getString(R.string.wizard_2));
            case 3:
                return FragmentShowGuide.newInstance("مدیریت شارژها", context.getResources().getString(R.string.wizard_3));
            case 4:
                return FragmentShowGuide.newInstance("پیگیری مشکلات", context.getResources().getString(R.string.wizard_4));
            case 5:
                return FragmentShowGuide.newInstance("اطلاع رسانی", context.getResources().getString(R.string.wizard_5));
            case 6:
                return FragmentShowGuide.newInstance("گزارشات", context.getResources().getString(R.string.wizard_6));
            case 7:
                return new FragmentWizardContactUs();
            case 8:
                return new FragmentWizardLast();
        }
        return new FragmentWizardFirst();
    }

    @Override
    public int getCount() {
        return 9;
    }
}
