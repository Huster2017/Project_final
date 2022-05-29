package com.example.myapplication.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private EditText editText;
    private Button btn;
    public static final String key = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        BTNLogin();
    }

    private void BTNLogin() {
        editText = (EditText) findViewById(R.id.ed_in);
        btn = (Button) findViewById(R.id.btn_cnn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString();
                String check = "";
                if(code.equals(check)){
                    Toast.makeText(Login.this,"Code is not null",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra(key,code);
                    startActivity(intent);
                }
            }
        });
    }

}
