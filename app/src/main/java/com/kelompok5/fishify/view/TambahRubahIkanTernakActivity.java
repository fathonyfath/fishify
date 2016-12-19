package com.kelompok5.fishify.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerActivity;
import com.esafirm.imagepicker.model.Image;
import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.model.IkanTernak;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.utils.Constant;
import com.kelompok5.fishify.utils.Validator;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/18/2016.
 */

public class TambahRubahIkanTernakActivity extends BaseActivity<PeternakanController> {

    public static final String IKAN_TERNAK_PARAM = "IdIkanTernakParam";
    public static final String PETERNAKAN_PARAM = "IdPeternakan";
    public static final int TAMBAH_RUBAH_IKAN_TERNAK_ID = Constant.TAMBAH_RUBAH_IKAN_IDENTIFIER;

    private static final int REQUEST_IMAGE_PICKER = Constant.REQUEST_IMAGE_PICKER;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tambahRubahIkanTernak_foto)
    ImageView imageIkan;

    @BindView(R.id.tambahRubahIkanTernak_ambilGambar)
    Button pilihGambar;

    @BindView(R.id.tambahRubahIkanTernak_namaIkan)
    EditText namaIkan;

    @BindView(R.id.tambahRubahIkanTernak_jenis)
    EditText jenisIkan;

    private ActionBar actionBar;

    private long idPeternakan;
    private long idIkanTernak;
    private boolean isDataBaru;

    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rubah_ikan_ternak);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        idIkanTernak = getIntent().getLongExtra(IKAN_TERNAK_PARAM, -1);
        idPeternakan = getIntent().getLongExtra(PETERNAKAN_PARAM, -1);
        isDataBaru = idIkanTernak == -1;

        if(isDataBaru) actionBar.setTitle(R.string.main_tambahIkanTernak);
        else actionBar.setTitle(R.string.main_rubahIkanTernak);

        fillField();

        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihGambarClick();
            }
        });
    }

    private void pilihGambarClick() {
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .folderMode(true)
                .folderTitle("Folder")
                .imageTitle("Tekan untuk memilih")
                .single()
                .showCamera(false)
                .start(REQUEST_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Image image = images.get(0);
            updateImageView(image);
        }
    }

    private void fillField() {
        if(!isDataBaru) {
            IkanTernak ikanTernak = getController().fetchIkanTernak(idIkanTernak);
            if(ikanTernak != null) {
                namaIkan.setText(ikanTernak.getNamaIkan());
                jenisIkan.setText(ikanTernak.getJenisIkan());

                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File file = cw.getDir("imageDir", Context.MODE_PRIVATE);

                File saveFile = new File(file, String.valueOf(idIkanTernak) + ".jpg");

                bitmap = BitmapFactory.decodeFile(saveFile.getPath());
                imageIkan.setImageBitmap(bitmap);
            }
        }
    }

    private void updateImageView(Image image) {
        bitmap = BitmapFactory.decodeFile(image.getPath());
        imageIkan.setImageBitmap(bitmap);
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tambah_rubah_ikan_ternak, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambahRubahIkanTernak_simpan:
                validate();
                return true;
            case R.id.tambahRubahIkanTernak_batal:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void validate() {
        CharSequence namaIkanString = namaIkan.getText();
        CharSequence jenisIkanString = jenisIkan.getText();

        boolean pass = true;

        if(bitmap == null) {
            Toast.makeText(this, "Foto tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            pass = false;
        }

        if(Validator.isEmpty(namaIkanString)) {
            namaIkan.setError(getResources().getString(R.string.error_emptyField));
            pass = false;
        }

        if(Validator.isEmpty(jenisIkanString)) {
            jenisIkan.setError(getResources().getString(R.string.error_emptyField));
            pass = false;
        }

        if(pass) onValidationSucceeded();
    }

    private void onValidationSucceeded() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File file = cw.getDir("imageDir", Context.MODE_PRIVATE);
        if(isDataBaru) {
            long idIkanTernak = getController().addIkanTernak(namaIkan.getText().toString(),
                            jenisIkan.getText().toString(), idPeternakan);
            File saveFile = new File(file, String.valueOf(idIkanTernak) + ".jpg");
            getController().saveImage(saveFile, bitmap);
        } else {
            getController().updateIkanTernak(idIkanTernak, namaIkan.getText().toString(), jenisIkan.getText().toString(),
                    idPeternakan);

            File saveFile = new File(file, String.valueOf(idIkanTernak) + ".jpg");
            getController().saveImage(saveFile, bitmap);
        }


        Toast.makeText(this, R.string.message_successUpdate, Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }
}
