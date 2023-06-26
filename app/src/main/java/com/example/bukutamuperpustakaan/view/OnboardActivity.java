package com.example.bukutamuperpustakaan.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bukutamuperpustakaan.MainActivity;
import com.example.bukutamuperpustakaan.R;
import com.example.bukutamuperpustakaan.databinding.ActivityOnboardBinding;
import com.example.bukutamuperpustakaan.network.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class OnboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnboardBinding binding = ActivityOnboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnloti.setOnClickListener(view1 -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        new InfoAsyncTask().execute();

    }
    @SuppressLint("StaticFieldLeak")
    public static class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> info = new HashMap<>();

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM tbl_users LIMIT 1";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    info.put("id", resultSet.getString("id"));
                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return info;
        }
        @Override
        protected void onPostExecute(Map<String, String> result) {
            System.out.println("INI HASIL");
            System.out.println(result.get("id"));
        }
    }
}