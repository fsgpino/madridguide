package io.keepcoding.madridguide.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.fragments.ActivitiesFragment;
import io.keepcoding.madridguide.interactors.GetAllActivitiesFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.OnGetAllElementsFromLocalCacheInteractorCompletion;
import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.manager.db.DBConstants;
import io.keepcoding.madridguide.manager.db.provider.MadridGuideProvider;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.OnElementClick;
import io.keepcoding.madridguide.views.MakerInfoView;

public class ActivitiesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ActivitiesFragment activitiesFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        activitiesFragment = (ActivitiesFragment) getFragmentManager().findFragmentById(R.id.activity_activities_fragment_activities);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.activities_map);

        // LoaderManager loaderManager = getSupportLoaderManager();
        // loaderManager.initLoader(0, null, this);

        // using interactors instead of Loaders
        GetAllActivitiesFromLocalCacheInteractor interactor = new GetAllActivitiesFromLocalCacheInteractor();
        interactor.execute(this, new OnGetAllElementsFromLocalCacheInteractorCompletion<Activities>() {
            @Override
            public void completion(Activities activities) {
                activitiesFragment.setListener(new OnElementClick<Activity>() {
                    @Override
                    public void clickedOn(@NonNull Activity activity, int position) {
                        Navigator.navigateFromActivitiesActivityToActivityDetailActivity(ActivitiesActivity.this, activity);
                    }
                });

                activitiesFragment.setActivities(activities);
                setUpMap(activities);
            }
        });

        setTitle(R.string.activity_main_button_activities_text);
    }

    // Cursor Loaders using Content Provider

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                MadridGuideProvider.ACTIVITIES_URI,
                DBConstants.ALL_ACTIVITY_COLUMNS,        // projection
                null,                               // where
                null,                               // campos del where
                null                                // order
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final Activities activities = ActivityDAO.getActivities(data);

        activitiesFragment.setListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(@NonNull Activity activity, int position) {
                Navigator.navigateFromActivitiesActivityToActivityDetailActivity(ActivitiesActivity.this, activity);
            }
        });

        activitiesFragment.setActivities(activities);
        setUpMap(activities);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setUpMap(final Activities activities) {

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
                .target(new LatLng(Double.parseDouble(getString(R.string.madrid_latitude)), Double.parseDouble(getString(R.string.madrid_longitude))))
                .zoom(Integer.parseInt(getString(R.string.map_zoom)))
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Create Markers in map
        for (Activity activity : activities.allElements()) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(activity.getLatitude(), activity.getLongitude())).title(activity.getName());
            marker.snippet(String.valueOf(activities.indexOf(activity)));
            googleMap.addMarker(marker);
        }

        // Set info view to maker
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Activity activity = activities.get(Integer.parseInt(marker.getSnippet()));
                MakerInfoView makerInfoView = new MakerInfoView(getApplicationContext());
                makerInfoView.setUp(activity.getLogoImgUrl(),activity.getName());
                return makerInfoView;
            }
        });

        // Action to click in maker info view
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Activity activity = activities.get(Integer.parseInt(marker.getSnippet()));
                Navigator.navigateFromActivitiesActivityToActivityDetailActivity(ActivitiesActivity.this, activity);
            }
        });

    }
}
