package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.SubjectionSectionListActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SubjectData;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {


    List<SubjectData> listsubitems, filterlist;
    Context mContext;
    int width, height;
    private PreferenceHelper phelper;


    public SubjectAdapter(List<SubjectData> list_subjects, Context context, String name) {
        this.listsubitems = list_subjects;
        this.mContext = context;
        this.filterlist = new ArrayList<SubjectData>();
        this.filterlist.addAll(this.listsubitems);
        phelper = new PreferenceHelper(mContext);

    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        return new SubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final SubjectData list_subject = filterlist.get(position);
        holder.tvsubjectname.setText(list_subject.getSubject_name());
        String url = listsubitems.get(position).getImages();

        Glide.with(mContext)
                .load(url)
                .thumbnail(Glide.with(mContext).load(R.drawable.circular_progress_bar))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.alert)
                .placeholder(R.drawable.custom_loader)
                .into(holder.tvsubjectimg);
        holder.msubject.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                phelper.setString(ResponseString.SUBJECT_ID, filterlist.get(position).getSubjectid().toString());
                phelper.setString(ResponseString.SUBJECT_NAME, filterlist.get(position).getSubject_name());
                Intent i = new Intent(mContext, SubjectionSectionListActivity.class);
                i.putExtra("subject_name", phelper.getString(ResponseString.SUBJECT_NAME, ""));
                i.putExtra("subject_id", phelper.getString(ResponseString.SUBJECT_ID, ""));
                i.putExtra("toolbar", phelper.getString(ResponseString.SUBJECT_NAME, ""));
                mContext.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout msubject;
        TextView tvsubjectname;
        ImageView tvsubjectimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvsubjectimg = itemView.findViewById(R.id.tvsubjectimg);
            msubject = itemView.findViewById(R.id.msubject);
            tvsubjectname = itemView.findViewById(R.id.tvsubjectname);
        }
    }
}
