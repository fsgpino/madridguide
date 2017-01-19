package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.interactors.CacheAllActivitiesInteractor;
import io.keepcoding.madridguide.interactors.CacheAllElementsInteractorResponse;
import io.keepcoding.madridguide.interactors.CacheAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllActivitiesInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllElementsInteractorResponse;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Shops;

public class MadridGuideApp extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        MadridGuideApp.appContext = new WeakReference<Context>(getApplicationContext());

        new GetAllShopsInteractor().execute(getApplicationContext(),
                new GetAllElementsInteractorResponse<Shops>() {
                    @Override
                    public void response(Shops shops) {
                        new CacheAllShopsInteractor().execute(getApplicationContext(),
                                shops, new CacheAllElementsInteractorResponse() {
                                    @Override
                                    public void response(boolean success) {
                                        // success, nothing to do here
                                    }
                                });
                    }
                }
        );

        new GetAllActivitiesInteractor().execute(getApplicationContext(),
                new GetAllElementsInteractorResponse<Activities>() {
                    @Override
                    public void response(Activities activities) {
                        new CacheAllActivitiesInteractor().execute(getApplicationContext(),
                                activities, new CacheAllElementsInteractorResponse() {
                                    @Override
                                    public void response(boolean success) {
                                        // success, nothing to do here
                                    }
                                });
                    }
                }
        );

        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        if (appContext != null) {
            return MadridGuideApp.appContext.get();
        }
        return null;
    }
}
