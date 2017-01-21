package io.keepcoding.madridguide.activities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class ActivitiesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ActivitiesFragment activitiesFragment;
    private Activities activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        activitiesFragment = (ActivitiesFragment) getFragmentManager().findFragmentById(R.id.activity_activities_fragment_activities);

        LoaderManager loaderManager = getSupportLoaderManager();
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
            }
        });
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
