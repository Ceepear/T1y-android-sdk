package net.t1y.v5.android;

import android.webkit.SafeBrowsingResponse;

import net.t1y.v5.android.batch.CreateBatch;
import net.t1y.v5.android.batch.DeleteBatch;
import net.t1y.v5.android.query.CreateCallback;
import net.t1y.v5.android.query.DeleteCallback;
import net.t1y.v5.android.query.QueryCallback;
import net.t1y.v5.android.query.PerfectQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class T1YQuery {
    public <T extends DataBean> void getDataByWhere(Class<T> tClass, PerfectQuery perfectQuery, QueryCallback<T> callback){
        T1Cloud.travelThread(()->{
            String s = perfectQuery.toString();
            System.out.println(s);
            String string = T1Cloud.client().query(tClass.getSimpleName(),s);
            Runnable runnable = T1Cloud.getStrategyFactory().load("query", string, (QueryCallback<String>) (code, msg, data1) -> {
                System.out.println(msg);
            }, null);
            T1Cloud.regressionThread(runnable);
        });
    }
    public <T extends DataBean> void getDataAll(Class<T> tClass,int page,int size, QueryCallback<List<T>> queryCallback){
        T1Cloud.travelThread(()->{
            String data = T1Cloud.client().readAll(tClass.getSimpleName(),page,size);
            Runnable runnable = T1Cloud.getStrategyFactory().load("query", data, (QueryCallback<String>) (code, msg, data1) -> {
                try {
                    JSONArray array = new JSONArray(data1);
                    List<T> list1 = new ArrayList<>();
                    for(int t=0;t<array.length();t++){
                        JSONObject object = array.getJSONObject(t);
                        list1.add(T1Cloud.getGson().fromJson(object.toString(),tClass));
                    }
                    queryCallback.onCallback(code,msg,list1);
                } catch (JSONException e) {
                    queryCallback.onCallback(code,e.getMessage(),null);
                }
            }, null);
            T1Cloud.regressionThread(runnable);
        });
    }
    public <T extends DataBean> void getDataById(Class<T> tClass,String objectId, QueryCallback<T> callback){
        T1Cloud.travelThread(()->{
            String data = T1Cloud.client().readOne(tClass.getSimpleName(), objectId);
            Runnable runnable = T1Cloud.getStrategyFactory().load("query", data, (QueryCallback<String>) (code, msg, data1) -> {
                if(code == 200){
                    callback.onCallback(code,msg,T1Cloud.getGson().fromJson(data1,tClass));
                }else{
                    callback.onCallback(code,msg,null);
                }
            }, null);
            T1Cloud.regressionThread(runnable);
        });
    }
    public <T extends DataBean> void createAll(CreateBatch<T> batch, CreateCallback callback){
        T1Cloud.travelThread(()->{
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            batch.filter(t -> {
                String json = T1Cloud.getGson().toJson(t);
                sb.append(json);
                sb.append(",");
            });
            String s = sb.substring(0,sb.length()-1);
            String data = T1Cloud.client().createAll(batch.getTableName(),s+"]");
            Runnable runnable = T1Cloud.getStrategyFactory().load("create", data, callback, batch);
            T1Cloud.regressionThread(runnable);
        });
    }
    public <T extends DataBean> void deleteAll(DeleteBatch<T> tDeleteBatch, DeleteCallback callback){
        T1Cloud.travelThread(()->{
            String data = T1Cloud.client().deleteAll(tDeleteBatch.getTableName(), tDeleteBatch.getObjects());
            Runnable runnable = T1Cloud.getStrategyFactory().load("delete", data, (DeleteCallback) (code, msg) -> {
                if(code == 200){
                    tDeleteBatch.removeAll();
                }
                callback.onCallback(code,msg);
            }, null);
            T1Cloud.regressionThread(runnable);
        });
    }

}
