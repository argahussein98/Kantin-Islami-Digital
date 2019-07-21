package com.example.android.kantinislamidigital.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.kantinislamidigital.Data.DataCendikia;
import com.example.android.kantinislamidigital.Detail.DetailCendikia;
import com.example.android.kantinislamidigital.R;

import java.util.ArrayList;

public class CendikiaAdapter extends RecyclerView.Adapter<CendikiaAdapter.ViewHolder>  {
    private ArrayList<DataCendikia> listdata;
    private Activity activity;

    public CendikiaAdapter(Activity activity, ArrayList<DataCendikia> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public CendikiaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cendikia, parent, false);
        CendikiaAdapter.ViewHolder vh = new CendikiaAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CendikiaAdapter.ViewHolder holder, final int position) {
        holder.id.setText(listdata.get(position).getId());
        holder.nama_penulis.setText(listdata.get(position).getNamaPenulis());
        holder.judul.setText(listdata.get(position).getJudul());
        holder.tanggal.setText(" "+listdata.get(position).getTanggal());
        final CendikiaAdapter.ViewHolder x=holder;
        Glide.with(activity)
                .load(listdata.get(position).getGambar())
                .into(holder.gambar);
        holder.id.setVisibility(View.GONE);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent KirimData = new Intent(activity, DetailCendikia.class);
                KirimData.putExtra("TGL",listdata.get(position).getTanggal());
                KirimData.putExtra("NAMA",listdata.get(position).getNamaPenulis());
                KirimData.putExtra("JDL",listdata.get(position).getJudul());
                KirimData.putExtra("EMAIL",listdata.get(position).getEmail());
                KirimData.putExtra("TEXT",listdata.get(position).getTextual());
                KirimData.putExtra("GMR",listdata.get(position).getGambar());
                activity.startActivity(KirimData);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, nama_penulis, judul, tanggal;
        private ImageView gambar;

        private LinearLayout detail;

        public ViewHolder(View v) {
            super(v);
            id=(TextView)v.findViewById(R.id.id);
            nama_penulis=(TextView)v.findViewById(R.id.nama_penulis);
            judul=(TextView)v.findViewById(R.id.judul);
            tanggal=(TextView)v.findViewById(R.id.tanggal);
            gambar=(ImageView)v.findViewById(R.id.gambar);

            detail = (LinearLayout)itemView.findViewById(R.id.detail);
        }
    }
}
