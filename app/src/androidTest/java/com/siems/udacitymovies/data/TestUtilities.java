package com.siems.udacitymovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createTestPosterValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContractOld.PosterEntry.COLUMN_POSTER_PATH, "/cGOPbv9wA5gEejkUN892JrveARt.jpg");
        testValues.put(MovieContractOld.PosterEntry.COLUMN_OVERVIEW, "Fearing the actions of a god-like " +
                "Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on " +
                "Metropolis’s most revered, modern-day savior, while the world wrestles with what sort " +
                "of hero it really needs. And with Batman and Superman at war with one another, a new " +
                "threat quickly arises, putting mankind in greater danger than it’s ever known before.");
        testValues.put(MovieContractOld.PosterEntry.COLUMN_RELEASE_DATE, "2016-03-23");
        testValues.put(MovieContractOld.PosterEntry.COLUMN_ID, 209112);
        testValues.put(MovieContractOld.PosterEntry.COLUMN_TITLE, "Batman v Superman: Dawn of Justice");
        testValues.put(MovieContractOld.PosterEntry.COLUMN_BACKDROP_PATH, "/vsjBeMPZtyB7yNsYY56XYxifaQZ.jpg");
        testValues.put(MovieContractOld.PosterEntry.COLUMN_VOTE_AVERAGE, 5.52);
        return testValues;
    }

    static ContentValues createTestTrailerValues(long posterRowId) {
        ContentValues trailerValues = new ContentValues();
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_POSTER_ID, posterRowId);
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_TRAILER_ID, "56b7f59ac3a36806ec00fdcf");
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_KEY, "_gBnmKOixDM");
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_NAME, "First Look");
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_SITE, "YouTube");
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_SIZE, 1080);
        trailerValues.put(MovieContractOld.TrailerEntry.COLUMN_TYPE, "Trailer");

        return trailerValues;
    }

}
