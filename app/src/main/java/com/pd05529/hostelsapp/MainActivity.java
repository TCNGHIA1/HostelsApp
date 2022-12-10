package com.pd05529.hostelsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.pd05529.hostelsapp.DAO.OwnerDAO;
import com.pd05529.hostelsapp.models.Owner;
import com.pd05529.hostelsapp.ui.BillFragment;
import com.pd05529.hostelsapp.ui.CustomerFragment;
import com.pd05529.hostelsapp.ui.InforFragment;
import com.pd05529.hostelsapp.ui.PassChangeFragment;
import com.pd05529.hostelsapp.ui.RoomFragment;
import com.pd05529.hostelsapp.ui.RoomTypeFragment;
import com.pd05529.hostelsapp.ui.ServiceFragment;
import com.pd05529.hostelsapp.ui.TurnoverFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout layout;
    Toolbar toolbar;
    NavigationView nv;
    View mHeaderView;
    SharedPreferences pref;
    TextView tvHeaderName;
    Bundle bundle;

    OwnerDAO ownerDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //mapping
        layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        //Thay toolbar cho actionbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        //navigation
        nv = findViewById(R.id.nav_view);
        //maping headerView
        mHeaderView = nv.getHeaderView(0);
        tvHeaderName = mHeaderView.findViewById(R.id.tvHeaderName);
        bundle = getIntent().getExtras();
        ownerDAO = new OwnerDAO(this);
        Owner obj = ownerDAO.getId(String.valueOf(bundle.getInt("id")));
        tvHeaderName.setText(obj.getName());


        //Click item navigationView
        nv.setNavigationItemSelectedListener((item -> {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragment = manager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_room: {
                    setTitle("Phòng trọ");
                    RoomFragment new_fragment = new RoomFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_bill: {
                    setTitle("Hóa đơn");
                    BillFragment new_fragment = new BillFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();

                    break;
                }
                case R.id.nav_customer: {
                    setTitle("Khách trọ");
                    CustomerFragment new_fragment = new CustomerFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_room_type: {
                    setTitle("Loại phòng");
                    RoomTypeFragment new_fragment = new RoomTypeFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_service: {
                    setTitle("Điện, nước");
                    ServiceFragment new_fragment = new ServiceFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_account: {
                    setTitle("Thông tin tài khoản");
                    InforFragment new_fragment = new InforFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_turnover: {
                    setTitle("Doanh thu");
                    TurnoverFragment new_fragment = new TurnoverFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_pass: {
                    setTitle("Thay đổi mật khẩu");
                    PassChangeFragment new_fragment = new PassChangeFragment();
                    fragment.replace(R.id.fragment, new_fragment).commit();
                    break;
                }
                case R.id.nav_logout: {
                    pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    break;
                }
                case R.id.nav_finish: {
                    finishAffinity();
                    break;
                }
            }
            layout.closeDrawers();
            return false;
        }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            layout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}