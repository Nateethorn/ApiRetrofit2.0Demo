package com.example.toshiba.youtubedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentListViewActivity extends AppCompatActivity implements StudentViewInterface{
    List<Student> students;
    StudentListViewPresenter presenter;
    @BindView(R.id.listView_student) ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_view);
        ButterKnife.bind(this);

        presenter = new StudentListViewPresenter(getApplicationContext());
        presenter.bindView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getDataFromAPI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    public void displayAllStudent(AllStudent allStudent) {
        students = allStudent.getStudents();
        StudentListViewAdapter adapter = new StudentListViewAdapter(getApplicationContext(),students);
        listView.setAdapter(adapter);
    }
}
