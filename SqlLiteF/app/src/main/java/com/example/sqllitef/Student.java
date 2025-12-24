package com.example.sqllitef;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "etudiants")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "niveau")
    private String niveau;

    public Student() {
    }

    public Student(String nom, String prenom, String niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.niveau = niveau;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNiveau() { return niveau; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
}