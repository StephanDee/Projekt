package nessi.main;

import nessi.main.ChallengeList.Tab1Ch;
import nessi.main.HallOfFameList.Tab2Fame;
import nessi.main.UserProfile.Tab3Profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    private String[] tabTitles = new String[]{"CH", "Hall Of Fame", "Profil"};

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Tab1Ch tab1 = new Tab1Ch();
                return tab1;
            case 1:
                Tab2Fame tab2 = new Tab2Fame();
                return tab2;
            case 2:
                Tab3Profile tab3 = new Tab3Profile();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}