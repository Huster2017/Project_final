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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessActivity extends AppCompatActivity {
    private static String data_input;
    public static final String key3="me2";
    private TextView txt;
    private EditText edt;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        Toolbar toolbar = findViewById(R.id.toolbar_mess);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        data_input = intent.getStringExtra(MainActivity.key1);
        if(data_input == null){
            data_input = intent.getStringExtra(MapActivity.key2);
        }
        txt = (TextView) findViewById(R.id.mess_get);
        getData(data_input);
        PushData(data_input);

    }

    private void PushData(String data_input) {
        edt = (EditText) findViewById(R.id.mess_push);
        btn = (Button) findViewById(R.id.btn_push);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = edt.getText().toString();
                String check = "";
                if(mess.equals(check)){
                    Toast.makeText(MessActivity.this,"Message is null",Toast.LENGTH_LONG).show();
                }else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("custom/"+data_input+"/message/mess_user");
                    myRef.setValue(mess);
                }
            }
        });
    }

    private void getData(String data_input) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("custom/"+data_input+"/message/mess");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value)
                txt.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            Toast.makeText(MessActivity.this,"Home Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MessActivity.this, MainActivity.class);
            intent.putExtra(key3,data_input);
            startActivity(intent);
        }else if(id ==R.id.mess){
            Toast.makeText(MessActivity.this,"You are in Mess",Toast.LENGTH_LONG).show();
        }else if(id == R.id.map){
            Toast.makeText(MessActivity.this,"Map Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MessActivity.this, MapActivity.class);
            intent.putExtra(key3,data_input);
            startActivity(intent);
        }

        return true;
    }
}
