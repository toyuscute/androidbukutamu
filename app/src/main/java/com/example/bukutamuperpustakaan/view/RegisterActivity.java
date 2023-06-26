package com.example.bukutamuperpustakaan.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bukutamuperpustakaan.MainActivity;
import com.example.bukutamuperpustakaan.R;
import com.example.bukutamuperpustakaan.databinding.ActivityRegisterBinding;
import com.example.bukutamuperpustakaan.model.Users;
import com.example.bukutamuperpustakaan.network.DatabaseConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private final String[] pendidikan = {"SD/MI", "SMP/MTS", "SMA/SMK", "Sarjana/Terapan"};
    private final String[] kelamin = {"Laki-laki", "Perempuan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ArrayAdapter<String> kategoriPendidikan = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, pendidikan);
        ArrayAdapter<String> kategoriKelamin = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, kelamin);
        binding.pendidikan.setAdapter(kategoriPendidikan);
        binding.jenisKelamin.setAdapter(kategoriKelamin);
        String email, userID = null, nama;
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            userID = intent.getStringExtra("userId");
            nama = intent.getStringExtra("nama");
            binding.email.setText(email);
            binding.name.setText(nama);
        }
        String finalUserID = userID;
        binding.register.setOnClickListener(view1 -> {
            String name = Objects.requireNonNull(binding.name.getText()).toString();
            String fullEmail = Objects.requireNonNull(binding.email.getText()).toString();
            String job =  Objects.requireNonNull(binding.job.getText()).toString();
            String education = binding.pendidikan.getText().toString();
            String gender = binding.jenisKelamin.getText().toString();
            String age = Objects.requireNonNull(binding.umur.getText()).toString();
            String address = Objects.requireNonNull(binding.alamat.getText()).toString();
            Users user = new Users(name, "finalUserID", name, fullEmail, job, education, gender, address, age, "user", finalUserID);
            InfoAsyncTask asyncTask = new InfoAsyncTask(this,user);
            asyncTask.execute();
        });
    }

    @SuppressLint("StaticFieldLeak")
    public static class InfoAsyncTask extends AsyncTask<Void, Void, Void> {
        private Users users;
        private Context context;

        public InfoAsyncTask(Context context, Users users) {
            this.context = context;
            this.users = users;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Connection connection = DatabaseConnection.getConnection();

                String sql = "INSERT INTO tbl_users (users_username, users_password, users_nama, users_email, users_pekerjaan, users_pendidikan_terakhir, users_jenis_kelamin, users_alamat, users_umur, role, googleId)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, users.getUserName());
                statement.setString(2, users.getPassword());
                statement.setString(3, users.getName());
                statement.setString(4, users.getEmail());
                statement.setString(5, users.getJob());
                statement.setString(6, users.getEducation());
                statement.setString(7, users.getGender());
                statement.setString(8, users.getAddress());
                statement.setString(9, users.getAge());
                statement.setString(10, users.getRole());
                statement.setString(11, users.getGoogleId());

                statement.executeUpdate();

                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error inserting user information", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);

        }
    }

}