package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.action.Login;
import com.example.myapplication.action.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView textView,txt;
    public static final String key1="h2";
    private static String data_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.data_home);
        Intent intent = getIntent();

        data_input = intent.getStringExtra(Login.key);;
        if(data_input != null){
            textView.setText("Thông tin khách hàng: ");
        }else{
            data_input  = intent.getStringExtra(MapActivity.key2);
            if(data_input == null){
                data_input = intent.getStringExtra(MessActivity.key3);
            }
            textView.setText("Thông tin khách hàng: ");
        }

        txt = (TextView) findViewById(R.id.data_home_data);
        getdata(data_input);
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
            Toast.makeText(MainActivity.this,"Map Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra(key1,data_input);
            startActivity(intent);
        }else if(id ==R.id.home){
            Toast.makeText(MainActivity.this,"You are in Home",Toast.LENGTH_LONG).show();

        }else if(id == R.id.mess){
            Toast.makeText(MainActivity.this,"Mess Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MessActivity.class);
            intent.putExtra(key1,data_input);
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
                txt.setText(user.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}