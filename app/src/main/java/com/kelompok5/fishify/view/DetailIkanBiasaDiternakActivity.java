package com.kelompok5.fishify.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.utils.mvc.BaseActivity;
import com.kelompok5.fishify.utils.mvc.BaseController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/19/2016.
 */

public class DetailIkanBiasaDiternakActivity extends BaseActivity<PeternakanController> {

    public static final String NAMA_IKAN = "NamaIkan";
    public static final String DESKRIPSI_IKAN = "DeskripsiIkan";

    @BindView(R.id.detailIkanBiasaTernak_namaIkan)
    TextView namaIkan;

    @BindView(R.id.detailIkanBiasaTernak_jenisIkan)
    TextView deskripsiIkan;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String namaIkanText;
    private String deskripsiIkanText;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ikan_biasa_ternak);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        actionBar.setTitle("Detail Ikan");

        namaIkanText = getIntent().getStringExtra(NAMA_IKAN);
        deskripsiIkanText = getIntent().getStringExtra(DESKRIPSI_IKAN);

        namaIkan.setText(namaIkanText);
        deskripsiIkan.setText(deskripsiIkanText);
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }
}
