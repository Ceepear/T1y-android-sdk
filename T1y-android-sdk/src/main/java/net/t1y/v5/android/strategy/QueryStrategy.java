package net.t1y.v5.android.strategy;

import net.t1y.v5.android.DataBean;
import net.t1y.v5.android.T1Cloud;
import net.t1y.v5.android.query.QueryCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class QueryStrategy implements RootStrategy {
    @Override
    public Runnable dataIsNull(Object object) {
        return ()->{
            ((QueryCallback<?>) object).onCallback(400,"网络错误！",null);
        };
    }

    @Override
    public Runnable dataNoJson(Object object) {
       return ()->{
            ((QueryCallback<?>) object).onCallback(400,"服务器错误！",null);
        };
    }

    @Override
    public Runnable dataHaveError(Object o, int code, String msg) {
        return ()->{
            ((QueryCallback<?>) o).onCallback(code,msg,null);
        };
    }

    @Override
    public Runnable dataIsSuccess(Object o, String msg, JSONObject data, Object other) throws JSONException {
        String data2 = data.getString("data").toString();
        return ()->{
            ((QueryCallback) o).onCallback(200,msg,data2);
        };
    }
}
