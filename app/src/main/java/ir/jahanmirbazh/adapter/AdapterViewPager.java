package ir.jahanmirbazh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ir.jahanmirbazh.fragment.FragmentBill;
import ir.jahanmirbazh.fragment.FragmentCost;
import ir.jahanmirbazh.fragment.FragmentHome;
import ir.jahanmirbazh.fragment.FragmentNews;
import ir.jahanmirbazh.fragment.FragmentPayment;
import ir.jahanmirbazh.fragment.FragmentTicket;

public class AdapterViewPager extends FragmentStatePagerAdapter {
int count;
    public AdapterViewPager(int count,FragmentManager fm) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new FragmentHome();
            case 1 :
                return new FragmentBill();
            case 2 :
                return new FragmentPayment();
            case 3 :
                return new FragmentTicket();
            case 4 :
                return new FragmentNews();
            case 5 :
                return new FragmentCost();
//            return new FragmentNews();

        }
        return new FragmentHome();
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Check";
            case 2:
                return "Payment";
            case 3:
                return "Ticket";
            case 4:
                return "News";
            case 5:
                return "Chat";
        }
        return super.getPageTitle(position);
    }

}
