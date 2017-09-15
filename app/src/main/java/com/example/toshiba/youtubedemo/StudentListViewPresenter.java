package com.example.toshiba.youtubedemo;

import android.content.Context;

class StudentListViewPresenter {
    StudentViewInterface studentViewInterface;
    Context ctx;

    StudentListViewPresenter(Context ctx) {
        this.ctx = ctx;
    }

    void bindView(StudentViewInterface studentViewInterface){
        this.studentViewInterface = studentViewInterface;
    }

    void unbindView(){
        studentViewInterface = null;
    }

    void getDataFromAPI(){
        new FeedStudentTask(studentViewInterface).execute();
    }
}
