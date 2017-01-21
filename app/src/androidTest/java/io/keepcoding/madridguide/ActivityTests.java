package io.keepcoding.madridguide;

import android.test.AndroidTestCase;

import io.keepcoding.madridguide.model.Activity;

public class ActivityTests extends AndroidTestCase {

    public static final String ACTIVITY = "activity";
    public static final String ADDRESS = "ADDRESS";
    public static final String DESC = "DESC";
    public static final String URL = "URL";

    public void testCanCreateAActivity() {
        Activity sut = new Activity(0, ACTIVITY);
        assertNotNull(sut);
    }

    public void testANewActivityStoresDataCorrectly() {
        Activity sut = new Activity(10, ACTIVITY);
        assertEquals(10, sut.getId());
        assertEquals(ACTIVITY, sut.getName());
    }

    public void testANewActivityStoresDataInPropertiesCorrectly() {
        Activity sut = new Activity(11, ACTIVITY)
                .setAddress(ADDRESS)
                .setDescription_es(DESC)
                .setUrl(URL);

        assertEquals(sut.getAddress(), ADDRESS);
        assertEquals(sut.getDescription_es(), DESC);
        assertEquals(sut.getUrl(),URL);
    }

}
