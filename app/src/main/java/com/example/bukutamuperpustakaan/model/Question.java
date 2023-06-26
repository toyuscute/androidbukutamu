package com.example.bukutamuperpustakaan.model;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> answerOptions;
    private int selectedOptionIndex = -1;

    // Konstruktor untuk inisialisasi pertanyaan dan opsi jawaban
    public Question(String questionText, List<String> answerOptions) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
    }

    // Mendapatkan teks pertanyaan
    public String getQuestionText() {
        return questionText;
    }

    // Mendapatkan daftar opsi jawaban
    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    // Mendapatkan indeks opsi jawaban yang dipilih
    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    // Mengatur indeks opsi jawaban yang dipilih
    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    // Mendapatkan opsi jawaban yang dipilih
    public String getSelectedOption() {
        if (selectedOptionIndex != -1) {
            return answerOptions.get(selectedOptionIndex);
        }
        return null;
    }
}
