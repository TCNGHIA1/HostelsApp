package com.pd05529.hostelsapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.models.RoomType;

public class DetailRoomType {
    private TextView tvName, tvId, tvArea, tvPrice, tvMax;
    private AlertDialog dialog;

    public DetailRoomType(Context context, RoomType... args) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_dialog_room_type, null);
        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvArea = view.findViewById(R.id.tvArea);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvMax = view.findViewById(R.id.tvMax);
        //
        RoomType obj = args[0];
        tvId.setText(String.valueOf(obj.getIdType()));
        tvName.setText(obj.getName());

        tvArea.setText(Html.fromHtml(obj.getSquareArea() + " m<sup>2</sup>"));
        tvPrice.setText(obj.getPrice() + " VND");
        tvMax.setText(obj.getMaxMember() + " người");

        //AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
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
