package io.keepcoding.madridguide;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;

public class ActivitiesTests extends AndroidTestCase {

    private static final String ACTIVITY = "activity";
    private static final String ADDRESS = "ADDRESS";

    public void testCreatingAActivitiesWithNullListReturnsNonNullShops() {
        Activities sut = Activities.build(null);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
    }

    public void testCreatingAActivitiesWithAListReturnsNonNullShops() {
        List<Activity> data = getActivities();

        Activities sut = Activities.build(data);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
        assertEquals(sut.allElements(), data);
        assertEquals(sut.allElements().size(), data.size());
    }

    @NonNull
    private List<Activity> getActivities() {
        List<Activity> data = new ArrayList<>();
        data.add(new Activity(1, ACTIVITY).setAddress(ADDRESS));
        data.add(new Activity(2, ACTIVITY).setAddress(ADDRESS));
        return data;
    }

}
