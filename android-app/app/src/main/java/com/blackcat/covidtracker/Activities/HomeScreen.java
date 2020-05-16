package com.blackcat.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.covidtracker.Fragments.About;
import com.blackcat.covidtracker.Fragments.DashBoard;
import com.blackcat.covidtracker.Fragments.India;
import com.blackcat.covidtracker.Fragments.News;
import com.blackcat.covidtracker.Fragments.Settings;
import com.blackcat.covidtracker.Fragments.World;
import com.blackcat.covidtracker.R;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ImageView hamburger;
    private TextView title ;
    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;

    private HashMap<String , Fragment.SavedState> fragmentInstances ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mDrawer = findViewById(R.id.layout_drawer);
        nvDrawer = findViewById(R.id.nav_view);
        hamburger = findViewById(R.id.menuButton);
        title = findViewById(R.id.fragTitle);

        manager = getSupportFragmentManager();
        fragmentInstances = new HashMap<>();

        hamburger.setOnClickListener(v -> {
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }else{
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        handleDrawer();

        Fragment dashboardFrag = DashBoard.newInstance();
        manager.beginTransaction()
                .replace(R.id.container,dashboardFrag)
                .addToBackStack(dashboardFrag.getClass().getSimpleName())
                .commit();

    }

    private void handleDrawer(){

        nvDrawer.setNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){

                case R.id.world_frag :
                    fragment = World.newInstance();
                    break;

                case R.id.india_frag :
                    fragment = India.newInstance();
                    break;

                case R.id.settings_frag :
                    fragment = Settings.newInstance();
                    break;

                case R.id.about_frag :
                    fragment = About.newInstance();
                    break;

                case R.id.news_frag :
                    fragment = News.newInstance();
                    break;

                case R.id.dashboard_frag :

                default:

                    fragment = DashBoard.newInstance();
                    break;
            }

            showFragments(fragment);

            title.setText(item.getTitle());
            mDrawer.closeDrawers();
            return true;
        });
    }

    private void showFragments(Fragment fragment){

        String BACKSTACK_TAG = fragment.getClass().getSimpleName();

        String  f = manager.getBackStackEntryAt( manager.getBackStackEntryCount() - 1).getName();
        String f2 = fragment.getClass().getSimpleName();
        fragmentInstances.put(f,manager.saveFragmentInstanceState(manager.findFragmentById(R.id.container)));

        try {
            if (!f.equals(f2) && !manager.popBackStackImmediate(BACKSTACK_TAG, 0)) {

                if (fragmentInstances.containsKey(f2)) {
                    fragment.setInitialSavedState(fragmentInstances.get(f2));
                }
                fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment, BACKSTACK_TAG);
                fragmentTransaction.addToBackStack(BACKSTACK_TAG);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit);
                fragmentTransaction.commit();
                manager.executePendingTransactions();

            }
        }catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}