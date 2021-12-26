package com.example.noteitdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView notesListView;
    static ArrayList<String> myNotes;
    static ArrayAdapter<String> arrayAdapter;
    static SharedPreferences sharedPreferences;

    int flag = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.AddNote:

                Intent intent = new Intent(getApplicationContext(), NotesEditorActivity.class);

                startActivity(intent);

               Toast.makeText(this, "New Note Added !", Toast.LENGTH_SHORT).show();
               return true;

            default:
                return true;
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("com.example.noteit", MODE_PRIVATE);

        notesListView = (ListView) findViewById(R.id.notesListView);

        myNotes = new ArrayList<String>();

        if (sharedPreferences.getStringSet("notes", null) == null)
            myNotes.add("This is the first note");

        else{
            HashSet<String> hashSet = new HashSet<String>(sharedPreferences.getStringSet("notes", null));

            myNotes = new ArrayList<>(hashSet);
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myNotes);
        notesListView.setAdapter(arrayAdapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (flag == 0) {

                    Intent intent = new Intent(getApplicationContext(), NotesEditorActivity.class);
                    intent.putExtra("NoteID", position);
                    startActivity(intent);

                }

            }
        });

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                flag = 1;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myNotes.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                HashSet<String > hashSet = new HashSet<String>(MainActivity.myNotes);
                                sharedPreferences.edit().putStringSet("notes", hashSet).apply();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();


                Toast.makeText(MainActivity.this, "Delete Note ??", Toast.LENGTH_SHORT).show();
                flag = 0;
                                
                return false;


            }

        });



    }
}