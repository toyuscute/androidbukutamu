package com.example.bukutamuperpustakaan.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bukutamuperpustakaan.MainActivity;
import com.example.bukutamuperpustakaan.databinding.ActivityLoginBinding;
import com.example.bukutamuperpustakaan.network.DatabaseConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("943754771571-ggi140p208i43ub660iv8mda9s55vncg.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        binding.signin.setOnClickListener(it -> signInWithGoogle());

        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                GoogleSignInAccount googleSignInAccount;
                try {
                    googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                if (googleSignInAccount != null) {
                    String userId = googleSignInAccount.getId();
                    String email = googleSignInAccount.getEmail();
                    String nama = googleSignInAccount.getDisplayName();

                    AsyncTask.execute(() -> {
                        try {
                            Connection connection = DatabaseConnection.getConnection();

                            String sql = "SELECT * FROM tbl_users WHERE googleId = ?";
                            PreparedStatement statement = connection.prepareStatement(sql);
                            statement.setString(1, userId);
                            ResultSet resultSet = statement.executeQuery();

                            if (resultSet.next()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                displayToast("Login Berhasil");
                            } else {
                                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                                registerIntent.putExtra("email", email);
                                registerIntent.putExtra("userId", userId);
                                registerIntent.putExtra("nama", nama);
                                startActivity(registerIntent);
                                displayToast("Anda belum terdaftar. Silakan daftar terlebih dahulu.");
                            }

                            resultSet.close();
                            statement.close();
                            connection.close();
                        } catch (Exception e) {
                            Log.e("LoginActivity", "Error checking googleId in tbl_users", e);
                        }
                    });
                }
            }
        }
    }


    private void signInWithGoogle() {
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        });
    }

    private void displayToast(String s) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show());
    }

}