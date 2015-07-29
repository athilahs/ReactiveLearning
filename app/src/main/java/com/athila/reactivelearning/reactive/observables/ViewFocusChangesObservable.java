package com.athila.reactivelearning.reactive.observables;

import android.view.View;

import com.athila.reactivelearning.reactive.events.OnFocusChangeEvent;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.functions.Action0;

/**
 * Created by athila on 26/07/15.
 */
public class ViewFocusChangesObservable {

    private ViewFocusChangesObservable() {
        throw new AssertionError("No instances");
    }

    public static Observable<OnFocusChangeEvent> focusChanges(final View target) {
        return focusChanges(target, false);
    }

    public static Observable<OnFocusChangeEvent> focusChanges(final View target, final boolean emitInitialValue) {
        return Observable.create(new OnSubscribeFocusChange(target, emitInitialValue));
    }

    /***************************************************************************************/
    /* Subscribers implementations to be used with ViewFocusChangesObservable
    /****************************************************************************************/
    private static class OnSubscribeFocusChange implements Observable.OnSubscribe<OnFocusChangeEvent> {

        private final boolean emitInitialValue;
        private final View target;

        public OnSubscribeFocusChange(final View target, final boolean emitInitialValue) {
            this.target = target;
            this.emitInitialValue = emitInitialValue;
        }

        @Override
        public void call(final Subscriber<? super OnFocusChangeEvent> observer) {
            Assertions.assertUiThread();

            View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    observer.onNext(OnFocusChangeEvent.create(view, b));
                }
            };
            target.setOnFocusChangeListener(onFocusChangeListener);

            if (emitInitialValue) {
                observer.onNext(OnFocusChangeEvent.create(target));
            }

            final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                @Override
                public void call() {
                    target.setOnFocusChangeListener(null);
                }
            });

            observer.add(subscription);
        }
    }
}
