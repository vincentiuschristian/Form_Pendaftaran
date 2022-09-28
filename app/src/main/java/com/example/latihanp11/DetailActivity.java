package com.example.latihanp11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.latihanp11.helper.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.Task;

public class DetailActivity extends AppCompatActivity {

    private DBHelper helper;
    private FusedLocationProviderClient client;
    private MapFragment mapFragment;

    String longtitude, latitude;

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

            if(nama.length()==0 && alamat.length()==0 && noTelp.length()==0){
                Toast.makeText(DetailActivity.this,
                        "Form tidak boleh kosong!",
                        Toast.LENGTH_SHORT).show();
            }else{
                insert(nama, alamat, noTelp);
            }
        });

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,  Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        Task<Location> task =  client.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                mapFragment.getMapAsync(googleMap -> {
                    double lat = location.getLatitude();
                    double longt = location.getLongitude();
                    latitude = String.valueOf(lat);
                    longtitude = String.valueOf(longt);
                    Button btnLokasi = findViewById(R.id.btnLokasi);
                    btnLokasi.setOnClickListener(view -> {
                        TextView tvLokasi = findViewById(R.id.tvLokasi);

                        tvLokasi.setText("Lokasi : Latitude "+latitude+" Longtitude :"+longtitude+"");

                    });
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    private void insert(String nama, String alamat, String noTelp){
        helper.insert(nama,alamat, noTelp);
        Toast.makeText(this, "Data sudah di simpan!", Toast.LENGTH_SHORT).show();
        finish();
    }

}