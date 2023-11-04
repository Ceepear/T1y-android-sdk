package net.t1y.v5.android.strategy;
import net.t1y.v5.android.DataBean;
import net.t1y.v5.android.T1Cloud;
import net.t1y.v5.android.batch.CreateBatch;
import net.t1y.v5.android.query.CreateCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateStrategy implements RootStrategy {
    @Override
    public Runnable dataIsNull(Object object) {
        return ()->{
            ((CreateCallback) object).onCallback(400,"网络错误！");
        };
    }
    @Override
    public Runnable dataHaveError(Object o, int code, String msg) {
        return ()->{
            ((CreateCallback) o).onCallback(code,msg);
        };
    }

    @Override
    public Runnable dataIsSuccess(Object o, String msg, JSONObject data, Object other) throws JSONException {
        if(data != null){

            if(other instanceof DataBean){
                String id = data.getString("objectId");
                DataBean bean = (DataBean) other;
                bean.setObjectId(id);
            }else if(other instanceof CreateBatch){
                CreateBatch c = (CreateBatch) other;
                JSONArray array = data.getJSONArray("objectId");
                c.addObjectId(array);
                c.removeAll();
            }
        }
        return ()->{
            ((CreateCallback) o).onCallback(200,msg);
        };
    }

    @Override
    public Runnable dataNoJson(Object object) {
        return ()->{
            ((CreateCallback) object).onCallback(400,"服务器错误！");
        };
    }
}
