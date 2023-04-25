package com.example.teacherportalactivity.Adapter;

import android.content.Context;

import com.example.teacherportalactivity.model.Activity.ActivityStatus;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.model.Activity.ActivityStatus;

import java.util.ArrayList;
import java.util.List;

public class StatusListAdapter extends RecyclerView.Adapter<StatusListAdapter.ViewHolder> {


    List<ActivityStatus> liststatus, filterlist;

    Context mContext;
    int width, height;


    public StatusListAdapter(List<ActivityStatus> statusName, Context mContext) {
        this.liststatus = statusName;

        this.filterlist = new ArrayList<ActivityStatus>();
        this.filterlist.addAll(this.liststatus);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public StatusListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_status_list, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new StatusListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusListAdapter.ViewHolder holder, int position) {
        final ActivityStatus list_activitydata = filterlist.get(position);
        holder.activitystatus.setText(list_activitydata.getStatusName());
        holder.llyt.setCardBackgroundColor(Color.parseColor(list_activitydata.getStatusColor()));
        holder.activitystatus.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.activitystatus.setSelected(true);
        holder.activitystatus.setHorizontallyScrolling(true);
        holder.activitystatus.setSingleLine(true);
        Log.e("value", list_activitydata.getStatusName());
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView activitystatus;
        CardView llyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            activitystatus = itemView.findViewById(R.id.activitystatus);
            llyt = itemView.findViewById(R.id.lyt);

        }
    }
}
