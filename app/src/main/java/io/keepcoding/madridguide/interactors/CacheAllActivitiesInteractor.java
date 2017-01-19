package io.keepcoding.madridguide.interactors;

import android.content.Context;
import android.os.Looper;

import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;

public class CacheAllActivitiesInteractor {
    public void execute(final Context context, final Activities activities, final CacheAllElementsInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityDAO dao = new ActivityDAO(context);

                boolean success = true;
                for (Activity activity: activities.allElements()) {
                    success = dao.insert(activity) > 0;
                    if (!success) {
                        break;
                    }
                }

                Looper main = Looper.getMainLooper();
                // TODO: put on Main Thread
                if (response != null) {
                    response.response(success);
                }
            }
        }).start();
    }
}
