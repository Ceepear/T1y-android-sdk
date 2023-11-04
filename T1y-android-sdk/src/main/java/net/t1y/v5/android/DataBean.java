package net.t1y.v5.android;
import android.provider.ContactsContract;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.t1y.v5.android.query.CreateCallback;
import net.t1y.v5.android.query.DeleteCallback;
import net.t1y.v5.android.query.UpdateCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBean {
    @FooAnnotation
    private JSONObject updateCachePool = new JSONObject();
    private String _id;

    public final String get_id() {
        return _id;
    }

    protected final void updateCache(String key, Object data){
        if(key == null && data == null){
            this.updateCachePool = new JSONObject();
            System.gc();
        }
        try {
            this.updateCachePool.put(key,data);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public final String getObjectId() {
        return this._id;
    }
    public final void setObjectId(String string){
        if(string == null){
            this._id = null;
        }else if(this._id == null){
            this._id = string;
        }else{
            return;
        }
    }
    public final void update(UpdateCallback updateCallback){
        if(this._id == null){
            updateCallback.onCallback(400,"该条目还未创建！");
        }
        if(updateCachePool.length() == 0){
            T1Cloud.travelThread(()->{
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("$set",T1Cloud.getGson().toJsonTree(DataBean.this));
                String data = T1Cloud.client().updateOne(this.getClass().getSimpleName(), this._id, jsonObject);
                Runnable runnable =  T1Cloud.getStrategyFactory().load("update",data,updateCallback,DataBean.this);
                T1Cloud.regressionThread(runnable);
            });
        }else{
            T1Cloud.travelThread(()->{
                try {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("$set",updateCachePool);
                    String data = T1Cloud.client().updateOne(this.getClass().getSimpleName(), this._id,jsonObject1.toString());
                    Runnable runnable =  T1Cloud.getStrategyFactory().load("update",data,updateCallback,DataBean.this);
                    T1Cloud.regressionThread(runnable);
                } catch (JSONException e) {
                    T1Cloud.regressionThread(()->{
                        updateCallback.onCallback(400,e.getMessage());
                    });
                }
            });

        }

    }
    public final void create(CreateCallback callback){
        if(this._id != null){
            callback.onCallback(400,"该条目已经创建过了");
            return;
        }
        T1Cloud.travelThread(()->{
            String data = T1Cloud.client().createOne(this.getClass().getSimpleName(),this);
            Runnable runnable = T1Cloud.getStrategyFactory().load("create",data,callback,DataBean.this);
            T1Cloud.regressionThread(runnable);
        });
    }
    public final void delete(DeleteCallback deleteCallback){
        if(this._id == null){
            deleteCallback.onCallback(400,"该条目还未创建！");
            return;
        }
        T1Cloud.travelThread(()->{
            String data = T1Cloud.client().deleteOne(this.getClass().getSimpleName(),this._id);
            Runnable runnable = T1Cloud.getStrategyFactory().load("delete",data,deleteCallback,DataBean.this);
            T1Cloud.regressionThread(runnable);
        });
    }
}
