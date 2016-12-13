package com.kelompok5.fishify.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by bradhawk on 12/13/2016.
 */

public abstract class Validator {

    public static boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isEmailValid(CharSequence text) {
        return Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public static boolean isMoreThanZero(CharSequence text) {
        return !isEmpty(text) && Float.parseFloat(text.toString()) > 0.0f;
    }

}
