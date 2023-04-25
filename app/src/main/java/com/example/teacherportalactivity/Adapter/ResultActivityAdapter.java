package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.BottomsheetDailog;
import com.example.teacherportalactivity.model.ClassListData;
import com.example.teacherportalactivity.model.Datumn;
import com.example.teacherportalactivity.model.Result;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultActivityAdapter extends RecyclerView.Adapter<ResultActivityAdapter.ViewHolder> {

    Activity context;
    private List<Datumn> cities;


    public ResultActivityAdapter(Activity context, List<Datumn> cities) {
        this.context = context;
        this.cities = cities;

        Collections.sort(this.cities, new Comparator<Datumn>() {
            @Override
            public int compare(Datumn t2, Datumn t1) {
                return t2.getLessonName().compareTo(t1.getLessonName());
            }
        });

        Collections.sort(this.cities, new Comparator<Datumn>() {
            @Override
            public int compare(Datumn t2, Datumn t1) {
                return t2.getGrade().compareTo(t1.getGrade());
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_resultlistitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Result city = cities.get(position);


        holder.subject.setText(String.valueOf(cities.get(position).getSubjectName()));
        holder.subject.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.subject.setSelected(true);
        holder.subject.setSingleLine(true);

        holder.lessons.setText(cities.get(position).getLessonName());
        holder.lessons.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.lessons.setSelected(true);
        holder.lessons.setSingleLine(true);

        holder.periods.setText(cities.get(position).getPeriodName());
        holder.periods.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.periods.setSelected(true);
        holder.periods.setSingleLine(true);

        holder.activity.setText(cities.get(position).getTotalCompletedActivity() + "/" + cities.get(position).getTotalActivity());
        holder.activity.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.activity.setSelected(true);
        holder.activity.setSingleLine(true);
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomsheetDailog(cities, position);
                bottomSheetDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView lessons, periods, activity, subject;
        private LinearLayout imgaddbtn;
        private ImageButton addbtn;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            subject = view.findViewById(R.id.subject_col);
            lessons = view.findViewById(R.id.lessons_col);
            periods = view.findViewById(R.id.period_col);
            activity = view.findViewById(R.id.activity_col);
            imgaddbtn = view.findViewById(R.id.imgbtn);
            addbtn = view.findViewById(R.id.addbtn);
        }
    }
}

