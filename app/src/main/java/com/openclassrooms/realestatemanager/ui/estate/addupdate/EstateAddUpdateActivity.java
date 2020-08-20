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

            Estate estate = new Estate();
            estate.setEstateMainPicture(bytesMain);
            estate.setEstateType(estateType);
            estate.setEstatePrice(Double.parseDouble(editPrice.getText().toString()));
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
            estate.setEstateHasBeenSold(false);
            estate.setEstateCreationDate(new Date());
            estate.setEstateSoldDate(null);
            estate.setEstateAgentName(editAgentName.getText().toString());
            long estateId = estateViewModel.createEstate(estate);

            if (extraOneImageHasBeenAdded) {
                String description = "/";
                if (!editExtraOne.getText().toString().isEmpty()) {
                    description = editExtraOne.getText().toString();
                }

                EstatePicture estatePicture = new EstatePicture();
                estatePicture.setEstatePictureEstateId(estateId);
                estatePicture.setEstatePictureImg(bytesExtraOne);
                estatePicture.setEstatePictureDescription(description);
                estateViewModel.createEstatePicture(estatePicture);
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
                estateViewModel.createEstatePicture(estatePicture);
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
                estateViewModel.createEstatePicture(estatePicture);
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
                estateViewModel.createEstatePicture(estatePicture);
            }

            showToastMessage("Le bien a été ajouté");
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
