package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.fragments.ShopsFragment;

public class ShopsActivity extends AppCompatActivity {

    private ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);



    }
}
