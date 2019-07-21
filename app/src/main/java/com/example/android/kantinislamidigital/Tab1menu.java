package com.example.android.kantinislamidigital;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.kantinislamidigital.Adapter.MenuAdapter;
import com.example.android.kantinislamidigital.Data.DataMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab1menu extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String URL = "http://kantin-digital.nand-project.com/admin/menu/";
    private MenuAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<DataMenu> listdata;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1menu, container, false);
        return rootView;
    }
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.menu_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setHasOptionsMenu(true);

        listdata = new ArrayList<DataMenu>();

        recyclerAdapter = new MenuAdapter(getActivity(),listdata);
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
                                    DataMenu item = new DataMenu();
                                    item.setId(data.getString("id"));
                                    item.setNamaMakanan(data.getString("nama_makanan"));
                                    item.setHarga(data.getString("harga"));
                                    item.setNamaPenjual(data.getString("nama_penjual"));
                                    item.setEmail(data.getString("email"));
                                    item.setKomposisi(data.getString("komposisi"));
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
    // ---------------------------------------------------------------------------->
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView  = new SearchView(getActivity());

        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                //Data akan berubah saat user menginputkan text/kata kunci pada SearchView
                nextText = nextText.toLowerCase();
                List<DataMenu> dataFilter = new ArrayList<>();
                for(DataMenu data : listdata){
                    String nama = data.getNamaMakanan().toLowerCase();

                    String user = data.getNamaPenjual().toLowerCase();
                    if (user.contains(nextText) || nama.contains(nextText)){
                        dataFilter.add(data);
                    }
                }
                if(recyclerAdapter != null){
                    recyclerAdapter.setFilter(dataFilter);
                }else {
                    return false;
                }
                return true;

            }
        });
        searchItem.setActionView(searchView);

    }

}
