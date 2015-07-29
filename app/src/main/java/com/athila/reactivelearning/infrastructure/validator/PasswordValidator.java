package com.athila.reactivelearning.infrastructure.validator;

import android.content.Context;
import android.text.TextUtils;

import com.athila.reactivelearning.R;

/**
 * Created by athila on 10/07/15.
 */
public class PasswordValidator extends Validator<String> {

    public PasswordValidator() {
        super();
    }

    public PasswordValidator(Context ctx) {
        explanationMessage = ctx.getString(R.string.error_msg_invaid_password);
    }

    @Override
    public boolean isValid(String password) {
        return !TextUtils.isEmpty(password);
    }
}
