package com.example.latihanp11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.latihanp11.helper.DBHelper;

public class DetailActivity extends AppCompatActivity {

    /*RadioButton  rb1, rb2;*/

    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = new DBHelper(this);

        EditText etNama = findViewById(R.id.etNama);
        EditText etAlamat = findViewById(R.id.etAlamat);
        EditText etNoTel = findViewById(R.id.etNoHP);

        Button tombol = findViewById(R.id.btnSubmit);
        tombol.setOnClickListener(view -> {
            String nama = etNama.getText().toString();
            String alamat = etAlamat.getText().toString();
            String noTelp = etNoTel.getText().toString();
            /*rb1 = (RadioButton) findViewById(R.id.rbLaki);
            rb2 = (RadioButton) findViewById(R.id.rbWanita);

            ContentValues contentValues = new ContentValues();
            if(rb1.isChecked()){
                contentValues.put("Jenis Kelamin", rb1.getText().toString());
            } else {
                contentValues.put("Jenis Kelamin", rb2.getText().toString());
            }*/

            if(nama.length()==0 && alamat.length()==0 && noTelp.length()==0){
                Toast.makeText(DetailActivity.this,
                        "Form tidak boleh kosong!",
                        Toast.LENGTH_SHORT).show();
            }else{
                insert(nama, alamat, noTelp);
            }
        });
    }


    private void insert(String nama, String alamat, String noTelp){
        helper.insert(nama,alamat, noTelp);
        Toast.makeText(this, "Data sudah di simpan!", Toast.LENGTH_SHORT).show();
        finish();
    }

}