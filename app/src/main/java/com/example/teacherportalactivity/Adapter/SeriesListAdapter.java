package com.example.teacherportalactivity.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.SemTermActivity;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.Data;
import com.example.teacherportalactivity.model.ResponseString;

import java.util.ArrayList;
import java.util.List;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.ViewHolder> implements Filterable {

    private final AppConnectivityManager mAppConnectivityManager;
    private final AppWeakReferenceManager mAppWeakReferenceManager;
    private final Dialog mDialog, mDialogSelectProduct;
    List<Data> listItems, filterList;
    Activity mContext;
    int width;
    int height;
    int listSize;
    private ProgressDialog progressDialog;
    PreferenceHelper pHelper;

    public SeriesListAdapter(List<Data> listItems, Activity context, PreferenceHelper pHelper) {
        this.listItems = listItems;
        mContext = context;
        this.pHelper = pHelper;
        mAppConnectivityManager = AppConnectivityManager.getInstance(mContext);
        mAppWeakReferenceManager = new AppWeakReferenceManager(mContext);
        this.filterList = new ArrayList<Data>();
        mDialog = new Dialog(mContext);
        mDialogSelectProduct = new Dialog(mContext);
        setUpInitDialog();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
        listSize = listItems.size();
    }


    private Button btnVideos;
    private LinearLayout liVideos;
    private ImageView ivVideos;
    private TextView tvPurchaseVideo, text_dialog;

    private void setUpInitDialog() {
        mDialogSelectProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogSelectProduct.setCancelable(true);

        // For voucher Payment


       /* mDialogSelectProduct.setContentView(R.layout.dialog_buy_options);

        text_dialog =mDialogSelectProduct.findViewById(R.id.text_dialog);
        btnVideos =mDialogSelectProduct.findViewById(R.id.btnVideos);
        liVideos =mDialogSelectProduct.findViewById(R.id.liVideos);
        ivVideos =mDialogSelectProduct.findViewById(R.id.ivVideos);
        tvPurchaseVideo =mDialogSelectProduct.findViewById(R.id.tvPurchaseVideo);*/
    }

    String finalMessage = "";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_serieslistitem, parent, false);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // holder.setIsRecyclable(false);
        final Data list_model = filterList.get(position);


        /*FOR IMAGE SHOWN*/
    /*    Glide.with(mContext)
                .load(list_model.getSeriesImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.loader)
                .into(holder.mImageView);*/


        holder.mseriesname.setText(list_model.getSeriesName());

        holder.mserieslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pHelper.setString(ResponseString.BOOK_SELECTION_ID, filterList.get(position).getId().toString());
                pHelper.setString(ResponseString.BOOK_SELECTION_CODE, filterList.get(position).getBook_code());
                pHelper.setString(ResponseString.BOOK_SELECTION_NAME, filterList.get(position).getSeriesName());
                pHelper.setString(ResponseString.SERIES_ID, filterList.get(position).getId().toString());
                pHelper.setString(ResponseString.SERIES_NAME, filterList.get(position).getSeriesName());
                pHelper.setString(ResponseString.SERIES_GRADE, filterList.get(position).getGrade());
                pHelper.setString(ResponseString.PRODUCT_TYPE_KEY, ResponseString.APP_CODE);
                //LoadPackages();
                Intent intent = new Intent(mContext, SemTermActivity.class);
                intent.putExtra("id", pHelper.getString(ResponseString.SERIES_ID, ""));
                intent.putExtra("name", pHelper.getString(ResponseString.SERIES_NAME, ""));

                mContext.startActivity(intent);
            }
        });

    }

//    private void callApiShowBuyOptions(String message, final Data data, final String appCode) {
//
//        mDialog.setContentView(R.layout.dialog_choose_paymnet_type);
//        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        TextView tvVoucher = mDialog.findViewById(R.id.tvVoucher);
//        TextView tvInApp = mDialog.findViewById(R.id.tvInApp);
//        TextView tvMessage = mDialog.findViewById(R.id.tvMessage);
//        TextView tvLabelTitle = mDialog.findViewById(R.id.tvLabelTitle);
//
//
//        //TODO: -------- changes for kitkat supports----------------------------------------
//
//
//        ImageView ivVoucher = mDialog.findViewById(R.id.ivVoucher);
//        ImageView ivInAppPurchase = mDialog.findViewById(R.id.ivInAppPurchase);
//        Drawable drawable = CommonUtils.changeDrawableColor(CommonUtils.setVectorForPreLollipop(R.drawable.book_open_page,mContext),R.color.black,mContext);
//        ivVoucher.setImageDrawable(drawable);
//        ivInAppPurchase.setImageDrawable(drawable);
//
//        //TODO: -------- end here----------------------------------------
//
//
//
//
//        tvLabelTitle.setText("Verify Voucher to Access "+data.getSeriesName());
//        if (message.equals("")){
//            tvMessage.setVisibility(View.GONE);
//        }else {
//            tvMessage.setVisibility(View.VISIBLE);
//            tvMessage.setText(message);
//        }
//        tvVoucher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, VoucherVerify.class);
//                intent.putExtra("book_id",data.getId().toString());
//                intent.putExtra("book_code",data.getBook_code());
//                intent.putExtra("series_name",data.getSeriesName());
//                intent.putExtra("product_buy_type",appCode);
//
//                mContext.startActivity(intent);
//                mDialog.dismiss();
//            }
//        });
//
//
//
//
//        // remove inApp purchaseing
////        tvInApp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (BookBilling.returnbookFoundOrNot(data.getBook_code().toLowerCase())){
////                    Intent inAppPurchaseIntent = new Intent(mContext, BaseGamePlayActivity.class);
////                    inAppPurchaseIntent.putExtra("BOOK_NAME", data.getBook_code().toLowerCase());
////                    mContext.startActivityForResult(inAppPurchaseIntent, 1);
////                }else {
////                    //new CustomToast().Show_Toast(mContext,mContext.getString(R.string.under_develop));
////                    Toast.makeText(mContext,mContext.getString(R.string.under_develop),Toast.LENGTH_LONG).show();
////                }
////                mDialog.dismiss();
////            }
////        });
//
//        mDialog.show();
//    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
//        return listItems.size();
//        return (null != filterList ? filterList.size() : 0);
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        TextView tv;
        TextView mseriesname;
        //        Button btn;
        RelativeLayout mserieslist;

        public ViewHolder(View itemView) {
            super(itemView);
            mserieslist = itemView.findViewById(R.id.mserieslist);
//            tv = itemView.findViewById(R.id.tv);
            mseriesname = itemView.findViewById(R.id.tvseriesname);
//            place_holder=itemView.findViewById(R.id.place_holder);
//        btn=itemView.findViewById(R.id.btn);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = listItems;
                } else {
                    List<Data> filteredList = new ArrayList<>();
                    for (Data row : listItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSeriesName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

