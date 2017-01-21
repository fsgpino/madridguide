package io.keepcoding.madridguide.model.mappers;

import java.util.LinkedList;
import java.util.List;

import io.keepcoding.madridguide.manager.net.ActivityEntity;
import io.keepcoding.madridguide.model.Activity;

public class ActivityEntityActivityMapper {
    public List<Activity> map(List<ActivityEntity> activityEntities) {
        List<Activity> result = new LinkedList<>();

        for (ActivityEntity entity: activityEntities) {
            Activity activity = new Activity(entity.getId(), entity.getName());

            activity.setImageUrl(entity.getImg());
            activity.setLogoImgUrl(entity.getLogoImg());
            activity.setAddress(entity.getAddress());
            activity.setUrl(entity.getUrl());

            // detect current lang
            activity.setDescription_es(entity.getDescriptionEs());
            activity.setDescription_en(entity.getDescriptionEn());

            activity.setLatitude(entity.getLatitude());
            activity.setLongitude(entity.getLongitude());

            result.add(activity);
        }

        return result;
    }
}
