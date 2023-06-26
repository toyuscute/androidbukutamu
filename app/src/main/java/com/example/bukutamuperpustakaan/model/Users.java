package com.example.bukutamuperpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private String userName; // Variabel untuk menyimpan nama pengguna
    private String password; // Variabel untuk menyimpan kata sandi
    private String name; // Variabel untuk menyimpan nama pengguna
    private String email; // Variabel untuk menyimpan alamat email pengguna
    private String job; // Variabel untuk menyimpan pekerjaan pengguna
    private String education; // Variabel untuk menyimpan pendidikan pengguna
    private String gender; // Variabel untuk menyimpan jenis kelamin pengguna
    private String address; // Variabel untuk menyimpan alamat pengguna

    private String age; // Variabel untuk menyimpan usia pengguna
    private String role; // Variabel untuk menyimpan peran pengguna
    private String googleId; // Variabel untuk menyimpan ID Google pengguna

    public Users() {}

    public Users( String userName, String password, String name, String email, String job, String education, String gender, String address, String age, String role, String googleId) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.job = job;
        this.education = education;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.role = role;
        this.googleId = googleId;
    }

    public String getUserName() { // Mendapatkan nama pengguna
        return userName;
    }

    public void setUserName(String userName) { // Mengatur nama pengguna
        this.userName = userName;
    }

    public String getPassword() { // Mendapatkan kata sandi
        return password;
    }

    public void setPassword(String password) { // Mengatur kata sandi
        this.password = password;
    }

    public String getName() { // Mendapatkan nama
        return name;
    }

    public void setName(String name) { // Mengatur nama
        this.name = name;
    }

    public String getEmail() { // Mendapatkan alamat email
        return email;
    }

    public void setEmail(String email) { // Mengatur alamat email
        this.email = email;
    }

    public String getJob() { // Mendapatkan pekerjaan
        return job;
    }

    public void setJob(String job) { // Mengatur pekerjaan
        this.job = job;
    }

    public String getEducation() { // Mendapatkan pendidikan
        return education;
    }

    public void setEducation(String education) { // Mengatur pendidikan
        this.education = education;
    }

    public String getGender() { // Mendapatkan jenis kelamin
        return gender;
    }

    public void setGender(String gender) { // Mengatur jenis kelamin
        this.gender = gender;
    }

    public String getAddress() { // Mendapatkan alamat
        return address;
    }

    public void setAddress(String address) { // Mengatur alamat
        this.address = address;
    }

    public String getAge() { // Mendapatkan usia
        return age;
    }

    public void setAge(String age) { // Mengatur usia
        this.age = age;
    }

    public String getRole() { // Mendapatkan peran
        return role;
    }

    public void setRole(String role) { // Mengatur peran
        this.role = role;
    }

    public String getGoogleId() { // Mendapatkan ID Google
        return googleId;
    }

    public void setGoogleId(String googleId) { // Mengatur ID Google
        this.googleId = googleId;
    }

    protected Users(Parcel in) {
        userName = in.readString();
        password = in.readString();
        name = in.readString();
        email = in.readString();
        job = in.readString();
        education = in.readString();
        gender = in.readString();
        address = in.readString();
        age = in.readString();
        role = in.readString();
        googleId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(job);
        dest.writeString(education);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(age);
        dest.writeString(role);
        dest.writeString(googleId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
