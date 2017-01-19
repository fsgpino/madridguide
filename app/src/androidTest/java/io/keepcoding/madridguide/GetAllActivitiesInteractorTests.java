package io.keepcoding.madridguide;

import android.test.AndroidTestCase;

import io.keepcoding.madridguide.interactors.GetAllActivitiesInteractorFakeImpl;
import io.keepcoding.madridguide.interactors.GetAllElementsInteractorResponse;
import io.keepcoding.madridguide.interactors.IGetAllElementsInteractor;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;

// deprecated: https://developer.android.com/topic/libraries/testing-support-library/index.html
public class GetAllActivitiesInteractorTests extends AndroidTestCase {

    public void testCanCreateInteractor() {
        IGetAllElementsInteractor getAllShopsInteractor = new GetAllActivitiesInteractorFakeImpl();

        getAllShopsInteractor.execute(getContext(), new GetAllElementsInteractorResponse<Activities>() {
            @Override
            public void response(Activities activities) {
                assertNotNull(activities);
                assertTrue(activities.size() > 0);

                Activity activity = activities.get(1);
                assertEquals(activity.getId(), 2);
            }
        });
    }

}
