package com.example.teacherportalactivity.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.model.ActivityData;
import com.example.teacherportalactivity.model.CountData;
import com.example.teacherportalactivity.model.Datumn;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomsheetDailog extends BottomSheetDialogFragment {

    private DataBaseHelper dbhelper;
    private String sub, less, p;
    private PreferenceHelper preferenceHelper;
    List<CountData> coundata = new ArrayList<>();
    List<ActivityData> activitydata = new ArrayList<>();
    private TextView lessons, periodss, mustdocount, shouldocount, coulddocount, e5count;
    private List<Datumn> city;
    int cc;

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    public BottomsheetDailog(List<Datumn> cities,int pos) {
        //Log.e("bhj", cities.getE5());
        city = cities;
        cc = pos;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        e5count = v.findViewById(R.id.e5count);
        mustdocount = v.findViewById(R.id.mustdocount);
        coulddocount = v.findViewById(R.id.couldocount);
        shouldocount = v.findViewById(R.id.shoulddocount);
        mustdocount.setText(String.valueOf(city.get(cc).getNoOfMustDo()) );
        coulddocount.setText(String.valueOf( city.get(cc).getNoOfCouldDo()));
        shouldocount.setText( String.valueOf(city.get(cc).getNoOfShouldDo()));
        e5count.setText(String.valueOf( city.get(cc).getNoOfE5s()));

        return v;
    }

}
