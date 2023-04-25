package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.Html;
import android.text.style.ReplacementSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.PDFActivity;
import com.example.teacherportalactivity.activity.PDFListActivity;
import com.example.teacherportalactivity.activity.PDFstudentActivity;
import com.example.teacherportalactivity.activity.SubjectionSectionListActivity;
import com.example.teacherportalactivity.activity.ThemeActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SubjectData;
import com.example.teacherportalactivity.model.SubjectionSectionData;

import java.util.ArrayList;
import java.util.List;

public class SubjectSectionAdapter extends RecyclerView.Adapter<SubjectSectionAdapter.ViewHolder> {

    List<SubjectionSectionData> listsubsecitems, filterlist;
    Context mContext;
    int width, height;
    private static PreferenceHelper phelper;
    private ProgressBar simpleProgressBar;

    public SubjectSectionAdapter(List<SubjectionSectionData> list_subjectsection, Context context, String name) {

        this.listsubsecitems = list_subjectsection;
        this.mContext = context;
        this.filterlist = new ArrayList<SubjectionSectionData>();
        this.filterlist.addAll(this.listsubsecitems);
        phelper = new PreferenceHelper(mContext);
        simpleProgressBar = new ProgressBar(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjectsection_list_item, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        return new SubjectSectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final SubjectionSectionData list_subsections = filterlist.get(position);
        holder.tvsubjectSectionname.setText(list_subsections.getSectionName());


        Glide.with(mContext)
                .asBitmap()
                .load(listsubsecitems.get(position).getSectionsIcon())
//          .placeholder(R.drawable.custom_loader)
                .centerInside()
                .into(holder.tvsubjectsectionimg);


        holder.msubjectsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listsubsecitems.get(position).getSectionName().equals("Lesson Plan")) {
                    phelper.setString(ResponseString.SUBJECT_ID, filterlist.get(position).getSubjectId().toString());
                    Intent intent = new Intent(mContext, ThemeActivity.class);
                    intent.putExtra("subject_id", phelper.getString(ResponseString.SUBJECT_ID, ""));
                    intent.putExtra("toolbar", phelper.getString(ResponseString.SUBJECT_ID, ""));
                    mContext.startActivity(intent);
//                } else if (listsubsecitems.get(position).getSectionName().equals("Sample Report")) {
                } else if (listsubsecitems.get(position).getSectionName().equals("Report")) {
                    Intent intent = new Intent(mContext, PDFListActivity.class);
                    mContext.startActivity(intent);
                } else {
                    String ss1 = list_subsections.getSectionUrl();
                    if (ss1 != null) {
                        String url1 = listsubsecitems.get(position).getSectionUrl();
                        Uri urlstr = Uri.parse(url1);
                        Intent urlintent = new Intent();
                        urlintent.setData(urlstr);
                        urlintent.setAction(Intent.ACTION_VIEW);
                        mContext.startActivity(urlintent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvsubjectSectionname;
        ImageView tvsubjectsectionimg;
        RelativeLayout msubjectsec;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvsubjectSectionname = itemView.findViewById(R.id.tvsubjectSectionname);
            tvsubjectsectionimg = itemView.findViewById(R.id.tvsubjectsectionimg);
            msubjectsec = itemView.findViewById(R.id.msubjectsec);

        }
    }
}
