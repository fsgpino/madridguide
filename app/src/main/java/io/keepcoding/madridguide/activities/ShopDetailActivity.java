package io.keepcoding.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.util.Constants;

public class ShopDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_shop_detail_shop_description_text)
    TextView shopDescriptionText;

    @BindView(R.id.activity_shop_detail_shop_address_text)
    TextView shopAddressText;

    Shop shop;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.activity_shop_detail_shop_map);

        getDetailShopFromCallingIntent();

        updateUI();

        setUpMap();
    }

    private void getDetailShopFromCallingIntent() {
        Intent i = getIntent();
        if (i != null) {
            shop = (Shop) i.getSerializableExtra(Constants.INTENT_KEY_SHOP_DETAIL);
        }
    }

    private void updateUI() {

        setTitle(shop.getName());

        if (Locale.getDefault().getLanguage().equals(Locale.ENGLISH.getLanguage())){
            shopDescriptionText.setText(shop.getDescription_en());
        }else{
            shopDescriptionText.setText(shop.getDescription_es());
        }

        shopAddressText.setText(shop.getAddress());

    }

    private void setUpMap() {

        GoogleMap googleMap = mapFragment.getMap();

        // Check if Maps is avaliable
        if (googleMap == null) {
            Toast.makeText(getApplicationContext(), "Maps no avaliable!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Map config
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        // Setting position camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(shop.getLatitude(), shop.getLongitude()))
                .zoom(Integer.parseInt(getString(R.string.map_zoom)))
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
