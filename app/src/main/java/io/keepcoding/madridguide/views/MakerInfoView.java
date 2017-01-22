package io.keepcoding.madridguide.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.R;

public class MakerInfoView extends RelativeLayout {

    WeakReference<Context> context;

    ImageView logoImageView;
    TextView nameTextView;

    public MakerInfoView(Context context) {
        super(context);

        this.context = new WeakReference<Context>(context);
        inflate(context, R.layout.maker_map_info, this);

        logoImageView = (ImageView) findViewById(R.id.maker_map_info_image_view);
        nameTextView = (TextView) findViewById(R.id.maker_map_info_text_view);

    }

    public void setUp(String imageURL, String title) {
        Picasso.with(context.get())
                .load(imageURL)
                .placeholder(android.R.drawable.ic_popup_sync)
                .into(logoImageView);

        nameTextView.setText(title);
    }

}
