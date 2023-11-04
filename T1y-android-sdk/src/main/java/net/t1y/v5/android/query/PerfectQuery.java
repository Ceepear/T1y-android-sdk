package net.t1y.v5.android.query;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
public class PerfectQuery {
    private JSONObject jsonObject = new JSONObject();
    private Body body = new Body();
    public void sort(String title,int t){
        try {
            JSONObject s = new JSONObject();
            s.put(title,(t==1||t==-1)?t:1);
            jsonObject.put("sort",s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void setPage(int t){
        try {
            jsonObject.put("page",t);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void setSize(int t){
        try {
            jsonObject.put("size",t);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject getJSONObject(){
        try {
            jsonObject.put("body",body.body);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
    public Body body(){
        return body;
    }

    @NonNull
    @Override
    public String toString() {
        return getJSONObject().toString();
    }

    public class Body{
        private Where where = new Where();
        private JSONObject body = new JSONObject();
        public synchronized Where createWhere(String title){
            JSONObject jsonObject = null;
            if(body.isNull(title)){
                jsonObject = new JSONObject();
                try {
                    body.put(title,jsonObject);
                    where.init(jsonObject);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    jsonObject = body.getJSONObject(title);
                    where.init(jsonObject);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            return where;
        }
        public class Where{
            private JSONObject object;
            void init(JSONObject jsonObject) {
                this.object = jsonObject;
            }
            public void or(int s){
                try {
                    object.put("$or",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void gt(int s){
                try {
                    object.put("$gt",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void lt(int s){
                try {
                    object.put("$lt",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void gte(int s){
                try {
                    object.put("$gte",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void lte(int s){
                try {
                    object.put("$gte",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void exist(){
                String s = "$exists";
                try {
                    object.put(s,true);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void absent(Object s1){
                String s = "$exists";
                try {
                    object.put(s,false);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void ne(Object s){
                try {
                    object.put("$ne",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void in(Object o){
                try {
                    object.put("$in",o);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void nin(Object o){
                try {
                    object.put("$in",o);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            public void regex(String s){
                try {
                    object.put("$regex",s);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
   }
}
