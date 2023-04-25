package com.example.teacherportalactivity.helper;

import static android.os.Build.ID;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.teacherportalactivity.model.ActivityData;
import com.example.teacherportalactivity.model.CountData;
import com.example.teacherportalactivity.model.ResponseCode;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.Result;
import com.example.teacherportalactivity.model.SchoolDetails;
import com.example.teacherportalactivity.model.StarryKnight;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "teacherportalapp.db";
    private static final String ROW_ID = "rowid";
    private static final String TABLE_SCHOOL_LIST = "SchoolListInformation";
    private static final String TABLE_REPORT_DETAIL = "ReportDetail";
    private static final String TABLE_ACTIVITY_DETAIL = "ActivityDetail";
    private static final String TABLE_STARRYKNIGHT_DETAIL = "StarryknightDetail";
    private static final String TABLE_DATA_COUNT = "noofcounttable";


    private static final String STARRYKNIGHT = "starryknight";
    private static final String ABOUTSTARRYKNIGHTTEXT ="starryknighttext";
    private static final String SCHOOL_CODE = "mcm_no";
    private static final String SCHOOL_NAME = "name";
    private static final String SCHOOL_CITY = "city";
    private static final String SCHOOL_STATE_CODE = "state_code";
    private static final String SCHOOL_ADDRESS = "address";
    private static final String THEME = "theme";
    private static final String GRADE = "grade";
    private static final String SEMESTER = "semester";
    private static final String SUBJECT = "subject";
    private static final String LESSONS = "lessons";
    private static final String PERIODS = "period";
    private static final String E5 = "e5";
    private static final String SHOULDOCOUNT ="shoulddocount";
    private static final String MUSTDOCOUNT ="mustdocount";
    private static final String COULDOCOUNT ="coulddocount";
    private static final String E5COUNT ="e5count";
    private static final String DODATA = "dodata";
    private static final String ACTIVITY = "activity";
    private static final String TITLE = "title";
    private static final String SUBTITLE = "subtitle";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORY = "category";
    private static final String CATEGORY1 = "category1";
    private static final String CATEGORY2= "category2";
    private static final String TIME = "time";
    private static final String STATUS = "status";



    public static final String CREATE_TABLE_SCHOOL_LIST = "CREATE TABLE "
            + TABLE_SCHOOL_LIST + "(" + SCHOOL_CODE + " TEXT, " + SCHOOL_NAME + " TEXT, " + SCHOOL_CITY + " TEXT, " + SCHOOL_STATE_CODE + " INT, " + SCHOOL_ADDRESS + " TEXT )";

    public static String CREATE_TABLE_1 = "CREATE TABLE "
            + TABLE_REPORT_DETAIL + "(" + GRADE + " TEXT, " + SUBJECT + " TEXT, " + LESSONS + " TEXT, " + PERIODS + " TEXT, " + E5 + " TEXT, " + DODATA + " TEXT, " + ACTIVITY + " TEXT )";

    public static String CREATE_TABLE_2 = "CREATE TABLE "
            + TABLE_ACTIVITY_DETAIL + "("+ ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT, "  + LESSONS + " TEXT, " + PERIODS + " TEXT, " + TITLE + " TEXT, " + SUBTITLE + " TEXT, " + DESCRIPTION + " TEXT, " + CATEGORY + " TEXT, " + CATEGORY1 + " TEXT, " + CATEGORY2 + " TEXT, " + TIME + " TEXT, " + STATUS + " TEXT )";


    public static String CREATE_TABLE_3 = "CREATE TABLE "
            + TABLE_STARRYKNIGHT_DETAIL + "("+ SEMESTER + " TEXT, "  + SUBJECT + " TEXT, " + THEME + " TEXT, " + LESSONS + " TEXT, " + ABOUTSTARRYKNIGHTTEXT + " TEXT, " + STARRYKNIGHT + " TEXT )";


    public static final String CREATE_TABLE_4= "CREATE TABLE "
            + TABLE_DATA_COUNT +"("+ SUBJECT + " TEXT, " + LESSONS + " TEXT, " + PERIODS + " TEXT, " + MUSTDOCOUNT + " TEXT, " + COULDOCOUNT + " TEXT, " + SHOULDOCOUNT + " TEXT, " + E5COUNT + " TEXT )";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCHOOL_LIST);
        db.execSQL(CREATE_TABLE_4);
        db.execSQL(CREATE_TABLE_3);
        db.execSQL(CREATE_TABLE_2);
        db.execSQL(CREATE_TABLE_1);

    }


    public static synchronized DataBaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DataBaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            if (newVersion == 2) {
               // db.execSQL(CREATE_TABLE_2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.i("tag", oldVersion + " newVersion " + newVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean InsertStarryKnight(String semester,String subject,String theme,String chapter,String starryknighttext, String star) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(SEMESTER,semester);
            contentValues.put(SUBJECT,subject);
            contentValues.put(THEME, theme);
            contentValues.put(LESSONS, chapter);
            contentValues.put(ABOUTSTARRYKNIGHTTEXT,starryknighttext);
            contentValues.put(STARRYKNIGHT, star);


            db.insert(TABLE_STARRYKNIGHT_DETAIL, null, contentValues);


        db.setTransactionSuccessful();
        db.endTransaction();

        return true;
    }

    @SuppressLint("Range")
    public List<StarryKnight> getSkn(String sem, String sub, String thm, String less) {
        List<StarryKnight> starryKnights = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_STARRYKNIGHT_DETAIL + " where " + SEMESTER + "=\"" + sem + "\" And " + SUBJECT + "=\"" + sub + "\" And " + THEME + "=\"" + thm + "\" And " + LESSONS + "=\"" + less + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String semester = cursor.getString(cursor.getColumnIndex(SEMESTER));
                    String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
                    String theme = cursor.getString(cursor.getColumnIndex(THEME));
                    String lessons = cursor.getString(cursor.getColumnIndex(LESSONS));
                    String starryknighttext= cursor.getString(cursor.getColumnIndex(ABOUTSTARRYKNIGHTTEXT));
                    String starryknight = cursor.getString(cursor.getColumnIndex(STARRYKNIGHT));


                    StarryKnight skn = new StarryKnight(semester,subject,theme,lessons,starryknighttext,starryknight);
                    starryKnights.add(skn);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return starryKnights;
    }


    public boolean InsertCOUNTData(List<CountData> countdata) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < countdata.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SUBJECT, countdata.get(i).getSubject());
            contentValues.put(LESSONS, countdata.get(i).getLessons());
            contentValues.put(PERIODS, countdata.get(i).getPeriods());
            contentValues.put(MUSTDOCOUNT, countdata.get(i).getMustdocount());
            contentValues.put(COULDOCOUNT, countdata.get(i).getCoulddocount());
            contentValues.put(SHOULDOCOUNT, countdata.get(i).getShoulddocount());
            contentValues.put(E5COUNT, countdata.get(i).getE5count());
            db.insert(TABLE_DATA_COUNT, null, contentValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return true;
    }
    @SuppressLint("Range")
    public List<CountData> getCount(String sub, String less, String p) {
        List<CountData> countdata = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_DATA_COUNT + " where " + SUBJECT + "=\"" + sub + "\" And " + LESSONS + "=\""
                + less + "\" And " + PERIODS + "=\"" + p + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
                    String lessons = cursor.getString(cursor.getColumnIndex(LESSONS));
                    String periods = cursor.getString(cursor.getColumnIndex(PERIODS));
                    String mustdocount = cursor.getString(cursor.getColumnIndex(MUSTDOCOUNT));
                    String coulddocount = cursor.getString(cursor.getColumnIndex(COULDOCOUNT));
                    String shoulddocount = cursor.getString(cursor.getColumnIndex(SHOULDOCOUNT));
                    String e5count = cursor.getString(cursor.getColumnIndex(E5COUNT));

                    CountData activityd = new CountData(sub,less, p, mustdocount, coulddocount, shoulddocount, e5count);
                    countdata.add(activityd);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return countdata;
    }


    public boolean InsertActivityData(List<ActivityData> activityData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        for (int i = 0; i < activityData.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SUBJECT, activityData.get(i).getSubject());
            contentValues.put(LESSONS, activityData.get(i).getLessons());
            contentValues.put(PERIODS, activityData.get(i).getPeriods());
            contentValues.put(TITLE, activityData.get(i).getTitle());
            contentValues.put(SUBTITLE, activityData.get(i).getSubtitle());
            contentValues.put(DESCRIPTION, activityData.get(i).getDescription());
            contentValues.put(CATEGORY, activityData.get(i).getCategory());
            contentValues.put(CATEGORY1, activityData.get(i).getCategory1());
            contentValues.put(CATEGORY2, activityData.get(i).getCategory2());
            contentValues.put(TIME, activityData.get(i).getTime());
            contentValues.put(STATUS, activityData.get(i).getStatus());
            db.insert(TABLE_ACTIVITY_DETAIL, null, contentValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return true;
    }


    @SuppressLint("Range")
    public List<ActivityData> getActivity(String sub,String less,String p) {
        List<ActivityData> activitydata = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_ACTIVITY_DETAIL
                + " where " + SUBJECT + "=\"" + sub + "\" And " + LESSONS + "=\""
                + less + "\" And " + PERIODS + "=\"" + p + "\" And " + STATUS + "=\"" + "true" + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
                    String lessons = cursor.getString(cursor.getColumnIndex(LESSONS));
                    String periods = cursor.getString(cursor.getColumnIndex(PERIODS));
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    String subtitle = cursor.getString(cursor.getColumnIndex(SUBTITLE));
                    String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    String category = cursor.getString(cursor.getColumnIndex(CATEGORY));
                    String category1 = cursor.getString(cursor.getColumnIndex(CATEGORY1));
                    String category2 = cursor.getString(cursor.getColumnIndex(CATEGORY2));
                    String time = cursor.getString(cursor.getColumnIndex(TIME));
                    String status = cursor.getString(cursor.getColumnIndex(STATUS));

                    ActivityData activityd = new ActivityData(sub,less,p, title, subtitle, description, category, category1,category2, time, status);
                    activitydata.add(activityd);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return activitydata;
    }


    @SuppressLint("Range")
    public List<ActivityData> getSelectedActivity(String sub,String less, String p) {
        List<ActivityData> activitydata = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_ACTIVITY_DETAIL + " where " + SUBJECT + "=\"" + sub + "\" And " + LESSONS + "=\"" + less + "\" And " + PERIODS + "=\"" + p + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                try {
//                    String less= cursor.getString(cursor.getColumnIndex(LESSONS));
//                    String p = cursor.getString(cursor.getColumnIndex(PERIODS));
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    String subtitle = cursor.getString(cursor.getColumnIndex(SUBTITLE));
                    String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    String category = cursor.getString(cursor.getColumnIndex(CATEGORY));
                    String category1 = cursor.getString(cursor.getColumnIndex(CATEGORY1));
                    String category2 = cursor.getString(cursor.getColumnIndex(CATEGORY2));
                    String time = cursor.getString(cursor.getColumnIndex(TIME));
                    String status = cursor.getString(cursor.getColumnIndex(STATUS));
                    ActivityData activityd = new ActivityData(sub, less, p, title, subtitle, description, category, category1, category2, time, status);
                    activitydata.add(activityd);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return activitydata;
    }

    public boolean updateactivitydata(String s1) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + TABLE_ACTIVITY_DETAIL + " SET status = " + "'" + "true" + "' " + "WHERE description = " + "'" + s1 + "'");
        return true;
    }


    public void deleteAllActivity() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ACTIVITY_DETAIL);
        db.close();
    }
    //select * from ReportDetail where grade="3" And subject="English" And lessons="Lesson 1" And period="Period 2"

    public boolean mActivityExist(List<ActivityData> activityData) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exist = false;
        Cursor cursor = null;
        for (int i = 0; i < activityData.size(); i++) {
            try {
                cursor = db.rawQuery("select * from " + TABLE_ACTIVITY_DETAIL + " where " + TITLE + "=\"" + activityData.get(i).getTitle() + "\" And " + SUBTITLE + "=\"" + activityData.get(i).getSubtitle() + "\" And " + DESCRIPTION + "=\"" + activityData.get(i).getDescription() + "\" And " + CATEGORY + "=\"" + activityData.get(i).getCategory() + "\" And " + CATEGORY1 + "=\"" + activityData.get(i).getCategory1() + "\" And " + TIME + "=\"" + activityData.get(i).getTime() + "\" And " + STATUS + "=\"" + activityData.get(i).getStatus() + "\"", null);
                if (cursor.getCount() > 0) {
                    exist = true;
                }
                cursor.close();
            } finally {
                cursor.close();

            }
        }

        return exist;
    }

    @SuppressLint("Range")

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////
    ////////////////////////////////////////////////////
    public boolean InsertGradeData(String grade, String subject, String lessons, String periods, String e5, String dodata, String activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GRADE, grade);
        contentValues.put(SUBJECT, subject);
        contentValues.put(LESSONS, lessons);
        contentValues.put(PERIODS, periods);
        contentValues.put(E5, e5);
        contentValues.put(DODATA, dodata);
        contentValues.put(ACTIVITY, activity);

        db.insert(TABLE_REPORT_DETAIL, null, contentValues);
        db.setTransactionSuccessful();
        db.endTransaction();

        return true;
    }

    public void deleteAllReport() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_REPORT_DETAIL);
        db.close();
    }


    public boolean mReportExist(String grade, String subject, String Lessons, String periods) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exist = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + TABLE_REPORT_DETAIL + " where " + GRADE + "=\"" + grade + "\" And " + SUBJECT + "=\"" + subject + "\" And " + LESSONS + "=\"" + Lessons + "\" And " + PERIODS + "=\"" + periods + "\"", null);
            if (cursor.getCount() > 0) {
                exist = true;
            }
            cursor.close();
        } finally {
            cursor.close();

        }
        return exist;
    }

    @SuppressLint("Range")
    public List<Result> getResultData() {
        List<Result> resultdata = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_REPORT_DETAIL, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    String grade = cursor.getString(cursor.getColumnIndex(GRADE));
                    String subject = cursor.getString(cursor.getColumnIndex(SUBJECT));
                    String lessons = cursor.getString(cursor.getColumnIndex(LESSONS));
                    String Periods = cursor.getString(cursor.getColumnIndex(PERIODS));
                    String e5 = cursor.getString(cursor.getColumnIndex(E5));
                    String dodata = cursor.getString(cursor.getColumnIndex(DODATA));
                    String activity = cursor.getString(cursor.getColumnIndex(ACTIVITY));
                    Result result = new Result(grade, subject, lessons, Periods, e5,dodata,activity);
                    resultdata.add(result);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return resultdata;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////  SCHOOL LIST TABLE   /////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean insertSchoolListData(String schoolCode, String schoolName, String schoolCity, String schoolStateCode, String schoolAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOL_CODE, schoolCode);
        contentValues.put(SCHOOL_NAME, schoolName);
        contentValues.put(SCHOOL_CITY, schoolCity);
        contentValues.put(SCHOOL_STATE_CODE, schoolStateCode);
        contentValues.put(SCHOOL_ADDRESS, schoolAddress);
        db.insert(TABLE_SCHOOL_LIST, null, contentValues);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return true;
    }

    public ArrayList<String> getSchoolList(String schollName) {
        List<SchoolDetails> mSchoolDetailsArrayList = new ArrayList<>();
        ArrayList<String> mSchoolList = new ArrayList<>();
        mSchoolList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String mSchholName = "%" + schollName + "%";
        //cursor = db.rawQuery("select * from " + TABLE_SCHOOL_LIST, null);
        cursor = db.rawQuery("select * from " + TABLE_SCHOOL_LIST + " where " + SCHOOL_NAME + " LIKE \"" + mSchholName + "\"  order by " + SCHOOL_NAME + " asc LIMIT 5", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String schoolName = cursor.getString(cursor.getColumnIndex(SCHOOL_NAME));
                @SuppressLint("Range") String schoolAddress = cursor.getString(cursor.getColumnIndex(SCHOOL_ADDRESS));
                mSchoolList.add(schoolName +
                        "\nAddress:- " + schoolAddress);
            } while (cursor.moveToNext());
        }
        mSchoolList.add("Preschool");
        mSchoolList.add("Others");
        cursor.close();
        db.close();
        return mSchoolList;
    }

    @SuppressLint("Range")
    public String getSchoolCode(String schoolStateCode, String schoolCity, String schoolName, String schoolAddress) {
        String ReturnVale = ResponseString.BLANK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_SCHOOL_LIST + " where " + SCHOOL_STATE_CODE + "=\"" + schoolStateCode + "\" And " + SCHOOL_CITY + "=\"" + schoolCity + "\" And " + SCHOOL_NAME + "=\"" + schoolName + "\" And " + SCHOOL_ADDRESS + "=\"" + schoolAddress + "\"", null);
        if (cursor.moveToFirst()) {
            do {
                ReturnVale = cursor.getString(cursor.getColumnIndex(SCHOOL_CODE));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ReturnVale;
    }

    public boolean mSchoolExist(String schoolCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from " + TABLE_SCHOOL_LIST + " where " + SCHOOL_CODE + "=\"" + schoolCode + "\"", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    public void deleteAllSchool() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SCHOOL_LIST);
        db.close();
    }

//




}
