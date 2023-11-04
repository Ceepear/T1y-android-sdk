package net.t1y.v5.android;
import net.t1y.v5.android.strategy.CreateStrategy;
import net.t1y.v5.android.strategy.DeleteStrategy;
import net.t1y.v5.android.strategy.QueryStrategy;
import net.t1y.v5.android.strategy.RootStrategy;
import net.t1y.v5.android.strategy.UpdateStrategy;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
class StrategyFactory {
    private Map<String, RootStrategy> strategyMap = new HashMap<>();
    public StrategyFactory(){
        strategyMap.put("delete",new DeleteStrategy());
        strategyMap.put("query",new QueryStrategy());
        strategyMap.put("update",new UpdateStrategy());
        strategyMap.put("create",new CreateStrategy());
    }
    public Runnable load(String type,String data,Object callback,Object other) {
        RootStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new NullPointerException("Please don't make things out of nothing!");
        }
        if (data == null) {
            return strategy.dataIsNull(callback);
        } else if (!data.startsWith("{")) {
            return strategy.dataNoJson(callback);
        } else {
            try {
                JSONObject object = new JSONObject(data);
                int code = object.getInt("code");
                String msg = object.getString("message");
                JSONObject data1;
                if(object.isNull("data")){
                    data1 = null;
                }else{
                    data1 = object.getJSONObject("data");
                }
                if (code == 400) {
                    return strategy.dataHaveError(callback, code, msg);
                } else if (code == 200) {
                    return strategy.dataIsSuccess(callback, msg,data1,other);
                } else {
                    return strategy.dataHaveError(callback, code, msg);
                }
            } catch (JSONException e) {
                return strategy.dataHaveError(callback, 400, e.getMessage());
            }
        }
    }


}
