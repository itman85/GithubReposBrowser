package com.nvg.exam.phannguyen.data.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.nvg.exam.phannguyen.data.db.AppDB;
import com.nvg.exam.phannguyen.data.models.converters.GitRepoConverter;
import com.nvg.exam.phannguyen.data.services.api.ApiGitRepoServiceImpl;
import com.nvg.exam.phannguyen.data.services.api.IGitReposWS;
import com.nvg.exam.phannguyen.data.services.local.LocalGitRepoServiceImpl;
import com.nvg.exam.phannguyen.domain.services.IApiGitRepoService;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phannguyen on 7/29/17.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    public IApiGitRepoService provideApiGitReposService(OkHttpClient okHttpClient, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IGitReposWS.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return new ApiGitRepoServiceImpl(retrofit.create(IGitReposWS.class));
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().addHeader("Content-Type",
                            "application/json").addHeader("Accept","application/json; charset=utf-8")
                            .addHeader("Accept-Language","en");

                    return chain.proceed(builder.build());
                })
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public ILocalGitRepoService provideLocalGitReposService(AppDB appDB, GitRepoConverter dataConverter){
        return  new LocalGitRepoServiceImpl(appDB,dataConverter);
    }

    @Provides
    @Singleton
    public AppDB provideAppDB(Application app){
        return  Room.databaseBuilder(app.getApplicationContext(),
                AppDB.class, "gitrepos_db").build();
    }

    @Provides
    @Singleton
    public GitRepoConverter provideGitRepoDataConverter(){
        return  new GitRepoConverter();
    }
}
