package com.pd05529.hostelsapp.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.pd05529.hostelsapp.DAO.BillDAO;
import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.DAO.Reg_infoDAO;
import com.pd05529.hostelsapp.DAO.RegisterDAO;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.adapter.BillAdapter;
import com.pd05529.hostelsapp.adapter.CustomerAdapter;
import com.pd05529.hostelsapp.dialog.DetailBill;
import com.pd05529.hostelsapp.dialog.DetailCustomer;
import com.pd05529.hostelsapp.dialog.DetailRoom;
import com.pd05529.hostelsapp.dialog.DialogBill;
import com.pd05529.hostelsapp.dialog.DialogCustomer;
import com.pd05529.hostelsapp.dialog.DialogReg_info;
import com.pd05529.hostelsapp.dialog.DialogRegister;
import com.pd05529.hostelsapp.models.Bill;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.models.Reg_info;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.models.RoomType;

import java.util.List;
import java.util.Objects;


public class DetailRoomFragment extends Fragment {

    public static DetailRoomFragment newInstance() {
        DetailRoomFragment fragment = new DetailRoomFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView tvName, tvArea, tvPrice, tvAddCus, tvAddBill, tvNullCus, tvNullBill, tvDetail;
    RecyclerView rclCustomer, rclBill;
    //DAO
    CustomerDAO customerDAO;
    RoomTypeDAO roomTypeDAO;
    BillDAO billDAO;
    RoomDAO roomDAO;
    RegisterDAO registerDAO;
    Reg_infoDAO reg_infoDAO;
    //object
    RoomType _objType;
    Room _obj;

    //Adapter
    BillAdapter billAdapter;
    CustomerAdapter customerAdapter;
    DetailRoomFragment current = this;

    List<Customer> list, cuslist;
    int count;
    int member;

    Register register, reg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_room, container, false);
        //
        //receive data from room activity
        Bundle bundle = this.getArguments();
        _obj = (Room) bundle.getSerializable("_ROOM_DETAIL");
        reg = (Register) bundle.getSerializable("_REG_DETAIL");
        //mapping
        rclBill = view.findViewById(R.id.rclBill);
        rclCustomer = view.findViewById(R.id.rclCustomer);
        rclBill = view.findViewById(R.id.rclBill);
        tvName = view.findViewById(R.id.tvId);
        tvArea = view.findViewById(R.id.tvArea);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvAddBill = view.findViewById(R.id.tvAddBill);
        tvAddCus = view.findViewById(R.id.tvAddCus);
        tvNullBill = view.findViewById(R.id.tvNullBill);
        tvNullCus = view.findViewById(R.id.tvNullCus);
        tvDetail = view.findViewById(R.id.tvDetail);
        //dao
        roomTypeDAO = new RoomTypeDAO(getActivity());
        customerDAO = new CustomerDAO(getActivity());
        roomDAO = new RoomDAO(getActivity());
        billDAO = new BillDAO(getActivity());
        registerDAO = new RegisterDAO(getActivity());
        reg_infoDAO = new Reg_infoDAO(getActivity());
        //roomType
        _objType = roomTypeDAO.getId(String.valueOf(_obj.getIdType()));
        //fill data
        tvName.setText(_obj.getName());
        tvArea.setText(Html.fromHtml(_objType.getSquareArea() + " m<sup>2</sup>"));
        tvPrice.setText(_objType.getPrice() + " VND");

        list = customerDAO.showCustomerIn(_obj.getIdRoom());
        register = registerDAO.getId(_obj.getIdRoom());
        if(list ==null && register!=null){
            registerDAO.deleteRoom(_obj.getIdRoom());
            reg_infoDAO.deleteReg(String.valueOf(register.getId()));
        }

        return view;
    }

    IntentFilter intentFilter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        intentFilter = new IntentFilter();
        intentFilter.addAction("_REG_ACTION");
        intentFilter.addAction("_BILL_ACTION");
        intentFilter.addAction("_REGISTER_CUSTOMER");
        //rcl
        rclCustomer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rclBill.setLayoutManager(new LinearLayoutManager(getActivity()));
        resetAdapter();

        customerAdapter.setItemClickListener((v, position) -> {
            showCusPopupMenu(v, position);
        });
        billAdapter.setItemClickListener((v, position) -> {
            showBillPopupMenu(v, position);
        });


        tvAddCus.setOnClickListener(v -> {
            list = customerDAO.showCustomerIn(_obj.getIdRoom());
            register = registerDAO.getId(_obj.getIdRoom());
            //Count Customer in room
            if (list == null) {
                count = 0;
            } else {
                count = list.size();
            }

            //max member RoomType
            cuslist = customerDAO.showCustomer();
            member = roomTypeDAO.getId(String.valueOf(_obj.getIdType())).getMaxMember();


            if (cuslist == null) {
                Toast.makeText(getActivity(), "Tất cả khách đã đăng kí!", Toast.LENGTH_SHORT).show();
            } else if (count >= member) {
                Toast.makeText(getActivity(), "Số người trong phòng đã tối đa!", Toast.LENGTH_SHORT).show();
            }else if(list ==null && register!=null){
                registerDAO.deleteRoom(_obj.getIdRoom());
                reg_infoDAO.deleteReg(String.valueOf(register.getId()));
                DialogRegister dialogRegister = new DialogRegister(getActivity(),_obj);
                dialogRegister.show();
            }else if(register!=null){
                DialogReg_info dialogReg_info = new DialogReg_info(getActivity(),register);
                dialogReg_info.show();
            }else {
                DialogRegister dialogRegister = new DialogRegister(getActivity(),_obj);
                dialogRegister.show();
            }
        });
        tvAddBill.setOnClickListener(v -> {
            if (roomDAO.showRoom() == null) {
                Toast.makeText(getActivity(), "Tất cả phòng không có khách trọ", Toast.LENGTH_SHORT).show();
            } else {
                dialogBill = new DialogBill(getActivity(), _obj);
                dialogBill.show();
            }
        });

        tvDetail.setOnClickListener(v -> {
            DetailRoom detailRoom = new DetailRoom(getActivity(), _obj, register);
            detailRoom.show();
        });
    }

    DialogBill dialogBill;

    private void showBillPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(current.getActivity(), view);
        popupMenu.inflate(R.menu.popup_menu);
        Bill obj = billAdapter.getItem(position);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_info: {
                        DetailBill dialog = new DetailBill(getActivity(), obj);
                        dialog.show();
                        return true;
                    }
                    case R.id.popup_update: {
                        dialogBill = new DialogBill(getActivity(), null, obj);
                        dialogBill.show();
                        return true;
                    }
                    case R.id.popup_delete: {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                                .setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn là muốn xóa?")
                                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //delete room from table
                                        billDAO.delete(String.valueOf(obj.getIdBill()));
                                        resetAdapter();
                                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    DialogCustomer dialogCustomer;

    private void showCusPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(current.getActivity(), view);
        popupMenu.inflate(R.menu.popup_menu);
        Customer obj = customerAdapter.getItem(position);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_info: {
                        DetailCustomer dialog = new DetailCustomer(getActivity(), obj);
                        dialog.show();
                        return true;
                    }
                    case R.id.popup_update: {
                        dialogCustomer = new DialogCustomer(getActivity(), obj);
                        dialogCustomer.show();
                        return true;
                    }
                    case R.id.popup_delete: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                .setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn không?")
                                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        reg_infoDAO.deleteCustom(obj.getIdCus());
                                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        resetAdapter();
                                    }
                                });
                        builder.create().show();
                        return true;
                    }
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle;
            Register register;
            switch (intent.getAction()) {
                case "_REG_ACTION": {
                    bundle = intent.getExtras();
                    //get data customer - register
                    Reg_info reg = (Reg_info) bundle.getSerializable("_REG_CUS");
                    register = (Register) bundle.getSerializable("_REG_ROOM");
                    Room room = roomDAO.getId(register.getIdRoom());
                    if (reg_infoDAO.insert(reg) != 1) {
                        Toast.makeText(context, "Đăng ký trọ thất bại!", Toast.LENGTH_SHORT).show();
                    } else {
                        room.setMode(0);
                        roomDAO.update(room);
                        Toast.makeText(context, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case "_REGISTER_CUSTOMER": {
                    bundle = intent.getExtras();
                    register = (Register) bundle.getSerializable("_NEW_REGISTER");
                    boolean mode = bundle.getBoolean("MODE");
                    if (mode) {
                        if(registerDAO.update(register)!=1){
                            Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if (registerDAO.insert(register) != 1) {
                            Toast.makeText(context, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                        } else if (getActivity() != null) {
                            DialogReg_info dialogReg_info = new DialogReg_info(getActivity(), registerDAO.getId(_obj.getIdRoom()));
                            dialogReg_info.show();
                        }
                    }
                    break;
                }
                case "_BILL_ACTION": {
                    bundle = intent.getExtras();
                    boolean mode = bundle.getBoolean("_MODE_BILL");
                    Bill obj = (Bill) bundle.getSerializable("_BILL_OBJ");
                    Log.i("//CHECK_BILL", obj.toString());
                    if (mode) {
                        billDAO.update(obj);
                        Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (billDAO.insert(obj) != 1) {
                            Toast.makeText(context, "Thêm mới thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
            resetAdapter();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private void resetAdapter() {
        customerAdapter = new CustomerAdapter(requireActivity().getApplicationContext(), customerDAO.showCustomerIn(_obj.getIdRoom()), 2);
        rclCustomer.setAdapter(customerAdapter);
        billAdapter = new BillAdapter(requireActivity().getApplicationContext(), billDAO.showBillRoom(_obj.getIdRoom()));
        rclBill.setAdapter(billAdapter);
    }
}