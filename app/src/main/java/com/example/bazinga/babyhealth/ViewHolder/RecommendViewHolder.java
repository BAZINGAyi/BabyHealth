package com.example.bazinga.babyhealth.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazinga.babyhealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.contactsImage)
    ImageView contactsImage;
    @BindView(R.id.contacts_Name)
    public TextView contacts_Name;
    public @BindView(R.id.contacts_Phone)
    TextView contacts_Phone;
    public @BindView(R.id.cardView)
    CardView cardView;

    public RecommendViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}