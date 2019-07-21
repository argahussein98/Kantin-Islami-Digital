package com.example.android.kantinislamidigital.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.kantinislamidigital.Data.DataMenu;
import com.example.android.kantinislamidigital.Detail.DetailMenu;
import com.example.android.kantinislamidigital.FormPemesanan;
import com.example.android.kantinislamidigital.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<DataMenu> listdata;
    private Activity activity;
    private Context context;

    public MenuAdapter(Activity activity, ArrayList<DataMenu> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_menu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.id.setText(listdata.get(position).getId());
        holder.nama_penjual.setText(listdata.get(position).getNamaPenjual());
        holder.nama_makanan.setText(listdata.get(position).getNamaMakanan());
        holder.harga.setText(" "+listdata.get(position).getHarga());
        final ViewHolder x=holder;
        Glide.with(activity)
                .load(listdata.get(position).getGambar())
                .into(holder.gambar);
        holder.id.setVisibility(View.GONE);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(activity, "Details "+listdata.get(position).getNamaMakanan(), Toast.LENGTH_SHORT).show();
                String makan=listdata.get(position).getNamaMakanan();
                String link=listdata.get(position).getGambar();
                String shareBody= makan+" tersedia di Kantin Islami Digital Selengkapnya cek disini "+link;

                Intent KirimData = new Intent(activity, DetailMenu.class);
                KirimData.putExtra("MKN",listdata.get(position).getNamaMakanan());
                KirimData.putExtra("HRG",listdata.get(position).getHarga());
                KirimData.putExtra("JUAL",listdata.get(position).getNamaPenjual());
                KirimData.putExtra("EMAIL",listdata.get(position).getEmail());
                KirimData.putExtra("KOM",listdata.get(position).getKomposisi());
                KirimData.putExtra("GMR",listdata.get(position).getGambar());
                KirimData.putExtra("SHARE",shareBody);
                activity.startActivity(KirimData);

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makan=listdata.get(position).getNamaMakanan();
                String link=listdata.get(position).getGambar();
                String shareBody= makan+" tersedia di Kantin Islami Digital Selengkapnya cek disini "+link;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                activity.startActivity(Intent.createChooser(shareIntent,"Share via"));

            }
        });
        holder.beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent KirimData = new Intent(activity, FormPemesanan.class);
                KirimData.putExtra("MKN",listdata.get(position).getNamaMakanan());
                KirimData.putExtra("HRG",listdata.get(position).getHarga());
                KirimData.putExtra("JUAL",listdata.get(position).getNamaPenjual());
                KirimData.putExtra("EMAIL",listdata.get(position).getEmail());
                activity.startActivity(KirimData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,nama_penjual,nama_makanan, harga;
        private ImageView gambar;

        private LinearLayout detail, share, beli;

        public ViewHolder(View v) {
            super(v);
            id=(TextView)v.findViewById(R.id.id);
            nama_penjual=(TextView)v.findViewById(R.id.nama_penjual);
            nama_makanan=(TextView)v.findViewById(R.id.nama_makanan);
            harga=(TextView)v.findViewById(R.id.harga);
            gambar=(ImageView)v.findViewById(R.id.gambar);

            detail = (LinearLayout)itemView.findViewById(R.id.detail);
            share = (LinearLayout)itemView.findViewById(R.id.share);
            beli = (LinearLayout)itemView.findViewById(R.id.beli);
        }
    }
    public void setFilter(List<DataMenu> filterList){
        listdata = new ArrayList<>();
        listdata.addAll(filterList);
        notifyDataSetChanged();
    }

}
