package com.openclassrooms.realestatemanager.ui.estate.addupdate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.FileUtils;
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
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;
import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
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

    @BindView(R.id.activity_add_update_estate_img_extra_one)
    ImageView imgExtraOne;

    @BindView(R.id.activity_add_update_estate_edit_extra_one)
    EditText editExtraOne;

    @BindView(R.id.activity_add_update_estate_img_extra_two)
    ImageView imgExtraTwo;

    @BindView(R.id.activity_add_update_estate_edit_extra_two)
    EditText editExtraTwo;

    @BindView(R.id.activity_add_update_estate_img_extra_three)
    ImageView imgExtraThree;

    @BindView(R.id.activity_add_update_estate_edit_extra_three)
    EditText editExtraThree;

    @BindView(R.id.activity_add_update_estate_img_extra_four)
    ImageView imgExtraFour;

    @BindView(R.id.activity_add_update_estate_edit_extra_four)
    EditText editExtraFour;

    boolean mainImageHasBeenAdded = false;
    boolean extraOneImageHasBeenAdded = false;
    boolean extraTwoImageHasBeenAdded = false;
    boolean extraThreeImageHasBeenAdded = false;
    boolean extraFourImageHasBeenAdded = false;
    EstateType estateType = null;

    boolean mainImgIsBeingModified = false;
    boolean extraOneImgIsBeingModified = false;
    boolean extraTwoImgIsBeingModified = false;
    boolean extraThreeImgIsBeingModified = false;
    boolean extraFourImgIsBeingModified = false;

    byte[] bytesMain;
    byte[] bytesExtraOne;
    byte[] bytesExtraTwo;
    byte[] bytesExtraThree;
    byte[] bytesExtraFour;

    EstateViewModel estateViewModel;

    String editedEstateId;
    Estate editedEstate;
    long editedEstatePictureIdOne = 0;
    long editedEstatePictureIdTwo = 0;
    long editedEstatePictureIdThree = 0;
    long editedEstatePictureIdFour = 0;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_estate);
        ButterKnife.bind(this);

        setTitle("Ajouter un bien");

        editedEstateId = getIntent().getStringExtra("estateId");
        if (editedEstateId != null) {
            editMode = true;
        }

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewModel();

        if (editMode) {
            observeEstateViewModel();
        }
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

    private void observeEstateViewModel() {
//        if (estateViewModel.getCurrentEstateList() != null) {
//            estateViewModel.getCurrentEstateList().observe(this, estateList -> {
//                for (Estate estate : estateList) {
//                    if (String.valueOf(estate.getEstateId()).equals(editedEstateId)) {
//                        editedEstate = estate;
//                        if (editedEstate.getEstateSoldDate() == null) {
//                            ckSold.setEnabled(true);
//                        } else {
//                            ckSold.setChecked(true);
//                        }
//
//                        updateEstateViews(estate);
//                        break;
//                    }
//                }
//            });
//        }
        editedEstate = estateViewModel.getEstate(Long.parseLong(editedEstateId));
        if (editedEstate.getEstateSoldDate() == null) {
            ckSold.setEnabled(true);
        } else {
            ckSold.setChecked(true);
        }
        updateEstateViews(editedEstate);

        estateViewModel.getCurrentEstatePictureList(Long.parseLong(editedEstateId)).observe(this, estatePictureList -> {
            updateEstatePictures(estatePictureList);
        });
    }

    public void updateEstateViews(Estate estate) {
        if (estate.getEstateMainPicture() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(estate.getEstateMainPicture(), 0, estate.getEstateMainPicture().length);
            imgMain.setImageBitmap(bmp);
            bytesMain = estate.getEstateMainPicture();
            mainImageHasBeenAdded = true;
        }

        estateType = estate.getEstateType();
        switch (estateType) {
            case FLAT: rdButtonFlat.setChecked(true); break;
            case HOUSE: rdButtonHouse.setChecked(true); break;
            case VILLA: rdButtonVilla.setChecked(true); break;
            case DUPLEX: rdButtonDuplex.setChecked(true); break;
            case MANSION: rdButtonMansion.setChecked(true); break;
            case PENTHOUSE: rdButtonPenthouse.setChecked(true); break;
        }

        editPrice.setText(String.valueOf(estate.getEstatePrice()));
        editDescription.setText(estate.getEstateDescription());
        editSurface.setText(estate.getEstateSurface());
        editNbRooms.setText(String.valueOf(estate.getEstateNbRooms()));
        editNbBathrooms.setText(String.valueOf(estate.getEstateNbBathrooms()));
        editNbBedrooms.setText(String.valueOf(estate.getEstateNbBedrooms()));
        editStreet.setText(estate.getEstateStreet());
        editPostal.setText(estate.getEstatePostal());
        editCity.setText(estate.getEstateCity());
        editCountry.setText(estate.getEstateCountry());

        setPointOfInterest(estate.getEstatePointsOfInterest());

        editAgentName.setText(estate.getEstateAgentName());
    }

    public void updateEstatePictures(List<EstatePicture> estatePictures) {
        int index = 0;
        for (EstatePicture estatePicture : estatePictures) {
            Bitmap bmp = BitmapFactory.decodeByteArray(estatePicture.getEstatePictureImg(), 0, estatePicture.getEstatePictureImg().length);

            if (index == 0) {
                imgExtraOne.setImageBitmap(bmp);
                bytesExtraOne = estatePicture.getEstatePictureImg();
                editExtraOne.setText(estatePicture.getEstatePictureDescription());
                extraOneImageHasBeenAdded = true;
                editedEstatePictureIdOne = estatePicture.getEstatePictureId();
            } else if (index == 1) {
                imgExtraTwo.setImageBitmap(bmp);
                bytesExtraTwo = estatePicture.getEstatePictureImg();
                editExtraTwo.setText(estatePicture.getEstatePictureDescription());
                extraTwoImageHasBeenAdded = true;;
                editedEstatePictureIdTwo = estatePicture.getEstatePictureId();
            } else if (index == 2) {
                imgExtraThree.setImageBitmap(bmp);
                bytesExtraThree = estatePicture.getEstatePictureImg();
                editExtraThree.setText(estatePicture.getEstatePictureDescription());
                extraThreeImageHasBeenAdded = true;;
                editedEstatePictureIdThree = estatePicture.getEstatePictureId();
            } else {
                imgExtraFour.setImageBitmap(bmp);
                bytesExtraFour = estatePicture.getEstatePictureImg();
                editExtraFour.setText(estatePicture.getEstatePictureDescription());
                extraFourImageHasBeenAdded = true;;
                editedEstatePictureIdFour = estatePicture.getEstatePictureId();
            }

            index += 1;
        }
    }

    @OnClick(R.id.activity_add_update_estate_img_main)
    public void addMainPicture() {
        mainImgIsBeingModified = true;
        askPermissionAndOpenGallery();
    }

    @OnClick(R.id.activity_add_update_estate_img_extra_one)
    public void addExtraPictureOne() {
        extraOneImgIsBeingModified = true;
        askPermissionAndOpenGallery();
    }

    @OnClick(R.id.activity_add_update_estate_img_extra_two)
    public void addExtraPictureTwo() {
        extraTwoImgIsBeingModified = true;
        askPermissionAndOpenGallery();
    }

    @OnClick(R.id.activity_add_update_estate_img_extra_three)
    public void addExtraPictureThree() {
        extraThreeImgIsBeingModified = true;
        askPermissionAndOpenGallery();
    }

    @OnClick(R.id.activity_add_update_estate_img_extra_four)
    public void addExtraPictureFour() {
        extraFourImgIsBeingModified = true;
        askPermissionAndOpenGallery();
    }

    public void askPermissionAndOpenGallery() {
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
                mainImgIsBeingModified = false;
                extraOneImgIsBeingModified = false;
                extraTwoImgIsBeingModified = false;
                extraThreeImgIsBeingModified = false;
                extraFourImgIsBeingModified = false;
                token.continuePermissionRequest();
            }
        }).check();
    }

    @OnClick({
            R.id.activity_add_update_estate_rdbtn_flat,
            R.id.activity_add_update_estate_rdbtn_duplex,
            R.id.activity_add_update_estate_rdbtn_house,
            R.id.activity_add_update_estate_rdbtn_mansion,
            R.id.activity_add_update_estate_rdbtn_penthouse,
            R.id.activity_add_update_estate_rdbtn_villa
    })
    public void onRadioButtonClicked(RadioButton radioButton) {
        rdButtonFlat.setChecked(false);
        rdButtonDuplex.setChecked(false);
        rdButtonHouse.setChecked(false);
        rdButtonMansion.setChecked(false);
        rdButtonPenthouse.setChecked(false);
        rdButtonVilla.setChecked(false);

        switch (radioButton.getId()) {
            case R.id.activity_add_update_estate_rdbtn_flat:
                rdButtonFlat.setChecked(true);
                estateType = EstateType.FLAT;
                break;
            case R.id.activity_add_update_estate_rdbtn_duplex:
                rdButtonDuplex.setChecked(true);
                estateType = EstateType.DUPLEX;
                break;
            case R.id.activity_add_update_estate_rdbtn_house:
                rdButtonHouse.setChecked(true);
                estateType = EstateType.HOUSE;
                break;
            case R.id.activity_add_update_estate_rdbtn_mansion:
                rdButtonMansion.setChecked(true);
                estateType = EstateType.MANSION;
                break;
            case R.id.activity_add_update_estate_rdbtn_penthouse:
                rdButtonPenthouse.setChecked(true);
                estateType = EstateType.PENTHOUSE;
                break;
            case R.id.activity_add_update_estate_rdbtn_villa:
                rdButtonVilla.setChecked(true);
                estateType = EstateType.VILLA;
                break;
        }
    }

    public void setPointOfInterest(String pointOfInterest) {
        if (pointOfInterest.contains("culture")) {
            ckCulture.setChecked(true);
        }
        if (pointOfInterest.contains("sante")) {
            ckHealth.setChecked(true);
        }
        if (pointOfInterest.contains("parcs")) {
            ckParks.setChecked(true);
        }
        if (pointOfInterest.contains("commerces")) {
            ckRetails.setChecked(true);
        }
        if (pointOfInterest.contains("ecole")) {
            ckSchool.setChecked(true);
        }
        if (pointOfInterest.contains("transports")) {
            ckTransports.setChecked(true);
        }
    }

    public String getPointOfInterest() {
        String pointOfInterests = "";

        if (ckCulture.isChecked()) {
            pointOfInterests += "culture ";
        }
        if (ckHealth.isChecked()) {
            pointOfInterests += "sante ";
        }
        if (ckParks.isChecked()) {
            pointOfInterests += "parcs ";
        }
        if (ckRetails.isChecked()) {
            pointOfInterests += "commerces ";
        }
        if (ckSchool.isChecked()) {
            pointOfInterests += "ecole ";
        }
        if (ckTransports.isChecked()) {
            pointOfInterests += "transports ";
        }

        return pointOfInterests;
    }

    @OnClick(R.id.activity_add_update_estate_btn_save)
    public void saveEstate() {
        if (!mainImageHasBeenAdded) {
            showToastMessage("Merci d'ajouter une image principale");
        } else if (estateType == null) {
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
        } else if (editAgentName.getText().toString().isEmpty()) {
            showToastMessage("Merci d'ajouter le nom de l'agent");
        } else if (!extraOneImageHasBeenAdded && !extraTwoImageHasBeenAdded &&
                   !extraThreeImageHasBeenAdded && !extraFourImageHasBeenAdded) {
            showToastMessage("Merci d'ajouter au moins une image extra");
        } else {
            String street = editStreet.getText().toString();
            String postal = editPostal.getText().toString();
            String city = editCity.getText().toString();
            String country = editCountry.getText().toString();
            String address = street + " " + postal + ", " + city + ", " + country;
            LatLng latLng = getLocationFromAddress(address);

            if (latLng == null) {
                showToastMessage("Adresse non valide");
                return;
            }
            long estateId = 0;

            Estate estate = new Estate();
            estate.setEstateMainPicture(bytesMain);
            estate.setEstateType(estateType);
            estate.setEstatePrice(Integer.parseInt(editPrice.getText().toString()));
            estate.setEstateDescription(editDescription.getText().toString());
            estate.setEstateSurface(editSurface.getText().toString());
            estate.setEstateNbRooms(Integer.parseInt(editNbRooms.getText().toString()));
            estate.setEstateNbBathrooms(Integer.parseInt(editNbBathrooms.getText().toString()));
            estate.setEstateNbBedrooms(Integer.parseInt(editNbBedrooms.getText().toString()));
            estate.setEstateStreet(street);
            estate.setEstatePostal(postal);
            estate.setEstateCity(city);
            estate.setEstateCountry(country);
            estate.setEstateLat(latLng.latitude);
            estate.setEstateLng(latLng.longitude);
            estate.setEstatePointsOfInterest(getPointOfInterest());
            estate.setEstateAgentName(editAgentName.getText().toString());

            // CREATE
            if (!editMode) {
                estate.setEstateHasBeenSold(false);
                estate.setEstateCreationDate(new Date());
                estate.setEstateSoldDate(null);

                estateId = estateViewModel.createEstate(estate);
            }
            // EDIT
            else {
                estate.setEstateHasBeenSold(ckSold.isChecked());
                estate.setEstateCreationDate(editedEstate.getEstateCreationDate());
                if (ckSold.isChecked()) {
                    if (editedEstate.getEstateSoldDate() != null) {
                        estate.setEstateSoldDate(editedEstate.getEstateSoldDate());
                    } else {
                        estate.setEstateSoldDate(new Date());
                    }
                } else {
                    estate.setEstateSoldDate(null);
                }

                estateId = Long.parseLong(editedEstateId);
                estate.setEstateId(estateId);
                estateViewModel.updateEstate(estate);
            }

            if (extraOneImageHasBeenAdded) {
                String description = "/";
                if (!editExtraOne.getText().toString().isEmpty()) {
                    description = editExtraOne.getText().toString();
                }

                EstatePicture estatePicture = new EstatePicture();
                estatePicture.setEstatePictureEstateId(estateId);
                estatePicture.setEstatePictureImg(bytesExtraOne);
                estatePicture.setEstatePictureDescription(description);

                if (!editMode) {
                    estateViewModel.createEstatePicture(estatePicture);
                } else {
                    if (editedEstatePictureIdOne == 0) {
                        estateViewModel.createEstatePicture(estatePicture);
                    } else {
                        estatePicture.setEstatePictureId(editedEstatePictureIdOne);
                        estateViewModel.updateEstatePicture(estatePicture);
                    }
                }
            }

            if (extraTwoImageHasBeenAdded) {
                String description = "/";
                if (!editExtraTwo.getText().toString().isEmpty()) {
                    description = editExtraTwo.getText().toString();
                }

                EstatePicture estatePicture = new EstatePicture();
                estatePicture.setEstatePictureEstateId(estateId);
                estatePicture.setEstatePictureImg(bytesExtraTwo);
                estatePicture.setEstatePictureDescription(description);

                if (!editMode) {
                    estateViewModel.createEstatePicture(estatePicture);
                } else {
                    if (editedEstatePictureIdTwo == 0) {
                        estateViewModel.createEstatePicture(estatePicture);
                    } else {
                        estatePicture.setEstatePictureId(editedEstatePictureIdTwo);
                        estateViewModel.updateEstatePicture(estatePicture);
                    }
                }
            }

            if (extraThreeImageHasBeenAdded) {
                String description = "/";
                if (!editExtraThree.getText().toString().isEmpty()) {
                    description = editExtraThree.getText().toString();
                }

                EstatePicture estatePicture = new EstatePicture();
                estatePicture.setEstatePictureEstateId(estateId);
                estatePicture.setEstatePictureImg(bytesExtraThree);
                estatePicture.setEstatePictureDescription(description);

                if (!editMode) {
                    estateViewModel.createEstatePicture(estatePicture);
                } else {
                    if (editedEstatePictureIdThree == 0) {
                        estateViewModel.createEstatePicture(estatePicture);
                    } else {
                        estatePicture.setEstatePictureId(editedEstatePictureIdThree);
                        estateViewModel.updateEstatePicture(estatePicture);
                    }
                }
            }

            if (extraFourImageHasBeenAdded) {
                String description = "/";
                if (!editExtraFour.getText().toString().isEmpty()) {
                    description = editExtraFour.getText().toString();
                }

                EstatePicture estatePicture = new EstatePicture();
                estatePicture.setEstatePictureEstateId(estateId);
                estatePicture.setEstatePictureImg(bytesExtraFour);
                estatePicture.setEstatePictureDescription(description);

                if (!editMode) {
                    estateViewModel.createEstatePicture(estatePicture);
                } else {
                    if (editedEstatePictureIdFour == 0) {
                        estateViewModel.createEstatePicture(estatePicture);
                    } else {
                        estatePicture.setEstatePictureId(editedEstatePictureIdFour);
                        estateViewModel.updateEstatePicture(estatePicture);
                    }
                }
            }

            if (!editMode) {
                showToastMessage("Le bien a été ajouté");
            } else {
                showToastMessage("Le bien a été modifié");
            }
            onBackPressed();
        }
    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            if (mainImgIsBeingModified) {
                bytesMain = saveImageBitmap(data, bytesMain);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesMain, 0, bytesMain.length);
                imgMain.setImageBitmap(bmp);
                mainImageHasBeenAdded = true;
            } else if (extraOneImgIsBeingModified) {
                bytesExtraOne = saveImageBitmap(data, bytesExtraOne);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesExtraOne, 0, bytesExtraOne.length);
                imgExtraOne.setImageBitmap(bmp);
                extraOneImageHasBeenAdded = true;
            } else if (extraTwoImgIsBeingModified) {
                bytesExtraTwo = saveImageBitmap(data, bytesExtraTwo);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesExtraTwo, 0, bytesExtraTwo.length);
                imgExtraTwo.setImageBitmap(bmp);
                extraTwoImageHasBeenAdded = true;
            } else if (extraThreeImgIsBeingModified) {
                bytesExtraThree = saveImageBitmap(data, bytesExtraThree);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesExtraThree, 0, bytesExtraThree.length);
                imgExtraThree.setImageBitmap(bmp);
                extraThreeImageHasBeenAdded = true;
            } else if (extraFourImgIsBeingModified) {
                bytesExtraFour = saveImageBitmap(data, bytesExtraFour);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytesExtraFour, 0, bytesExtraFour.length);
                imgExtraFour.setImageBitmap(bmp);
                extraFourImageHasBeenAdded = true;
            }
        }

        mainImgIsBeingModified = false;
        extraOneImgIsBeingModified = false;
        extraTwoImgIsBeingModified = false;
        extraThreeImgIsBeingModified = false;
        extraFourImgIsBeingModified = false;
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] saveImageBitmap(Intent data, byte[] bytesToChange) {
        // Get a list of picked images
        Image image = ImagePicker.getFirstImageOrNull(data);
        Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        bytesToChange = stream.toByteArray();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

        return bytesToChange;
    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
