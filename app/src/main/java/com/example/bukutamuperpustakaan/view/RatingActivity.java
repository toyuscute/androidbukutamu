package com.example.bukutamuperpustakaan.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bukutamuperpustakaan.MainActivity;
import com.example.bukutamuperpustakaan.adapter.QuestionAdapter;
import com.example.bukutamuperpustakaan.databinding.ActivityRatingBinding;
import com.example.bukutamuperpustakaan.model.Question;
import com.example.bukutamuperpustakaan.model.Survey;
import com.example.bukutamuperpustakaan.network.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RatingActivity extends AppCompatActivity implements QuestionAdapter.OnAllOptionsSelectedChangeListener {
    private QuestionAdapter questionAdapter;
    private LinearLayout popupLayout;

    private List<Question> questionList;

    private Button submitButton;

    private String userID;

    private Survey survey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRatingBinding binding = ActivityRatingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.runningText.setSelected(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        popupLayout = binding.popupLayout;
        Button popupSubmitButton = binding.popupSubmitButton;
        submitButton = binding.submit;
        ImageView close = binding.closeButton;
        EditText popupEditText = binding.popupEditText;
        Intent intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userId");
        }

        AsyncTask.execute(() -> {
            try {
                Connection connection = DatabaseConnection.getConnection();

                String sql = "SELECT * FROM tbl_users WHERE googleId = '" + userID + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("users_nama");
                    String email = resultSet.getString("users_email");
                    String job = resultSet.getString("users_pekerjaan");
                    String education = resultSet.getString("users_pendidikan_terakhir");
                    String gender = resultSet.getString("users_jenis_kelamin");
                    String age = resultSet.getString("users_umur");
                    String address = resultSet.getString("users_alamat");
                    int idInt = Integer.parseInt(id);
                    int ageInt = Integer.parseInt(age);
                    survey.setSpmIdUser(idInt);
                    survey.setSpmNama(name);
                    survey.setSpmEmail(email);
                    survey.setSpmPekerjaan(job);
                    survey.setSpmPendikanTerakir(education);
                    survey.setSpmJenisKelamin(gender);
                    survey.setSpmUmur(ageInt);
                    survey.setSpmAlamat(address);
                    binding.name.setText("Nama : "+name);
                    binding.email.setText("Email : "+email);
                    binding.job.setText("Pekerjaan : "+job);
                    binding.education.setText("Pendidikan Terakhir : "+education);
                    binding.gender.setText("Jenis Kelamin : "+gender);
                    binding.age.setText("Umur : "+age);
                    binding.address.setText("Alamat : "+address);

                } else {
                    System.out.println("Something When Wrong");
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.e("LoginActivity", "Error checking googleId in tbl_users", e);
            }
        });

        submitButton.setOnClickListener(v -> showPopup());

        questionList = new ArrayList<>();
        questionList.add(new Question("1. Kesesuaian persyaratan pelayanan dengan jenis pelayanannya:", Arrays.asList("Tidak Sesuai", "Kurang Sesuai", "Sesuai", "Sangat Sesuai")));
        questionList.add(new Question("2. Kemudahan prosedur pelayanan di unit ini:", Arrays.asList("Tidak Mudah", "Kurang Mudah", "Mudah", "Sangat Mudah")));
        questionList.add(new Question("3. Kecepatan waktu dalam memberikan pelayanan:", Arrays.asList("Tidak Cepat", "Kurang Cepat", "Cepat", "Sangat Cepat")));
        questionList.add(new Question("4. Kewajaran biaya/tarif dalam pelayanan:", Arrays.asList("Sangat Mahal", "Cukup Mahal", "Murah", "Gratis")));
        questionList.add(new Question("5. Kesesuaian produk pelayanan antara yang tercantum dalam standar pelayanan dengan hasil yang diberikan:", Arrays.asList("Tidak Sesuai", "Kurang Sesuai", "Sesuai", "Sangat Sesuai")));
        questionList.add(new Question("6. Kompetensi/kemampuan petugas dalam pelayanan:", Arrays.asList("Tidak Kompeten", "Kurang Kompeten", "Kompeten", "Sangat Kompeten")));
        questionList.add(new Question("7. Perilaku petugas dalam pelayanan terkait kesopanan dan keramahan:", Arrays.asList("Tidak Sopan dan Ramah", "Kurang Sopan dan Ramah", "Sopan dan Ramah", "Sangat Sopan dan Ramah")));
        questionList.add(new Question("8. Pendapat tentang kualitas sarana dan prasarana:", Arrays.asList("Buruk", "Cukup", "Baik", "Sangat Baik")));
        questionList.add(new Question("9. Penanganan pengaduan pengguna layanan:", Arrays.asList("Tidak Ada", "Ada tetapi tidak berfungsi", "Berfungsi kurang maksimal", "Dikelola dengan baik")));

        questionAdapter = new QuestionAdapter(questionList, this);
        binding.recyclerview.setAdapter(questionAdapter);
        survey = new Survey();
        close.setOnClickListener(v -> hidePopup());

        popupSubmitButton.setOnClickListener(v -> {
            int id = survey.getSpmIdUser();
            String name = survey.getSpmNama();
            String email = survey.getSpmEmail();
            String job = survey.getSpmPekerjaan();
            String education = survey.getSpmPendikanTerakir();
            String gender = survey.getSpmJenisKelamin();
            int age = survey.getSpmUmur();
            String address = survey.getSpmAlamat();
            String pertanyaan1 = survey.getSpmPertanyaan1();
            String pertanyaan2 = survey.getSpmPertanyaan2();
            String pertanyaan3 = survey.getSpmPertanyaan3();
            String pertanyaan4 = survey.getSpmPertanyaan4();
            String pertanyaan5 = survey.getSpmPertanyaan5();
            String pertanyaan6 = survey.getSpmPertanyaan6();
            String pertanyaan7 = survey.getSpmPertanyaan7();
            String pertanyaan8 = survey.getSpmPertanyaan8();
            String pertanyaan9 = survey.getSpmPertanyaan9();
            String inputText = popupEditText.getText().toString().trim();
            survey.setSpmIdUser(id);
            survey.setSpmNama(name);
            survey.setSpmEmail(email);
            survey.setSpmPekerjaan(job);
            survey.setSpmPendikanTerakir(education);
            survey.setSpmJenisKelamin(gender);
            survey.setSpmAlamat(address);
            survey.setSpmUmur(age);
            survey.setSpmPertanyaan1(pertanyaan1);
            survey.setSpmPertanyaan2(pertanyaan2);
            survey.setSpmPertanyaan3(pertanyaan3);
            survey.setSpmPertanyaan4(pertanyaan4);
            survey.setSpmPertanyaan5(pertanyaan5);
            survey.setSpmPertanyaan6(pertanyaan6);
            survey.setSpmPertanyaan7(pertanyaan7);
            survey.setSpmPertanyaan8(pertanyaan8);
            survey.setSpmPertanyaan9(pertanyaan9);
            survey.setSpmPertanyaan10(inputText);
            insertSurvey(survey);
            hidePopup();
        });




    }

    private void insertSurvey(Survey survey) {
        AsyncTask.execute(() -> {
            try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO tbl_survey_kepuasan_masyarakat (spm_id_users, spm_nama, spm_email, spm_pekerjaan, spm_pendidikan_terakhir, spm_jenis_kelamin, spm_alamat, spm_umur, spm_pertanyaan_1, spm_pertanyaan_2, spm_pertanyaan_3, spm_pertanyaan_4, spm_pertanyaan_5, spm_pertanyaan_6, spm_pertanyaan_7, spm_pertanyaan_8, spm_pertanyaan_9, spm_pertanyaan_10) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, survey.getSpmIdUser());
            preparedStatement.setString(2, survey.getSpmNama());
            preparedStatement.setString(3, survey.getSpmEmail());
            preparedStatement.setString(4, survey.getSpmPekerjaan());
            preparedStatement.setString(5, survey.getSpmPendikanTerakir());
            preparedStatement.setString(6, survey.getSpmJenisKelamin());
            preparedStatement.setString(7, survey.getSpmAlamat());
            preparedStatement.setInt(8, survey.getSpmUmur());
            preparedStatement.setString(9, survey.getSpmPertanyaan1());
            preparedStatement.setString(10, survey.getSpmPertanyaan2());
            preparedStatement.setString(11, survey.getSpmPertanyaan3());
            preparedStatement.setString(12, survey.getSpmPertanyaan4());
            preparedStatement.setString(13, survey.getSpmPertanyaan5());
            preparedStatement.setString(14, survey.getSpmPertanyaan6());
            preparedStatement.setString(15, survey.getSpmPertanyaan7());
            preparedStatement.setString(16, survey.getSpmPertanyaan8());
            preparedStatement.setString(17, survey.getSpmPertanyaan9());
            preparedStatement.setString(18, survey.getSpmPertanyaan10());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                runOnUiThread(() -> {
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(this, "Terimakasih sudah berpartisipasi dalam Survey ini", Toast.LENGTH_SHORT).show();
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Something When Wrong", Toast.LENGTH_SHORT).show();
                });
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        });
    }

    private void showPopup() {
        popupLayout.setVisibility(View.VISIBLE);
    }

    private void hidePopup() {
        popupLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAllOptionsSelectedChanged(boolean allOptionsSelected) {
        submitButton.setEnabled(allOptionsSelected);
        if (allOptionsSelected) {
            survey.setSpmPertanyaan1(questionList.get(0).getSelectedOption());
            survey.setSpmPertanyaan2(questionList.get(1).getSelectedOption());
            survey.setSpmPertanyaan3(questionList.get(2).getSelectedOption());
            survey.setSpmPertanyaan4(questionList.get(3).getSelectedOption());
            survey.setSpmPertanyaan5(questionList.get(4).getSelectedOption());
            survey.setSpmPertanyaan6(questionList.get(5).getSelectedOption());
            survey.setSpmPertanyaan7(questionList.get(6).getSelectedOption());
            survey.setSpmPertanyaan8(questionList.get(7).getSelectedOption());
            survey.setSpmPertanyaan9(questionList.get(8).getSelectedOption());
        }
    }

//    private String getSelectedOptionsFromAdapter() {
//        StringBuilder selectedOptions = new StringBuilder();
//        for (Question question : questionList) {
//            int selectedIndex = question.getSelectedOptionIndex();
//            if (selectedIndex != -1) {
//                List<String> answerOptions = question.getAnswerOptions();
//                selectedOptions.append(answerOptions.get(selectedIndex));
//                selectedOptions.append(", ");
//            }
//        }
//        if (selectedOptions.length() > 0) {
//            selectedOptions.deleteCharAt(selectedOptions.length() - 2);
//        }
//        return selectedOptions.toString();
//    }

}