package com.kelompok5.fishify.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.model.IkanTernak;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/18/2016.
 */

public class DetailIkanTernakActivity extends BaseActivity<PeternakanController> {

    public static final String IKAN_TERNAK_PARAM = "IdIkanTernak";
    public static final String ID_PETERNAKAN = "IdPeternakan";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detailIkanTernak_foto)
    ImageView gambarIkan;

    @BindView(R.id.detailIkanTernak_namaIkan)
    TextView namaIkan;

    @BindView(R.id.detailIkanTernak_jenisIkan)
    TextView jenisIkan;

    private ActionBar actionBar;

    private long idIkanTernak;
    private long idPeternakan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ikan_ternak);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        actionBar.setTitle("Detail Ikan Ternak");

        idIkanTernak = getIntent().getLongExtra(IKAN_TERNAK_PARAM, -1);
        idPeternakan = getIntent().getLongExtra(ID_PETERNAKAN, -1);
    }

    private void updateField() {
        IkanTernak ikanTernak = getController().fetchIkanTernak(idIkanTernak);

        namaIkan.setText(ikanTernak.getNamaIkan());
        jenisIkan.setText(ikanTernak.getJenisIkan());

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File file = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File saveFile = new File(file, String.valueOf(idIkanTernak) + ".jpg");

        Bitmap bitmap = BitmapFactory.decodeFile(saveFile.getPath());
        gambarIkan.setImageBitmap(bitmap);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        updateField();
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_ikan_ternak, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detailIkanTernak_edit:
                showEditIkanTernak();
                return true;
            case R.id.detailIkanTernak_delete:
                showDeleteIkanTernak();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TambahRubahIkanTernakActivity.TAMBAH_RUBAH_IKAN_TERNAK_ID) {
            if(resultCode == RESULT_OK) {
                updateField();
            }
        }
    }

    private void showEditIkanTernak() {
        Intent intent = new Intent(this, TambahRubahIkanTernakActivity.class);
        intent.putExtra(TambahRubahIkanTernakActivity.PETERNAKAN_PARAM, idPeternakan);
        intent.putExtra(TambahRubahIkanTernakActivity.IKAN_TERNAK_PARAM, idIkanTernak);
        startActivityForResult(intent, TambahRubahIkanTernakActivity.TAMBAH_RUBAH_IKAN_TERNAK_ID);
    }

    private void showDeleteIkanTernak() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message_hapusIkanTernak);
        builder.setMessage(R.string.message_hapusIkanTernakBody);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onDeleteIkanTernak();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onDeleteIkanTernak() {
        getController().deleteIkanTernak(idIkanTernak);
        finish();
    }
}
