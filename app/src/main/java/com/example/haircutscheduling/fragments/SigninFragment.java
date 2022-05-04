package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SigninFragment extends Fragment {

    private FirebaseAuth mAuth;//chức năng xác thực người dùng
    public FirebaseDatabase database;//cơ sở dữ liệu được cung cấp bởi Google và  được lưu trữ trên nền tảng cloud
    MainActivity mainActivity;

    public SigninFragment() {
        // Required empty public constructor
    }

    public static SigninFragment newInstance() {
        SigninFragment fragment = new SigninFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();//chỉ cho phép một thread chạy một phương thức tại cùng một thời điểm
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        Button register = view.findViewById(R.id.buttonRegister);
        register.setOnClickListener(v -> {

            EditText nameTextView = view.findViewById(R.id.editTextPersonName);
            EditText emailTextView = view.findViewById(R.id.editTextTextEmailAddressSignin);
            EditText passwordTextView = view.findViewById(R.id.editTextTextPasswordSignin);
            EditText phoneTextView = view.findViewById(R.id.editTextPhone);

            String name = nameTextView.getText().toString();
            String email = emailTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            String phone = phoneTextView.getText().toString();
            //getText trả về Editable, nên muốn ra chuỗi “thuần” thì phải thêm bước toString

            mainActivity = (MainActivity) getActivity();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty())
            {
                Toast.makeText(mainActivity, "\n" +
                        "Please fill in all required information", Toast.LENGTH_LONG).show();
                //một thông báo nhỏ mà ứng dụng gửi tới người dùng, nó xuất hiện gần phía cuối màn hình một khoảng thời gian dài 3,5 s
            }
            else {
                User user = new User(name, email, password, phone);
                Register(user);
            }
        });

        return view;
    }

    public void Register(User user) {
        String userName = user.getEmail();
        String password = user.getPassword();
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(mainActivity, task -> {
                    if (task.isSuccessful()) {
                        //task là một tập hợp các activity mà người dùng tương tác khi thực hiện một công việc nhất định
                        //isSuccessful() để tìm ra code trạng thái trong khoảng 200-300 xác định một yêu cầu thành công.
                        DatabaseReference myRef = database.getReference("users").child(user.getPhone());
                        myRef.setValue(user);
                        mainActivity.Login(userName, password);
                    } else {
                        // If sign in fails, display a message to the user.
                        mainActivity.setFragment(new SigninFragment());
                        Toast.makeText(mainActivity, "Something went wrong, Please try again.\nNote: \n" +
                                "A valid email and password with at least 6 characters is required.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}