package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapActivity extends AppCompatActivity {
    MapView map = null;

    private TextView txt;
    public static final String key2 = "ma2";
    private static String data_input;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.toolbar_map);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        data_input = intent.getStringExtra(MainActivity.key1);
        if(data_input == null){
            data_input = intent.getStringExtra(MessActivity.key3);
            if(data_input == null){
                data_input = intent.getStringExtra(UserActivity.key4);
            }
        }

        final Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        map = (MapView) findViewById(R.id.map_view);
        map.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = map.getController();
        mapController.setZoom(16);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        GeoPoint startpoint = new GeoPoint(20.967994, 105.840798);
        mapController.setCenter(startpoint);

        txt = (TextView) findViewById(R.id.text_map);

        getdata(data_input);
     }

     public void Marker(float x, float y){

        map.getOverlays().clear();
        GeoPoint my_locate = new GeoPoint(y,x);
        Marker loc = new Marker(map);
        loc.setPosition(my_locate);
        loc.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(loc);
     }
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            Toast.makeText(MapActivity.this,"Home Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            intent.putExtra(key2,data_input);
            startActivity(intent);
        }else if(id ==R.id.map){
            Toast.makeText(MapActivity.this,"You are in Map",Toast.LENGTH_LONG).show();
        }else if(id == R.id.mess){
            Toast.makeText(MapActivity.this,"Mess Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MapActivity.this, MessActivity.class);
            intent.putExtra(key2,data_input);
            startActivity(intent);
        }else if(id == R.id.user){
            Toast.makeText(MapActivity.this,"User Opened",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MapActivity.this, UserActivity.class);
            intent.putExtra(key2,data_input);
            startActivity(intent);
        }

        return true;
    }
    private void getdata(String ID){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("custom/"+ID+"/Location");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                int check = value.indexOf(',');
                int leng = value.length();
                float ala = Float.parseFloat(value.substring(1,check));
                float along = Float.parseFloat(value.substring(check+1,leng-1));
                txt.setText("Tọa độ hiện tại: ("+ala+","+along+")");
                Marker(ala,along);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
