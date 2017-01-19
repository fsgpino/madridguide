package io.keepcoding.madridguide.interactors;

import android.content.Context;

public interface IGetAllElementsInteractor<T> {
    public void execute(final Context context, final GetAllElementsInteractorResponse<T> response);
}