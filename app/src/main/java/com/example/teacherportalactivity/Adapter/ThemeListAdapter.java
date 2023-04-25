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
import com.example.teacherportalactivity.activity.LessonListActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.Theme.ThemeData;

import java.util.ArrayList;
import java.util.List;

public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.ViewHolder> {

    List<ThemeData> listtheme, filterlist;
    Context mContext;
    int width, height;
    private static PreferenceHelper phelper;

    public ThemeListAdapter(List<ThemeData> list_theme, Context mcontext, String theme_name) {

        this.listtheme = list_theme;
        this.mContext = mcontext;
        this.filterlist = new ArrayList<ThemeData>();
        this.filterlist.addAll(this.listtheme);
        phelper = new PreferenceHelper(mContext);

    }

    @NonNull
    @Override
    public ThemeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_listitem, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new ThemeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ThemeData list_themeitem = filterlist.get(position);
        String s=list_themeitem.getThemeName();
        holder.themenumber.setText(s+":");
        holder.themename.setText(list_themeitem.getThemeTitle());
        holder.themename.setSelected(true);
        holder.mthemeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phelper.setString(ResponseString.THEME_ID,filterlist.get(position).getThemeId().toString());

                Intent intent = new Intent(mContext, LessonListActivity.class);

                intent.putExtra("theme_id",phelper.getString(ResponseString.THEME_ID,""));
                intent.putExtra("toolbar",phelper.getString(ResponseString.SUBJECT_NAME,""));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView themename, themenumber;

        LinearLayout mthemeview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            themename = itemView.findViewById(R.id.themename);
            themenumber = itemView.findViewById(R.id.themenumber);
            mthemeview = itemView.findViewById(R.id.mthemeview);

        }
    }
}
