package com.fransiskaindri202102369.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class ForexMainActivity extends AppCompatActivity {
    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView qarTextView, ronTextView, rsdTextView, rubTextView, rwfTextView, sarTextView, sbdTextView, scrTextView, sdgTextView, sekTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex_main2);

        swipeRefreshLayout1 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);
        qarTextView = (TextView) findViewById(R.id.qarTextView);
        ronTextView = (TextView) findViewById(R.id.ronTextView);
        rsdTextView = (TextView) findViewById(R.id.rsdTextView);
        rubTextView = (TextView) findViewById(R.id.rubTextView);
        rwfTextView = (TextView) findViewById(R.id.rwfTextView);
        sarTextView = (TextView) findViewById(R.id.sarTextView);
        sbdTextView = (TextView) findViewById(R.id.sbdTextView);
        scrTextView = (TextView) findViewById(R.id.scrTextView);
        sdgTextView = (TextView) findViewById(R.id.sdgTextView);
        sekTextView = (TextView) findViewById(R.id.sekTextView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        initSwipeRefreshLayout();
        initForex();
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initForex();

                swipeRefreshLayout1.setRefreshing(false);
            }
        });
    }

    public String formatNumber(double number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    private void initForex(){
        loadingProgressBar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=767ee779134c4a21b8d5bd418f5d3232";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                ForexRootModel rootModel = gson.fromJson(new String(responseBody), ForexRootModel.class);
                ForexRatesModel ratesModel = rootModel.getRatesModel();

                double qar = ratesModel.getIDR() / ratesModel.getQAR();
                double ron = ratesModel.getIDR() / ratesModel.getRON();
                double rsd = ratesModel.getIDR() / ratesModel.getRSD();
                double rub = ratesModel.getIDR() / ratesModel.getRUB();
                double rwf = ratesModel.getIDR() / ratesModel.getRWF();
                double sar = ratesModel.getIDR() / ratesModel.getSAR();
                double sbd = ratesModel.getIDR() / ratesModel.getSBD();
                double scr = ratesModel.getIDR() / ratesModel.getSCR();
                double sdg = ratesModel.getIDR() / ratesModel.getSDG();
                double sek = ratesModel.getIDR() / ratesModel.getSEK();
                double idr = ratesModel.getIDR();


                qarTextView.setText(formatNumber(qar, "###,##0.##"));
                ronTextView.setText(formatNumber(ron, "###,##0.##"));
                rsdTextView.setText(formatNumber(rsd, "###,##0.##"));
                rubTextView.setText(formatNumber(rub, "###,##0.##"));
                rwfTextView.setText(formatNumber(rwf, "###,##0.##"));
                sarTextView.setText(formatNumber(sar, "###,##0.##"));
                sbdTextView.setText(formatNumber(sbd, "###,##0.##"));
                scrTextView.setText(formatNumber(scr, "###,##0.##"));
                sdgTextView.setText(formatNumber(sdg, "###,##0.##"));
                sekTextView.setText(formatNumber(sek, "###,##0.##"));

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }
        });
    }
}