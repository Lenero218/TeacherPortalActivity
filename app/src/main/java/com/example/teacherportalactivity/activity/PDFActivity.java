package com.example.teacherportalactivity.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.teacherportalactivity.Adapter.SubjectSectionAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.utils.CommonUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final String TAG = PDFActivity.class.getSimpleName();
    public static String SAMPLE_FILE;
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String dirPath;
    File dir;
    private SubjectSectionAdapter subjectSectionAdapter;
    Intent in;
    ImageView teachereport;
    private static final int MY_PERMISSION_REQUEST_STORAGE = 1;
    Intent intent;
    FloatingActionButton download;
    private ProgressBar progressPdfLoader;
    private PreferenceHelper pHelper;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fromDialogBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void fromDialogBack(
    ) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(PDFActivity.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_pdf_view);
        in = getIntent();
        pdfView = (PDFView) findViewById(R.id.pdfview);
        progressPdfLoader = findViewById(R.id.progressPdfLoader);
        teachereport = findViewById(R.id.teacherreport);
        download = findViewById(R.id.download);
        String ss = in.getStringExtra("reports").toString();
        if (ss.equals("Teacher Report")) {
            teachereport.setVisibility(View.VISIBLE);
            download.setVisibility(View.GONE);
            progressPdfLoader.setVisibility(View.GONE);
            pdfView.setVisibility(View.GONE);
        }


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
        toolbar_title.setText(ss);
        pHelper = new PreferenceHelper(this);
        download = findViewById(R.id.download);

        SAMPLE_FILE = pHelper.getString(ResponseString.pdf_url, ResponseString.BLANK);

        dirPath = SAMPLE_FILE;

        dir = new File(dirPath);


        /**
         * Give the permission to access storage
         * */
        if (ContextCompat.checkSelfPermission(PDFActivity.this, android.Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PDFActivity.this, android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(PDFActivity.this, new String[]{android.Manifest
                        .permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);
            } else {
                ActivityCompat.requestPermissions(PDFActivity.this, new String[]{android.Manifest
                        .permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE);
            }
        }


        displayFromUrl(SAMPLE_FILE);

        //TODO Download pdf
//        imgButtonpdf.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//
//                String fullFileName = dirPath + File.separator + SAMPLE_FILE;
//                File file = new File(fullFileName);
//
//                if (file.exists()) {
//                    /**Dialog Show if file exists
//                     *
//                     * */
//                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PDFActivity
//                            .this, AlertDialog.THEME_HOLO_LIGHT);
//                    // Setting Alert Dialog Title
//                    alertDialogBuilder.setTitle("Confirm..!!!");
//                    // Setting Alert Dialog Message
//                    alertDialogBuilder.setMessage("Are you sure, You want to Replace the file.");
//                    alertDialogBuilder.setCancelable(false);
//
//                    alertDialogBuilder.setPositiveButton("Replace", new DialogInterface
//                            .OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            copyAssets(SAMPLE_FILE);
//                        }
//                    });
//
//                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//
//                } else {
//                    copyAssets(SAMPLE_FILE);
//                }
//            }
//        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ss");
                String formattedDate = sdf.format(d);
                String fileName = SAMPLE_FILE.substring(SAMPLE_FILE.lastIndexOf('/') + 1);
                Log.d("file_name", fileName);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(SAMPLE_FILE));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, formattedDate + fileName);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
        File file = new File(getFilesDir(), ResponseString.FILE_ANDROID_ASSETS_FOLDER + SAMPLE_FILE);

        try {
            pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .load();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void displayFromUrl(String pdfUrl) {

        try {
            new RetrievePdfStream().execute(pdfUrl);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load Url :" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }



    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressPdfLoader.setVisibility(View.VISIBLE);
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                progressPdfLoader.setVisibility(View.GONE);
                return null;

            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(PDFActivity.this)
                    .enableAnnotationRendering(true)
                    .onLoad(PDFActivity.this)
                    .load();

//            progressPdfLoader.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
        progressPdfLoader.setVisibility(View.GONE);
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    private void copyAssets(String fileName) {

        if (!dir.exists()) {
            dir.mkdirs();

        }
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fileName);
            File outFile = new File(dirPath, fileName);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(this, "Find file in this directory" + dirPath, Toast.LENGTH_LONG).show();

        } catch (IOException io) {
            io.getMessage();
            Toast.makeText(this, "Failure!", Toast.LENGTH_SHORT).show();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(PDFActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
        }
    }

}
