package com.openclassrooms.realestatemanager.ui.estate.addupdate;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstateAddUpdateActivity extends AppCompatActivity {

    @BindView(R.id.activity_add_update_estate_img_main)
    ImageView imgMain;

    @BindView(R.id.activity_add_update_estate_rdgroup_type)
    RadioGroup rdGroupeType;

    @BindView(R.id.activity_add_update_estate_rdbtn_flat)
    RadioButton rdButtonFlat;

    @BindView(R.id.activity_add_update_estate_rdbtn_duplex)
    RadioButton rdButtonDuplex;

    @BindView(R.id.activity_add_update_estate_rdbtn_house)
    RadioButton rdButtonHouse;

    @BindView(R.id.activity_add_update_estate_rdbtn_mansion)
    RadioButton rdButtonMansion;

    @BindView(R.id.activity_add_update_estate_rdbtn_penthouse)
    RadioButton rdButtonPenthouse;

    @BindView(R.id.activity_add_update_estate_rdbtn_villa)
    RadioButton rdButtonVilla;

    @BindView(R.id.activity_add_update_estate_ckbox_culture)
    CheckBox ckCulture;

    @BindView(R.id.activity_add_update_estate_ckbox_health)
    CheckBox ckHealth;

    @BindView(R.id.activity_add_update_estate_ckbox_parks)
    CheckBox ckParks;

    @BindView(R.id.activity_add_update_estate_ckbox_retails)
    CheckBox ckRetails;

    @BindView(R.id.activity_add_update_estate_ckbox_school)
    CheckBox ckSchool;

    @BindView(R.id.activity_add_update_estate_ckbox_transports)
    CheckBox ckTransports;

    @BindView(R.id.activity_add_update_estate_ckbox_sold)
    CheckBox ckSold;

    @BindView(R.id.activity_add_update_estate_edit_agentname)
    EditText editAgentName;

    @BindView(R.id.activity_add_update_estate_edit_city)
    EditText editCity;

    @BindView(R.id.activity_add_update_estate_edit_country)
    EditText editCountry;

    @BindView(R.id.activity_add_update_estate_edit_description)
    EditText editDescription;

    @BindView(R.id.activity_add_update_estate_edit_nb_bathrooms)
    EditText editNbBathrooms;

    @BindView(R.id.activity_add_update_estate_edit_nb_bedrooms)
    EditText editNbBedrooms;

    @BindView(R.id.activity_add_update_estate_edit_nb_rooms)
    EditText editNbRooms;

    @BindView(R.id.activity_add_update_estate_edit_postal)
    EditText editPostal;

    @BindView(R.id.activity_add_update_estate_edit_price)
    EditText editPrice;

    @BindView(R.id.activity_add_update_estate_edit_street)
    EditText editStreet;

    @BindView(R.id.activity_add_update_estate_edit_surface)
    EditText editSurface;

    boolean mainImageHasBeenAdded = false;
    boolean typeHasBeenAdded = false;
    boolean pointsOfInterestHasBeenAdded = false;
    boolean oneExtraImageHasBeenAdded = false;

    EstateViewModel estateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_estate);
        ButterKnife.bind(this);

        setTitle("Ajouter un bien");

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public void initViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);
    }

    @OnClick(R.id.activity_add_update_estate_img_main)
    public void addMainPicture() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                ImagePicker.create(EstateAddUpdateActivity.this)
                        .returnMode(ReturnMode.ALL)
                        .folderMode(true)
                        .toolbarFolderTitle("Dossier")
                        .toolbarImageTitle("Sélectionner")
                        .toolbarArrowColor(Color.WHITE)
                        .includeVideo(false)
                        .single()
                        .imageDirectory("Camera")
                        .start();
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    @OnClick(R.id.activity_add_update_estate_btn_save)
    public void saveEstate() {
        if (!mainImageHasBeenAdded) {
            showToastMessage("Merci d'ajouter une image principale");
        } else if (!typeHasBeenAdded) {
            showToastMessage("Merci d'ajouter un type");
        } else if (editPrice.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un prix");
        } else if (editDescription.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter une description");
        } else if (editSurface.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter une surface");
        } else if (editNbRooms.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un nombre de pièces");
        } else if (editNbBathrooms.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un nombre de salles de bain");
        } else if (editNbBedrooms.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un nombre de chambres");
        } else if (editStreet.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter une rue");
        } else if (editPostal.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un code postal");
        } else if (editCity.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter une ville");
        } else if (editCountry.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter un pays");
        } else if (!pointsOfInterestHasBeenAdded) {
            showToastMessage("Merci d'ajouter un point d'intêret");
        } else if (editAgentName.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter le nom de l'agent");
        } else if (!oneExtraImageHasBeenAdded) {
            showToastMessage("Merci d'ajouter au moins une image extra");
        } else {
            Estate estate = new Estate();
            estate.setEstateMainPicture("");
            estate.setEstateType(EstateType.VILLA);
            estate.setEstatePrice(1750000d);
            estate.setEstateDescription("Villa luxe de ouf on se met bien");
            estate.setEstateSurface("700m2");
            estate.setEstateNbRooms(10);
            estate.setEstateNbBathrooms(4);
            estate.setEstateNbBedrooms(5);
            estate.setEstateStreet("18 rue Rivoli");
            estate.setEstatePostal("75001");
            estate.setEstateCity("Paris");
            estate.setEstateCountry("France");
            estate.setEstatePointsOfInterest("school parcs");
            estate.setEstateHasBeenSold(false);
            estate.setEstateCreationDate(null);
            estate.setEstateSoldDate(null);
            estate.setEstateAgentName("Anthony Fillion-Maillet");
            estateViewModel.createEstate(estate);
        }
    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            System.out.println(requestCode);
            System.out.println(resultCode);
            System.out.println(data);
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
