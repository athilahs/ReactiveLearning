package com.athila.reactivelearning.reactive.events;

import android.view.View;

/**
 * Created by athila on 26/07/15.
 */
class OnFocusChangeEvent_General extends OnFocusChangeEvent {

    private final View view;
    private final boolean hasFocus;

    OnFocusChangeEvent_General(View view, boolean hasFocus) {
        if(view == null) {
            throw new NullPointerException("Null view");
        } else {
            this.view = view;
            this.hasFocus = hasFocus;
        }
    }

    public View view() {
        return this.view;
    }

    public boolean hasFocus() {
        return this.hasFocus;
    }

    public String toString() {
        return "OnFocusChangeEvent{view=" + this.view + ", hasFocus=" + this.hasFocus + "}";
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof OnFocusChangeEvent)) {
            return false;
        } else {
            OnFocusChangeEvent that = (OnFocusChangeEvent)o;
            return this.view.equals(that.view()) && this.hasFocus() == that.hasFocus();
        }
    }

    @Override
    public int hashCode() {
        int result = view.hashCode();
        result = 31 * result + (hasFocus ? 1 : 0);
        return result;
    }
}
