package com.kelompok5.fishify.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.RegisterController;
import com.kelompok5.fishify.utils.mvc.BaseActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bradhawk on 11/30/2016.
 */

public class RegisterActivity extends BaseActivity<RegisterController> {

    @BindView(R.id.register_viewPager)
    ViewPager registerViewPager;

    private Page1ViewHolder page1;
    private Page2ViewHolder page2;

    private ViewPagerItemAdapter adapter;

    @Override
    protected RegisterController createController() {
        return new RegisterController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if(getController().isUserRegistered()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        adapter = new ViewPagerItemAdapter(ViewPagerItems.with(this)
                                        .add("", R.layout.view_register_1)
                                        .add("", R.layout.view_register_2)
                                        .create());

        registerViewPager.setAdapter(adapter);

        if (page1 == null) page1 = new Page1ViewHolder(adapter.getPage(0));

        registerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if(page1 == null) page1 = new Page1ViewHolder(adapter.getPage(position));
                        break;
                    case 1:
                        if(page2 == null) page2 = new Page2ViewHolder(adapter.getPage(position));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    class Page1ViewHolder {

        private View view;

        Page1ViewHolder(View view) {
            this.view = view;
        }
    }

    class Page2ViewHolder implements Validator.ValidationListener {

        @NotEmpty
        @BindView(R.id.register_editNama)
        EditText namaLengkapEditText;

        @NotEmpty
        @BindView(R.id.register_editTanggalLahir)
        EditText tanggalLahirEditText;

        @NotEmpty
        @Email
        @BindView(R.id.register_editEmail)
        EditText emailEditText;

        @BindView(R.id.register_groupJenis)
        RadioGroup jenisKelaminGroup;

        @BindView(R.id.register_textRegister)
        TextView registerButton;

        private View view;

        private Validator validator;
        private Date birthDate;

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        Page2ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);

            validator = new Validator(this);
            validator.setValidationListener(this);

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validator.validate();
                }
            });

            tanggalLahirEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) showDatePicker();
                }
            });
        }

        @Override
        public void onValidationSucceeded() {
            String namaLengkap = namaLengkapEditText.getText().toString();
            String jenisKelamin = (jenisKelaminGroup.getCheckedRadioButtonId()
                    == R.id.register_laki)? "Laki-Laki" : "Perempuan";
            String email = emailEditText.getText().toString();
            if(getController().register(namaLengkap, jenisKelamin, birthDate, email)) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(view.getContext());

                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                } else {
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        }

        private void updateLabel() {
            String format = "dd MMMM yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            tanggalLahirEditText.setText(sdf.format(calendar.getTime()));
            birthDate = calendar.getTime();
        }

        private void showDatePicker() {
            new DatePickerDialog(view.getContext(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
}
