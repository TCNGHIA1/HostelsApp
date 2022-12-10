package com.pd05529.hostelsapp.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.DAO.RegisterDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.support.Converter;

import java.util.Calendar;
import java.util.Date;

public class DialogRegister {
    private AlertDialog dialog;
    private Button btnDateOut,btnDateIn;
    private boolean mode;
    Register register;
    public DialogRegister(Context context, Room room,Register... registers) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_register, null);
        btnDateIn = view.findViewById(R.id.btnDateIn);
        btnDateOut = view.findViewById(R.id.btnDateOut);

        Calendar calendar = Calendar.getInstance();
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);
        if(registers.length>0){
            btnDateIn.setText(Converter.toTimestamp(registers[0].getDateReg()));
            btnDateOut.setText(Converter.toTimestamp(registers[0].getDateOut()));
            mode = true;
        }else{
            mode = false;
        }

        btnDateIn.setOnClickListener(v->{
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    btnDateIn.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            };
            int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        btnDateOut.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    btnDateOut.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            };
            int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, dateSetListener, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        //Show dialog
        String date;
        btnDateIn.getText().toString().trim();
        date = btnDateIn.getText().toString();
        if(date.isEmpty()){
            date = Converter.toTimestamp(new Date());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String finalDate = date;
        builder.setView(view)
                .setNegativeButton("Tiếp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentBR = new Intent();
                        Bundle bundleBR = new Bundle();

                        if(mode){
                            int id = registers[0].getId();
                            register = new Register(id,room.getIdRoom(),registers[0].getDateReg(),Converter.toDate(btnDateOut.getText().toString()));
                            bundleBR.putBoolean("MODE",true);
                            bundleBR.putSerializable("_NEW_REGISTER", register);
                        }else{
                            register = new Register(room.getIdRoom(),
                                    Converter.toDate(finalDate), Converter.toDate(btnDateOut.getText().toString()));
                            bundleBR.putBoolean("MODE",false);
                            bundleBR.putSerializable("_NEW_REGISTER", register);

                        }
                        intentBR.putExtras(bundleBR);
                        intentBR.setAction("_REGISTER_CUSTOMER");
                        context.sendBroadcast(intentBR);
                    }
                })
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }
}
