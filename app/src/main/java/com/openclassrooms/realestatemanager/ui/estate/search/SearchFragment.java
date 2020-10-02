package com.openclassrooms.realestatemanager.ui.estate.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.MainActivity;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {
    
    @BindView(R.id.fragment_search_rdgroup_type)
    RadioGroup rdGroupeType;

    @BindView(R.id.fragment_search_rdbtn_flat)
    RadioButton rdButtonFlat;

    @BindView(R.id.fragment_search_rdbtn_duplex)
    RadioButton rdButtonDuplex;

    @BindView(R.id.fragment_search_rdbtn_house)
    RadioButton rdButtonHouse;

    @BindView(R.id.fragment_search_rdbtn_mansion)
    RadioButton rdButtonMansion;

    @BindView(R.id.fragment_search_rdbtn_penthouse)
    RadioButton rdButtonPenthouse;

    @BindView(R.id.fragment_search_rdbtn_villa)
    RadioButton rdButtonVilla;
    
    @BindView(R.id.fragment_search_edit_price_min)
    EditText editPriceMin;

    @BindView(R.id.fragment_search_edit_price_max)
    EditText editPriceMax;

    @BindView(R.id.fragment_search_edit_room_min)
    EditText editRoomMin;

    @BindView(R.id.fragment_search_edit_room_max)
    EditText editRoomMax;

    @BindView(R.id.fragment_search_edit_postal)
    EditText editPostal;

    MainActivity mainActivity;

    EstateType estateType = null;
    boolean searchPriceValid = false;
    boolean nbRoomsValid = false;
    boolean postalCodeValid = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            String estateTypeSaved = savedInstanceState.getString("estateType");
            if (estateTypeSaved.isEmpty()) {
                estateType = null;
            } else {
                estateType = EstateType.fromDisplayString(estateTypeSaved);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (estateType == null) {
            outState.putString("estateType", "");
        } else {
            outState.putString("estateType", estateType.getType());
        }
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.fragment_search_btn_search)
    public void performSearch() {
        // If search not possible, stop the function
        if (!checkIfSearchIsPossible())
            return;

        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        // Beginning of query string
        queryString += "SELECT * FROM Estate WHERE ";

        if (estateType != null) {
            queryString += "estateType = ? AND ";
            args.add(estateType.getType());
        }
        if (searchPriceValid) {
            String minPrice = editPriceMin.getText().toString();
            String maxPrice = editPriceMax.getText().toString();

            queryString += "estatePrice >= ? AND estatePrice <= ? AND ";
            args.add(minPrice);
            args.add(maxPrice);
        }
        if (nbRoomsValid) {
            String minRoom = editRoomMin.getText().toString();
            String maxRoom = editRoomMax.getText().toString();

            queryString += "estateNbRooms >= ? AND estateNbRooms <= ? AND ";
            args.add(minRoom);
            args.add(maxRoom);
        }
        if (postalCodeValid) {
            String postalCode = editPostal.getText().toString();

            queryString += "estatePostal LIKE ? AND ";
            args.add(postalCode);
        }

        // Remove the last 5 chars to remove " AND " from the string
        queryString = queryString.substring(0, queryString.length() - 5);

        // End of query string
        queryString += ";";

        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        mainActivity.getViewModel().getSearchEstateList(query);

        mainActivity.showReinitFiltersBtn();
        mainActivity.onBackPressed();
    }

    @OnClick({
            R.id.fragment_search_rdbtn_flat,
            R.id.fragment_search_rdbtn_duplex,
            R.id.fragment_search_rdbtn_house,
            R.id.fragment_search_rdbtn_mansion,
            R.id.fragment_search_rdbtn_penthouse,
            R.id.fragment_search_rdbtn_villa
    })
    public void onRadioButtonClicked(RadioButton radioButton) {
        rdButtonFlat.setChecked(false);
        rdButtonDuplex.setChecked(false);
        rdButtonHouse.setChecked(false);
        rdButtonMansion.setChecked(false);
        rdButtonPenthouse.setChecked(false);
        rdButtonVilla.setChecked(false);

        switch (radioButton.getId()) {
            case R.id.fragment_search_rdbtn_flat:
                rdButtonFlat.setChecked(true);
                estateType = EstateType.FLAT;
                break;
            case R.id.fragment_search_rdbtn_duplex:
                rdButtonDuplex.setChecked(true);
                estateType = EstateType.DUPLEX;
                break;
            case R.id.fragment_search_rdbtn_house:
                rdButtonHouse.setChecked(true);
                estateType = EstateType.HOUSE;
                break;
            case R.id.fragment_search_rdbtn_mansion:
                rdButtonMansion.setChecked(true);
                estateType = EstateType.MANSION;
                break;
            case R.id.fragment_search_rdbtn_penthouse:
                rdButtonPenthouse.setChecked(true);
                estateType = EstateType.PENTHOUSE;
                break;
            case R.id.fragment_search_rdbtn_villa:
                rdButtonVilla.setChecked(true);
                estateType = EstateType.VILLA;
                break;
        }
    }

    public boolean checkIfSearchIsPossible() {
        boolean atLeastOneCriteria = false;

        if (estateType != null) {
            atLeastOneCriteria = true;
        }
        if (!editPriceMin.getText().toString().isEmpty() && !editPriceMax.getText().toString().isEmpty()) {
            int minPrice = Integer.parseInt(editPriceMin.getText().toString());
            int maxPrice = Integer.parseInt(editPriceMax.getText().toString());
            if (minPrice > maxPrice) {
                showToastMessage("Le prix maximum doit être supérieur au prix minimum");
                return false;
            } else {
                atLeastOneCriteria = true;
                searchPriceValid = true;
            }
        }
        if (!editRoomMin.getText().toString().isEmpty() && !editRoomMax.getText().toString().isEmpty()) {
            int minRoom = Integer.parseInt(editRoomMin.getText().toString());
            int maxRoom = Integer.parseInt(editRoomMax.getText().toString());
            if (minRoom > maxRoom) {
                showToastMessage("Le nombre de pièces maximum doit être supérieur au nombre de pièces minimum");
                return false;
            } else {
                atLeastOneCriteria = true;
                nbRoomsValid = true;
            }
        }
        if (!editPostal.getText().toString().isEmpty()) {
            atLeastOneCriteria = true;
            postalCodeValid = true;
        }

        if (!atLeastOneCriteria) {
            showToastMessage("Veuillez remplir au moins un critère");
        }
        return atLeastOneCriteria;
    }

    public void showToastMessage(String message) {
        Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
    }
}
