package com.reail.point.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reail.point.R;
import com.reail.point.model.SearchData;

import java.util.ArrayList;
import java.util.List;

public class SearchAdpter extends RecyclerView.Adapter<SearchAdpter.ViewHolder> implements Filterable {

    Context context;
    int type = 0;
    ArrayList<SearchData> data, dataFiltered;
    private IonItemSelect ionItemSelect;

    public SearchAdpter(Context context, ArrayList<SearchData> data, int type) {
        this.context = context;
        this.data = data;
        this.dataFiltered = data;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_rv, parent, false);
        return new ViewHolder(view);
    }

    public void registerOnItemClickListener(IonItemSelect ionItemSelect) {
        this.ionItemSelect = ionItemSelect;
    }

    @Override
    public void onBindViewHolder(SearchAdpter.ViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvStatus.setText(data.get(position).getStatus());
        if (type == 1) {
            holder.tvLat.setText("lat: " + data.get(position).getLat());
            holder.tvLong.setText("long: " + data.get(position).getLog());
        } else {
            holder.tvLat.setText(data.get(position).getLat());
            holder.tvLong.setText(data.get(position).getLog());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    data = dataFiltered;
                } else {
                    ArrayList<SearchData> filteredList = new ArrayList<>();
                    for (SearchData row : dataFiltered) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    data = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<SearchData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvStatus, tvLat, tvLong;
        LinearLayout ll_body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvStatus = itemView.findViewById(R.id.status);
            tvLat = itemView.findViewById(R.id.lat);
            tvLong = itemView.findViewById(R.id.log);
            ll_body = itemView.findViewById(R.id.ll_body);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (ionItemSelect != null)
                ionItemSelect.onItemSelect(getAdapterPosition());
        }
    }

    public interface IonItemSelect {
        void onItemSelect(int position);
    }
}
