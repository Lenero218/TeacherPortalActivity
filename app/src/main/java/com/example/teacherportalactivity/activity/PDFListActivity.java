package com.example.teacherportalactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;

public class PDFListActivity extends AppCompatActivity {

    private PreferenceHelper pHelper;
    private LinearLayout teacherpdf, Principalpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.setFreezesText(true);
        toolbar_title.setSingleLine(true);
        toolbar_title.setMarqueeRepeatLimit(-1);
        toolbar_title.setFocusable(true);
        toolbar_title.setSelected(true);

        pHelper = new PreferenceHelper(this);

        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setSingleLine(true);

//        toolbar_title.setText(" Grade " + pHelper.getString(ResponseString.CLASS_NAME, ResponseString.BLANK));
        toolbar_title.setText("Report");
        String s = getIntent().getStringExtra("mytext");
        Log.e("show", String.valueOf(s));

        String s1 = getIntent().getStringExtra("pdf_name");
        teacherpdf = findViewById(R.id.teacherpdf);
        Principalpdf = findViewById(R.id.Principalpdf);


        teacherpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PDFListActivity.this, Resultactivity.class);

               /* Intent i = new Intent(PDFListActivity.this, PDFActivity.class);
                i.putExtra("reports","Teacher Report");*/
//              pHelper.setString(ResponseString.pdf_url, "https://apps.rsgr.in/teachersapp/TEACHER_REPORT.pdf");
                startActivity(i);
            }
        });
        Principalpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PDFListActivity.this, PDFActivity.class);
                pHelper.setString(ResponseString.pdf_url, "https://apps.rsgr.in/teachersapp/PRINCIPAL_REPORT.pdf");
                i.putExtra("reports", "Principal Report");
                startActivity(i);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}