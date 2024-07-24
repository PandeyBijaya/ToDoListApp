package com.firstapp.todolistapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("select * from tasks")
    List<TaskData> fetchData();

    @Insert
    void insertData(TaskData taskData);

    @Update
    void updateTask(TaskData taskData);

    @Delete
    void deleteTask(TaskData taskData);
}
