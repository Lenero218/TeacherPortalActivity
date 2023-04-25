package com.example.teacherportalactivity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;
import java.util.Locale;

public class PDFstudentActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
//    private static final String TAG = class.getSimpleName();

    public static final String SAMPLE_FILE2 = "CRM_1_2.pdf";
    public static final String SAMPLE_FILE3 = "CRM_3_5.pdf";
    public static final String SAMPLE_FILE4 = "Tricky_questions.pdf";
    public static final String SAMPLE_FILE1 = "Introduction.pdf";

    public static final String Peaks_CRM_1_2 = "https://bms.rsgr.in/storage/img/teachersapp/pdf/Peaks_THB_G1_CRM_Updated.pdf";
    public static final String Peaks_CRM_3_5 = "https://bms.rsgr.in/storage/img/teachersapp/pdf/CRM_Combine%20all%20subject.pdf";
    public static final String Peaks_Intro = "https://bms.rsgr.in/storage/img/teachersapp/pdf/Peaks_THB_Introduction%20page.pdf";
    //    public static final String SAMPLE_FILE4 = "Tricky_questions.pdf";
    public static final String Pinnacles_CRM_1_2 = "https://bms.rsgr.in/storage/img/teachersapp/pdf/Pinnacles-2-CRM-combined.pdf";
    public static final String Pinnacles_CRM_3_5 = "https://bms.rsgr.in/storage/img/teachersapp/pdf/Pinnacles-3-CRM-combined.pdf";
    public static final String Pinnacles_Intro = "https://bms.rsgr.in/storage/img/teachersapp/pdf/Pinnacles-intro.pdf";

    private static PreferenceHelper phelper;

    Intent in;
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        pdfView = (PDFView) findViewById(R.id.pdfview);

        in = getIntent();

        phelper = new PreferenceHelper(this);
        if (in.getStringExtra("pdf_name").equalsIgnoreCase("classroom_workshet")) {
            String selectedGrade = phelper.getString(ResponseString.CLASS_NAME, "");
            switch (selectedGrade) {
                case "1":
                    displayFromAsset(SAMPLE_FILE2);
                    break;
                case "2":
                    displayFromAsset(SAMPLE_FILE2);
                    break;
                case "3":
                    displayFromAsset(SAMPLE_FILE3);
                    break;
                case "4":
                    displayFromAsset(SAMPLE_FILE3);
                    break;
                case "5":
                    displayFromAsset(SAMPLE_FILE3);
                    break;
                default:
                    displayFromAsset(SAMPLE_FILE3);
            }
        } else if (in.getStringExtra("pdf_name").equalsIgnoreCase("tricky_workshet")) {
            displayFromAsset(SAMPLE_FILE4);
        } else if (in.getStringExtra("pdf_name").equalsIgnoreCase("introduction")) {
            displayFromAsset(SAMPLE_FILE1);
        }

    }

    private String displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
//        pdfView.fromUri(Uri.parse(assetFileName)).defaultPage(pageNumber).enableSwipe(true).swipeHorizontal(false).onPageChange(this).enableAnnotationRendering(true).onLoad(this).scrollHandle(new DefaultScrollHandle(this)).load();

        pdfView.fromAsset(assetFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)

                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
        return assetFileName;
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        String segments = pdfFileName.substring(0, pdfFileName.indexOf('.'));
        if (segments.equals("Student")) {
            setTitle(String.format("%s %s / %s", "Students' Worksheet", page + 1, pageCount));
        } else if (segments.equals("Teacher_Worksheet")) {
            setTitle(String.format("%s %s / %s", "Teachers' Worksheet", page + 1, pageCount));
        } else if (segments.contains("CRM_")) {
            setTitle(String.format("%s %s / %s", "Classroom Management Tips", page + 1, pageCount));
        } else {
            setTitle(String.format("%s %s / %s", segments.replace("_", " "), page + 1, pageCount));
        }

    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {


            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
