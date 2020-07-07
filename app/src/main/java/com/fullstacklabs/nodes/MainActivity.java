package com.fullstacklabs.nodes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.fullstacklabs.nodes.models.Nodes;
import com.fullstacklabs.nodes.network.NodeService;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    SparseArray<Nodes> nodes = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        ExpandableListView listview = (ExpandableListView) findViewById(R.id.list_view);

        ExpandableListAdapter adapter = new ExpandableListAdapter(this, nodes);
        listview.setAdapter(adapter);

        defineNetworkServices();

    }

    private void defineNetworkServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thawing-springs-53971.herokuapp.com/api/v1/status/")
                .build();

        NodeService service = retrofit.create(NodeService.class);

        service.getNodeStatus("https://thawing-springs-53971.herokuapp.com/api/v1/status/").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("Nodes", response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Nodes", t.toString());
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createData() {
        for (int j = 0; j < 5; j++) {
            Nodes node = new Nodes("Thawning Springs " + j);
            for (int i = 0; i < 5; i++) {
                node.children.add("Sub Item" + i);
            }
            nodes.append(j, node);
        }
    }
}
