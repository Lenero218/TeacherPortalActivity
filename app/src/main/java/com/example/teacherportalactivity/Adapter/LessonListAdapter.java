package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.LessonPlanActivity_latest;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.LessonList.LessonListData;
import com.example.teacherportalactivity.model.ResponseString;

import java.util.ArrayList;
import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {

    List<LessonListData> listlesson, filterlist;
    Context mContext;
    int width, height;
    private static PreferenceHelper phelper;

    public LessonListAdapter(List<LessonListData> list_lesson, Context mcontext, String theme_name) {

        this.listlesson = list_lesson;
        this.mContext = mcontext;
        this.filterlist = new ArrayList<LessonListData>();
        this.filterlist.addAll(this.listlesson);
        phelper = new PreferenceHelper(mContext);

    }

    @NonNull
    @Override
    public LessonListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lessonlistitem, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new LessonListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final LessonListData list_lessonitem = filterlist.get(position);
        holder.tvchapternumber.setText(list_lessonitem.getLessonName());
        holder.tvchaptername.setText(list_lessonitem.getLessonChapterName());
        int i = (int) Float.parseFloat(String.valueOf(list_lessonitem.getPercentage()));
        holder.simpleProgressBar.setProgress(i);
        holder.percent_progress.setText(i + "%");


        holder.lessonslinearlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phelper.setString(ResponseString.LESSON_ID, filterlist.get(position).getLessonId().toString());
                phelper.setString(ResponseString.LESSON_NAME, filterlist.get(position).getLessonChapterName());
                Intent i = new Intent(mContext, LessonPlanActivity_latest.class);

                i.putExtra("lessonid", phelper.getString(ResponseString.LESSON_ID, ""));
                i.putExtra("toolbar", filterlist.get(position).getLessonChapterName());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lessonslinearlyt;
        TextView tvchapternumber, tvchaptername, percent_progress;
        ProgressBar simpleProgressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvchaptername = itemView.findViewById(R.id.tvchaptername);
            tvchapternumber = itemView.findViewById(R.id.tvchapternumber);
            lessonslinearlyt = itemView.findViewById(R.id.lessonslinearlyt);
            simpleProgressBar = itemView.findViewById(R.id.simpleProgressBar);
            percent_progress = itemView.findViewById(R.id.percent_progress);
        }
    }
}
