package com.fullstacklabs.nodes.network;

import com.fullstacklabs.nodes.models.NodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NodeService {
    @GET
    public Call<NodeResponse> getNodeStatus(@Url String url);
}
