package com.nvg.exam.phannguyen.data.services.api;

import com.nvg.exam.phannguyen.data.models.api.ApiGitRepoModel;
import com.nvg.exam.phannguyen.data.models.api.CoinTickerModel;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by phannguyen on 7/29/17.
 */

public interface IGitReposWS {
    public static final String END_POINT = "https://api.binance.com/";//"https://api.github.com/";

    @GET("/users/sr/repos")
    Observable<List<ApiGitRepoModel>> getGitReposByPage(@Query("per_page") int perPage, @Query("page") int page);

    @GET("/repos/sr/{name}")
    Observable<ApiGitRepoModel> getDetailGitRepo(@Path("name")String name);

//    @GET("/api/v1/ticker/24hr?symbol=ETHBTC")
//    Observable<Response<CoinTickerModel>> getCoinsTickerData();

}
