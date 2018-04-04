package com.letsdogether.dogether;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Rakeshk on 03-04-2018.
 */

interface Api {

    //@GET("{owner}/issues")
    @GET("issues")

    //Observable<List<IssuesModel>> getIssues(@Path("owner") String strOwner);
    Observable<List<IssuesModel>> getIssues();
}
