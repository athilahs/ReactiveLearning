package com.athila.reactivelearning.reactive.events;

import android.view.View;


/**
 * Created by athila on 26/07/15.
 */
public abstract class OnFocusChangeEvent {
    public abstract View view();
    public abstract boolean hasFocus();

    public static OnFocusChangeEvent create(final View view) {
        return create(view, view.hasFocus());
    }

    public static OnFocusChangeEvent create(final View view, final boolean hasFocus) {
        return new OnFocusChangeEvent_General(view, hasFocus);
    }
}
