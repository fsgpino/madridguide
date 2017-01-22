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
import io.keepcoding.madridguide.util.CacheValidator;

public class MadridGuideApp extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        MadridGuideApp.appContext = new WeakReference<Context>(getApplicationContext());

        if (!CacheValidator.isShopsCacheValid(getAppContext())) {
            new GetAllShopsInteractor().execute(getAppContext(),
                    new GetAllElementsInteractorResponse<Shops>() {
                        @Override
                        public void response(Shops shops) {
                            new CacheAllShopsInteractor().execute(getAppContext(),
                                    shops, new CacheAllElementsInteractorResponse() {
                                        @Override
                                        public void response(boolean success) {
                                            // success, nothing to do here
                                        }
                                    });
                        }
                    }
            );
        }

        if (!CacheValidator.isActivitiesCacheValid(getAppContext())) {
            new GetAllActivitiesInteractor().execute(getAppContext(),
                    new GetAllElementsInteractorResponse<Activities>() {
                        @Override
                        public void response(Activities activities) {
                            new CacheAllActivitiesInteractor().execute(getAppContext(),
                                    activities, new CacheAllElementsInteractorResponse() {
                                        @Override
                                        public void response(boolean success) {
                                            // success, nothing to do here
                                        }
                                    });
                        }
                    }
            );
        }

        Picasso.with(getAppContext()).setIndicatorsEnabled(true);
        Picasso.with(getAppContext()).setLoggingEnabled(true);

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
