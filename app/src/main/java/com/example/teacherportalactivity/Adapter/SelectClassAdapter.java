package com.example.teacherportalactivity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.SeriesListActivity;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ClassListData;
import com.example.teacherportalactivity.model.ResponseString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectClassAdapter extends RecyclerView.Adapter<SelectClassAdapter.ViewHolder> {

    List<ClassListData> listItems,filterList;
    Context mContext;
    private PreferenceHelper pHelper;
    private DataBaseHelper dbhelper;



    public SelectClassAdapter(List<ClassListData> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;

//        Collections.sort(listItems, new Comparator<ClassListData>() {
//            @Override
//            public int compare(ClassListData t2, ClassListData t1) {
//
//        return String.format("%100s", t2.getClass_name()).compareTo(String.format("%100s",t1.getClass_name()));
//
//            }
//        });

        Collections.sort(listItems, new Comparator<ClassListData>() {
            @Override
            public int compare(ClassListData t2, ClassListData t1) {

                return String.format("%100s", t2.getGrade()).compareTo(String.format("%100s",t1.getGrade()));

            }
        });
        this.filterList = new ArrayList<ClassListData>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
        pHelper = new PreferenceHelper(mContext);
       dbhelper = new DataBaseHelper(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_class_list_item,parent,false);
//        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        width = windowManager.getDefaultDisplay().getWidth();
//        height = windowManager.getDefaultDisplay().getHeight();

        return new SelectClassAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final SelectClassAdapter.ViewHolder holder, int position) {
        final ClassListData list_model = filterList.get(position);



        //only to show grade: 3

/*        if(list_model.getClass_name().equals("3")){

            holder.mCardView.setVisibility(View.VISIBLE);
            holder.mCardView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        else
        {
            holder.mCardView.setVisibility(View.GONE);
            holder.mCardView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }*/


        holder.tv.setText(list_model.getGrade());
//        holder.mCardView.setLayoutParams(new CardView.LayoutParams((width / 2) - (width / 32), (int) (height / 3)));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = list_model.getGrade();
                String sectionname = list_model.getSections();
                String userName = list_model.getUserName();
                pHelper.setString(ResponseString.CLASS_NAME, className);
                pHelper.setString(ResponseString.USER_ID, String.valueOf(list_model.getUserId()));
                pHelper.setString(ResponseString.MCM_CODE,list_model.getMcmCode());
                pHelper.setString(ResponseString.REG_SECTION_NAME,sectionname);
                pHelper.setString(ResponseString.USER_NAME, userName);

                 Log.e("user_id",String.valueOf(list_model.getUserId()));
                Intent i=new Intent(mContext, SeriesListActivity.class);
                mContext.startActivity(i);
                ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
//        return listItems.size();
//        return (null != filterList ? filterList.size() : 0);
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            mCardView=itemView.findViewById(R.id.mCardView);
            tv = itemView.findViewById(R.id.tvItemName);
        }
    }

}