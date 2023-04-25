package com.example.teacherportalactivity.interfaces;

import android.widget.EditText;

public interface SpinnerSelectionListener {
    void onSectionChanged(EditText editText, String Value, int Position);
    void onSectionChanged(EditText editText, StringBuilder Value, int Position);
}