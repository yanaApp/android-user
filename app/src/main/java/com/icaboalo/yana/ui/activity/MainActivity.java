package com.icaboalo.yana.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.ui.adapter.OnDialogButtonClick;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.util.VUtil;

import static com.icaboalo.yana.R.string.label_activity_complete;

public class MainActivity extends AppCompatActivity implements OnDialogButtonClick{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VUtil.checkForToken(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationViewOnClick();
        replaceFragment(new ActionPlanFragment());
    }

    void navigationViewOnClick(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){

                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onPositiveClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        dialog.dismiss();
    }

    @Override
    public void onNeutralClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        switch (labelResource){
            case label_activity_complete:
                Intent goToCompleteActivity = new Intent(MainActivity.this, LoginActivity.class);
                goToCompleteActivity.putExtra(getString(R.string.extra_activity), ((ActivityApiModel) object).getmId());
                startActivity(goToCompleteActivity);
                break;
        }
        dialog.dismiss();
    }

    @Override
    public void onNegativeClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        dialog.dismiss();
    }
}
