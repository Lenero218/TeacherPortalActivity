package com.example.teacherportalactivity.Adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.ActivityDataClass;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.onClickCheckWithPosition;
import com.example.teacherportalactivity.model.Activity.ActivityData_new;
import com.example.teacherportalactivity.model.Activity.ActivityStatus;
import com.example.teacherportalactivity.model.PostResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ActivityClassAdapter extends RecyclerView.Adapter<ActivityClassAdapter.ViewHolder> {

    List<ActivityData_new> listlesson, filterlist;
    Context mContext;
    JSONArray jsonArray = new JSONArray();
    int width, height;
    Boolean[] expandable_collapse;
    Boolean[] isCheckedList;
    ScaleGestureDetector mScaleGestureDetector;


    public ActivityClassAdapter(List<ActivityData_new> activity_data, Context context, String period_id, int width) {

        this.listlesson = activity_data;
        this.mContext = context;
        this.filterlist = new ArrayList<ActivityData_new>();
        this.filterlist.addAll(this.listlesson);
//        phelper = new PreferenceHelper(mContext);
        expandable_collapse = new Boolean[activity_data.size()];
        isCheckedList = new Boolean[activity_data.size()];
        for (int i = 0; i < expandable_collapse.length; i++) {
            expandable_collapse[i] = true;
            isCheckedList[i] = false;
        }
    }

    @NonNull
    @Override
    public ActivityClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_expandable_listitem, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        return new ActivityClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityClassAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final ActivityData_new list_activitydata = filterlist.get(position);
        if (list_activitydata.getIsCompleted() == 0) {
            holder.checkItem.setEnabled(true);
            if(isCheckedList[position]){
                holder.checkItem.setChecked(true);
            }else{
                holder.checkItem.setChecked(false);
            }
        } else {
            holder.checkItem.setEnabled(false);
            holder.checkItem.setChecked(true);
        }

        String ss4 = list_activitydata.getActivityTime();
        List<ActivityStatus> ss3 = list_activitydata.getActivityStatusList();
        String ss1 = list_activitydata.getActivityTitle();
        String ss2 = list_activitydata.getActivitySubtitle();
        holder.subtitle.setTypeface(null, Typeface.BOLD);
        holder.subtitle.setTextSize(18);

        if (ss1 == null) {
            holder.title.setText("");
        } else {
            holder.title.setText(Html.fromHtml(list_activitydata.getActivityTitle()));
        }
        if (ss2 == null) {
            holder.subtitle.setText("");
        } else {
            holder.subtitle.setText(Html.fromHtml(list_activitydata.getActivitySubtitle()));
        }
        if (ss3.isEmpty()) {
            holder.ll_status_timelayout.setVisibility(GONE);
            holder.checkItem.setVisibility(GONE);
        } else {
            holder.ll_status_timelayout.setVisibility(VISIBLE);
            holder.checkItem.setVisibility(VISIBLE);
        }
        if (ss4 == null) {
//            holder.ll_status_timelayout.setVisibility(GONE);
//            holder.checkItem.setVisibility(GONE);
        } else {
            holder.activitytime.setText(Html.fromHtml(list_activitydata.getActivityTime()));
        }
        holder.subtitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.subtitle.setSelected(true);
        holder.subtitle.setSingleLine(true);

        List<JSONObject> sList = splitHtml(list_activitydata.getActivityDescription());
        holder.ll_description.removeAllViews();
        if (sList != null && sList.size() > 0) {
            for (JSONObject object : sList) {

                if (object.has("data")) {
                    TextView tv = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    tv.setLayoutParams(params);
                    tv.setTextSize(18);
                    tv.setTextColor(mContext.getResources().getColor(R.color.textcolour));
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    try {
                        tv.setText(Html.fromHtml(object.getString("data")));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    holder.ll_description.addView(tv);
                } else {
                    ImageView iv = new ImageView(mContext);
                    iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    try {
                        Glide.with(mContext)
                                .load(object.getString("img"))
                                .thumbnail(Glide.with(mContext).load(R.drawable.circular_progress_bar))
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .error(R.drawable.alert)
                                .into(iv);
                        holder.ll_description.addView(iv);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            TextView tv = new TextView(mContext);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(18);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolour));
            tv.setText("Description");
            holder.ll_description.addView(tv);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recylcler_view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        StatusListAdapter childItemAdapter = new StatusListAdapter(list_activitydata.getActivityStatusList(), mContext);
        holder.recylcler_view.setLayoutManager(layoutManager);
        holder.recylcler_view.setAdapter(childItemAdapter);
        StatusListAdapter horizontalAdapter = new StatusListAdapter(list_activitydata.getActivityStatusList(), mContext);
        holder.recylcler_view.setAdapter(horizontalAdapter);

        holder.expandbleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandable_collapse[position]) {
                    holder.ll_description.setVisibility(GONE);
                    expandable_collapse[position] = false;
                    holder.expandbleimg.animate().rotation(180).setDuration(500).start();
                } else {
                    expandable_collapse[position] = true;
                    holder.ll_description.setVisibility(VISIBLE);
                    holder.expandbleimg.animate().rotation(180).rotation(0).start();

                }
            }
        });

        holder.checkItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                ArrayList<PostResult> postResults = new ArrayList<>();
                JSONObject jsonObject = null;

                if(filterlist.get(position).getIsCompleted() == 0){
//                    filterlist.get(position).setIsCompleted(1);
                    isCheckedList[position] = true;
                }else {
//                    filterlist.get(position).setIsCompleted(0);
                    isCheckedList[position] = false;
                }

                if (holder.checkItem.isChecked()) {
//                    holder.checkItem.setChecked(true);
//                    postResults.add(new PostResult(list_activitydata.getActivityId().toString(), "1"));
//                    for (int i = 0; i < postResults.size(); i++) {
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("activity_id", list_activitydata.getActivityId().toString());
                            jsonObject.put("is_completed", "1");
                        } catch (JSONException exp) {

                        }
//                    }
                    jsonArray.put(jsonObject);
                    ((onClickCheckWithPosition) mContext).onClick(jsonArray);
                    checkbox("u");
                } else {
                    for(int i=0; i<jsonArray.length(); i++){
                        try {
                            String id = jsonArray.getJSONObject(i).getString("activity_id");
                            if(id.equals(list_activitydata.getActivityId().toString())){
                                jsonArray.remove(i);
                                break;
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
//                    jsonArray.remove(jsonArray.length() - 1);
                    ((onClickCheckWithPosition) mContext).onClick(jsonArray);
                }
//                notifyDataSetChanged();
            }
        });


        /*holder.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<PostResult> postResults = new ArrayList<>();
                JSONObject jsonObject = null;

                if(filterlist.get(position).getIsCompleted() == 0){
                    filterlist.get(position).setIsCompleted(1);
                }else {
                    filterlist.get(position).setIsCompleted(0);
                }

                *//*if (holder.checkItem.isChecked()) {
//                    holder.checkItem.setChecked(true);
                    postResults.add(new PostResult(list_activitydata.getActivityId().toString(), "1"));
                    for (int i = 0; i < postResults.size(); i++) {
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("activity_id", postResults.get(i).getActivityId());
                            jsonObject.put("is_completed", postResults.get(i).getIsCompleted());
                        } catch (JSONException exp) {

                        }
                    }
                    jsonArray.put(jsonObject);
                    ((onClickCheckWithPosition) mContext).onClick(jsonArray);
                    checkbox("u");
                } else {
                    jsonArray.remove(jsonArray.length() - 1);
                    ((onClickCheckWithPosition) mContext).onClick(jsonArray);
                }
            }
        });*/
    }

    List<JSONObject> splitHtml(String s) {

        List<JSONObject> sList = new ArrayList();
        String str = s;
        while (str.contains("<img")) {
            String s1 = str.substring(0, str.indexOf("<img"));
            s1 = s1.trim();
            if (!s1.endsWith("</p>")) {
                s1 = s1 + "</p>";
            }
            JSONObject jObj = new JSONObject();
            try {
                jObj.put("data", s1);
                sList.add(jObj);
                str = str.substring(str.indexOf("<img"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            str = str.trim();
            String img = "";
            try {
                img = str.substring(10, str.indexOf("\"/>"));
                str = str.substring(str.indexOf("\"/>"));
                str = str.substring(3);
            } catch (StringIndexOutOfBoundsException es) {
                try {
                    img = str.substring(10, str.indexOf("\" />"));
                    str = str.substring(str.indexOf("\" />"));
                    str = str.substring(4);
                } catch (StringIndexOutOfBoundsException es2) {
                    try {
                        img = str.substring(10, str.indexOf("\">"));
                        str = str.substring(str.indexOf("\">"));
                        str = str.substring(2);
                    } catch (StringIndexOutOfBoundsException es3) {
                        try {
                            img = str.substring(10, str.indexOf("\" >"));
                            str = str.substring(str.indexOf("\" >"));
                            str = str.substring(3);
                        } catch (StringIndexOutOfBoundsException es4) {

                        }
                    }
                }
            }

            JSONObject jObj2 = new JSONObject();
            try {
                jObj2.put("img", img);
                sList.add(jObj2);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            str = str.trim();
            if (str.length() > 0) {
                if (!str.startsWith("<p>")) {
                    str = "<p>" + str;
                }
            }
        }

        if (str.length() > 0) {
            if (!str.startsWith("<p>")) {
                str = "<p>" + str;
            }
            JSONObject jObj3 = new JSONObject();
            try {
                jObj3.put("data", str);
                sList.add(jObj3);
                str = "";
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return sList;
    }

    int getPositionRemove(JSONArray jsonArray, int position) throws JSONException {
        int j = 0;
        JSONArray list = new JSONArray();
        int len = jsonArray.length();
        if (jsonArray != null) {
            for (int i = 0; i < len; i++) {
                //Excluding the item at position
                if (i != position) {
                    list.put(jsonArray.get(i));
                }
            }
        }
        return j;
    }

    private String checkbox(String qq) {
        String s2 = qq;
        if (mContext instanceof ActivityDataClass) {
            ((ActivityDataClass) mContext).showcount(s2);
        }
        return s2;
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardview;
        TextView title, subtitle, activitytime, activitystatus;
        ImageView clock;
        ImageButton expandbleimg;
        CheckBox checkItem;
        RecyclerView recylcler_view;
        LinearLayout ll_description, ll_status_timelayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            activitytime = itemView.findViewById(R.id.activitytime);
            activitystatus = itemView.findViewById(R.id.activitystatus);
            clock = itemView.findViewById(R.id.clock);
            expandbleimg = itemView.findViewById(R.id.expandbleimg);
            checkItem = itemView.findViewById(R.id.checkItem);
            recylcler_view = itemView.findViewById(R.id.recylcler_view);
            ll_status_timelayout = itemView.findViewById(R.id.ll_status_timelayout);
            ll_description = itemView.findViewById(R.id.ll_description);
        }
    }
}

