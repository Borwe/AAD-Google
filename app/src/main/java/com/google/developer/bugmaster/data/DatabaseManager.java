package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private BugsDbHelper mBugsDbHelper;

    private DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public Cursor queryAllInsects(String sortOrder) {
        //TODO: Implement the query
        SQLiteDatabase db=mBugsDbHelper.getReadableDatabase();

        String projection[]={
                BugsContract.BugEntry.FRIENDLY_NAME, BugsContract.BugEntry.SCIENTIFIC_NAME,
                BugsContract.BugEntry.CLASSIFICATION, BugsContract.BugEntry.IMAGE_ASSET,
                BugsContract.BugEntry.DANGER_LEVEL
        };

        Cursor returnCursor=db.query(BugsContract.BugEntry.TABLE_NAME,
                projection,null,null,null,null,sortOrder);
        return returnCursor;
    }

    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        //TODO: Implement the query
        SQLiteDatabase db=mBugsDbHelper.getReadableDatabase();

        String projection[]={
                BugsContract.BugEntry.FRIENDLY_NAME, BugsContract.BugEntry.SCIENTIFIC_NAME,
                BugsContract.BugEntry.CLASSIFICATION, BugsContract.BugEntry.IMAGE_ASSET,
                BugsContract.BugEntry.DANGER_LEVEL
        };

        String selection= BugsContract.BugEntry._ID+" = ?";
        String selectionArgs[]={Integer.toString(id)};

        Cursor returnCursor=db.query(BugsContract.BugEntry.TABLE_NAME,projection, selection,selectionArgs,
                null,null,null);

        return returnCursor;
    }
}
