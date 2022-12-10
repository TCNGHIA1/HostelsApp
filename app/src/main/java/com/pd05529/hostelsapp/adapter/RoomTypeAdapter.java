package com.pd05529.hostelsapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.RoomType;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomTypeViewHolder> implements Filterable {
    private List<RoomType> list;
    private LayoutInflater inflater;
    private List<RoomType> newList;

    public RoomTypeAdapter(Context context, List<RoomType> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        newList = new ArrayList<>(list);
    }

    public static ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        RoomTypeAdapter.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RoomTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item_room_type, parent, false);
        return new RoomTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        RoomType obj = list.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvPrice.setText(Converter.toStr(obj.getPrice()) + " VND");
        holder.tvArea.setText(Html.fromHtml(obj.getSquareArea() + " m<sup>2</sup>"));
        holder.tvMax.setText("Tối đa: " + obj.getMaxMember());
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(v, position);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public RoomType getItem(int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }

    public class RoomTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvArea, tvMax;

        public RoomTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvId);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvMax = itemView.findViewById(R.id.tvMax);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RoomType> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RoomType item : newList) {
                    if (item.getName().toLowerCase().contains(filterPattern)
                    ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
