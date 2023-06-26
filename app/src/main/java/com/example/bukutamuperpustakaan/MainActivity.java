package com.example.bukutamuperpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.bukutamuperpustakaan.databinding.ActivityMainBinding;
import com.example.bukutamuperpustakaan.view.OnboardActivity;
import com.example.bukutamuperpustakaan.view.RatingActivity;
import com.example.bukutamuperpustakaan.view.SplashActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menghubungkan layout activity_main.xml dengan kelas MainActivity menggunakan View Binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Menerima intent dan mendapatkan userID yang dikirimkan
        Intent intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userId");
        }
        // Membuat URL QR Code dengan menambahkan userID ke dalamnya
        String qrLink = "https://bktm.my.id/bukutamuperpus/qr_tamu.php?googleid=" + userID;
        // Membuat QR Code dengan memanggil metode generateCodeQR dan melewatkan binding dan URL QR Code
        generateCodeQR(binding, qrLink);

        // Menambahkan onClickListener ke tombol refresh untuk membuka RatingActivity dengan mengirimkan userID
        binding.refresh.setOnClickListener(view1 -> {
            Intent intent1 = new Intent(MainActivity.this, RatingActivity.class);
            intent1.putExtra("userId", userID);
            startActivity(intent1);
        });
    }

    // Metode untuk menghasilkan QR Code dengan menggunakan QR Generator Library
    private void generateCodeQR(ActivityMainBinding binding, String value) {
        // Mendapatkan ukuran layar perangkat
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = Math.min(width, height);
        dimen = dimen * 3 / 4;

        // Membuat objek QRGEncoder dengan nilai dan jenis konten QR Code
        QRGEncoder qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, dimen);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);

        try {
            // Mendapatkan bitmap QR Code dari QRGEncoder dan menampilkan di ImageView
            bitmap = qrgEncoder.getBitmap();
            binding.imageViewQR.setImageBitmap(bitmap);
        } catch (Exception ignored) {

        }
    }
}
