package com.openclassrooms.realestatemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.estate.addupdate.EstateAddUpdateActivity;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;
import com.openclassrooms.realestatemanager.ui.loan.LoanActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addFragment(R.id.activity_main_frame_layout_list, new EstateListFragment(), 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.activity_main_menu_add_estate:
                startActivity(new Intent(this, EstateAddUpdateActivity.class));
                return true;
            case R.id.activity_main_menu_search:
                return true;
            case R.id.activity_main_menu_loan_simulator:
                startActivity(new Intent(this, LoanActivity.class));
                return true;
            case R.id.activity_main_menu_map:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addFragment(int frameLayout, Fragment fragment, int enterAnim, int exitAnim) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        transaction.add(frameLayout, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void replaceFragment(int frameLayout, Fragment fragment, int enterAnim, int exitAnim) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        transaction.replace(frameLayout, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }


}
