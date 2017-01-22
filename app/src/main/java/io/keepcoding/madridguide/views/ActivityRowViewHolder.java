package io.keepcoding.madridguide.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activity;

public class ActivityRowViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    ImageView logoImageView;
    RelativeLayout rowRelativeLayout;
    WeakReference<Context> context;

    public ActivityRowViewHolder(View itemView) {
        super(itemView);

        context = new WeakReference<Context>(itemView.getContext());
        nameTextView = (TextView) itemView.findViewById(R.id.row_activity_name);
        logoImageView = (ImageView) itemView.findViewById(R.id.row_activity_logo);
        rowRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.row_activity_view);
    }

    public void setActivity(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        this.nameTextView.setText(activity.getName());
        Picasso.with(context.get())
                .load(activity.getLogoImgUrl())
                //.networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(android.R.drawable.ic_popup_sync)
                .into(logoImageView);

        Picasso.with(context.get())
                .load(activity.getImageUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        rowRelativeLayout.setBackgroundDrawable(new BitmapDrawable(context.get().getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

    }
}
