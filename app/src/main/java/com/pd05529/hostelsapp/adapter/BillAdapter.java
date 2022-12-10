package com.pd05529.hostelsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.support.Converter;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> implements Filterable {
    private List<Bill> list;
    private List<Bill> newList;

    private Context context;
    private RoomTypeDAO roomTypeDAO;
    private RoomDAO roomDAO;
    private static ItemClickListener itemClickListener;

    public BillAdapter(Context context, List<Bill> list) {
        this.list = list;
        this.context = context;
        roomTypeDAO = new RoomTypeDAO(context);
        roomDAO = new RoomDAO(context);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        BillAdapter.itemClickListener = itemClickListener;
    }

    public void setList(List<Bill> list) {
        if (list != null) {
            list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

    }
    public Bill getItem(int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }
    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        Bill obj = list.get(position);
        //id room
        holder.tvId.setText(obj.getIdRoom());
        //number water
        int water = obj.getNewWater() - obj.getOldWater();
        holder.tvWater.setText(water + "");
        //number electric
        int elec = obj.getNewElec() - obj.getOldElec();
        holder.tvElec.setText(elec + "");
        //Price room
        long price = roomTypeDAO.getId(String.valueOf(roomDAO.getId(obj.getIdRoom()).getIdType())).getPrice();
        holder.tvPrice.setText(Converter.toStr(price) + " VNĐ");
        //owe
        holder.tvOwe.setText(Converter.toStr(obj.getOwe()) + " VNĐ");
        //amount
        holder.tvTotal.setText(Converter.toStr(obj.getAmount()) + " VNĐ");
        //date
        holder.tvDate.setText(Converter.toTimestamp(obj.getDate()));
        //click
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(v, position);
            }
        });
        String priceOwe;
        if(obj.getOwe()>=0){
            holder.tvOwe.setText("Tiền khách nợ:");
            priceOwe = Converter.toStr(obj.getOwe())+" VNĐ";
        }else {
            holder.tvOwe.setText("Tiền chủ nợ:");
            long owe = -(obj.getOwe());
            holder.tvOwePrice.setText(owe + "");
            priceOwe = Converter.toStr(owe)+" VNĐ";
        }
        holder.tvOwePrice.setText(priceOwe);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvWater, tvElec, tvTotal, tvOwe, tvDate, tvPrice,tvOwePrice;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvWater = itemView.findViewById(R.id.tvWater);
            tvElec = itemView.findViewById(R.id.tvElec);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvOwePrice = itemView.findViewById(R.id.tvOwePrice);
            tvOwe = itemView.findViewById(R.id.tvOwe);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Bill> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Bill item : newList) {
                    if (item.getIdRoom().toLowerCase().contains(filterPattern)) {
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
