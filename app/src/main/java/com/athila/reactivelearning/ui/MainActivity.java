package com.athila.reactivelearning.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.athila.reactivelearning.R;
import com.athila.reactivelearning.infrastructure.validator.EmailValidator;
import com.athila.reactivelearning.infrastructure.validator.PasswordValidator;
import com.athila.reactivelearning.reactive.events.OnFocusChangeEvent;
import com.athila.reactivelearning.reactive.events.OnViewClickEvent;
import com.athila.reactivelearning.reactive.observables.ViewClicksObservable;
import com.athila.reactivelearning.reactive.observables.ViewFocusChangesObservable;
import com.athila.reactivelearning.ui.custom.ValidatableEditText;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private ValidatableEditText etLogin;
    private ValidatableEditText etPassword;
    private Button btnLogin;

    private Observable<OnFocusChangeEvent> loginFocusObservable;
    private Observable<OnFocusChangeEvent> passwordFocusObservable;
    private Observable<OnViewClickEvent> loginButtonClickObservable;

    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUI();
        initialize();
        subscribeValidators();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
    }

    private void loadUI() {
        etLogin = (ValidatableEditText)findViewById(R.id.et_login);
        etPassword = (ValidatableEditText)findViewById(R.id.et_password);
        btnLogin = (Button)findViewById(R.id.btn_login);
    }

    private void initialize() {
        etLogin.validator = new EmailValidator(this);
        etPassword.validator = new PasswordValidator(this);

        loginFocusObservable = ViewFocusChangesObservable.focusChanges(etLogin);
        passwordFocusObservable = ViewFocusChangesObservable.focusChanges(etPassword);
        loginButtonClickObservable = ViewClicksObservable.viewClicks(btnLogin);

        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }

    }

    private void subscribeValidators() {
        Subscription focusChangesSubscriptions = Observable.merge(loginFocusObservable, passwordFocusObservable)
                .subscribe(new Observer<OnFocusChangeEvent>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(OnFocusChangeEvent onFocusChangeEvent) {
                        if (!onFocusChangeEvent.hasFocus()) {
                            ValidatableEditText field = (ValidatableEditText) onFocusChangeEvent.view();
                            validateField(field);
                        }
                    }
                });
        subscriptions.add(focusChangesSubscriptions);

        Subscription loginButtonSubscription = loginButtonClickObservable.subscribe(new Observer<OnViewClickEvent>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(OnViewClickEvent onViewClickEvent) {
                validateAllFields();
            }
        });
        subscriptions.add(loginButtonSubscription);
    }

    private void validateField(ValidatableEditText field) {
        if (!field.isInputValid()) {
            field.setError(field.getValidationFailedMessage());
        }
    }

    private void validateAllFields() {
        validateField(etLogin);
        validateField(etPassword);
    }
}
