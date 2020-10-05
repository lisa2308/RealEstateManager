package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.room.EstateRoomDatabase;
import com.openclassrooms.realestatemanager.data.room.provider.EstateContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;
    private int USER_ID = 1;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), EstateRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
    }

//    @Test
//    public void getItemsWhenNoItemInserted() {
//        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
//        assertThat(cursor, notNullValue());
//        assertThat(cursor.getCount(), is(0));
//        cursor.close();
//    }

    @Test
    public void insertAndGetEstate() {
        // INSERT
        final Uri uri = mContentResolver.insert(EstateContentProvider.URI_ESTATE, generateItem());
        // GET
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("estateDescription")), is("Description de la maison"));
    }

    // ---
    private ContentValues generateItem(){
        final ContentValues values = new ContentValues();

        values.put("estateId", "1");
        values.put("estateType", "House");
        values.put("estatePrice", "10000000");
        values.put("estateDescription", "Description de la maison");
        values.put("estateSurface", "200m2");
        values.put("estateNbRooms", "8");
        values.put("estateNbBathrooms", "5");
        values.put("estateNbBedrooms", "5");
        values.put("estateStreet", "33 Rue Guersant");
        values.put("estatePostal", "75017");
        values.put("estateCity", "Paris");
        values.put("estateCountry", "France");
        values.put("estateLat", "48.881680");
        values.put("estateLng", "2.288920");
        values.put("estatePointsOfInterest", "ecole transports");
        values.put("estateHasBeenSold", "true");
        values.put("estateCreationDate", "1599830783");
        values.put("estateSoldDate", "1599832783");
        values.put("estateAgentName", "Lisa Perrin");

        return values;
    }
}
