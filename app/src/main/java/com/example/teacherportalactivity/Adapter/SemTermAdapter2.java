package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.PDFstudentActivity;
import com.example.teacherportalactivity.activity.SubjectActivity;
import com.example.teacherportalactivity.activity.WebViewActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SemTermData;
import com.example.teacherportalactivity.model.SemTermData2;

import java.util.ArrayList;
import java.util.List;


public class SemTermAdapter2 extends RecyclerView.Adapter<SemTermAdapter2.ViewHolder> {

//    List<SemTermData2> listItems, filterList;
    List<SemTermData> listItems, filterList;
    String toolbar_path;
    Context mContext;
    int width;
    int height;
    private static PreferenceHelper phelper;

    public SemTermAdapter2(List<SemTermData> listItems, Context context, String path) {
        this.listItems = listItems;
        mContext = context;
        toolbar_path = path;
//        this.filterList = new ArrayList<SemTermData2>();
        this.filterList = new ArrayList<SemTermData>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
        phelper = new PreferenceHelper(mContext);

    }

    @Override
    public SemTermAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semterm_list_item2, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        return new SemTermAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SemTermAdapter2.ViewHolder holder, int position) {

//        SemTermData2 list_model2 = filterList.get(position);
        SemTermData list_model2 = filterList.get(position);

//        if (list_model2.getTermSem().equals("Semester 1") || list_model2.getTermSem().equals("Semester 2") || list_model2.getTermSem().equals("Term 1") || list_model2.getTermSem().equals("Term 2") || list_model2.getTermSem().equals("Term 3")) {
//            holder.tvSemTermname.setVisibility(View.GONE);
//        } else {
            holder.tvSemTermname.setText(list_model2.getTermSem());
            holder.mSemTerm.setCardBackgroundColor(Color.parseColor("#01b1ea"));
//        }

        holder.tvSemTermname.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (holder.tvSemTermname.getText().equals("Classroom Management Tips")) {
                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
                    intent.putExtra("pdf_name", "classroom_workshet");
                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("Tricky Questions")) {
                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
                    intent.putExtra("pdf_name", "tricky_workshet");
                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("Introduction")) {
                    Intent intent = new Intent(mContext, PDFstudentActivity.class);
                    intent.putExtra("pdf_name", "introduction");
                    mContext.startActivity(intent);
                } else if (holder.tvSemTermname.getText().equals("eLive Portal")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent i = new Intent(mContext, SubjectActivity.class);
                    i.putExtra("sem_term_id", String.valueOf(list_model2.getId()));
                    i.putExtra("toolbar", list_model2.getTermSem());
                    i.putExtra("series_name", toolbar_path);
                    phelper.setString(ResponseString.SEMESTER_ID, holder.tvSemTermname.getText().toString());
                    mContext.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView mSemTerm;
        TextView tvSemTermname;

        public ViewHolder(View itemView) {
            super(itemView);
            mSemTerm = itemView.findViewById(R.id.mSemTerm2);
            tvSemTermname = itemView.findViewById(R.id.tvSemTermname2);
        }
    }
}

