package com.example.latihanp11.model;

public class Person {
    private final int id;
    private final String nama, alamat, noTelp;

    public Person(int id, String nama, String alamat, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }



    public int getId() {
        return id;
    }

    public String getNama() {return nama;}

    public String getAlamat() {
        return alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    }


