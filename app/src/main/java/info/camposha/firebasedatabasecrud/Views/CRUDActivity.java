package info.camposha.firebasedatabasecrud.Views;

import android.content.Context;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Helpers.FirebaseCRUDHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CRUDActivity extends AppCompatActivity {

    //we'll have several instance fields
    private EditText nameTxt, descriptionTxt, galaxyTxt, starTxt;
    private TextView headerTxt;
    private DateTimePickerEditText dobTxt,dodTxt;
    private ProgressBar mProgressBar;

    private final Context c = CRUDActivity.this;
    private Scientist receivedScientist;
    private FirebaseCRUDHelper crudHelper=new FirebaseCRUDHelper();
    private DatabaseReference db= Utils.getDatabaseRefence();

    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);

        headerTxt = findViewById(R.id.headerTxt);
        nameTxt = findViewById(R.id.nameTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        galaxyTxt = findViewById(R.id.galaxyTxt);
        starTxt = findViewById(R.id.starTxt);

        dobTxt = findViewById(R.id.dobTxt);
        dobTxt.setFormat(Utils.DATE_FORMAT);
        dodTxt = findViewById(R.id.dodTxt);
        dodTxt.setFormat(Utils.DATE_FORMAT);
    }

    private void insertData() {
        String name, description, galaxy, star, dob,dod;
        if (Utils.validate(nameTxt, descriptionTxt, galaxyTxt)) {
            name = nameTxt.getText().toString();
            description = descriptionTxt.getText().toString();
            galaxy = galaxyTxt.getText().toString();
            star = starTxt.getText().toString();

            if (dobTxt.getDate() != null) {
                Date date=dobTxt.getDate();
                dob = dobTxt.getFormat().format(dobTxt.getDate());
            } else {
                dobTxt.setError("Invalid Date");
                dobTxt.requestFocus();
                return;
            }
            if (dodTxt.getDate() != null) {
                dod = dodTxt.getFormat().format(dodTxt.getDate());
            } else {
                dodTxt.setError("Invalid Date");
                dodTxt.requestFocus();
                return;
            }

            Scientist newScientist=new Scientist(name,description,galaxy,star,dob,dod);
            crudHelper.insert(this,db,mProgressBar,newScientist);

        }
    }

    private void updateData() {
        String name, description, galaxy, star, dob,dod;
        if (Utils.validate(nameTxt, descriptionTxt, galaxyTxt)) {
            name = nameTxt.getText().toString();
            description = descriptionTxt.getText().toString();
            galaxy = galaxyTxt.getText().toString();
            star = starTxt.getText().toString();

            if (dobTxt.getDate() != null) {
                dob = dobTxt.getFormat().format(dobTxt.getDate());
            } else {
                dobTxt.setError("Invalid Date");
                dobTxt.requestFocus();
                return;
            }
            if (dodTxt.getDate() != null) {
                dod = dodTxt.getFormat().format(dodTxt.getDate());
            } else {
                dodTxt.setError("Invalid Date");
                dodTxt.requestFocus();
                return;
            }

            Scientist newScientist=new Scientist(name,description,galaxy,star,dob,dod);
            crudHelper.update(this,db,mProgressBar,receivedScientist,newScientist);

        }
    }

    private void deleteData() {
        crudHelper.delete(this,db,mProgressBar,receivedScientist);
    }

    private void showSelectedStarInEditText() {
        starTxt.setOnClickListener(v -> Utils.selectStar(c, starTxt));
    }

    @Override
    public void onBackPressed() {
        Utils.showInfoDialog(this, "Warning", "Are you sure you want to exit?");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedScientist == null) {
            getMenuInflater().inflate(R.menu.new_item_menu, menu);
            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.edit_item_menu, menu);
            headerTxt.setText("Edit Existing Scientist");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedScientist != null) {
                    updateData();
                } else {
                    Utils.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedScientist != null) {
                    deleteData();
                } else {
                    Utils.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Utils.openActivity(this, ScientistsActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attach Base Context
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * When our activity is resumed we will receive our data and set them to their editing
     * widgets.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Scientist o = Utils.receiveScientist(getIntent(), c);
        if (o != null) {
            receivedScientist = o;
            nameTxt.setText(receivedScientist.getName());
            descriptionTxt.setText(receivedScientist.getDescription());
            galaxyTxt.setText(receivedScientist.getGalaxy());
            starTxt.setText(receivedScientist.getStar());
            Object dob = receivedScientist.getDob();
            if (dob != null) {
                dobTxt.setDate(Utils.giveMeDate(dob.toString()));
            }
            Object dod = receivedScientist.getDod();
            if (dod != null) {
                dodTxt.setDate(Utils.giveMeDate(dod.toString()));
            }
        } else {
            //Utils.show(c,"Received Scientist is Null");
        }
    }

    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }
}
//end

