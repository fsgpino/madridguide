package io.keepcoding.madridguide.interactors;

import android.content.Context;

import java.util.List;

import io.keepcoding.madridguide.manager.net.NetworkManager;
import io.keepcoding.madridguide.manager.net.ActivityEntity;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.mappers.ActivityEntityActivityMapper;
import io.keepcoding.madridguide.util.CacheValidator;

public class GetAllActivitiesInteractor implements IGetAllElementsInteractor<Activities> {
    public void execute(final Context context, final GetAllElementsInteractorResponse<Activities> response) {
        NetworkManager networkManager = new NetworkManager(context);
        networkManager.getActivitiesFromServer(new NetworkManager.GetActivitiesListener() {
            @Override
            public void getActivityEntitiesSuccess(List<ActivityEntity> result) {
                List<Activity> activities = new ActivityEntityActivityMapper().map(result);
                if (response != null) {
                    response.response(Activities.build(activities));
                }
                CacheValidator.resetActivitiesCacheTime(context);
            }

            @Override
            public void getActivityEntitiesDidFail() {
                if (response != null) {
                    response.response(null);
                }
            }
        });
    }
}
