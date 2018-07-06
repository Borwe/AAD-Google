package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.developer.bugmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    //Used to read data from res/ and assets/
    private Resources mResources;

    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database
        String createDB="CREATE TABLE " + BugsContract.BugEntry.TABLE_NAME + "(" +
                BugsContract.BugEntry._ID +" INTEGER PRIMARY KEY," +
                BugsContract.BugEntry.FRIENDLY_NAME + " TEXT NOT NULL," +
                BugsContract.BugEntry.SCIENTIFIC_NAME+ " TEXT NOT NULL,"+
                BugsContract.BugEntry.CLASSIFICATION+ " TEXT NOT NULL,"+
                BugsContract.BugEntry.IMAGE_ASSET+" TEXT NOT NULL,"+
                BugsContract.BugEntry.DANGER_LEVEL+" INTEGER NOT NULL)";

        db.execSQL(createDB);
        try {
            readInsectsFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        if(oldVersion<newVersion){
            //drop table
            String drop_table="DROP TABLE IF EXISTS "+ BugsContract.BugEntry.TABLE_NAME;
            db.execSQL(drop_table);
            //recreate table
            onCreate(db);
        }
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance

        JSONObject jsonObject=new JSONObject(rawJson);
        JSONArray bugsArray=jsonObject.getJSONArray("insects");

        for(int i=0;i<bugsArray.length();i++){
            //bugs
            JSONObject bug=bugsArray.getJSONObject(i);

            ContentValues contentValues=new ContentValues();

            contentValues.put(BugsContract.BugEntry.FRIENDLY_NAME,
                    bug.getString(BugsContract.BugEntry.FRIENDLY_NAME));
            contentValues.put(BugsContract.BugEntry.SCIENTIFIC_NAME,
                    bug.getString(BugsContract.BugEntry.SCIENTIFIC_NAME));
            contentValues.put(BugsContract.BugEntry.CLASSIFICATION,
                    bug.getString(BugsContract.BugEntry.CLASSIFICATION));
            contentValues.put(BugsContract.BugEntry.IMAGE_ASSET,
                    bug.getString(BugsContract.BugEntry.IMAGE_ASSET));
            contentValues.put(BugsContract.BugEntry.DANGER_LEVEL,
                    bug.getInt(BugsContract.BugEntry.DANGER_LEVEL));

            db.insert(BugsContract.BugEntry.TABLE_NAME,null,contentValues);
        }
    }
}
