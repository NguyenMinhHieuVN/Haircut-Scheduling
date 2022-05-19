package com.example.haircutscheduling.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;
import com.example.haircutscheduling.classes.Day;
import com.example.haircutscheduling.classes.FirstEntry;
import com.example.haircutscheduling.fragments.AdminFragment;
import com.example.haircutscheduling.fragments.LoginFragment;
import com.example.haircutscheduling.fragments.MainFragment;
import com.example.haircutscheduling.fragments.SelectAppointmentsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FirebaseAuth mAuth;//chuc nang xac thuc nguoi dung
    private FirebaseDatabase database;

    private boolean savedUserFlag;
    public static final String SHARED_PREFS_LOGIN = "loginSharedPrefs";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";

    public static final String SHARED_PREFS_CONTACT = "contactSharedPrefs";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        fragmentManager = getSupportFragmentManager();

        if (FirstEntry.flag) {
            FirstEntry.flag = false;
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_LOGIN, MODE_PRIVATE);
            //lưu lại các các thông số bạn đã thiết lập trước đó
            String userName = prefs.getString(USERNAME, "");
            String password = prefs.getString(PASSWORD, "");

            if (userName.equals("") || password.equals("")) {
                savedUserFlag = false;
                setLoginFragment();
            } else {
                savedUserFlag = true;
                Login(userName, password);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.findFragmentById(R.id.fragmentcon).getClass() == MainFragment.class ||
                fragmentManager.findFragmentById(R.id.fragmentcon).getClass() == AdminFragment.class) {
            FirstEntry.flag = true;
        }

        super.onBackPressed();
    }

    public void setLoginFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.fragmentcon, loginFragment).commit();
    }

    public void Login(String userName, String password) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //isSuccessful() de tim ra code trang thai torng khoang 200-300 xac dinh mot yeu cau thanh cong
                            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_LOGIN, MODE_PRIVATE).edit();
                            //cho phep ban luu lai cac thong so ban da thiet lap truoc do
                            editor.putString(USERNAME, userName);
                            editor.putString(PASSWORD, password);
                            editor.apply();
                            setMainFragment();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "\n" +
                                    "Logged in successfully", Toast.LENGTH_LONG).show();
                        } else {
                            if (userName.equals("admin") && password.equals("admin")) {
                                setFragment(new AdminFragment());
                                Toast.makeText(MainActivity.this, "\n" +
                                        "Connect as administrator", Toast.LENGTH_LONG).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                setLoginFragment();
                                Toast.makeText(MainActivity.this, "\n" +
                                        "Connection failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void setMainFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();

        if (!savedUserFlag) {
            fragmentTransaction.replace(R.id.fragmentcon, mainFragment).addToBackStack(null).commit();
            // thay doi duoc luu vao ngan xep phia sau de nguoi dung co th khoi phuc lai phan doan truoc do bang cach nhan nut quay lai.
        } else {
            fragmentTransaction.replace(R.id.fragmentcon, mainFragment).commit();
        }
    }

    public void setFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //la mot class cho pep them, xoa, sua cac fragment trong stack.flag
        fragmentTransaction.replace(R.id.fragmentcon, fragment).addToBackStack(null).commit();
        //thay the 1 fragment cu bang 1 fragment moi vao View duoc khai bao truoc trong file xml
    }

    public void setSelectAppointmentsFragment(HairStyleDataModel hairStyleDataModel) {
        fragmentTransaction = fragmentManager.beginTransaction();
        SelectAppointmentsFragment selectAppointmentsFragment = new SelectAppointmentsFragment(hairStyleDataModel);
        fragmentTransaction.replace(R.id.fragmentcon, selectAppointmentsFragment).addToBackStack(null).commit();
    }

    public void logOut() {
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_LOGIN, MODE_PRIVATE).edit();
        editor.putString(USERNAME, "");
        editor.putString(PASSWORD, "");
        editor.apply();
    }

    public void VerifyOperationTimeExist() {
        DatabaseReference myRef = database.getReference("settings").child("OperationTime");
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                }
                else{
                    if(task.getResult().hasChildren()) {
                        Object objData = task.getResult().getValue(Object.class);
                        HashMap<String, Day> hashMap = (HashMap) objData;
                        initDaysHours(myRef,hashMap);
                    }
                }
            }
        });
    }

    private void initDaysHours(DatabaseReference myRefChild, HashMap<String, Day> hashMap)
    {
        Day sunday = new Day("Sunday","9:00", "17:00",false);
        Day monday = new Day("Monday","9:00", "17:00",false);
        Day tuesday = new Day("Tuesday","9:00", "17:00",false);
        Day wednesday = new Day("Wednesday","9:00", "17:00",false);
        Day thursday = new Day("Thursday","9:00", "17:00",false);
        Day friday = new Day("Friday","00:00", "00:00",true);
        Day saturday = new Day("Saturday","00:00", "00:00",true);

        if(!hashMap.containsKey("1")) myRefChild.child("1").setValue(sunday);
        if(!hashMap.containsKey("2")) myRefChild.child("2").setValue(monday);
        if(!hashMap.containsKey("3")) myRefChild.child("3").setValue(wednesday);
        if(!hashMap.containsKey("4")) myRefChild.child("4").setValue(tuesday);
        if(!hashMap.containsKey("5")) myRefChild.child("5").setValue(thursday);
        if(!hashMap.containsKey("6"))myRefChild.child("6").setValue(friday);
        if(!hashMap.containsKey("7"))myRefChild.child("7").setValue(saturday);
    }
}