package com.example.teacherportalactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.utils.CommonUtils;

import org.json.JSONObject;

public class Microlessondescription extends AppCompatActivity implements InternetRetryListener {

    TextView desc2, desc3, desc4, desc5;
    Intent ints;
    Button nextbutton;

    private PreferenceHelper pHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(Microlessondescription.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_microlessondesc);


        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.setFreezesText(true);
        toolbar_title.setFocusable(true);
        toolbar_title.setSelected(true);
        toolbar_title.setText(Html.fromHtml("Micro Lesson Plan"));


        desc2 = findViewById(R.id.desc2);
        desc4 = findViewById(R.id.desc4);
        desc5 = findViewById(R.id.desc5);
        desc3 = findViewById(R.id.desc3);
        nextbutton = findViewById(R.id.nextbutton_1);
        pHelper = new PreferenceHelper(this);

        try {
            ints = getIntent();
            String s = ints.getStringExtra("micro");
            JSONObject jsonObject = new JSONObject(s);
            String no_of_teaching_periods = jsonObject.getString("no_of_teaching_periods");
            String t_aid = jsonObject.getString("t_aid");
            String digital_asset = jsonObject.getString("digital_asset");
            String i_affirm = jsonObject.getString("i_affirm");
            desc2.setText(no_of_teaching_periods);
            desc3.setText(Html.fromHtml(t_aid));
            desc4.setText(digital_asset);
            desc5.setText(i_affirm);
        } catch (Exception ae) {

        }


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Microlessondescription.this, PeriodClasslist.class);
                i.putExtra("lessonid", pHelper.getString(ResponseString.LESSON_ID, ""));
                i.putExtra("lessonname", pHelper.getString(ResponseString.LESSON_NAME, ""));
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
    public void onRetry() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
