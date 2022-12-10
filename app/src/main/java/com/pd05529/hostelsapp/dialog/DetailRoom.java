package com.pd05529.hostelsapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.DAO.Reg_infoDAO;
import com.pd05529.hostelsapp.DAO.RegisterDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Reg_info;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.models.RoomType;
import com.pd05529.hostelsapp.support.Converter;

public class DetailRoom {
    TextView tvId,tvName,tvType,tvPrice,tvCount,tvDateReg,tvDateOut;
    ConstraintLayout layoutReg;
    RegisterDAO registerDAO;
    RoomTypeDAO roomTypeDAO;
    CustomerDAO customerDAO;
    AlertDialog dialog;
    public DetailRoom(Context context, Room room, Register... register){
        View view = LayoutInflater.from(context).inflate(R.layout.detail_dialog_room,null);
        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvType = view.findViewById(R.id.tvType);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvCount = view.findViewById(R.id.tvCount);
        tvDateReg = view.findViewById(R.id.tvDateReg);
        tvDateOut = view.findViewById(R.id.tvDateOut);
        layoutReg = view.findViewById(R.id.layoutReg);

        registerDAO = new RegisterDAO(context);
        roomTypeDAO = new RoomTypeDAO(context);
        customerDAO = new CustomerDAO(context);
        tvId.setText(room.getIdRoom());
        tvName.setText(room.getName());
        RoomType roomType = roomTypeDAO.getId(String.valueOf(room.getIdType()));
        tvType.setText(roomType.toString());
        tvPrice.setText(Converter.toStr(roomType.getPrice())+" VNĐ");
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view)
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (register != null && customerDAO.showCustomerIn(room.getIdRoom())!=null) {
            layoutReg.setVisibility(View.VISIBLE);
            int count = customerDAO.showCustomerIn(room.getIdRoom()).size();
            tvCount.setText(String.valueOf(count));
            tvDateReg.setText(Converter.toTimestamp(register[0].getDateReg()));
            tvDateOut.setText(Converter.toTimestamp(register[0].getDateOut()));
            builder.setPositiveButton("Chỉnh sửa đăng kí ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogRegister dialogRegister = new DialogRegister(context,room,register);
                    dialogRegister.show();
                }
            });
        }




        dialog = builder.create();
    }
    public void show(){
        dialog.show();
    }
}
