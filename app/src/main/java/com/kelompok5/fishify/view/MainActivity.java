package com.kelompok5.fishify.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.MainController;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/10/2016.
 */

public class MainActivity extends BaseActivity<MainController> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_navNama)
    TextView navigationName;
    @BindView(R.id.main_navEmail)
    TextView navigationEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        windowForNavigationDrawer();
    }

    @Override
    protected MainController createController() {
        return new MainController();
    }

    private void windowForNavigationDrawer() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

    private void updateNavigationText() {

    }
}
