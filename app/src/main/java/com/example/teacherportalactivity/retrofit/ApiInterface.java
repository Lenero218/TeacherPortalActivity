package com.example.teacherportalactivity.retrofit;

import com.example.teacherportalactivity.model.Activity.ActivityModel;
import com.example.teacherportalactivity.model.LessonList.LessonListModel;
import com.example.teacherportalactivity.model.OtpResponseData;
import com.example.teacherportalactivity.model.ResponseData;
import com.example.teacherportalactivity.model.ResultDisplay;
import com.example.teacherportalactivity.model.SemTermModel;
import com.example.teacherportalactivity.model.SemTermModel2;
import com.example.teacherportalactivity.model.SeriesList;
import com.example.teacherportalactivity.model.SubjectModel;
import com.example.teacherportalactivity.model.SubjectionSectionModel;
import com.example.teacherportalactivity.model.Theme.ThemeModel;
import com.example.teacherportalactivity.model.city;
import com.example.teacherportalactivity.model.classes;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.Token;
import com.example.teacherportalactivity.model.school;
import com.example.teacherportalactivity.model.state;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {


//    @Headers({"Content-Type: application/json","companycode: 0001","username: R35","password: R35","employeecode: R35"})
//    @Headers({"Content-Type: application/json","mobile: 9928100718","password: 123456"})


//    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
//    @GET
//    Call<Token> getToken(@Header("Authorization") String authorization ,  @Body HashMap<String, String> userData);

    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @GET
    Call<state> getState(@Header("Authorization") String authorization, @Url String url);

    @Headers({"Content-Type: application/json", "CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("getCities")
    Call<city> getCity(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @Headers({"Content-Type: application/json", "CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("mcmList")
    Call<school> getSchool(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @Headers({"Content-Type: application/json", "CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("getGradeList")
    Call<classes> getClass(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("acUserRegistration")
    Call<ResponseData> registration(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("loginByOtp")
    Call<ResponseData> login(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    //
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    @POST("otpAndVoucherVerification")
    Call<OtpResponseData> verifyOtp(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getGradeListByUser")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<ResponseData> getGradeListByUser(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getSeriesListByGrade")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<SeriesList> getSeries(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getTermSemester")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<SemTermModel> getTermSem(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getTermSemester")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<SemTermModel2> getTermSem2(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getSubjects")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<SubjectModel> getSubject(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);


    @POST("getSubjectSectionsList")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<SubjectionSectionModel> getSubjectSection(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getSubjectThemeList")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<ThemeModel> gettheme(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getThemeLessonList")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<LessonListModel> getlessonlist(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);


    @POST("getlessonperioddetails")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<Token> getlessonid(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);


    @POST("getPeriodActivityDetails")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<ActivityModel> getactivityid(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);


    @POST("postActivityCompletion")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<String> postDataActivityid(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);

    @POST("getActivityResultList")
    @Headers({"CONNECT_TIMEOUT:30000", "READ_TIMEOUT:30000", "WRITE_TIMEOUT:30000"})
    Call<ResultDisplay> getActivityResultList(@Header("Authorization") String authorization, @Body HashMap<String, String> userData);


}
