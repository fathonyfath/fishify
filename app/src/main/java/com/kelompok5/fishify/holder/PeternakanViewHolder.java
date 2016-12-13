package com.kelompok5.fishify.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelompok5.fishify.R;
import com.kelompok5.fishify.model.Peternakan;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableFrameLayout;

/**
 * Created by bradhawk on 12/14/2016.
 */

public class PeternakanViewHolder extends BindableFrameLayout<Peternakan> {

    @BindView(R.id.itemPeternakan_namaPeternakan)
    TextView namaPeternakanTextView;

    public PeternakanViewHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_peternakan;
    }

    @Override
    public void onViewInflated() {
        ButterKnife.bind(this);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(Peternakan peternakan) {
        namaPeternakanTextView.setText(peternakan.getNamaPeternakan());
    }
}
