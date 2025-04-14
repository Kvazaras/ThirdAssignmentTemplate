package com.example.third_assignment_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private ListView lvNotes;
    private ArrayAdapter listAdapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvNotes = findViewById(R.id.lvNotes);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.notesList = new ArrayList<String>(sp.getStringSet("notes", new HashSet<String>()));
        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, notesList);
        this.lvNotes.setAdapter(this.listAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Your adapter loses reference to your list.
        //https://stackoverflow.com/questions/15422120/notifydatasetchange-not-working-from-custom-adapter
        notesList.clear();
        this.notesList.addAll((sp.getStringSet("notes",new HashSet<String>())));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note_activity:
                Intent addActivityIntent = new Intent(getApplicationContext(), com.example.third_assignment_template.AddNoteActivity.class);
                startActivity(addActivityIntent);
                return true;
            case R.id.delete_note_activity:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
