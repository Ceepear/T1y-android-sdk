package net.t1y.v5.android.strategy;

import org.json.JSONException;
import org.json.JSONObject;

public interface RootStrategy {
    Runnable dataIsNull(Object object);
    Runnable dataNoJson(Object object);
    Runnable dataHaveError(Object o, int code, String msg);
    Runnable dataIsSuccess(Object o, String msg,JSONObject data, Object other) throws JSONException;
}
