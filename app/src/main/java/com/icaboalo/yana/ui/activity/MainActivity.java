package com.icaboalo.yana.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.ui.fragment.ContactsFragment;
import com.icaboalo.yana.ui.fragment.ProfileFragment;
import com.icaboalo.yana.ui.fragment.ProgressFragment;
import com.icaboalo.yana.util.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    public static final String CLOSE = "Close";
    public static final String PROFILE = "Profile";
    public static final String ACTION_PLAN = "Action plan";
    public static final String CONTACTS = "Contacts";
    public static final String PROGRESS = "Progress";
    public static final String HELP = "Help";
    public static final String LOG_OUT = "Log out";

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    LinearLayout leftDrawer;


    private ViewAnimator viewAnimator;
    private List<SlideMenuItem> menuList = new ArrayList<>();
    ActionPlanFragment actionPlanFragment;
    ProfileFragment profileFragment;
    ProgressFragment progressFragment;
    ContactsFragment contactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PrefUtils.getToken(this).equals("TOKEN") || PrefUtils.getToken(this).isEmpty()){
            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }


        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        setToolbar();
        createMenuList();
        initFragments();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, actionPlanFragment).commit();
        viewAnimator = new ViewAnimator(this, menuList, actionPlanFragment, mDrawerLayout, this);


//        replaceFragment(new ActionPlanFragment());

        if (!PrefUtils.isProfileComplete(this)){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Perfil no completo");
            alertDialog.setMessage("Por favor ayudanos a completar tu perfil para obtener más información");
            alertDialog.setPositiveButton("VAMOS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    replaceFragment(new ProfileFragment());
                }
            });
            alertDialog.setNeutralButton("MÁS TARDE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setNegativeButton("NO VOLVER A MOSTRAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PrefUtils.setProfileComplete(MainActivity.this, true);
                }
            });
            alertDialog.show();
        }
    }

    void initFragments(){
        actionPlanFragment = new ActionPlanFragment();
        profileFragment = new ProfileFragment();
        progressFragment = new ProgressFragment();
        contactsFragment = new ContactsFragment();
    }

    void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                leftDrawer.removeAllViews();
                leftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && leftDrawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

        };
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    void createMenuList(){
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.cancel_outlined_circular_32);
        menuList.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(PROFILE, R.drawable.ic_person_white_48dp);
        menuList.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ACTION_PLAN, R.drawable.ic_offline_pin_white_48dp);
        menuList.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(CONTACTS, R.drawable.ic_people_white_48dp);
        menuList.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(PROGRESS, R.drawable.ic_poll_white_48dp);
        menuList.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(HELP, R.drawable.ic_menu_share);
        menuList.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(LOG_OUT, R.drawable.ic_power_settings_new_white_48dp);
        menuList.add(menuItem6);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }


    private ScreenShotable replaceFragment(Resourceble slideMenuItem, ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.container);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        switch (slideMenuItem.getName()){
            case ACTION_PLAN:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, actionPlanFragment).commit();
                return actionPlanFragment;
            case PROFILE:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return profileFragment;
            case CONTACTS:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, contactsFragment).commit();
                return contactsFragment;
            case PROGRESS:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, progressFragment).commit();
                return progressFragment;
            default:
                return actionPlanFragment;
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()){
            case CLOSE:
                return screenShotable;
            case PROFILE:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                return replaceFragment(slideMenuItem, screenShotable, position);

            default:
                if (slideMenuItem.getName().equals(LOG_OUT)){
                    SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                    sharedPreferences.edit().remove(PrefConstants.tokenPref).apply();
                    finish();
                    return screenShotable;
                } else{
                    return replaceFragment(slideMenuItem, screenShotable, position);
                }
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        leftDrawer.addView(view);
    }
}
