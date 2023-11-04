package net.t1y.v5.android.query;
public interface QueryCallback<T> {
    void onCallback(int code,String msg,T data);
}
