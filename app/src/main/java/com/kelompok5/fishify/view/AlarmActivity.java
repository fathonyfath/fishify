package com.kelompok5.fishify.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/20/2016.
 */

public class AlarmActivity extends BaseActivity<PeternakanController> {

    @BindView(R.id.alarm_button)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }
}
