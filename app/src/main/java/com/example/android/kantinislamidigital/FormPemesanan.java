package com.example.android.kantinislamidigital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormPemesanan extends AppCompatActivity {
    int quantity = 1, meja=1;
    private String har, name, email, pesan;
    private EditText text;
    private TextView harga, pesanan;
    private Button increment, decrement, MejaMinus, MejaPlus;

    int hargatot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pemesanan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Form Pemesanan");

        pesanan = (TextView) findViewById(R.id.pesanan);
        pesan = getIntent().getStringExtra("MKN");
        pesanan.setText(pesan);

        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });

        MejaMinus = findViewById(R.id.meja_minus);
        MejaPlus = findViewById(R.id.meja_plus);


        MejaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MejaMinus();
            }
        });

        MejaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MejaPlus();
            }
        });

        harga = (TextView) findViewById(R.id.harga);
        har = getIntent().getStringExtra("HRG");
        harga.setText(har);
        hargatot= Integer.parseInt(har);
    }

    public void submitOrder(View view) {
        text = findViewById(R.id.name_field);
        name = text.getText()+"";

        String priceMessage=createOrderSummary(name, hargatot);

        email = getIntent().getStringExtra("EMAIL");

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "+email)); //hanya email app yang bisa menangani ini
        intent.putExtra(Intent.EXTRA_SUBJECT,name+" pesan "+ pesan);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    private String createOrderSummary(String name, int hargatot){
        String priceMessage="Nama    : "+name;
        priceMessage +="\nMeja      : "+meja;
        priceMessage +="\nJumlah : "+quantity;
        priceMessage +="\nTotal      : Rp "+hargatot;
        priceMessage +="\nThaks You!";
        return priceMessage;
    }

    public void increment() {
        if(quantity==100){
            // Show an error message as a toast
            Toast.makeText(this, "kamu tidak dapat memesan lebih dari 100 pesanan", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity+1;
        displayQuantity(quantity);
        hargatot = quantity*Integer.parseInt(har);
        displayPrice(hargatot);
    }

    public void decrement() {
        if(quantity==1){
            // Show an error message as a toast
            Toast.makeText(this, "kamu tidak dapat memesan kurang dari 1 pesanan", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity-1;
        displayQuantity(quantity);
        hargatot = quantity*Integer.parseInt(har);
        displayPrice(hargatot);
    }

    public void MejaPlus() {
        if(meja==100){
            // Show an error message as a toast
            Toast.makeText(this, "kamu tidak dapat memesan lebih dari 100 pesanan", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        meja = meja+1;
        displayMeja(meja);
    }

    public void MejaMinus() {
        if(meja==1){
            Toast.makeText(this, "kamu tidak dapat memesan kurang dari 1 pesanan", Toast.LENGTH_SHORT).show();
            return;
        }
        meja = meja-1;
        displayMeja(meja);
    }
    private void displayMeja(int numberofMeja) {
        TextView mejaTextView = (TextView) findViewById(R.id.no_meja);
        mejaTextView.setText("" + numberofMeja);
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    private void displayPrice(int numberOfPrices) {
        harga.setText(numberOfPrices+"");
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
