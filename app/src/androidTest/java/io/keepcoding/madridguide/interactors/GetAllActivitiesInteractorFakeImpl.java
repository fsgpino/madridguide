package io.keepcoding.madridguide.interactors;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.util.MainThread;

public class GetAllActivitiesInteractorFakeImpl implements IGetAllElementsInteractor<Activities> {
    @NonNull
    private List<Activity> getActivities() {
        List<Activity> data = new ArrayList<>();
        data.add(new Activity(1, "1").setAddress("AD 1"));
        data.add(new Activity(2, "2").setAddress("AD 2"));
        return data;
    }

    @Override
    public void execute(Context context, final GetAllElementsInteractorResponse<Activities> response) {
        List<Activity> data = getActivities();

        final Activities sut = Activities.build(data);

        // simulate connection
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MainThread.run(new Runnable() {
            @Override
            public void run() {
                response.response(sut);
            }
        });
    }
}
