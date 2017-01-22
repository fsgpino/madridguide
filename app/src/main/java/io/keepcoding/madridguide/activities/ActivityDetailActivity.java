package io.keepcoding.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.util.Constants;

public class ActivityDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_activity_detail_activity_description_text)
    TextView activityDescriptionText;

    @BindView(R.id.activity_activity_detail_activity_address_text)
    TextView activityAddressText;

    Activity activity;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);

        ButterKnife.bind(this);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.activity_activity_detail_activity_map);

        getDetailActivityFromCallingIntent();

        updateUI();

        setUpMap();
    }

    private void getDetailActivityFromCallingIntent() {
        Intent i = getIntent();
        if (i != null) {
            activity = (Activity) i.getSerializableExtra(Constants.INTENT_KEY_ACTIVITY_DETAIL);
        }
    }

    private void updateUI() {

        setTitle(activity.getName());

        if (Locale.getDefault().getLanguage().equals(Locale.ENGLISH.getLanguage())){
            activityDescriptionText.setText(activity.getDescription_en());
        }else{
            activityDescriptionText.setText(activity.getDescription_es());
        }

        activityAddressText.setText(activity.getAddress());
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
                .target(new LatLng(activity.getLatitude(), activity.getLongitude()))
                .zoom(Integer.parseInt(getString(R.string.map_zoom)))
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
