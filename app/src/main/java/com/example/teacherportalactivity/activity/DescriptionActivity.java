package com.example.teacherportalactivity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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

public class DescriptionActivity extends AppCompatActivity implements InternetRetryListener {

    TextView desc, title, desc2_note;
    Intent ints;
    Button nextbutton;
    String titles, descs, mlp_period, mlp_teachingaids, mlp_digitalasset, mlp_i_affirm;
    private PreferenceHelper pHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(DescriptionActivity.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_description);


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

        desc = findViewById(R.id.desc);
        title = findViewById(R.id.title_obj);
        desc2_note = findViewById(R.id.desc2_note);


        nextbutton = findViewById(R.id.nextbutton_1);
        pHelper = new PreferenceHelper(this);

        try {

       /*     if (ints != null && !ints.getStringExtra("desc").equals(""))
            {
                descs = ints.getStringExtra("desc");
                titles = ints.getStringExtra("title");
                toolbar_title.setText(ints.getStringExtra("toolbar"));
            }*/
            ints = getIntent();

            descs = ints.getStringExtra("desc");
            titles = ints.getStringExtra("title");
            toolbar_title.setText(ints.getStringExtra("toolbar"));

        } catch (Exception ae) {

        }


//        tt_1.setText(toolbar_title.getText().toString());
        title.setText(Html.fromHtml(String.valueOf(titles)));
        String[] desc_note = descs.split("<p><b>Note");
        desc.setText(Html.fromHtml(String.valueOf(desc_note[0])));
        if (desc_note.length > 1) {
            desc2_note.setText(Html.fromHtml(String.valueOf("<p><b>Note" + desc_note[1])));
            desc2_note.setVisibility(View.VISIBLE);
        }

        if (toolbar_title.getText().equals("Micro Lesson Plan")) {
            nextbutton.setVisibility(View.VISIBLE);
            nextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(DescriptionActivity.this, PeriodClasslist.class);
                    i.putExtra("lessonid", pHelper.getString(ResponseString.LESSON_ID, ""));
                    i.putExtra("lessonname", pHelper.getString(ResponseString.LESSON_NAME, ""));
                    startActivity(i);
                }
            });
        }

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
