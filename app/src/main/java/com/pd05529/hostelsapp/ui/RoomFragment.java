package com.pd05529.hostelsapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pd05529.hostelsapp.DAO.CustomerDAO;
import com.pd05529.hostelsapp.DAO.Reg_infoDAO;
import com.pd05529.hostelsapp.DAO.RegisterDAO;
import com.pd05529.hostelsapp.DAO.RoomDAO;
import com.pd05529.hostelsapp.DAO.RoomTypeDAO;
import com.pd05529.hostelsapp.R;
import com.pd05529.hostelsapp.adapter.RoomAdapter;
import com.pd05529.hostelsapp.dialog.DialogReg_info;
import com.pd05529.hostelsapp.dialog.DialogRegister;
import com.pd05529.hostelsapp.dialog.DialogRoom;
import com.pd05529.hostelsapp.models.Customer;
import com.pd05529.hostelsapp.models.Reg_info;
import com.pd05529.hostelsapp.models.Register;
import com.pd05529.hostelsapp.models.Room;
import com.pd05529.hostelsapp.models.RoomType;

import java.util.List;


public class RoomFragment extends Fragment {

    public RoomFragment() {
        // Required empty public constructor
    }


    public static RoomFragment newInstance() {
        RoomFragment fragment = new RoomFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    private FloatingActionButton open_btn, add_btn, del_btn;
    private RecyclerView recyclerView;
    private TextView tv_add_btn, tv_del_btn;
    private RoomFragment current = this;
    private boolean clicked = false;

    public RoomDAO roomDAO;
    private RoomAdapter roomAdapter;
    private DialogRoom dialogRoom;
    private RoomTypeDAO roomTypeDAO;
    private CustomerDAO customerDAO;
    private RegisterDAO registerDAO;
    private Reg_infoDAO reg_infoDAO;
    //intentFilter
    IntentFilter intentFilter;

    Room _obj;

    List<Customer> list,cuslist;
    Register register;
    int count;
    int member;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        //
        intentFilter = new IntentFilter();
        intentFilter.addAction("_REG_ACTION");
        intentFilter.addAction("_REGISTER_CUSTOMER");
        intentFilter.addAction("CHECK_ROOM");
        //mapping
        open_btn = view.findViewById(R.id.open_btn);
        add_btn = view.findViewById(R.id.add_btn);
        del_btn = view.findViewById(R.id.del_btn);
        tv_del_btn = view.findViewById(R.id.tv_del_btn);
        tv_add_btn = view.findViewById(R.id.tv_add_btn);
        //list


        //button floating
        open_btn.setOnClickListener(v -> {
            onAddButtonClicked();

        });
        add_btn.setOnClickListener(v -> {
            dialogRoom = new DialogRoom(getActivity());
            dialogRoom.show();
        });
        del_btn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Xóa tất cả dữ liệu")
                    .setMessage("Bạn có chắc chắn không?")
                    .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            roomDAO.deleteAll();
                            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            resetAdapter();
                            registerDAO.deleteAll();
                        }
                    })
                    .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        });
        //dao
        roomDAO = new RoomDAO(getActivity());
        roomTypeDAO = new RoomTypeDAO(getActivity());
        customerDAO = new CustomerDAO(getActivity());
        registerDAO = new RegisterDAO(getActivity());
        reg_infoDAO = new Reg_infoDAO(getActivity());
        //recyclerview
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        roomAdapter = new RoomAdapter(getActivity(), roomDAO.getAll());
        resetAdapter();

        //Delete room not register
        for (Room room: roomDAO.getAll()) {
            list = customerDAO.showCustomerIn(room.getIdRoom());
            register = registerDAO.getId(room.getIdRoom());
            if(list ==null && register!=null){
                registerDAO.deleteRoom(room.getIdRoom());
                reg_infoDAO.deleteReg(String.valueOf(register.getId()));
            }
        }


        //button adapter
        roomAdapter.setItemClickListener((v, position) -> {
            _obj = roomAdapter.getItem(position);



            PopupMenu popupMenu = new PopupMenu(current.getActivity(), v);
            popupMenu.inflate(R.menu.room_popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.popup_info: {
                            //go to detail room activity
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("_ROOM_DETAIL", _obj);
                            bundle.putSerializable("_REG_DETAIL",register);

                            DetailRoomFragment detailRoomFragment = new DetailRoomFragment();
                            detailRoomFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, detailRoomFragment).commit();
                            break;
                        }
                        case R.id.popup_reg: {
                            //Kiểm tra phòng có đăng kí hay chưa
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
                                Toast.makeText(getActivity(), "Không có khách trống!", Toast.LENGTH_SHORT).show();
                            } else if (count >= member) {
                                    Toast.makeText(getActivity(), "Số người trong phòng đã tối đa!", Toast.LENGTH_SHORT).show();
                            }else if(register!=null){
                                DialogReg_info dialogReg_info = new DialogReg_info(getActivity(),register);
                                dialogReg_info.show();
                            }else {
                                DialogRegister dialogRegister = new DialogRegister(getActivity(),_obj);
                                dialogRegister.show();
                            }
                                return true;
                        }
                            case R.id.popup_update: {
                                //Update information room
                                dialogRoom = new DialogRoom(getActivity(), _obj);
                                dialogRoom.show();
                                return true;
                            }
                            case R.id.popup_delete: {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                        .setTitle("Xác nhận")
                                        .setMessage("Bạn có chắc chắn là muốn xóa?")
                                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //delete room from table
                                                roomDAO.delete(_obj.getIdRoom());
                                                resetAdapter();
                                                Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                                registerDAO.deleteRoom(_obj.getIdRoom());
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
            });
        }


        @Override
        public void onCreateOptionsMenu (@NonNull Menu menu, @NonNull MenuInflater inflater){
            super.onCreateOptionsMenu(menu, inflater);
            getActivity().getMenuInflater().inflate(R.menu.menu, menu);
            MenuItem searchItem = menu.findItem(R.id.menu_search);

            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint("Thông tin cần tìm");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    roomAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }

        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle;
                Register register;
                switch (intent.getAction()) {
                    case "CHECK_ROOM":{
                        bundle = intent.getExtras();
                        boolean mode = bundle.getBoolean("MODE");
                        Room obj =(Room) bundle.getSerializable("_ROOM");

                        if (mode) {

                            if (roomDAO.update(obj) != 1) {
                                Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                                resetAdapter();
                            }
                        } else {

                                if (roomDAO.insert(obj) != 1) {
                                    Toast.makeText(context, "Thêm mới thất bại!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        break;
                    }
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
                }
                resetAdapter();
            }
        };

        @Override
        public void onResume () {
            super.onResume();
            getActivity().registerReceiver(broadcastReceiver, intentFilter);
        }
        @Override
        public void onDestroy () {
            super.onDestroy();
            getActivity().unregisterReceiver(broadcastReceiver);
        }

        public void resetAdapter () {
            roomAdapter = new RoomAdapter(getActivity().getApplicationContext(),roomDAO.getAll());
            recyclerView.setAdapter(roomAdapter);
        }

        private void onAddButtonClicked () {
            setVisibility(clicked);
            setAnimation(clicked);
            setClickable(clicked);
            clicked = !clicked;
        }

        private void setVisibility ( boolean clicked){
            if (!clicked) {
                add_btn.setVisibility(View.VISIBLE);
                del_btn.setVisibility(View.VISIBLE);
                tv_add_btn.setVisibility(View.VISIBLE);
                tv_del_btn.setVisibility(View.VISIBLE);
            } else {
                add_btn.setVisibility(View.INVISIBLE);
                del_btn.setVisibility(View.INVISIBLE);
                tv_add_btn.setVisibility(View.INVISIBLE);
                tv_del_btn.setVisibility(View.INVISIBLE);

            }
        }

        private void setAnimation ( boolean clicked){
            if (!clicked) {
                Animation rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);
                Animation fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
                add_btn.setAnimation(fromBottom);
                del_btn.setAnimation(fromBottom);
                tv_add_btn.setAnimation(fromBottom);
                tv_del_btn.setAnimation(fromBottom);
                open_btn.setAnimation(rotateOpen);
            } else {
                Animation rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
                Animation toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
                add_btn.setAnimation(toBottom);
                del_btn.setAnimation(toBottom);
                tv_add_btn.setAnimation(toBottom);
                tv_del_btn.setAnimation(toBottom);
                open_btn.setAnimation(rotateClose);
            }
        }

        private void setClickable ( boolean clicked){
            if (!clicked) {
                add_btn.setClickable(true);
                del_btn.setClickable(true);
            } else {
                add_btn.setClickable(false);
                del_btn.setClickable(false);
            }
        }
    }