package com.blackcat.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
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
    private DashBoard dashBoardFrag;
    private World worldFrag;
    private About aboutFrag;
    private India indiaFrag;

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

        hamburger.setOnClickListener(v -> {
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }else{
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        handleDrawer();

        worldFrag = World.newInstance();
        aboutFrag = About.newInstance();
        indiaFrag = India.newInstance();


        title.setText("World");
        manager.beginTransaction()
                .replace(R.id.container,worldFrag)
                .commit();

    }

    private void handleDrawer(){

        nvDrawer.setNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){

                case R.id.india_frag :
                    fragment = indiaFrag;
                    break;

                case R.id.about_frag :
                    fragment = aboutFrag;
                    break;

                case R.id.world_frag :

                default :

                    fragment = worldFrag;
                    break;

//                case R.id.settings_frag :
//                    fragment = Settings.newInstance();
//                    break;
//
//
//                case R.id.news_frag :
//                    fragment = News.newInstance();
//                    break;
//
//                case R.id.dashboard_frag :
//
//                default:
//
//                    fragment = DashBoard.newInstance();
//                    break;

            }

            showFragments(fragment);

            title.setText(item.getTitle());
            mDrawer.closeDrawers();
            return true;
        });
    }

    private void showFragments(Fragment fragment){

        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit);
        fragmentTransaction.commit();
        manager.executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}