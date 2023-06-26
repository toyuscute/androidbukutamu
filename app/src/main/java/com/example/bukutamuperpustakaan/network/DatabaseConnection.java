package com.example.bukutamuperpustakaan.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Mendapatkan koneksi ke database MySQL
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // URL koneksi database
        final String URL = "jdbc:mysql://103.55.39.181:3306/bktmmyid_bukutamuperpus";
        // Nama pengguna untuk mengakses database
        final String USER = "bktmmyid_bukutamuperpus";
        // Kata sandi untuk mengakses database
        final String PASSWORD = "bukutamuperpus";

        // Menggunakan kelas DriverManager untuk mendapatkan koneksi menggunakan URL, pengguna, dan kata sandi yang ditentukan
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
