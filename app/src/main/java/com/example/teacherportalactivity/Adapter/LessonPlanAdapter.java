package com.example.teacherportalactivity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.DescriptionActivity;
import com.example.teacherportalactivity.activity.LessonPlanActivity_latest;
import com.example.teacherportalactivity.activity.Microlessondescription;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.StarryKnight;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.PlanDetail;
import com.google.android.material.transition.Hold;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LessonPlanAdapter extends RecyclerView.Adapter<LessonPlanAdapter.ViewHolder> {

    List<PlanDetail> listlessonid, filterlist;

    Context mContext;
    int width, height;
    private static PreferenceHelper phelper;
    private ProgressBar simpleProgressBar;
    private DataBaseHelper dataBaseHelper;
    private List<StarryKnight> activity1 = new ArrayList<>();
    String thm, less, sub, sem;


    public LessonPlanAdapter(List<PlanDetail> planDetails, Context mcontext, String lesson_id) {

        this.listlessonid = planDetails;
        this.mContext = mcontext;
        this.filterlist = new ArrayList<PlanDetail>();
        this.filterlist.addAll(this.listlessonid);
        phelper = new PreferenceHelper(mContext);
        simpleProgressBar = new ProgressBar(mcontext);
        dataBaseHelper = new DataBaseHelper(mcontext);
        thm = phelper.getString(ResponseString.THEME, "");
        less = phelper.getString(ResponseString.LESSON_NAME, "");
        sem = phelper.getString(ResponseString.SEMESTER_NAME, "");
        sub = phelper.getString(ResponseString.SUBJECT_NAME, "");
    }

    @NonNull
    @Override
    public LessonPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lessoniditem, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        return new LessonPlanAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull LessonPlanAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final PlanDetail list_lessonidsitem = filterlist.get(position);

        holder.tvlessonids.setText(list_lessonidsitem.getLessonPlanName());

        activity1 = dataBaseHelper.getSkn(sem, sub, thm, less);
        if (activity1.size() > 0) {
            Log.e("value", activity1.get(0).getStarryknight());
            holder.textstarryknight.setText(activity1.get(0).getStarryknighttext());
            holder.textstarryknight.setEnabled(false);
            Drawable drawable = holder.ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#0064A8"), PorterDuff.Mode.SRC_ATOP);
            // stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        }


        if (list_lessonidsitem.getLessonPlanName().equals("Starry Knights")) {

            String ss2 = filterlist.get(position).getLessonPlanTitle();
            String ss3 = filterlist.get(position).getLessonPlanDescription();


            if (ss2 == null && ss3 != null) {
                holder.tvlessonplandesc.setVisibility(View.VISIBLE);
                holder.tvlessonplandesc.setText(Html.fromHtml(list_lessonidsitem.getLessonPlanDescription()));
                holder.textstarryknight.setVisibility(View.VISIBLE);
                holder.ratingBar.setVisibility(View.VISIBLE);
                holder.savebtn.setVisibility(View.VISIBLE);
            } else if (ss3 == null && ss2 == null) {
                holder.tvlessonplandesc.setText("");
                holder.tvlessonplandesc.setVisibility(View.VISIBLE);
                holder.textstarryknight.setVisibility(View.VISIBLE);
                holder.ratingBar.setVisibility(View.VISIBLE);
                holder.savebtn.setVisibility(View.VISIBLE);

            } else if (ss2 != null && ss3 != null) {
                holder.tvlessonplandesc.setVisibility(View.VISIBLE);
                holder.tvlessonplandesc.setText(Html.fromHtml(list_lessonidsitem.getLessonPlanDescription()));
                holder.textstarryknight.setVisibility(View.VISIBLE);
                holder.ratingBar.setVisibility(View.VISIBLE);
                holder.savebtn.setVisibility(View.VISIBLE);
            }

        }
        holder.tvlessonids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (list_lessonidsitem.getLessonPlanName().equals("Learning Outcomes") || list_lessonidsitem.getLessonPlanName().equals("Learning Objectives")) {

                    String ss = filterlist.get(position).getLessonPlanTitle();
                    String ss1 = filterlist.get(position).getLessonPlanDescription();


                    if (ss == null || ss1 == null) {
                        phelper.setString(ResponseString.learning_TITLE, "");
                        phelper.setString(ResponseString.learning_DESC, "");
                        Intent i = new Intent(mContext, DescriptionActivity.class);
                        i.putExtra("desc", phelper.getString(ResponseString.learning_DESC, ""));
                        i.putExtra("title", phelper.getString(ResponseString.learning_TITLE, ""));
                        i.putExtra("toolbar", filterlist.get(position).getLessonPlanName());
                        mContext.startActivity(i);

                    } else if (ss != null || ss1 != null) {
                        phelper.setString(ResponseString.learning_TITLE, filterlist.get(position).getLessonPlanTitle());
                        phelper.setString(ResponseString.learning_DESC, filterlist.get(position).getLessonPlanDescription());
                        Intent i = new Intent(mContext, DescriptionActivity.class);
                        i.putExtra("toolbar", filterlist.get(position).getLessonPlanName());
                        i.putExtra("desc", phelper.getString(ResponseString.learning_DESC, ""));
                        i.putExtra("title", phelper.getString(ResponseString.learning_TITLE, ""));
                        mContext.startActivity(i);
                    }

                } else if (list_lessonidsitem.getLessonPlanName().equals("Micro Lesson Plan")) {
                    try {

                        Intent i = new Intent(mContext, Microlessondescription.class);
                        i.putExtra("micro", filterlist.get(position).getLessonPlanDescription());
                        mContext.startActivity(i);

                    } catch (Exception exp) {

                    }


                }

            }
        });

        holder.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataBaseHelper.InsertStarryKnight(phelper.getString(ResponseString.SEMESTER_NAME, ResponseString.BLANK),
                        phelper.getString(ResponseString.SUBJECT_NAME, ResponseString.BLANK),
                        phelper.getString(ResponseString.THEME, ResponseString.BLANK),
                        phelper.getString(ResponseString.LESSON_NAME, ResponseString.BLANK), holder.textstarryknight.getText().toString(), "true");

                Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout lessonsidlinearlyt;
        Button savebtn;
        EditText textstarryknight;
        RatingBar ratingBar;
        TextView tvlessonids, tvlessonplandesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonsidlinearlyt = itemView.findViewById(R.id.lessonsidlinearlyt);
            tvlessonids = itemView.findViewById(R.id.tvlessonids);
            tvlessonplandesc = itemView.findViewById(R.id.tvlessonplandesc);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            savebtn = itemView.findViewById(R.id.savebtn);
            textstarryknight = itemView.findViewById(R.id.textstarryknight);
        }
    }
}
