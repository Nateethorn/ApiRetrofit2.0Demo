package com.example.toshiba.youtubedemo;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

class FeedStudentTask extends AsyncTask<Void, Void, JSONObject> {
    StudentViewInterface studentViewInterface;

    FeedStudentTask(StudentViewInterface studentViewInterface) {
        this.studentViewInterface = studentViewInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://thaicfp.com/webservices/json-example.php").build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return new JSONObject(response.body().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        Gson gson = new Gson();
        AllStudent allStudent = gson.fromJson(String.valueOf(jsonObject),AllStudent.class);
        studentViewInterface.displayAllStudent(allStudent);
    }
}
