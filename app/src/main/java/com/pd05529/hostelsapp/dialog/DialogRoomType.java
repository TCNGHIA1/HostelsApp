package com.pd05529.hostelsapp.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.RoomType;

public class DialogRoomType {
    private TextInputLayout tilName, tilArea, tilPrice, tilMax;
    private AlertDialog dialog;
    private RoomTypeDAO roomTypeDAO;
    private Button btnSave, btnCancel;
    private boolean mode;

    public DialogRoomType(Context context, RoomType... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_room_type, null);
        tilName = view.findViewById(R.id.tilName);
        tilArea = view.findViewById(R.id.tilArea);
        tilPrice = view.findViewById(R.id.tilPrice);
        tilMax = view.findViewById(R.id.tilMax);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        //
        roomTypeDAO = new RoomTypeDAO(context);
        if (args.length > 0 && args != null) {
            RoomType obj = args[0];
            tilArea.getEditText().setText(String.valueOf(obj.getSquareArea()));
            tilName.getEditText().setText(obj.getName());
            tilPrice.getEditText().setText(String.valueOf(obj.getPrice()));
            tilMax.getEditText().setText(String.valueOf(obj.getMaxMember()));
            mode = true;
        } else {
            mode = false;
        }


        //add dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view);
        dialog = builder.create();
        //Event click button

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        btnSave.setOnClickListener(v -> {
            if (!validate(tilName) || !validate(tilArea) || !validate(tilPrice) || !validate(tilMax)) {
                return;
            }
            String name = tilName.getEditText().getText().toString().trim();
            int area = Integer.parseInt(tilArea.getEditText().getText().toString().trim());
            int price = Integer.parseInt(tilPrice.getEditText().getText().toString().trim());
            int max = Integer.parseInt(tilMax.getEditText().getText().toString().trim());
            //BroadCast
            Intent intentBR = new Intent();
            Bundle bundle = new Bundle();
            if (mode) {
                int id = args[0].getIdType();
                RoomType obj = new RoomType(id, name, price, area, max);

                bundle.putBoolean("MODE", true);
                bundle.putSerializable("_ROOMTYPE", obj);
                dialog.dismiss();
            } else {
                RoomType obj = new RoomType(name, price, area, max);
                bundle.putBoolean("MODE", false);
                bundle.putSerializable("_ROOMTYPE", obj);
            }
            intentBR.putExtras(bundle);
            intentBR.setAction("CHECK_ROOMTYPE");
            context.sendBroadcast(intentBR);
            dialog.dismiss();
        });

    }

    private boolean validate(TextInputLayout input) {
        String str = input.getEditText().getText().toString().trim();
        if (str.isEmpty()) {
            input.setError("Không để trống dữ liệu");
            return false;
        } else {
            input.setError("");
            return true;
        }
    }

    public void show() {
        dialog.show();
    }

}
