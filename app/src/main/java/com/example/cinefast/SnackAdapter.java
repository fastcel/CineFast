package com.example.cinefast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SnackAdapter extends ArrayAdapter<Snack> {

    public SnackAdapter(Context context, List<Snack> snacks) {
        super(context, 0, snacks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.snack_item_layout, parent, false);
        }

        Snack snack = getItem(position);

        if (snack == null) return convertView; // 🔥 prevents crash

        ImageView image = convertView.findViewById(R.id.snackImage);
        TextView name = convertView.findViewById(R.id.snackName);
        TextView desc = convertView.findViewById(R.id.snackDesc);
        TextView price = convertView.findViewById(R.id.snackPrice);
        TextView quantity = convertView.findViewById(R.id.tvQuantity);
        MaterialButton btnInc = convertView.findViewById(R.id.btnInc);
        MaterialButton btnDec = convertView.findViewById(R.id.btnDec);

        image.setImageResource(snack.getImage());
        name.setText(snack.getName());
        desc.setText(snack.getDescription());
        price.setText("Rs " + snack.getPrice());
        quantity.setText(String.valueOf(snack.getQuantity()));

        btnInc.setOnClickListener(v -> {
            snack.setQuantity(snack.getQuantity() + 1);
            quantity.setText(String.valueOf(snack.getQuantity()));
        });

        btnDec.setOnClickListener(v -> {
            if (snack.getQuantity() > 0) {
                snack.setQuantity(snack.getQuantity() - 1);
                quantity.setText(String.valueOf(snack.getQuantity()));
            }
        });

        return convertView;
    }
}