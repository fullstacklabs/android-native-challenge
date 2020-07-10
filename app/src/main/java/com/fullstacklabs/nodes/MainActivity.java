package com.fullstacklabs.nodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.SparseArray;

import com.fullstacklabs.nodes.databinding.ActivityMainBinding;
import com.fullstacklabs.nodes.models.NodeResponse;
import com.fullstacklabs.nodes.models.Node;
import com.fullstacklabs.nodes.network.NodeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String[] mNodeUrls = new String[] {
            "https://thawing-springs-53971.herokuapp.com",
            "https://secret-lowlands-62331.herokuapp.com",
            "https://calm-anchorage-82141.herokuapp.com",
            "http://localhost:3002",
    };
    SparseArray<Node> mNodes = new SparseArray<>();
    ExpandableRecyclerAdapter mAdapter;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        createNodes();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ExpandableRecyclerAdapter(mNodes);
        recyclerView.setAdapter(mAdapter);

        getNodeStatus();
    }

    private void getNodeStatus() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mNodeUrls[0]) // How to avoid setting unnecessary baseUrl
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NodeService service = retrofit.create(NodeService.class);

        for(int i = 0; i < this.mNodes.size(); i++) {
            int key = this.mNodes.keyAt(i);
            Node node = this.mNodes.get(key);

            String GET_STATUS_ENDPOINT = "/api/v1/status";
            node.setStatus(getString(R.string.loading_status));
            mAdapter.notifyDataSetChanged();
            service.getNodeStatus(node.getUrl() + GET_STATUS_ENDPOINT).enqueue(new Callback<NodeResponse>() {
                @Override
                public void onResponse(Call<NodeResponse> call, retrofit2.Response<NodeResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            NodeResponse nodeResponse = response.body();

                            node.setTitle(nodeResponse.nodeName);
                            node.setStatus(getString(R.string.online_status));
                        } else {
                            node.setStatus(getString(R.string.offline_status));
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<NodeResponse> call, Throwable t) {
                    node.setStatus(getString(R.string.offline_status));
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void createNodes() {
        int size = this.mNodeUrls.length;
        for (int i = 0; i < size; i++) {
            String title = "Node " + i;
            String url = this.mNodeUrls[i];
            Node node = new Node(title, url, getString(R.string.offline_status));

            node.blocks.add("Blocks Go Here");
            mNodes.append(i, node);
        }
    }
}
