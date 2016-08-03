package com.siems.udacitymovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
        Note that this tests that the Poster table has the correct columns.
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieContract.PosterEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.TrailerEntry.TABLE_NAME);

        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MovieDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the poster entry and trailer entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.PosterEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> posterColumnHashSet = new HashSet<String>();
        posterColumnHashSet.add(MovieContract.PosterEntry._ID);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_POSTER_PATH);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_OVERVIEW);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_RELEASE_DATE);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_ID);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_TITLE);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_BACKDROP_PATH);
        posterColumnHashSet.add(MovieContract.PosterEntry.COLUMN_VOTE_AVERAGE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            posterColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required poster entry columns",
                posterColumnHashSet.isEmpty());
        db.close();
    }

    public void testPosterTable() {
        insertPoster();
    }

    public void testTrailerTable() {
        // First insert the poster, and then use the posterRowId to insert the trailer.
        long posterRowId = insertPoster();
        assertFalse("Error: Poster Not Inserted Correctly", posterRowId == -1L);

        // First step: Get reference to writable database
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create ContentValues of what you want to insert
        ContentValues testValues = TestUtilities.createTestTrailerValues(posterRowId);

        // Insert ContentValues into database and get a row ID back
        long trailerRowId = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, testValues);
        assertTrue(trailerRowId != -1);

        // Query the database and receive a Cursor back
        Cursor trailerCursor = db.query(
                MovieContract.TrailerEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to a valid database row
        assertTrue( "Error: No Records returned from trailer query", trailerCursor.moveToFirst() );

        // Validate data in resulting Cursor with the original ContentValues
        TestUtilities.validateCurrentRecord("testInsertReadDb trailerEntry failed to validate",
                trailerCursor, testValues);
        assertFalse( "Error: More than one record returned from trailer query",
                trailerCursor.moveToNext() );
        // Close the cursor and database
        trailerCursor.close();
        dbHelper.close();
    }

    public long insertPoster() {
        // First step: Get reference to writable database
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create ContentValues of what you want to insert
        ContentValues testValues = TestUtilities.createTestPosterValues();

        // Insert ContentValues into database and get a row ID back
        long posterRowId;
        posterRowId = db.insert(MovieContract.PosterEntry.TABLE_NAME, null, testValues);
        assertTrue(posterRowId != -1);

        // Query the database and receive a Cursor back
        Cursor cursor = db.query(
                MovieContract.PosterEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to a valid database row
        assertTrue( "Error: No Records returned from poster query", cursor.moveToFirst() );

        // Validate data in resulting Cursor with the original ContentValues
        TestUtilities.validateCurrentRecord("Error: Poster Query Validation Failed",
                cursor, testValues);

        // Close the cursor and database
        cursor.close();
        db.close();
        return posterRowId;
    }


}

