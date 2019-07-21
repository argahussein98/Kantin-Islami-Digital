package com.example.android.kantinislamidigital.Detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.kantinislamidigital.R;

public class DetailCendikia extends AppCompatActivity {
    TextView penulis, judul, tanggal;
    String tulis, jdl, tgl, img, textual;
    ImageView GambarCendikia;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_cendikia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Artikel");

        penulis = (TextView) findViewById(R.id.nama_penulis);
        tulis = getIntent().getStringExtra("NAMA");
        penulis.setText(tulis);

        judul = (TextView) findViewById(R.id.judul);
        jdl = getIntent().getStringExtra("JDL");
        judul.setText(jdl);

        tanggal = (TextView) findViewById(R.id.tanggal);
        tgl = getIntent().getStringExtra("TGL");
        tanggal.setText(tgl);

        GambarCendikia = (ImageView) findViewById(R.id.gambar);
        img = getIntent().getStringExtra("GMR");
        Glide.with(DetailCendikia.this)
                .load(img)
                .into(GambarCendikia);

        web = findViewById(R.id.textual);
        textual = getIntent().getStringExtra("TEXT");
        web.loadData(textual, "text/html", "UTF-8");
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
