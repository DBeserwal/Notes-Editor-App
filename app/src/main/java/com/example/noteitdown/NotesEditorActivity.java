package com.example.noteitdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditorActivity extends AppCompatActivity {
    EditText notesEditorEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);



        notesEditorEditText = (EditText) findViewById(R.id.notesEditorEditText);

        Intent intent = getIntent();
        int NoteID = intent.getIntExtra("NoteID", -1);

        notesEditorEditText.setText(MainActivity.myNotes.get(NoteID));

        notesEditorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.myNotes.set(NoteID, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();


                HashSet<String > hashSet = new HashSet<String>(MainActivity.myNotes);
                MainActivity.sharedPreferences.edit().putStringSet("notes", hashSet).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}