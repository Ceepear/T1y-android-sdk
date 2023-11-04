package net.t1y.v5.android.strategy;

import net.t1y.v5.android.DataBean;
import net.t1y.v5.android.query.DeleteCallback;
import net.t1y.v5.android.query.UpdateCallback;
import net.t1y.v5.android.query.UpdateCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateStrategy implements RootStrategy {
    @Override
    public Runnable dataIsNull(Object object) {
        return ()->{
            ((UpdateCallback) object).onCallback(400,"网络错误！");
        };
    }
    @Override
    public Runnable dataHaveError(Object o, int code, String msg) {
        return ()->{
            ((UpdateCallback) o).onCallback(code,msg);
        };
    }

    @Override
    public Runnable dataIsSuccess(Object o, String msg, JSONObject data, Object other) throws JSONException {
        DataBean bean = (DataBean) other;
        bean.updateCache(null,null);
        return ()->{
            ((UpdateCallback) o).onCallback(200,msg);
        };
    }

    @Override
    public Runnable dataNoJson(Object object) {
        return ()->{
            ((UpdateCallback) object).onCallback(400,"网络错误！");
        };
    }
}
