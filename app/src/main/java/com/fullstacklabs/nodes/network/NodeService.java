package com.fullstacklabs.nodes.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NodeService {
    @GET
    public Call<ResponseBody> getNodeStatus(@Url String url);
}
