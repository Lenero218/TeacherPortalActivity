package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.ActivityDataClass;
import com.example.teacherportalactivity.helper.PreferenceHelper;

import com.example.teacherportalactivity.model.Activity.ActivityData_new;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.PeriodData;

import java.util.ArrayList;
import java.util.List;

public class PeriodClassListAdapter extends RecyclerView.Adapter<PeriodClassListAdapter.ViewHolder> {

    List<PeriodData> listperiod, filterlist;
    Context context;
    int width, height;
    private static PreferenceHelper phelper;

    public PeriodClassListAdapter(List<PeriodData> period_id, Context mcontext, String periodid) {
        this.listperiod = period_id;
        this.context = mcontext;
        this.filterlist = new ArrayList<PeriodData>();
        this.filterlist.addAll(this.listperiod);
        phelper = new PreferenceHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_periodnewclass, parent, false);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new PeriodClassListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PeriodClassListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final PeriodData list_period = filterlist.get(position);
        holder.tvperiodidds.setText(list_period.getPeriodName());
        holder.lessonslinearlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phelper.setString(ResponseString.PERIOD_ID, filterlist.get(position).getPeriodId().toString());
                phelper.setString(ResponseString.PERIOD_NAME, filterlist.get(position).getPeriodName());
                Intent i = new Intent(context, ActivityDataClass.class);
                i.putExtra("period_id", phelper.getString(ResponseString.PERIOD_ID, ""));
                i.putExtra("period_name", phelper.getString(ResponseString.PERIOD_NAME, ""));

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvperiodidds;
        RelativeLayout lessonslinearlyt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvperiodidds = itemView.findViewById(R.id.tvperiodidds);
            lessonslinearlyt = itemView.findViewById(R.id.lessonslinearlyt);
        }
    }
}
