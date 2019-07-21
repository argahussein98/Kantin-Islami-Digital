package com.example.android.kantinislamidigital.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.kantinislamidigital.FormPemesanan;
import com.example.android.kantinislamidigital.R;

public class DetailMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Makanan");

        TextView penjual = (TextView) findViewById(R.id.nama_penjual);
        final String jual = getIntent().getStringExtra("JUAL");
        penjual.setText(jual);

        TextView makanan = (TextView) findViewById(R.id.nama_makanan);
        final String makan = getIntent().getStringExtra("MKN");
        makanan.setText(makan);

        TextView harga = (TextView) findViewById(R.id.harga);
        final String har = getIntent().getStringExtra("HRG");
        harga.setText(har);

        ImageView GambarJudul = (ImageView) findViewById(R.id.gambar);
        String img = getIntent().getStringExtra("GMR");
        Glide.with(DetailMenu.this)
                .load(img)
                .into(GambarJudul);

        WebView komposisi = (WebView) findViewById(R.id.komposisi);
        String kom = getIntent().getStringExtra("KOM");
        komposisi.loadData(kom, "text/html", "UTF-8");

        LinearLayout share = (LinearLayout)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sh = getIntent().getStringExtra("SHARE");

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, sh);
                startActivity(shareIntent);
            }
        });
        final String email = getIntent().getStringExtra("EMAIL");

        LinearLayout beli = (LinearLayout)findViewById(R.id.beli);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent KirimData = new Intent(DetailMenu.this, FormPemesanan.class);
                KirimData.putExtra("MKN",makan);
                KirimData.putExtra("HRG",har);
                KirimData.putExtra("JUAL",jual);
                KirimData.putExtra("EMAIL",email);
                startActivity(KirimData);
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
