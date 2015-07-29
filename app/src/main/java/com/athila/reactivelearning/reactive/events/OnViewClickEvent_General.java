package com.athila.reactivelearning.reactive.events;

import android.view.View;

/**
 * Created by athila on 26/07/15.
 */
class OnViewClickEvent_General extends OnViewClickEvent {

    private final View view;

    OnViewClickEvent_General(View view) {
        if(view == null) {
            throw new NullPointerException("Null view");
        } else {
            this.view = view;
        }
    }

    public View view() {
        return this.view;
    }

    public String toString() {
        return "OnViewClickEvent{view=" + this.view + "}";
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof OnViewClickEvent)) {
            return false;
        } else {
            OnViewClickEvent that = (OnViewClickEvent)o;
            return this.view.equals(that.view());
        }
    }

    @Override
    public int hashCode() {
        int result = view.hashCode();
        result = 31 * result;
        return result;
    }
}
