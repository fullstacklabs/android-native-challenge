package com.fullstacklabs.nodes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.fullstacklabs.nodes.models.NodeResponse;
import com.fullstacklabs.nodes.models.Node;
import com.fullstacklabs.nodes.network.NodeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static String STATUS_ENDPOINT = "/api/v1/status";
    String[] urls = new String[] {
            "https://thawing-springs-53971.herokuapp.com",
            "https://secret-lowlands-62331.herokuapp.com",
            "https://calm-anchorage-82141.herokuapp.com",
            "http://localhost:3002",
    };
    SparseArray<Node> nodes = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNodes();
        ExpandableListView listview = (ExpandableListView) findViewById(R.id.list_view);

        ExpandableListAdapter adapter = new ExpandableListAdapter(this, nodes);
        listview.setAdapter(adapter);

        getNodeStatus();
    }

    private void getNodeStatus() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thawing-springs-53971.herokuapp.com/api/v1/status/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NodeService service = retrofit.create(NodeService.class);

//        service.getNodeStatus("https://thawing-springs-53971.herokuapp.com/api/v1/status/").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                Log.i("Nodes", response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Nodes", t.toString());
//                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
//            }
//        });

        for(int i = 0; i < this.nodes.size(); i++) {
            int key = this.nodes.keyAt(i);
            Node node = this.nodes.get(key);

            service.getNodeStatus(node.url + STATUS_ENDPOINT).enqueue(new Callback<NodeResponse>() {
                @Override
                public void onResponse(Call<NodeResponse> call, retrofit2.Response<NodeResponse> response) {
                    Log.i("Nodes", "code: " + response.code());

                    if (response.isSuccessful()){
                        if (response.body() != null){
                            NodeResponse nodeResponse = response.body();
                            Log.i("Nodes", nodeResponse.nodeName);
                            node.setTitle(nodeResponse.nodeName);
                        }else{
                            Log.i("Nodes", "Error: Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<NodeResponse> call, Throwable t) {
                    Log.e("Nodes", t.toString());
                    Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void createNodes() {
        int size = this.urls.length;
        for (int i = 0; i < size; i++) {
            String title = "Node " + i;
            String url = this.urls[i];
            Node node = new Node(title, url);

            node.blocks.add("Blocks Go Here");
            nodes.append(i, node);
        }
    }
}
