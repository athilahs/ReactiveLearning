package com.athila.reactivelearning.reactive.events;

import android.view.View;


/**
 * Created by athila on 26/07/15.
 */
public abstract class OnViewClickEvent {
    public abstract View view();

    public static OnViewClickEvent create(final View view) {
        return new OnViewClickEvent_General(view);
    }
}
