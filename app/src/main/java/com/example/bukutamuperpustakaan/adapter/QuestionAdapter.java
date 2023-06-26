package com.example.bukutamuperpustakaan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bukutamuperpustakaan.R;
import com.example.bukutamuperpustakaan.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questions;
    private OnAllOptionsSelectedChangeListener listener;

    public QuestionAdapter(List<Question> questions, OnAllOptionsSelectedChangeListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Membuat tampilan item baru menggunakan layout inflater dan mengembalikan tampilan tersebut dalam QuestionViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        // Mengikat data pertanyaan ke ViewHolder
        Question question = questions.get(position);
        holder.questionTextView.setText(question.getQuestionText());

        // Mengatur opsi jawaban pada RadioButton
        List<String> answerOptions = question.getAnswerOptions();
        holder.option1RadioButton.setText(answerOptions.get(0));
        holder.option2RadioButton.setText(answerOptions.get(1));
        holder.option3RadioButton.setText(answerOptions.get(2));
        holder.option4RadioButton.setText(answerOptions.get(3));

        // Menghapus listener sebelumnya dan menghapus seleksi RadioButton sebelumnya
        holder.radioGroup.setOnCheckedChangeListener(null);
        holder.radioGroup.clearCheck();

        // Menetapkan listener untuk masing-masing RadioButton
        holder.option1RadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Menandai opsi yang dipilih pada pertanyaan dan memperbarui status seleksi semua opsi
            if (isChecked) {
                question.setSelectedOptionIndex(0);
                updateAllOptionsSelectedStatus();
            }
        });

        // Menetapkan listener untuk masing-masing RadioButton
        // (sama seperti di atas untuk opsi 2, 3, dan 4)

        // Memperbarui seleksi RadioButton berdasarkan indeks opsi yang dipilih sebelumnya
        int selectedIndex = question.getSelectedOptionIndex();
        if (selectedIndex != -1) {
            RadioButton selectedRadioButton;
            switch (selectedIndex) {
                case 0:
                    selectedRadioButton = holder.option1RadioButton;
                    break;
                case 1:
                    selectedRadioButton = holder.option2RadioButton;
                    break;
                case 2:
                    selectedRadioButton = holder.option3RadioButton;
                    break;
                case 3:
                    selectedRadioButton = holder.option4RadioButton;
                    break;
                default:
                    selectedRadioButton = null;
            }
            if (selectedRadioButton != null) {
                selectedRadioButton.setChecked(true);
            }
        } else {
            holder.radioGroup.clearCheck();
        }
    }

    @Override
    public int getItemCount() {
        // Mengembalikan jumlah item dalam daftar pertanyaan
        return questions.size();
    }

    // Memperbarui status seleksi semua opsi jawaban dan memberitahu listener
    private void updateAllOptionsSelectedStatus() {
        boolean allOptionsSelected = true;
        for (Question question : questions) {
            if (question.getSelectedOptionIndex() == -1) {
                allOptionsSelected = false;
                break;
            }
        }
        // Memanggil metode onAllOptionsSelectedChanged pada listener untuk memberitahu perubahan status
        listener.onAllOptionsSelectedChanged(allOptionsSelected);
    }

    // Interface untuk mendengarkan perubahan status seleksi semua opsi jawaban
    public interface OnAllOptionsSelectedChangeListener {
        void onAllOptionsSelectedChanged(boolean allOptionsSelected);
    }

    // Kelas ViewHolder untuk menyimpan referensi ke tampilan item dalam RecyclerView
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup radioGroup;
        RadioButton option1RadioButton;
        RadioButton option2RadioButton;
        RadioButton option3RadioButton;
        RadioButton option4RadioButton;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menginisialisasi tampilan item dalam ViewHolder
            questionTextView = itemView.findViewById(R.id.questionTextView);
            radioGroup = itemView.findViewById(R.id.answerOptionsRadioGroup);
            option1RadioButton = itemView.findViewById(R.id.option1RadioButton);
            option2RadioButton = itemView.findViewById(R.id.option2RadioButton);
            option3RadioButton = itemView.findViewById(R.id.option3RadioButton);
            option4RadioButton = itemView.findViewById(R.id.option4RadioButton);
        }
    }
}
