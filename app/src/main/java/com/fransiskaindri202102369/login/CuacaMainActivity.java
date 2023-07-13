package com.fransiskaindri202102369.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CuacaMainActivity extends AppCompatActivity {

    private RecyclerView _recyclerview2;
    private SwipeRefreshLayout _swipeRefreshLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca_main);

        _recyclerview2 = (RecyclerView) findViewById(R.id.recyclerView2);
        _swipeRefreshLayout2 = findViewById(R.id.swipeRefreshLayout2);

        initRecyclerView2();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout()
    {
        _swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                initRecyclerView2();

            }
        });
    }

    private void initRecyclerView2(){
        String url = "https://api.openweathermap.org/data/2.5/forecast?id=1630789&appid=f8585cc33af81c67923b33c4546ffcb1";
        AsyncHttpClient ahc = new AsyncHttpClient();

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                CuacaRootModel rm = gson.fromJson(new String(responseBody), CuacaRootModel.class);

                RecyclerView.LayoutManager lm = new LinearLayoutManager(CuacaMainActivity.this);
                CuacaAdapter ca = new CuacaAdapter(CuacaMainActivity.this, rm);

                _recyclerview2.setLayoutManager(lm);
                _recyclerview2.setAdapter(ca);

                _swipeRefreshLayout2.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG). show();
                _swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }
}