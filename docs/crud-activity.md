## Activity for Adding,Updating and Deleting

In this lesson we want to explore our CRUDActivity. This activity will be re-used for three purposes. First it will allow us register or post a new scientist to Firebase Realtime Database. Secondly it will allow us update existing scientist object. Thirdly it will allow us delete a scientist object.

NOTE /= This lesson is part of a multi-episode free course on perform CRUD operations against Firebase Realtime Database, in the process creating a full application.

This lesson involves us:
1. Creating our CRUD Activity as well as its corresponding activity_crud.xml.
2. Creating two menu resource files: first one shown when we open our CRUD Activity for the sake of adding a new scientist. Secondly shown when we open our CRUD activity for sake of editing or deleting Firebase data.
3. Invoke CRUD methods defined in the FirebaseCRUDHelper. Those methods will allow us to update as well delete our data.

### How our CRUD Activity Works

1. User clicks the 'Add New' card in the Dashboard or `Add New` menu item in the scientists activity.
2. The CRUD Activity is opened.
3. A null scientist object is passed in the process.
4. Because null is passed we inflate `add_new_item_menu.xml` as opposed to `edit_item_menu.xml` in our toolbar as a menu item.
5. The `add_new_item_menu.xml` will have a menu item that when clicked posts or inserts our data to Firebase Realtime database.
6. The data being inserted will be typed in the edittexts in the page.
7. The data will first be validated by a method we had earlier defined in the Utils class.
8. If data insert fails, an error message is shown to the user in a cardview within the same activity.
9. If the data insert succeeds, the CRUDActivity is closed, and the ScientistsActivity is opened where recyclerview is scrolled down to the last added item.
10. If the user clicks a recyclerview item, the detail page is opened.
11. If the user clicks the edit button or  edit menu item, the CRUDActivity is opened.
12. This time the scientist whose details were being shown is passed along.
13. Because a scientist object is being passed, our CRUDActivity will know that it's opened for the sake of editing or deleting.
14. Thus the `edit_item_menu.xml` is inflated as the menu resource file as opposed to the `add_new_item_menu.xml`.
15. That `edit_item_menu.xml` will have menu items for updating as well as deleting, shown as icons.
16. The user types the data and clicks update button.
17. Our Firebase data is updated.
18. If an error occurs, the error is shown in a cardview within the same page.
19. If the update is successful, the CRUD Activity is finished, and the Scientists activity is opened.
20. If the user clicks, the delete button, data is deleted and the scientists activity is opened.

### Step 1: Create New Item Menu
Under the res directory, create a folder called `menu`.
Inside it create an xml file called: `new_item_menu`:

The `new_item_menu.xml` will provide menu items for:
1. Adding/Inserting data to Firebase
2. Viewing/Selecting data from Firebase

Add the following code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- Options menu for the CRUDActivity -->
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".Views.CRUDActivity">

    <item
        android:id="@+id/insertMenuItem"
        android:title="SAVE"
        android:icon="@drawable/m_add"
        app:showAsAction="always" />
    <item
        android:id="@+id/viewAllMenuItem"
        android:title="VIEW ALL"
        android:icon="@drawable/m_list"
        app:showAsAction="always" />
</menu>
```


### Step 2: Create Edit Item Menu
Under our menu directory add an xml file called `edit_item_menu.xml`.

Add the following code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- Options menu for the CRUDActivity -->
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".Views.CRUDActivity">

    <item
        android:id="@+id/editMenuItem"
        android:title="SAVE"
        android:icon="@drawable/m_done"
        app:showAsAction="always" />
    <item
        android:id="@+id/deleteMenuItem"
        android:title="DELETE"
        android:icon="@drawable/m_delete"
        app:showAsAction="always" />
    <item
        android:id="@+id/viewAllMenuItem"
        android:title="VIEW ALL"
        android:icon="@drawable/m_list"
        app:showAsAction="ifRoom" />
</menu>
```

The save menu item will be used to update our firebase data while the delete menu wil be used to delete data from Firebase.

### Step 3: Create CRUD Activity Layout

#### (a). activity_crud.xml

This is our CRUD Activity layout. It will be inflated into our CRUD Activity.

Add the following code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@android:color/holo_red_light"
    tools:context=".Views.CRUDActivity">

    <ProgressBar
        android:id="@+id/mProgressBarSave"
        style="?android:attr/progressBarStyleHorizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:indeterminate="true"
        android:indeterminateBehavior="cycle"
        android:visibility="gone" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:paddingBottom="5dp"
        android:paddingLeft="5dp"
        android:paddingRight="5dp"
        android:paddingTop="5dp">


        <TextView
            android:id="@+id/headerTxt"
            fontPath="fonts/Roboto-Bold.ttf"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="5dp"
            android:text="Scientists Editing Page"
            android:textAlignment="center"
            android:textAppearance="@style/TextAppearance.AppCompat.Large"
            android:textColor="@color/white"
            tools:ignore="MissingPrefix" />

        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/card_margin"
            app:cardBackgroundColor="@color/white">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="@dimen/text_padding"
                    android:text="INSTRUCTIONS"
                    android:textAppearance="@style/TextAppearance.AppCompat.Title"
                    android:textColor="@color/skyblue" />

                <TextView
                    android:id="@+id/instructionsTV"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="@dimen/text_padding"
                    android:text="Provide the Scientist details in the edittext then click the save button in the toolbar. Some fields like name and galaxy must be provided." />

            </LinearLayout>

        </androidx.cardview.widget.CardView>

        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/card_margin">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="@dimen/text_padding"
                    android:text="Personal Details"
                    android:textAppearance="@style/TextAppearance.AppCompat.Title"
                    android:textColor="@color/niceGreenish"
                    tools:ignore="MissingPrefix" />

                <EditText
                    android:id="@+id/nameTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="16dp"
                    android:layout_marginTop="16dp"
                    android:hint="Name"
                    tools:ignore="MissingPrefix" />

                <EditText
                    android:id="@+id/descriptionTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="16dp"
                    android:layout_marginTop="16dp"
                    android:hint="Description"
                    android:minLines="3"
                    tools:ignore="MissingPrefix" />

            </LinearLayout>

        </androidx.cardview.widget.CardView>

        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/card_margin">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="@dimen/text_padding"
                    android:text="Residence Details"
                    android:textAppearance="@style/TextAppearance.AppCompat.Title"
                    android:textColor="@color/niceGreenish" />

                <EditText
                    android:id="@+id/galaxyTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="16dp"
                    android:layout_marginTop="16dp"
                    android:hint="Galaxy"
                    tools:ignore="MissingPrefix" />

                <EditText
                    android:id="@+id/starTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginBottom="16dp"
                    android:layout_marginTop="16dp"
                    android:hint="Star"
                    tools:ignore="MissingPrefix" />
            </LinearLayout>

        </androidx.cardview.widget.CardView>


        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/card_margin">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="@dimen/text_padding"
                    android:text="TimeLine Details"
                    android:textAppearance="@style/TextAppearance.AppCompat.Title"
                    android:textColor="@color/niceGreenish" />

                <android.helper.DateTimePickerEditText
                    android:id="@+id/dobTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_margin="@dimen/text_padding"
                    android:hint="Date of Birth"
                    tools:ignore="MissingPrefix" />
                <android.helper.DateTimePickerEditText
                    android:id="@+id/dodTxt"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_margin="@dimen/text_padding"
                    android:hint="Date of Death"
                    tools:ignore="MissingPrefix" />
            </LinearLayout>

        </androidx.cardview.widget.CardView>

    </LinearLayout>
    </ScrollView>

</LinearLayout>
```

You can see from the layout we have used the following widgets:

|No.|Widget|Role|
|--|------|-----|
|1.|LinearLayout| To align it's children linearly, either horizontally or vertically.|
|2.|ScrollView | To allow for scrolling of our it's children. It provides a scrollbar if the elemens exceed the viewport.It should have only one direct child.|
|3.| CardView | To group other widgets in beautiful cards.|
|4.| TextView | To provide labels. |
|5.| EditText | To provide an input widget for entering texts. |
|6.| DateTimePickerEditText | To provide a wheelpicker for picking dates. |
|7.|ProgressBar | To allow us show progress while posting to Firebase, updating Firebase or deleting from Firebase. |


### Step 4 : Create CRUD Activity

#### (a). CRUDActivity.java

This is the activity, as we said responsible for C, U and D in CRUD. That is we create our scientist, update it and delete it. The R or Read will be performed in the listing or Scientists Activity.

Start by adding our imports:
```java
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
```

You can see that we are using androidx artifacts.

Then create the activity:
```java
public class CRUDActivity extends AppCompatActivity {
```
Then we defin our instance fields in the class:
```java
    private EditText nameTxt, descriptionTxt, galaxyTxt, starTxt;
    private TextView headerTxt;
    private DateTimePickerEditText dobTxt,dodTxt;
    private ProgressBar mProgressBar;

    private final Context c = CRUDActivity.this;
    private Scientist receivedScientist;
    private FirebaseCRUDHelper crudHelper=new FirebaseCRUDHelper();
    private DatabaseReference db= Utils.getDatabaseRefence();
```
Those instance fields include our edittexts and datetimepicker for entering our data, our progressbar for showing progress and texview for showing header title. Moreover we have a `Scientist` object which will hold the scientist that is passed along for editing. We also have a FirebaseCRUDHelper instance which will allow us to invoke methods that contain logic for performing CRUD. We also have a database reference which will be passed along.

Then we create a method to initialize the above widgets:
```java
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

```

Let's now create a method to insert:
```java
    private void insertData() {
```

We will be inserting the following fields as properties of our scientist object:
```java
        String name, description, galaxy, star, dob,dod;
```

Then we perform validation:
```java
        if (Utils.validate(nameTxt, descriptionTxt, galaxyTxt)) {
```
If validation passes, we obtain values of our edittexts:
```java
            name = nameTxt.getText().toString();
            description = descriptionTxt.getText().toString();
            galaxy = galaxyTxt.getText().toString();
            star = starTxt.getText().toString();
```
Then validate both our date of birth and date of death:
```java
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
```
Then instantiate the Scientist, passing along it's properties via the constructor:
```java
            Scientist newScientist=new Scientist(name,description,galaxy,star,dob,dod);
```
Then post/insert to Firebase:
```java
            crudHelper.insert(this,db,mProgressBar,newScientist);
        }
    }
```

Then we create our update method and follow the same process:
```java
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
```

To delete we do the following:
```java
    private void deleteData() {
        crudHelper.delete(this,db,mProgressBar,receivedScientist);
    }
```

#### FULL CODE
Here is the full code for `CRUDActiviy.java`:
```java
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


```

Now move over to the next lesson.
