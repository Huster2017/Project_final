package com.example.myapplication.action;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Warning_Fall;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText editText;
    private Button btn;
    public static final String key = "";
    public boolean log_check = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        BTNLogin();
    }
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    private void BTNLogin() {
        initPreferences();
        editText = (EditText) findViewById(R.id.ed_in);
        btn = (Button) findViewById(R.id.btn_cnn);
        String savedData = sharedPreferences.getString("DATA", "");
        editText.setText(savedData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString();
                String check = "";
                if (v == btn) {
                    String data = code;
                    editor.putString("DATA", data);
                    editor.commit();
                }
                if(code.equals(check)){
                    Toast.makeText(Login.this,"Đừng bỏ trống",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Login.this,"Thành công",Toast.LENGTH_LONG).show();

                    Intent intent2 = new Intent(Login.this, Warning_Fall.class);
                    intent2.putExtra("key",code);
                    startService(intent2);

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra(key,code);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
