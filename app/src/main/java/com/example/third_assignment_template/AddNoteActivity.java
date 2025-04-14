package com.example.third_assignment_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void onAddNoteClick(View view) {
        EditText txtNote = findViewById(R.id.txtNote);
        String noteText = txtNote.getText().toString();

        //https://stackoverflow.com/questions/14034803/misbehavior-when-trying-to-store-a-string-set-using-sharedpreferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spEd = sp.edit();

        Set<String> oldSet = sp.getStringSet(Constants.SHARED_PREF_NOTES_LIST, new HashSet<String>());
        Set<String> newStrSet = new HashSet<String>();

        newStrSet.add(noteText);
        newStrSet.addAll(oldSet);

        spEd.putStringSet(Constants.SHARED_PREF_NOTES_LIST, newStrSet);
        spEd.apply();

        writeStringToFileInternalStorage(this, "TestFile.txt", noteText);

        finish();
    }

    public static void writeStringToFileInternalStorage(Context context, String filename, String content) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            System.out.println("Successfully wrote to file in internal storage: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file in internal storage: " + filename);
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
