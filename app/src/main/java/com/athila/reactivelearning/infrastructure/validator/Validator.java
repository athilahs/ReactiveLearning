package com.athila.reactivelearning.infrastructure.validator;

/**
 * Created by athila on 10/07/15.
 */
public abstract class Validator<T> {
    protected String explanationMessage;
    public abstract boolean isValid(T target);

    public String getExplanationMessage() {
        return explanationMessage;
    }
}
