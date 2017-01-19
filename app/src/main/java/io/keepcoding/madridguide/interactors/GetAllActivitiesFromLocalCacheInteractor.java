package io.keepcoding.madridguide.interactors;

import android.content.Context;

import java.util.List;

import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.util.MainThread;

public class GetAllActivitiesFromLocalCacheInteractor {
    public void execute(final Context context, final OnGetAllElementsFromLocalCacheInteractorCompletion<Activities> completion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityDAO dao = new ActivityDAO(context);

                List<Activity> activityList = dao.query();
                final Activities shops = Activities.build(activityList);

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        completion.completion(shops);
                    }
                });


            }
        }).start();
    }
}
