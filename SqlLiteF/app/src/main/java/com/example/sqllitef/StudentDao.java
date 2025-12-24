package com.example.sqllitef;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student student);

    @Query("SELECT * FROM etudiants ORDER BY nom ASC")
    List<Student> getAll();

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("DELETE FROM etudiants")
    void deleteAll();

    @Query("SELECT * FROM etudiants WHERE niveau = :niveau")
    List<Student> getByLevel(String niveau);

    @Query("SELECT COUNT(*) FROM etudiants")
    int getCount();
}