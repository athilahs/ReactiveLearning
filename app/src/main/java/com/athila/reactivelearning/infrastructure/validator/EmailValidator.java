package com.athila.reactivelearning.infrastructure.validator;

import android.content.Context;

import com.athila.reactivelearning.R;

import static android.util.Patterns.EMAIL_ADDRESS;

/**
 * Created by athila on 10/07/15.
 */
public class EmailValidator extends Validator<String> {

    public EmailValidator() {
        super();
    }

    public EmailValidator(Context ctx) {
        explanationMessage = ctx.getString(R.string.error_msg_invaid_email);
    }

    @Override
    public boolean isValid(String email) {
        return EMAIL_ADDRESS.matcher(email).matches();
    }
}
