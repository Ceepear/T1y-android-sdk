package net.t1y.v5.android.batch;

import net.t1y.v5.android.DataBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CreateBatch<T extends DataBean> extends BaseBatch<T>{

    public void addObjectId(JSONArray objectId) throws JSONException {
        for(int t = 0;t<arrayList.size();t++){
            arrayList.get(t).setObjectId(objectId.getString(t));
        }
    }
    public interface OnFilter<T>{
        void on(T t);
    }
    public void filter(OnFilter<T> filter){
        for(int t = 0;t<arrayList.size();t++){
            filter.on(arrayList.get(t));
        }
    }
}
