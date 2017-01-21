package io.keepcoding.madridguide.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activity;

public class ActivityRowViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    ImageView logoImageView;
    WeakReference<Context> context;

    public ActivityRowViewHolder(View itemView) {
        super(itemView);

        context = new WeakReference<Context>(itemView.getContext());
        nameTextView = (TextView) itemView.findViewById(R.id.row_activity_name);
        logoImageView = (ImageView) itemView.findViewById(R.id.row_activity_logo);
    }

    public void setActivity(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        this.nameTextView.setText(activity.getName());
        Picasso.with(context.get())
                .load(activity.getLogoImgUrl())
                //.networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(logoImageView);
    }
}
