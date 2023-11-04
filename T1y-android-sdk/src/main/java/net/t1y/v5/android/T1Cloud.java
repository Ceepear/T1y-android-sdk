package net.t1y.v5.android;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T1Cloud {
    static Gson getGson() {
        isInit();
        return that.gson;
    }

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setExclusionStrategies(new ExclusionStrategy() {
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(FooAnnotation.class) != null;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(FooAnnotation.class) != null;
        }
    }).create();
    private ExecutorService thread = Executors.newSingleThreadExecutor();
    private StrategyFactory strategyFactory = new StrategyFactory();
    static StrategyFactory getStrategyFactory() {
        return that.strategyFactory;
    }

    static void travelThread(Runnable runnable){
        isInit();
        that.thread.execute(runnable);
    }
    private static T1Cloud that;
    private T1YClient t1YClient;
    private LifecycleCallbacks lifecycleCallbacks;
    public static void init(Application context,Option option){that = new T1Cloud(context,option);}
    private T1Cloud(Application context, Option option){
        lifecycleCallbacks = new LifecycleCallbacks();
        context.registerActivityLifecycleCallbacks(lifecycleCallbacks);
        this.t1YClient = new T1YClient(option.Url,option.Application_ID,option.APIKey,option.getSecretKey());
    }
    public static T1YClient client(){
        isInit();
        return that.t1YClient;
    }
    private static void isInit(){
        if(that==null){
            throw new NullPointerException("T1Cloud is not initialized, please execute T1Cloud. init() under Application！");
        }
    }
    static void regressionThread(Runnable runnable){
        isInit();
        that.lifecycleCallbacks.post(runnable);
    }
    private class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private List<Activity> activities = new ArrayList<>();
        private Activity activity;
        public void post(Runnable runnable){
            activity.runOnUiThread(runnable);
        }
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            activities.add(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            if(this.activity == activity){
                this.activity = null;
            }
            activities.remove(activity);
            if(activities.isEmpty()){
                T1Cloud.recovery();
            }
        }
    }
    private static void recovery(){
        that.gson = null;
        that.thread.shutdown();
        that.thread = null;
        that.t1YClient = null;
        that.lifecycleCallbacks = null;
        that = null;
    }
}
