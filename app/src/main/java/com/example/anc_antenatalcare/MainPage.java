package com.example.anc_antenatalcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.feedback:
                Toast.makeText(this, "You are in Feedback now",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainPage.this, Feedback.class));
                return true;
            case R.id.help:
                Toast.makeText(this, "Item selected: help",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainPage.this, Help.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "You are Logged out",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainPage.this, DecisionPage.class));
                return true;
            default:
                return false;
        }

    }

}
