package com.singtel.groupit.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.util.AlertHelper;
import com.singtel.groupit.util.NetworkUtils;
import com.singtel.groupit.util.UiUtils;
import com.singtel.groupit.util.Utils;
import com.singtel.groupit.view.fragment.MainFragment;
import com.singtel.groupit.view.fragment.MenuFragment;


/**
 * Created by lanna on 6/17/16.
 *
 */

public class MainActivity extends SlidingActivity {

    @Override
    protected int getLayoutRes() {
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCustomActionBar();

        Utils.replaceFragment(this, MainFragment.newInstance(), R.id.fragment_content, false);
    }

    @Override
    protected void onDrawerClosed() {
        UiUtils.hideKeyboard(this, findViewById(R.id.menu_frame));
    }

    @Override
    protected Fragment onCreateMenuPanel() {
        return MenuFragment.getInstance();
    }

    private void createCustomActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final View ivMenu = toolbar.findViewById(R.id.iv_action_bar_menu);
        ivMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ivMenu.clearAnimation();
                if (NetworkUtils.isOnline(getApplicationContext())) {
                    showMenu();
                } else {
                    AlertHelper.showInternetAlert(getApplicationContext(), null);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isMenuShowing()) {
            showContent();
            return;
        }

        super.onBackPressed();
    }
}
