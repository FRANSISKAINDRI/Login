package com.fransiskaindri202102369.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TampilMahasiswaActivity extends AppCompatActivity
{
    private FloatingActionButton _addButton;
    private RecyclerView _recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_mahasiswa);

        _recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);

        initAddButton();
        loadRecyclerView();
    }
    private void loadRecyclerView(){
        AsyncHttpClient ahc = new AsyncHttpClient();
        String url = "https://stmikpontianak.net/011100862/tampilMahasiswa.php";

        ahc.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Gson g = new Gson();
                List<MahasiswaModel> mahasiswaModelList = g.fromJson(new String(responseBody), new TypeToken<List<MahasiswaModel>>(){}.getType());

                RecyclerView.LayoutManager lm = new LinearLayoutManager(TampilMahasiswaActivity.this);
                _recyclerView1.setLayoutManager(lm);

                MahasiswaAdapter ma = new MahasiswaAdapter(TampilMahasiswaActivity.this, mahasiswaModelList);
                _recyclerView1.setAdapter(ma);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAddButton() {
        _addButton = findViewById(R.id.addButton);

        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahMahsiswaActivity.class);
                startActivity(intent);

                loadRecyclerView();
            }
        });
    }
}