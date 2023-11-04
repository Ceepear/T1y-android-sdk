package net.t1y.v5.android.strategy;
import net.t1y.v5.android.DataBean;
import net.t1y.v5.android.query.DeleteCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteStrategy implements RootStrategy {
    @Override
    public Runnable dataIsSuccess(Object o, String msg, JSONObject data, Object other) throws JSONException {
        if(other != null){
            DataBean bean = (DataBean) other;
            bean.setObjectId(null);
        }
        return ()->{
            ((DeleteCallback) o).onCallback(200,msg);
        };
    }

    @Override
    public Runnable dataIsNull(Object object) {
        return ()->{
            ((DeleteCallback) object).onCallback(400,"网络错误！");
        };
    }
    @Override
    public Runnable dataHaveError(Object o, int code, String msg) {
        return ()->{
            ((DeleteCallback) o).onCallback(code,msg);
        };
    }
    @Override
    public Runnable dataNoJson(Object object) {
        return ()->{
            ((DeleteCallback) object).onCallback(400,"服务器错误！");
        };
    }
}
