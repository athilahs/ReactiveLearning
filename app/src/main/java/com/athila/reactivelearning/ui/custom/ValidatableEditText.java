package com.athila.reactivelearning.ui.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.athila.reactivelearning.infrastructure.validator.Validator;

/**
 * Created by athila on 10/07/15.
 */
public class ValidatableEditText extends EditText {

    public Validator<String> validator;

    public ValidatableEditText(Context context) {
        super(context);
    }

    public ValidatableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isInputValid() {
        if (validator == null) {
            return !TextUtils.isEmpty(getText());
        }

        return validator.isValid(getText().toString());
    }

    public String getValidationFailedMessage() {
        return validator == null ? null : validator.getExplanationMessage();
    }
}
