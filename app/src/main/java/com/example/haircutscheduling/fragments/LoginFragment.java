package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {

    MainActivity mainActivity;
    public FirebaseDatabase database;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button signin = view.findViewById(R.id.buttonSignin);
        signin.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            mainActivity.setFragment(new SigninFragment());
        });

        Button login = view.findViewById(R.id.buttonLogin);
        login.setOnClickListener(v -> {

            TextView userNameTextView = view.findViewById(R.id.editTextTextEmailAddressLogin);
            TextView passwordTextView = view.findViewById(R.id.editTextTextPasswordLogin);

            String userName = userNameTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            //getText trả về Editable, nên muốn ra chuỗi “thuần” thì phải thêm bước toString

            mainActivity = (MainActivity) getActivity();

            if(userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(mainActivity, "\n" +
                        "Please enter your username and password", Toast.LENGTH_LONG).show();
                //một thông báo nhỏ mà ứng dụng gửi tới người dùng, nó xuất hiện gần phía cuối màn hình một khoảng thời gian dài 3,5 s
            }
            else {
                mainActivity.Login(userName,password);
            }
        });

        return view;
    }
}