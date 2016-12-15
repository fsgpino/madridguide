package io.keepcoding.madridguide.views;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Shop;

public class ShopRowViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    ImageView logoImageView;

    public ShopRowViewHolder(View rowNote) {
        super(rowNote);

        nameTextView = (TextView) rowNote.findViewById(R.id.row_shop_name);
        logoImageView = (ImageView) rowNote.findViewById(R.id.row_shop_logo);
    }

    public void setShop(@Nullable Shop shop) {
        if (shop == null) {
            return;
        }
        this.nameTextView.setText(shop.getName());
        this.logoImageView.setImageBitmap(null);

    }
}
