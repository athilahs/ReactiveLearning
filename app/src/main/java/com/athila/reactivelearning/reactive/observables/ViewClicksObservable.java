package com.athila.reactivelearning.reactive.observables;

import android.view.View;

import com.athila.reactivelearning.reactive.events.OnViewClickEvent;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.functions.Action0;

/**
 * Created by athila on 26/07/15.
 */
public class ViewClicksObservable {

    private ViewClicksObservable() {
        throw new AssertionError("No instances");
    }

    public static Observable<OnViewClickEvent> viewClicks(final View target) {
        return viewClicks(target, false);
    }

    public static Observable<OnViewClickEvent> viewClicks(final View target, final boolean emitInitialValue) {
        return Observable.create(new OnSubscribeViewClicks(target, emitInitialValue));
    }

    /***************************************************************************************/
    /* Subscribers implementations to be used with ViewClicksObservable
    /****************************************************************************************/
    private static class OnSubscribeViewClicks implements Observable.OnSubscribe<OnViewClickEvent> {

        private final boolean emitInitialValue;
        private final View target;

        public OnSubscribeViewClicks(final View target, final boolean emitInitialValue) {
            this.target = target;
            this.emitInitialValue = emitInitialValue;
        }

        @Override
        public void call(final Subscriber<? super OnViewClickEvent> observer) {
            Assertions.assertUiThread();

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    observer.onNext(OnViewClickEvent.create(view));
                }
            };
            target.setOnClickListener(onClickListener);

            if (emitInitialValue) {
                observer.onNext(OnViewClickEvent.create(target));
            }

            final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                @Override
                public void call() {
                    target.setOnClickListener(null);
                }
            });

            observer.add(subscription);
        }
    }
}
