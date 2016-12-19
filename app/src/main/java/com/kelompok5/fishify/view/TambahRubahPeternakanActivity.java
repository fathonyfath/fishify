package com.kelompok5.fishify.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.utils.Constant;
import com.kelompok5.fishify.utils.Validator;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 12/12/2016.
 */

public class TambahRubahPeternakanActivity extends BaseActivity<PeternakanController> {

    public static final String PETERNAKAN_PARAM = "IdPeternakanParam";
    public static final int TAMBAH_RUBAH_ID = Constant.TAMBAH_RUBAH_IDENTIFIER;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tambahRubah_namaPeternakan)
    EditText namaPeternakanEditText;
    @BindView(R.id.tambahRubah_panjangPeternakan)
    EditText panjangPeternakanEditText;
    @BindView(R.id.tambahRubah_lebarPeternakan)
    EditText lebarPeternakanEditText;

    private ActionBar actionBar;

    private long idPeternakan;
    private boolean isDataBaru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rubah_peternakan);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        idPeternakan = getIntent().getLongExtra(PETERNAKAN_PARAM, -1);
        isDataBaru = idPeternakan == -1;

        if(isDataBaru) actionBar.setTitle(R.string.main_tambahPeternakan);
        else toolbar.setTitle(R.string.main_rubahPeternakan);

        fillField();
    }

    private void fillField() {
        if(!isDataBaru) {
            Peternakan peternakan = getController().fetchPeternakan(idPeternakan);
            if(peternakan != null) {
                namaPeternakanEditText.setText(peternakan.getNamaPeternakan());
                panjangPeternakanEditText.setText(String.valueOf(peternakan.getPanjang()));
                lebarPeternakanEditText.setText(String.valueOf(peternakan.getLebar()));
            }
        }
    }

    private void validate() {
        CharSequence namaPeternakan = namaPeternakanEditText.getText();
        CharSequence lebarPeternakan = lebarPeternakanEditText.getText();
        CharSequence panjangPeternakan = panjangPeternakanEditText.getText();

        boolean pass = true;

        if(Validator.isEmpty(namaPeternakan)) {
            namaPeternakanEditText.setError(getResources().getString(R.string.error_emptyField));
            pass = false;
        }

        if(!Validator.isMoreThanZero(lebarPeternakan)) {
            lebarPeternakanEditText.setError(getResources().getString(R.string.error_numberError));
            pass = false;
        }

        if(!Validator.isMoreThanZero(panjangPeternakan)) {
            panjangPeternakanEditText.setError(getResources().getString(R.string.error_numberError));
            pass = false;
        }

        if(Validator.isEmpty(lebarPeternakan)) {
            lebarPeternakanEditText.setError(getResources().getString(R.string.error_emptyField));
            pass = false;
        }

        if(Validator.isEmpty(panjangPeternakan)) {
            panjangPeternakanEditText.setError(getResources().getString(R.string.error_emptyField));
            pass = false;
        }

        if(pass) onValidationSucceeded();
    }

    private void onValidationSucceeded() {
        CharSequence namaPeternakan = namaPeternakanEditText.getText();
        CharSequence lebarPeternakan = lebarPeternakanEditText.getText();
        CharSequence panjangPeternakan = panjangPeternakanEditText.getText();

        if(isDataBaru) {
            boolean result = getController().addPeternakan(namaPeternakan.toString(),
                    Float.parseFloat(panjangPeternakan.toString()),
                    Float.parseFloat(lebarPeternakan.toString()));
            if(result) {
                Toast.makeText(this, R.string.message_successInsert, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        } else {
            boolean result = getController().updatePeternakan(idPeternakan, namaPeternakan.toString(),
                    Float.parseFloat(panjangPeternakan.toString()),
                    Float.parseFloat(lebarPeternakan.toString()));
            if(result) {
                Toast.makeText(this, R.string.message_successUpdate, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tambah_rubah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambahRubah_simpan:
                validate();
                return true;
            case R.id.tambahRubah_batal:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
