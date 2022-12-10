package com.pd05529.hostelsapp.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.DAO.SerDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Calendar;
import java.util.Date;

public class DialogBill {
    private AlertDialog dialog;
     TextInputLayout tilOldWater, tilNewWater, tilOldElec, tilNewElec, tilSerMore, tilPrice, tilCus;
     TextView tvPhong;
     Button btnSave, btnCancel,btnDate;
     TextInputLayout tilNote;
     Spinner spnRoom;
    private boolean mode;
    private RoomDAO roomDAO;
    private RoomTypeDAO roomTypeDAO;
    ArrayAdapter<Room> adapter;
    long priceRoom;

    public DialogBill(Context context,Room room, Bill... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bill, null);
        tilOldWater = view.findViewById(R.id.tilOldWater);
        tilOldElec = view.findViewById(R.id.tilOldElec);
        tilNewWater = view.findViewById(R.id.tilNewWater);
        tilNewElec = view.findViewById(R.id.tilNewElec);
        tilSerMore = view.findViewById(R.id.tilSerMore);
        tilPrice = view.findViewById(R.id.tilPrice);
        tilCus = view.findViewById(R.id.tilCus);
        spnRoom = view.findViewById(R.id.spnRoom);
        tvPhong = view.findViewById(R.id.tvPhong);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnDate = view.findViewById(R.id.btnDate);
        tilNote = view.findViewById(R.id.tilNote);

        roomTypeDAO = new RoomTypeDAO(context);
        roomDAO = new RoomDAO(context);
        if (args.length > 0) {
            spnRoom.setVisibility(View.GONE);
            tvPhong.setText("Phòng: " + args[0].getIdRoom());
            tilOldWater.getEditText().setText(""+args[0].getOldWater());
            tilOldElec.getEditText().setText(""+args[0].getOldElec());
            tilNewWater.getEditText().setText(""+args[0].getNewWater());
            tilNewElec.getEditText().setText(""+args[0].getNewElec());
            tilCus.getEditText().setText(""+args[0].getPrePay());//Tiền khách trả
            tilSerMore.getEditText().setText(""+args[0].getMore());//Tiền dịch vụ khác
            tilNote.getEditText().setText(args[0].getNote());
            priceRoom = roomTypeDAO.getId(String.valueOf(roomDAO.getId(args[0].getIdRoom()).getIdType())).getPrice();
            tilPrice.setVisibility(View.VISIBLE);
            tilPrice.getEditText().setText(priceRoom + "");
            mode = true;
        } else {
            if(room==null){
                adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, roomDAO.showRoom());
                spnRoom.setAdapter(adapter);
            }else{
                tvPhong.setText("Phòng: " + room.getIdRoom());
                spnRoom.setVisibility(View.GONE);
            }
            btnDate.setText(Converter.toTimestamp(new Date()));
            mode = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        dialog = builder.create();

        btnDate.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    btnDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            if (!validate(tilOldWater) || !validate(tilNewWater) || !validate(tilOldElec) || !validate(tilNewElec)
            || !validate(tilOldElec,tilNewElec)||!validate(tilOldWater,tilNewWater)){
                Toast.makeText(context, "Vui lòng điền chính xác!", Toast.LENGTH_SHORT).show();
                return;
            }
            int oldWater = Integer.parseInt(tilOldWater.getEditText().getText().toString().trim());
            int oldElec = Integer.parseInt(tilOldElec.getEditText().getText().toString().trim());
            int newWater = Integer.parseInt(tilNewWater.getEditText().getText().toString().trim());
            int newElec  = Integer.parseInt(tilNewElec.getEditText().getText().toString().trim());

            long more;
            if(tilSerMore.getEditText().getText().toString().trim().isEmpty()){
                more = 0;
            }else{
                more = Long.parseLong(tilSerMore.getEditText().getText().toString().trim());
            }

            long pay;
            if(tilCus.getEditText().getText().toString().trim().isEmpty()){
                pay = 0;
            }else{
                pay = Long.parseLong(tilCus.getEditText().getText().toString().trim());
            }
            String note = tilNote.getEditText().getText().toString().trim();
            String idRoom;
            SerDAO serDAO = new SerDAO(context);
            long amountWater = (long) (newWater - oldWater) * (serDAO.getId(String.valueOf(2)).getPrice());
            long amountElec = (long) (newElec - oldElec) * (serDAO.getId(String.valueOf(1)).getPrice());
            long amount;
            long owe;
            Intent intentBR = new Intent();
            Bundle bundleBR = new Bundle();
            Bill obj;
            if (mode) {
                amount = amountElec + amountWater + more + priceRoom;//amount room
                owe = amount - pay;

                int id = args[0].getIdBill();
                idRoom = args[0].getIdRoom();
                obj = new Bill(id, idRoom, amount, pay, owe, more, args[0].getDate(), note, oldWater, newWater, oldElec, newElec);
                bundleBR.putBoolean("_MODE_BILL", true);
                dialog.dismiss();
            } else {
                if (room == null) {
                    priceRoom = roomTypeDAO.getId(String.valueOf(adapter.getItem(spnRoom.getSelectedItemPosition()).getIdType())).getPrice();
                    idRoom = adapter.getItem(spnRoom.getSelectedItemPosition()).getIdRoom();
                }else{
                    idRoom = room.getIdRoom();
                    priceRoom = roomTypeDAO.getId(String.valueOf(room.getIdType())).getPrice();
                }
                amount = amountElec + amountWater + more + priceRoom;
                owe = amount - pay;
                obj = new Bill(idRoom, amount, pay, owe, more, Converter.toDate(btnDate.getText().toString()), note, oldWater, newWater, oldElec, newElec);
                bundleBR.putBoolean("_MODE_BILL", false);
            }
            bundleBR.putSerializable("_BILL_OBJ", obj);
            intentBR.putExtras(bundleBR);
            intentBR.setAction("_BILL_ACTION");
            context.sendBroadcast(intentBR);
            dialog.dismiss();
        });


    }


    public void show() {
        dialog.show();
    }

    private boolean validate(TextInputLayout tilitText) {
        String str = tilitText.getEditText().getText().toString().trim();
        if (str.isEmpty()) {
            tilitText.setError("Không để trống dữ liệu!");
            return false;
        } else {
            tilitText.setError(null);
            return true;
        }
    }
    private boolean validate(TextInputLayout til1, TextInputLayout til2){
        int str1 = Integer.parseInt(til1.getEditText().getText().toString().trim());
        int str2 = Integer.parseInt(til2.getEditText().getText().toString().trim());
        if (str2 < str1) {
            til2.setError("Vui lòng nhập lại!");
            return false;
        }
        return true;
    }
}
