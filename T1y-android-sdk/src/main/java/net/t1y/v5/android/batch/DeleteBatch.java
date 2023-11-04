package net.t1y.v5.android.batch;
import net.t1y.v5.android.DataBean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteBatch<T extends DataBean> extends BaseBatch<T>{
    public String getObjects(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int t = 0;t<arrayList.size();t++){
            stringBuilder.append("\"");
            T a = arrayList.get(t);
            stringBuilder.append(a.getObjectId());
            stringBuilder.append("\"");
            if(t != arrayList.size()-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
