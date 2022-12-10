package com.pd05529.hostelsapp.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.models.RoomType;
import com.pd05529.hostelsapp.ui.RoomFragment;

public class DialogRoom {
    private final TextInputLayout tilName;
    private final TextInputLayout tilNote;
    private final TextInputLayout tilId;
    private final AlertDialog dialog;

    private final Spinner spnType;
    private final RoomTypeDAO roomTypeDAO;
    private final RoomDAO roomDAO;

    private final Button btnSave;
    private final Button btnCancel;
    private final boolean mode;
    ArrayAdapter<RoomType> typeAdapter;
    Context context;

    public DialogRoom(Context context, Room... args) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_room, null);
        tilName = view.findViewById(R.id.tilName);
        tilNote = view.findViewById(R.id.tilNote);
        tilId = view.findViewById(R.id.tilId);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        spnType = view.findViewById(R.id.spnType);
        //
        roomTypeDAO = new RoomTypeDAO(context);
        roomDAO = new RoomDAO(context);
        if (args.length > 0) {
            Room obj = args[0];
            tilId.getEditText().setText(obj.getIdRoom());
            tilId.setEnabled(false);
            tilName.getEditText().setText(obj.getName());
            tilNote.getEditText().setText(obj.getNote());
            mode = true;
        } else {
            mode = false;
        }
        if (roomTypeDAO.getAll() != null) {
            typeAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, roomTypeDAO.getAll());
            spnType.setAdapter(typeAdapter);
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
            if (!validate(tilId) || !validate(tilName) || !checkSpinner() || !checkValue(tilId)) {
                return;
            }
            String name = tilName.getEditText().getText().toString().trim();
            String id = tilId.getEditText().getText().toString().trim();
            String note = tilNote.getEditText().getText().toString().trim();
            int type = typeAdapter.getItem(spnType.getSelectedItemPosition()).getIdType();


            Intent intentBR = new Intent();
            Bundle bundle = new Bundle();
            if (mode) {
                int roomMode = args[0].getMode();
                Room obj = new Room(id, name, type, roomMode, note);
                bundle.putBoolean("MODE", true);
                bundle.putSerializable("_ROOM", obj);
                dialog.dismiss();
            } else {
                Room obj = new Room(id, name, type, 1, note);
                bundle.putBoolean("MODE", false);
                bundle.putSerializable("_ROOM", obj);
            }
            intentBR.putExtras(bundle);
            intentBR.setAction("CHECK_ROOM");
            context.sendBroadcast(intentBR);
            dialog.dismiss();

        });

    }

    private boolean checkValue(TextInputLayout inputLayout) {
        String str = inputLayout.getEditText().getText().toString().trim().toUpperCase();
        if (roomDAO.getId(str) != null) {
            inputLayout.setError("Mã khách hàng đã tồn tại!");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSpinner() {
        if (spnType.getCount() == 0) {
            Toast.makeText(context, "Vui lòng tạo mới loại phòng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
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
