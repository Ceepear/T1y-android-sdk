package net.t1y.v5.android.batch;
import net.t1y.v5.android.DataBean;
import java.util.ArrayList;
import java.util.List;

public class BaseBatch<T extends DataBean> {
   protected List<T> arrayList = new ArrayList<>();
    String tableName = null;
    public void put(T dataBean){
        if(tableName == null){
            tableName =  dataBean.getClass().getSimpleName();
        }
        arrayList.add(dataBean);
    }
    public void put(List<T> ts){
        if(!ts.isEmpty()){
            tableName = ts.get(0).getClass().getSimpleName();
        }
        arrayList.addAll(ts);
    }

    public String getTableName() {
        return tableName;
    }
    public void remove(T data){
        arrayList.remove(data);
    }
    public void removeAll(){
        arrayList.clear();
    }
}
