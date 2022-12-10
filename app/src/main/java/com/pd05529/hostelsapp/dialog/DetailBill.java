package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.BillDAO;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.DAO.SerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.support.Converter;

public class DetailBill {
    TextView tvId,tvNumElec,tvElec,tvNumWater,tvWater,tvPriceMore,tvPrice,tvPay,tvOwe,tvPriceOwe;
    TextInputLayout tilNote;
    AlertDialog dialog;
    BillDAO billDAO;
    public DetailBill(Context context, Bill... bills){
        View view = LayoutInflater.from(context).inflate(R.layout.detail_dialog_bill,null);
        tvId =view.findViewById(R.id.tvId);
        tvNumElec =view.findViewById(R.id.tvNumElec);
        tvElec =view.findViewById(R.id.tvElec);
        tvNumWater =view.findViewById(R.id.tvNumWater);
        tvWater =view.findViewById(R.id.tvWater);
        tvPriceMore =view.findViewById(R.id.tvPriceMore);
        tvPrice =view.findViewById(R.id.tvPrice);
        tvPay =view.findViewById(R.id.tvPay);
        tvOwe =view.findViewById(R.id.tvOwePrice);
        tvPriceOwe =view.findViewById(R.id.tvPriceOwe);
        tilNote = view.findViewById(R.id.tilNote);

        billDAO = new BillDAO(context);
        Bill bill = bills[0];

        tvId.setText(bill.getIdRoom());
        int elec = bill.getNewElec()- bill.getOldElec();
        int water = bill.getNewWater()- bill.getOldWater();

        SerDAO serDAO = new SerDAO(context);
        RoomTypeDAO roomTypeDAO = new RoomTypeDAO(context);
        RoomDAO roomDAO = new RoomDAO(context);
        long priceRoom = roomTypeDAO.getId(String.valueOf(roomDAO.getId(bill.getIdRoom()).getIdType())).getPrice();

        long amountWater = water * (serDAO.getId(String.valueOf(2)).getPrice());
        long amountElec = elec * (serDAO.getId(String.valueOf(1)).getPrice());
        tvNumElec.append("\n("+bill.getOldElec()+" - "+ bill.getNewElec()+")");
        tvNumWater.append("\n("+bill.getOldWater()+" - "+ bill.getNewWater()+")");
        tvElec.setText(Converter.toStr(amountElec) +" VNĐ");
        tvWater.setText(Converter.toStr(amountWater)+" VNĐ");
        tvPrice.setText(Converter.toStr(priceRoom)+" VNĐ");
        tvPay.setText(Converter.toStr(bill.getPrePay())+" VNĐ");
        tvPriceMore.setText(Converter.toStr(bill.getMore())+" VNĐ");
        String priceOwe;
        if(bill.getOwe()>=0){
            tvOwe.setText("Tiền khách nợ:");
            priceOwe = Converter.toStr(bill.getOwe())+" VNĐ";
        }else {
            tvOwe.setText("Tiền chủ nợ:");
            long owe = -(bill.getOwe());
            priceOwe = Converter.toStr(owe)+" VNĐ";
            tvPriceOwe.setTextColor(Color.parseColor("#FA312A"));
        }
        tvPriceOwe.setText(priceOwe);

        tilNote.getEditText().setText(bill.getNote());
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view)
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
    }
    public void show(){
        dialog.show();
    }
}
