package com.simonyan51.a51movies.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by simonyan51 on 09.08.2017.
 */

public abstract class CheckMessageHelper {

    public static final String KEY = "CHECK";
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;

    public static Message executeMessage(Handler handler, int state) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, state);
        message.setData(bundle);
        return message;
    }
}
