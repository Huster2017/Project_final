package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.action.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private TextView cur_text;
    private static String data_input;
    public static final String key4="us2";
    private EditText edt1,edt2,edt3;
    private Button btn_update;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        data_input = intent.getStringExtra(MainActivity.key1);
        if(data_input == null){
            data_input = intent.getStringExtra(MessActivity.key3);
            if(data_input == null){
                data_input = intent.getStringExtra(MapActivity.key2);
            }
        }

        cur_text = (TextView) findViewById(R.id.cur_info);
        getdata(data_input);
        Update_infor(data_input);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.map){
            Toast.makeText(UserActivity.this,"Map Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserActivity.this, MapActivity.class);
            intent.putExtra(key4,data_input);
            startActivity(intent);
        }else if(id ==R.id.user){
            Toast.makeText(UserActivity.this,"You are in User",Toast.LENGTH_LONG).show();

        }else if(id == R.id.mess){
            Toast.makeText(UserActivity.this,"Mess Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserActivity.this, MessActivity.class);
            intent.putExtra(key4,data_input);
            startActivity(intent);
        }else if(id == R.id.home){
            Toast.makeText(UserActivity.this,"Home Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.putExtra(key4,data_input);
            startActivity(intent);
        }

        return true;
    }
    private void getdata(String ID) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("custom/"+ID+"");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Profile user = dataSnapshot.getValue(Profile.class);
                //Log.d(TAG, "Value is: " + value);
                cur_text.setText(user.toString2());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void Update_infor(String data_input){
        edt1 = (EditText) findViewById(R.id.name_update);
        edt2 = (EditText) findViewById(R.id.CCCD_update);
        edt3 = (EditText) findViewById(R.id.phone_update);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt1.getText().toString();
                String cccd = edt2.getText().toString();
                String phone = edt3.getText().toString();
                String check = "";
                if(name.equals(check) && cccd.equals(check) && phone.equals(check)){
                    Toast.makeText(UserActivity.this,"Nothing is updated",Toast.LENGTH_LONG).show();
                }else{
                    if(name.length() > 2){
                        String add_name = "Name";
                        update(name,data_input,add_name);
                    }
                    if(cccd.length() > 2){
                        String add_cccd = "CCCD";
                        update(cccd,data_input,add_cccd);
                    }
                    if(phone.length() > 2){
                        String add_phone = "Phone";
                        update(phone,data_input,add_phone);
                    }
                }
            }
        });
    }
    private void update(String mess,String data_input,String add_update){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("custom/"+data_input+"/"+add_update);
        myRef.setValue(mess);
    }
}
