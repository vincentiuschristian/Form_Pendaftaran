package com.example.latihanp11;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.latihanp11.helper.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class DetailActivity extends AppCompatActivity {

    private DBHelper helper;
    private FusedLocationProviderClient client;
    private TextView lokasi;
    private Button btnFind;
    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        helper = new DBHelper(this);

        EditText etNama = findViewById(R.id.etNama);
        EditText etAlamat = findViewById(R.id.etAlamat);
        EditText etNoTel = findViewById(R.id.etNoHP);
        lokasi = findViewById(R.id.tvLokasi);

        client = LocationServices.getFusedLocationProviderClient(DetailActivity.this);

        btnFind = findViewById(R.id.btnLokasi);
        btnFind.setOnClickListener(view -> {
            getLocation();
        });

        Button btnUpload =findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.image);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        Button tombol = findViewById(R.id.btnSubmit);
        tombol.setOnClickListener(view -> {
            String nama = etNama.getText().toString();
            String alamat = etAlamat.getText().toString();
            String noTelp = etNoTel.getText().toString();

            if (nama.length() == 0 && alamat.length() == 0 && noTelp.length() == 0) {
                Toast.makeText(DetailActivity.this,
                        "Form tidak boleh kosong!",
                        Toast.LENGTH_SHORT).show();
            } else {
                insert(nama, alamat, noTelp);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Izin Lokasi tidak di aktifkan!", Toast.LENGTH_SHORT).show();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getLocation();
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
        } else {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location !=null) {
                        lokasi.setText(String.valueOf(location.getLatitude())+"  "+(location.getLongitude()));
                    } else {
                        Toast.makeText(getApplicationContext(), "Lokasi tidak Aktif!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void insert(String nama, String alamat, String noTelp){
        helper.insert(nama,alamat, noTelp);
        Toast.makeText(this, "Data sudah di simpan!", Toast.LENGTH_SHORT).show();
        finish();
    }

}