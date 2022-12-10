package com.pd05529.hostelsapp.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> implements Filterable {
    private List<Room> list;
    private List<Room> newList;
    private Context context;

    private RoomTypeDAO typeDAO;
    private CustomerDAO customerDAO;

    public RoomAdapter(Context context, List<Room> list) {
        this.list = list;
        this.context = context;
        newList = new ArrayList<>(list);
        typeDAO = new RoomTypeDAO(context);
        customerDAO = new CustomerDAO(context);
    }

    public static ItemClickListener itemClickListener;
    public static ItemClickListener itemCancelClickListener;
    public static ItemClickListener itemNewClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        RoomAdapter.itemClickListener = itemClickListener;
    }

    public void setItemCancelClickListener(ItemClickListener itemCancelClickListener) {
        RoomAdapter.itemCancelClickListener = itemCancelClickListener;
    }

    public void setItemNewClickListener(ItemClickListener itemNewClickListener) {
        RoomAdapter.itemNewClickListener = itemNewClickListener;
    }

    public void setList(List<Room> list) {
        list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        Room obj = list.get(position);
        if (customerDAO.showCustomerIn(obj.getIdRoom()) == null) {
            obj.setMode(1);
        } else {
            obj.setMode(0);
        }
        holder.tvName.setText(obj.getName());
        holder.tvType.setText(typeDAO.getId(String.valueOf(obj.getIdType())).getName());
        holder.tvId.setText(obj.getIdRoom());

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(v, position);
            }
        });

        if (obj.getMode() == 0) {
            holder.tvMode.setText("Đã được thuê");
            holder.tvMode.setTextColor(ColorStateList.valueOf(Color.parseColor("#FA312A")));
            holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6C74E1")));
        } else {
            holder.tvMode.setText("Còn trống");
            holder.tvMode.setTextColor(ColorStateList.valueOf(Color.parseColor("#214fc6")));
            holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#696969")));
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public Room getItem(int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvType, tvMode, tvId;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            tvMode = itemView.findViewById(R.id.tvMode);
            tvId = itemView.findViewById(R.id.tvId);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Room> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Room item : newList) {
                    if (item.getIdRoom().toLowerCase().contains(filterPattern) || item.getName().toLowerCase().contains(filterPattern) ||
                            typeDAO.getId(String.valueOf(item.getIdType())).getName().toLowerCase().contains(filterPattern)) {
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
