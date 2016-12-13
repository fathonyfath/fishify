package com.kelompok5.fishify.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.controller.PeternakanController;
import com.kelompok5.fishify.holder.PeternakanViewHolder;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.model.User;
import com.kelompok5.fishify.utils.AbstractView;
import com.kelompok5.fishify.utils.mvc.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;

/**
 * Created by bradhawk on 12/10/2016.
 */

public class MainActivity extends BaseActivity<PeternakanController> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_navNama)
    TextView navigationName;
    @BindView(R.id.main_navEmail)
    TextView navigationEmail;
    @BindView(R.id.main_container)
    FrameLayout containerLayout;

    @BindView(R.id.main_drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_navItem_listPeternakan)
    FrameLayout peternakanButton;
    @BindView(R.id.main_navItem_listIkanTernak)
    FrameLayout ikanTernakButton;
    @BindView(R.id.main_navItem_keluar)
    FrameLayout keluarButton;

    private ActionBar actionBar;

    private DaftarPeternakanView daftarPeternakanView;
    private IkanTernakView ikanTernakView;

    private AbstractView activatedView;

    private RecyclerMultiAdapter peternakanAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(toolbar != null) setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) actionBar = getSupportActionBar();

        windowForNavigationDrawer();
        updateNavigationText();
        intializeViews();
        initializeNavDrawer();

        peternakanAdapter = SmartAdapter.empty()
                .map(Peternakan.class, PeternakanViewHolder.class)
                .recyclerAdapter();

        daftarPeternakanView.peternakanRecycler.setLayoutManager(new LinearLayoutManager(this));
        daftarPeternakanView.peternakanRecycler.setAdapter(peternakanAdapter);

        peternakanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDaftarPeternakan();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        ikanTernakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDaftarIkanTernak();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        keluarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    private void intializeViews() {
        daftarPeternakanView = new DaftarPeternakanView(
                LayoutInflater.from(this).inflate(R.layout.view_daftar_peternakan, null));
        ikanTernakView = new IkanTernakView(LayoutInflater.from(this)
                .inflate(R.layout.view_daftar_ikan_ternak, null));

        showDaftarPeternakan();
    }

    private void initializeNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showDaftarPeternakan() {
        activatedView = daftarPeternakanView;

        containerLayout.removeAllViews();
        containerLayout.addView(daftarPeternakanView.view);
        daftarPeternakanView.onViewVisible();
    }

    private void showDaftarIkanTernak() {
        activatedView = ikanTernakView;

        containerLayout.removeAllViews();
        containerLayout.addView(ikanTernakView.view);
        ikanTernakView.onViewVisible();
    }

    @Override
    protected PeternakanController createController() {
        return new PeternakanController();
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
        User loginUser = getController().getLoginUser();
        navigationName.setText(loginUser.getNamaLengkap());
        navigationEmail.setText(loginUser.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(activatedView instanceof DaftarPeternakanView) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_tambahPeternakan:
                tambahPeternakanButton();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        fillPeternakanList();
    }

    private void fillPeternakanList() {
        List<Peternakan> peternakanList = getController().fetchAllPeternakan();
        if(!peternakanList.isEmpty()) {
            peternakanAdapter.clearItems();
            peternakanAdapter.setItems(peternakanList);
            peternakanAdapter.notifyDataSetChanged();

            daftarPeternakanView.showRecycler();
        } else {
            daftarPeternakanView.showMessage();
        }
    }

    private void tambahPeternakanButton() {
        Intent intent = new Intent(this, TambahRubahPeternakanActivity.class);
        startActivity(intent);
    }

    class DaftarPeternakanView extends AbstractView {

        @BindView(R.id.daftarPeternakan_recycler)
        RecyclerView peternakanRecycler;
        @BindView(R.id.daftarPeternakan_noDataAvailable)
        TextView messageTextView;

        private View view;

        DaftarPeternakanView(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }

        @Override
        protected void onViewVisible() {
            setupActionBar();
            invalidateOptionsMenu();
        }

        private void setupActionBar() {
            actionBar.setTitle(R.string.main_listPeternakan);
        }

        private void showRecycler() {
            peternakanRecycler.setVisibility(View.VISIBLE);
            messageTextView.setVisibility(View.GONE);
        }

        private void showMessage(){
            peternakanRecycler.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);
        }
    }

    class IkanTernakView extends AbstractView {

        private View view;

        IkanTernakView(View view) {
            this.view = view;
        }

        @Override
        protected void onViewVisible() {
            setupActionBar();
            invalidateOptionsMenu();
        }

        private void setupActionBar() {
            actionBar.setTitle(R.string.main_listIkanTernak);
        }

    }
}
