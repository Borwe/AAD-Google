package com.google.developer.bugmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.bugmaster.data.DatabaseManager;
import com.google.developer.bugmaster.data.Insect;
import com.google.developer.bugmaster.data.InsectRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    RecyclerView recycler_view;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recycler_view= (RecyclerView) findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        class LoadDataToDB extends AsyncTask<Void,Void,Void>{
            ProgressDialog progressDialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                mCursor=DatabaseManager.getInstance(MainActivity.this).queryAllInsects(null);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.hide();
                progressDialog.cancel();

                InsectRecyclerAdapter insectRecyclerAdapter=
                        new InsectRecyclerAdapter(mCursor);
                RecyclerView.LayoutManager rLayoutMan=
                        new LinearLayoutManager(MainActivity.this);
                recycler_view.setLayoutManager(rLayoutMan);
                recycler_view.setAdapter(insectRecyclerAdapter);
            }
        }

        new LoadDataToDB().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                //TODO: Implement the sort action
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {
        //TODO: Launch the quiz activity
        switch (v.getId()){
            case R.id.fab:
                Intent quiz=new Intent(MainActivity.this,QuizActivity.class);

                ArrayList<Insect> insects=new ArrayList<>();
                while(mCursor.moveToNext()){
                    insects.add(new Insect(mCursor));
                }
                Collections.shuffle(insects);
                Random picker=new Random();
                int no=picker.nextInt(insects.size());

                Insect answer=insects.get(no);

                ArrayList<Insect> insects_send=new ArrayList<>();
                insects_send.add(answer);
                insects.remove(no);

                for(int i=0;i<4;i++){
                    if(insects.get(i).name.equals(answer.name)==false){
                        insects_send.add(insects.get(i));
                    }
                }

                Collections.shuffle(insects_send);

                quiz.putParcelableArrayListExtra(QuizActivity.EXTRA_INSECTS,insects_send);
                quiz.putExtra(QuizActivity.EXTRA_ANSWER,answer);

                startActivity(quiz);
                break;
        }
    }
}
