package com.kelompok5.fishify.utils.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bradhawk on 11/30/2016.
 */

public abstract class BaseActivity<T extends BaseController> extends AppCompatActivity {

    private T controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = createController();
    }

    protected T getController() {
        return controller;
    }

    protected abstract T createController();

}
