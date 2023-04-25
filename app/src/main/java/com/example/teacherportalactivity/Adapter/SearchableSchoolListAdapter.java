package com.example.teacherportalactivity.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.interfaces.SpinnerSelectionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SearchableSchoolListAdapter extends RecyclerView.Adapter<SearchableSchoolListAdapter.MyRecyclerViewHolder> implements Filterable {

    private List<String> mList;
    private final Activity mActivity;
    private SearchFilter searchFilter;
    private Integer mClickPositions;
    private Dialog mDialog;
    private final EditText editText;

    public SearchableSchoolListAdapter(Activity mActivity, EditText editText, ArrayList<String> mList, Dialog mDialog) {
        this.mList = mList;
        this.mActivity=mActivity;
        this.mDialog=mDialog;
        this.editText=editText;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder contactViewHolder, int position) {
        contactViewHolder.spinnerText.setTag(position);
        contactViewHolder.spinnerText.setText(mList.get(position));
        contactViewHolder.spinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickPositions = (Integer) view.getTag();
                String Value = mList.get(mClickPositions);
                ((SpinnerSelectionListener)mActivity).onSectionChanged(editText,Value,mClickPositions);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.simple_spinner_items, parent, false);
        return new MyRecyclerViewHolder(view);
    }
    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView spinnerText;
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            spinnerText = itemView.findViewById(R.id.spinnerText);
        }
    }
    @Override
    public Filter getFilter() {

        if (searchFilter == null)
            searchFilter = new SearchFilter(this, mList);
        return searchFilter;
    }

    public void setItems(ArrayList<String> mList) {
        this.mList = mList;
    }

    private static class SearchFilter extends Filter {

        private final SearchableSchoolListAdapter mNearMeListAdapter;
        private final List<String> originalList;
        private final List<String> filteredList;
        private FilterResults results;

        private SearchFilter(SearchableSchoolListAdapter mListAdapter, List<String> originalList) {
            super();
            this.mNearMeListAdapter = mListAdapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toUpperCase().trim();
                for (final String name : originalList) {
                    if ((name.toUpperCase().trim()).contains(filterPattern) ) {
                        filteredList.add(name);
                    }
                }
            }
            results.count = filteredList.size();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mNearMeListAdapter.mList = (ArrayList<String>) results.values;
            mNearMeListAdapter.notifyDataSetChanged();
        }
    }
}
