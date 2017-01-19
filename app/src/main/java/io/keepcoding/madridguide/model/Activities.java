package io.keepcoding.madridguide.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Activities implements IElementsIterate<Activity>, IElementsUpdate<Activity> {

    private List<Activity> activities;

    public static @NonNull Activities build(@NonNull List<Activity> activityList) {
        Activities activities = new Activities();

        if (activityList == null) {
            activities.activities = new ArrayList<>();
        } else {
            activities.activities = activityList;
        }

        return activities;
    }

    private Activities() {
    }

    @Override
    public long size() {
        return activities.size();
    }

    @Override
    public @Nullable
    Activity get(long index) {
        if (index > size()) {
            return null;
        }
        return activities.get((int)index);
    }

    @Override
    public @NonNull  List<Activity> allElements() {
        return this.activities;
    }

    @Override
    public void add(Activity activity) {
        this.activities.add(activity);
    }

    @Override
    public void delete(Activity activity) {
        this.activities.remove(activity);
    }

    @Override
    public void edit(Activity activity, long index) {
        this.activities.set((int) index, activity);
    }
}
