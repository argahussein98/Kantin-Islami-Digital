package com.example.android.kantinislamidigital;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.kantinislamidigital.Adapter.CendikiaAdapter;
import com.example.android.kantinislamidigital.Data.DataCendikia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab2Cendikia extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String URL = "http://kantin-digital.nand-project.com/admin/cendikia/";
    private CendikiaAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<DataCendikia> listdata;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2cendikia, container, false);
        return rootView;
    }
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.cendikia_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listdata = new ArrayList<DataCendikia>();

        recyclerAdapter = new CendikiaAdapter(getActivity(),listdata);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                ambidata();
            }
        });
    }
    public void ambidata(){
        mSwipeRefreshLayout.setRefreshing(true);
        JsonArrayRequest arrReq = new JsonArrayRequest(URL+"/json/script.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = response.length(); i >= 0; i--) {
                                try {
                                    JSONObject data = response.getJSONObject(i);
                                    DataCendikia item = new DataCendikia();
                                    item.setId(data.getString("id"));
                                    item.setTanggal(data.getString("tanggal"));
                                    item.setNamaPenulis(data.getString("nama_penulis"));
                                    item.setJudul(data.getString("judul"));
                                    item.setEmail(data.getString("email"));
                                    item.setTextual(data.getString("text"));
                                    item.setGambar(URL+"images/"+data.getString("gambar"));
                                    listdata.add(item);
                                    recyclerAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            Toast.makeText(getActivity(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Aplikasi tidak meresponse", Toast.LENGTH_SHORT).show();

//                ProgressBar Pbar2 = (ProgressBar)findViewById(R.id.progress);
//                if (error != null){
//                    Pbar2.setVisibility(View.GONE);
//                }else{
//                    Pbar2.setVisibility(View.VISIBLE);
//                }

                    }
                }) {
        };
        Volley.newRequestQueue(getActivity()).add(arrReq);
    }

    @Override
    public void onRefresh() {
        if(recyclerAdapter != null){
            listdata.clear();
            recyclerAdapter.notifyDataSetChanged();
            ambidata();
        }else {
            ambidata();
        }
    }
}