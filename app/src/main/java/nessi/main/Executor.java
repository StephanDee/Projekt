package nessi.main;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import nessi.main.ChallengeList.Tab1Ch;
import nessi.main.HallOfFameList.Tab2Fame;
import nessi.main.UserProfile.Tab3Profile;

public class Executor extends AppCompatActivity implements Tab2Fame.OnFragmentInteractionListener,
Tab1Ch.OnFragmentInteractionListener, Tab3Profile.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("CH"));
        tabLayout.addTab(tabLayout.newTab().setText("Hall Of Fame"));
        tabLayout.addTab(tabLayout.newTab().setText("Profil"));

        final ViewPager view = (ViewPager) findViewById(R.id.container);
        PagerAdapter mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        view.setAdapter(mSectionsPagerAdapter);
        view.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_executor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
