package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.bumptech.glide.util.Util;
import com.openclassrooms.realestatemanager.data.room.EstateRoomDatabase;
import com.openclassrooms.realestatemanager.data.room.provider.EstateContentProvider;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ConnectivityTest {

    private WifiManager wifiManager;

    @Before
    public void setUp() throws Exception {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    // ONLY FOR SDK <= 28
    @Test
    public void isNetworkAvailableTest() {
        wifiManager.setWifiEnabled(false);
        boolean isNetworkAvailable = Utils.isNetworkAvailable(getApplicationContext());
        assertThat(isNetworkAvailable, is(false));

        wifiManager.setWifiEnabled(true);
        isNetworkAvailable = Utils.isNetworkAvailable(getApplicationContext());
        assertThat(isNetworkAvailable, is(true));
    }

//    private void setMobileDataEnabled(Context context, boolean enabled) {
//        try {
//            final Class conmanClass = Class.forName(connectivityManager.getClass().getName());
//            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
//            iConnectivityManagerField.setAccessible(true);
//            final Object iConnectivityManager = iConnectivityManagerField.get(connectivityManager);
//            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
//            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
//            setMobileDataEnabledMethod.setAccessible(true);
//            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
}
