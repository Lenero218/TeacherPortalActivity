package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.PDFActivity;
import com.example.teacherportalactivity.activity.PDFListActivity;
import com.example.teacherportalactivity.activity.PDFstudentActivity;
import com.example.teacherportalactivity.activity.SubjectActivity;
import com.example.teacherportalactivity.activity.WebViewActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SemTermData;

import java.util.ArrayList;
import java.util.List;


public class SemTermAdapter extends RecyclerView.Adapter<SemTermAdapter.ViewHolder> {

    List<SemTermData> listItems, filterList;
    String toolbar_path;
    Context mContext;
    int width;
    int height;
    private static PreferenceHelper phelper;

    public SemTermAdapter(List<SemTermData> listItems, Context context, String path) {
        this.listItems = listItems;
        mContext = context;
        toolbar_path = path;
        this.filterList = new ArrayList<SemTermData>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
        phelper = new PreferenceHelper(mContext);

    }

    @Override
    public SemTermAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semterm_list_item, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new SemTermAdapter.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final SemTermAdapter.ViewHolder holder, int position) {
        final SemTermData list_model = filterList.get(position);
        if (list_model.getTermSem().equals("eLive Portal") || list_model.getTermSem().equals("Classroom Management Tips") || list_model.getTermSem().equals("Introduction") || list_model.getTermSem().equals("Tricky Questions")) {
//            holder.tvSemTermname.setVisibility(View.GONE);
            holder.mSemTerm.setBackgroundColor(Color.parseColor("#01B1EA"));
        } else {
            holder.mSemTerm.setBackgroundColor(Color.parseColor("#ffffff"));
        }
//        else {
        holder.tvSemTermname.setText(list_model.getTermSem());

        holder.tvSemTermname.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tvSemTermname.setSelected(true);
        holder.tvSemTermname.setSingleLine(true);


//        if (list_model.getStatus() == 1) {
//            holder.tvSemTermname.setVisibility(View.VISIBLE);
//        }else{
//            holder.tvSemTermname.setVisibility(View.GONE);
//        }

        holder.tvSemTermname.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                phelper.setString(ResponseString.SEMESTER_ID, filterList.get(position).getId().toString());
                phelper.setString(ResponseString.SEMESTER_NAME, filterList.get(position).getTermSem());
//                intent.putExtra("id", pHelper.getString(ResponseString.SERIES_ID, ""));
                String seriesName = phelper.getString(ResponseString.SERIES_NAME, "");

                if (holder.tvSemTermname.getText().equals("Classroom Management Tips")) {

                    setCRM();

                    Intent i = new Intent(mContext, PDFActivity.class);
                    i.putExtra("reports", "Classroom Management Tips");
                    mContext.startActivity(i);


//                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
//                    intent.putExtra("pdf_name", "classroom_workshet");
//                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("Tricky Questions")) {
                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
                    intent.putExtra("pdf_name", "tricky_workshet");
                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("Introduction")) {

                    if (seriesName.equals("Peaks")) {
                        Intent i = new Intent(mContext, PDFActivity.class);
                        phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_Intro);
                        i.putExtra("reports", "Introduction");
                        mContext.startActivity(i);
                    } else if (seriesName.equals("Pinnacles")) {
                        Intent i = new Intent(mContext, PDFActivity.class);
                        phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_Intro);
                        i.putExtra("reports", "Introduction");
                        mContext.startActivity(i);
                    }

//                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
//                    intent.putExtra("pdf_name", "Introduction");
//                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("eLive Portal")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent i = new Intent(mContext, SubjectActivity.class);
                    i.putExtra("sem_term_id", phelper.getString(ResponseString.SEMESTER_ID, ""));
                    i.putExtra("toolbar", phelper.getString(ResponseString.SEMESTER_NAME, ""));
//                    i.putExtra("series_name", toolbar_path);

                    mContext.startActivity(i);
                }

            }
        });
    }

    private void setCRM() {
        String selectedGrade = phelper.getString(ResponseString.CLASS_NAME, "");
        String seriesName = phelper.getString(ResponseString.SERIES_NAME, "");
        if (seriesName.equals("Peaks")) {
            switch (selectedGrade) {
                case "1":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_1_2);
                    break;
                case "2":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_1_2);
                    break;
                case "3":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_3_5);
                    break;
                case "4":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_3_5);
                    break;
                case "5":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_3_5);
                    break;
                default:
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Peaks_CRM_3_5);
            }
        } else if (seriesName.equals("Pinnacles")) {

            switch (selectedGrade) {
                case "1":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_1_2);
                    break;
                case "2":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_1_2);
                    break;
                case "3":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_3_5);
                    break;
                case "4":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_3_5);
                    break;
                case "5":
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_3_5);
                    break;
                default:
                    phelper.setString(ResponseString.pdf_url, PDFstudentActivity.Pinnacles_CRM_3_5);
            }
        }
    }

    @Override
    public int getItemCount() {
//        return listItems.size();
//        return (null != filterList ? filterList.size() : 0);
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mSemTerm;
        TextView tvSemTermname;

        public ViewHolder(View itemView) {
            super(itemView);
            mSemTerm = itemView.findViewById(R.id.mSemTerm);
            tvSemTermname = itemView.findViewById(R.id.tvSemTermname);

        }
    }

}

