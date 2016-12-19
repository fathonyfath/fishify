package com.kelompok5.fishify.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.holder.IkanTernakViewHolder;
import com.kelompok5.fishify.model.IkanTernak;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

/**
 * Created by bradhawk on 12/14/2016.
 */

public class DetailPeternakanActivity extends BaseActivity<PeternakanController> {

    public static final String PETERNAKAN_PARAM = "IdPeternakanParam";
    
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.detailPeternakan_namaPeternakan)
    TextView namaPeternakanTextView;
    @BindView(R.id.detailPeternakan_panjangPeternakan)
    TextView panjangPeternakanTextView;
    @BindView(R.id.detailPeternakan_lebarPeternakan)
    TextView lebarPeternakanTextView;

    @BindView(R.id.detailPeternakan_recyclerIkan)
    RecyclerView ikanListRecycler;
    @BindView(R.id.detailPeternakan_noDataAvailable)
    TextView messageNoDataAvailable;

    @BindView(R.id.detailPeternakan_addIkan)
    ImageView addIkan;

    private ActionBar actionBar;

    private long idPeternakan;

    private RecyclerMultiAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_peternakan);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.main_detailPeternakan);

        idPeternakan = getIntent().getLongExtra(PETERNAKAN_PARAM, -1);
        if(idPeternakan == -1) cancelActivityShowing();

        adapter = SmartAdapter.empty()
                .map(IkanTernak.class, IkanTernakViewHolder.class)
                .listener(new ViewEventListener<IkanTernak>() {
                    @Override
                    public void onViewEvent(int actionId, IkanTernak item, int position, View view) {
                        Intent intent = new Intent(DetailPeternakanActivity.this, DetailIkanTernakActivity.class);
                        intent.putExtra(DetailIkanTernakActivity.ID_PETERNAKAN, idPeternakan);
                        intent.putExtra(DetailIkanTernakActivity.IKAN_TERNAK_PARAM, item.getIdIkanTernak());
                        startActivity(intent);
                    }
                })
                .recyclerAdapter();

        ikanListRecycler.setNestedScrollingEnabled(false);
        ikanListRecycler.setLayoutManager(new LinearLayoutManager(this));
        ikanListRecycler.setAdapter(adapter);

        addIkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIkan();
            }
        });
    }

    private void addIkan() {
        Intent intent = new Intent(this, TambahRubahIkanTernakActivity.class);
        intent.putExtra(TambahRubahIkanTernakActivity.PETERNAKAN_PARAM, idPeternakan);
        startActivityForResult(intent, TambahRubahIkanTernakActivity.TAMBAH_RUBAH_IKAN_TERNAK_ID);
    }

    private void showRecycler() {
        ikanListRecycler.setVisibility(View.VISIBLE);
        messageNoDataAvailable.setVisibility(View.GONE);
    }

    private void showMessage() {
        ikanListRecycler.setVisibility(View.GONE);
        messageNoDataAvailable.setVisibility(View.VISIBLE);
    }

    private void refreshDetailPeternakan() {
        Peternakan peternakan = getController().fetchPeternakan(idPeternakan);
        namaPeternakanTextView.setText(peternakan.getNamaPeternakan());
        panjangPeternakanTextView.setText(String.format(Locale.US, "Panjang: %.2f meter", peternakan.getPanjang()));
        lebarPeternakanTextView.setText(String.format(Locale.US, "Lebar: %.2f meter", peternakan.getLebar()));
    }

    private void refreshIkanList() {
        List<IkanTernak> ikanTernakList = getController().fetchIkanTernakByPeternakan(idPeternakan);
        adapter.setItems(ikanTernakList);
        adapter.notifyDataSetChanged();
        if(!ikanTernakList.isEmpty()) {
            showRecycler();
        } else {
            showMessage();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshIkanList();
        refreshDetailPeternakan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_peternakan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detailPetenakan_edit:
                showEditPeternakan();
                return true;
            case R.id.detailPetenakan_delete:
                showDeletePeternakan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditPeternakan() {
        Intent intent = new Intent(this, TambahRubahPeternakanActivity.class);
        intent.putExtra(TambahRubahPeternakanActivity.PETERNAKAN_PARAM, idPeternakan);
        startActivityForResult(intent, TambahRubahPeternakanActivity.TAMBAH_RUBAH_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TambahRubahPeternakanActivity.TAMBAH_RUBAH_ID) {
            if(resultCode == Activity.RESULT_OK) {
                refreshDataDisplay();
            }
        } else if(requestCode == TambahRubahIkanTernakActivity.TAMBAH_RUBAH_IKAN_TERNAK_ID) {
            if(resultCode == RESULT_OK) {
                refreshDataDisplay();
            }
        }
    }

    private void showDeletePeternakan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message_hapusPeternakan);
        builder.setMessage(R.string.message_hapusPeternakanBody);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onDeletePeternakan();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onDeletePeternakan() {
        getController().deletePeternakan(idPeternakan);
        finish();
    }

    private void refreshDataDisplay() {
        refreshDetailPeternakan();
        refreshIkanList();
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
    }
    
    private void cancelActivityShowing() {
        Toast.makeText(this, R.string.error_parameterInvalid, Toast.LENGTH_SHORT).show();
        finish();
    }
}
