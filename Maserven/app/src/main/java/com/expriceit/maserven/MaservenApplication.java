package com.expriceit.maserven;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarContext;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by stalyn on 21/11/2017.
 */

public class MaservenApplication extends Application {
    private static MaservenApplication mInstance;

    private Retrofit restAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Inicializando BD","--------->>>");
        SugarContext.init(this);
        mInstance=this;
        //Comentado por Sgranda  mDatabaseHelper = new Database(this);

        // Try to restrict data dir file permissions to owner (this app's UID) only. This mitigates the
        // security vulnerability where SQLite database transaction journals are world-readable.
        // NOTE: This also prevents all files in the data dir from being world-accessible, which is fine
        // because this application does not need world-accessible files.
       /* try {
            FileUtilities.restrictAccessToOwnerOnly(
                    getApplicationContext().getApplicationInfo().dataDir);
        } catch (Throwable e) {
            // Ignore this exception and don't log anything to avoid attracting attention to this fix
        }*/
        //Setear variable retrofit
        restAdapter = retrofitOTP();

        // During test runs the injector may have been configured already. Thus we take care to avoid
        // overwriting any existing configuration here.
        //DependencyInjector.configureForProductionIfNotConfigured(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
    public static MaservenApplication getApplication() {
        return mInstance;
    }

   /* public Dao<PinEntities, Integer> getmPinDao() throws SQLException {
        if (mPinDao == null)
            mPinDao = mDatabaseHelper.getDao(PinEntities.class);
        return mPinDao;
    }*/

    private Retrofit retrofitOTP(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit client=new Retrofit.Builder()
                .baseUrl(getString(R.string.server_localhost))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return client;
    }

    public Retrofit getRestAdapter(){
        return this.restAdapter;
    }


}

