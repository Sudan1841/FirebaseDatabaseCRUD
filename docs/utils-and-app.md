## Utility Methods and App Class

In this lesson we are creating two important methods. The first method is our app class. The second is the Utils class. This lesson is part of the android firebase realtime database crud full app development course we are currently covering. In the course we see how to create a full project from scratch based on Firebase Realtime database and supporting all CRUD operations.

### Objectives of this Lesson
Here are the objectives of this lesson:
1. Create utility methods that will be needed throughout the project.
2. Load a custom font into our android app across all activities.

### (a). App.java
The role of this class is simple: Load custom font into our app. We will use a library we had earlier on added in our dependecies: Calligraphy.

#### Step 1: Add Imports:
```java
import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
```
You can see among our imports include the [Application](/android/application) class. Well this is an android component and from it we will derive.

#### Step 2: Create Class
Our class will derive from `Application` class:
```java
public class App extends Application {
```

#### Step 3: Initialize Calligraphy
We now need to initialize our Calligraphy. The best place to that is in the `onCreate()` of our application class:
```java
    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Verdana.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
```

#### FULL CODE:
Here is the full code of App.java
```java
package info.camposha.firebasedatabasecrud;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Verdana.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
}
//end

```

### (b). Utils.java
This class as we said is to contain our utility methods. It is best practice ton confine methods usable across the whole app in one simple class from which they can be called. Such methods are normally made static so that we don't need to every now and then instantiate them to invoke our methods.

#### Step 1: Create the Class
```java
public class Utils {
```
#### Step 2: Show Toast message
This method will allow us show toast messages throughout various activities.
```java
    public static void show(Context c,String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
```
#### Step 3: Validate Edittexts
Suppose we want a simple method that can validate us any number of edittexts. We just need to pass that method a param of edittexts:
```java
    public static boolean validate(EditText... editTexts){
        EditText nameTxt = editTexts[0];
        EditText descriptionTxt = editTexts[1];
        EditText galaxyTxt = editTexts[2];

        if(nameTxt.getText() == null || nameTxt.getText().toString().isEmpty()){
            nameTxt.setError("Name is Required Please!");
            return false;
        }
        if(descriptionTxt.getText() == null || descriptionTxt.getText().toString().isEmpty()){
            descriptionTxt.setError("Description is Required Please!");
            return false;
        }
        if(galaxyTxt.getText() == null || galaxyTxt.getText().toString().isEmpty()){
            galaxyTxt.setError("Galaxy is Required Please!");
            return false;
        }
        return true;

    }
```
In our case three edittexts must not be empty for the validation to pass.

 #### Step 4: Opening a new activity
 ```java
     public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }
 ```

#### Step 5: Showing InfoDialog
```java
    public static void showInfoDialog(final AppCompatActivity activity, String title,
                                      String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.indigo)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.m_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Relax", v -> {})
                .setNeutralButton("Go Home", v -> openActivity(activity, DashboardActivity.class))
                .setNegativeButton("Go Back", v -> activity.finish())
                .show();
    }
```

#### Step 6: Showing SingleChoice Dialog
```java
    public static void selectStar(Context c,final EditText starTxt){
        String[] stars ={"Rigel","Aldebaran","Arcturus","Betelgeuse","Antares","Deneb",
                "Wezen","VY Canis Majoris","Sirius","Alpha Pegasi","Vega","Saiph","Polaris",
                "Canopus","KY Cygni","VV Cephei","Uy Scuti","Bellatrix","Naos","Pollux",
                "Achernar","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Stars Picker")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.m_star)
                .setMessage("Select the Star where the Scientist was born.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> starTxt.setText(item))
                .show();
    }
```

#### Step 7: Converting String to Date
```java
    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
```

#### Step 8: Sending Serialized Object to another activity
Well first make sure that object's class is implementing the Serializable interface.

Then:
```java
    public static void sendScientistToActivity(Context c, Scientist scientist,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("SCIENTIST_KEY",scientist);
        c.startActivity(i);
    }
```

Take note of the key we have passed we will need it in the next method when receiving that object.

#### Step 9: Receiving a Serialized Object then deserializing it.

Well the method above had serialized our object. Now this method will deserialize it.
```java
    public  static Scientist receiveScientist(Intent intent, Context c){
        try {
            return (Scientist) intent.getSerializableExtra("SCIENTIST_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SCIENTIST ERROR: "+e.getMessage());
        }
        return null;
    }
```
You can see we've used the `getSerializableExtra()`, passing in the key we had supplied earlier on.

#### Step 10: Showing and Hiding a ProgressBar
Well you simply use the `setVisibility()` method, passing in the appropriate constants.
```java
    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }
    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }

```

#### Step 11: Getting a Firebase Database reference
```java
    public static DatabaseReference getDatabaseRefence() {
        return FirebaseDatabase.getInstance().getReference();
    }
```

#### FULL CODE
Here is the full code for Utils.java:
```java
package info.camposha.firebasedatabasecrud.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.R;
import info.camposha.firebasedatabasecrud.Views.DashboardActivity;


public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static List<Scientist> DataCache =new ArrayList<>();

    public static String searchString = "";

    public static void show(Context c,String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
    public static boolean validate(EditText... editTexts){
        EditText nameTxt = editTexts[0];
        EditText descriptionTxt = editTexts[1];
        EditText galaxyTxt = editTexts[2];

        if(nameTxt.getText() == null || nameTxt.getText().toString().isEmpty()){
            nameTxt.setError("Name is Required Please!");
            return false;
        }
        if(descriptionTxt.getText() == null || descriptionTxt.getText().toString().isEmpty()){
            descriptionTxt.setError("Description is Required Please!");
            return false;
        }
        if(galaxyTxt.getText() == null || galaxyTxt.getText().toString().isEmpty()){
            galaxyTxt.setError("Galaxy is Required Please!");
            return false;
        }
        return true;

    }
    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }
    /**
     * This method will allow us show an Info dialog anywhere in our app.
     */
    public static void showInfoDialog(final AppCompatActivity activity, String title,
                                      String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.indigo)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.m_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Relax", v -> {})
                .setNeutralButton("Go Home", v -> openActivity(activity, DashboardActivity.class))
                .setNegativeButton("Go Back", v -> activity.finish())
                .show();
    }

    /**
     * This method will allow us show a single select dialog where we can select and return a
     * star to an edittext.
     */
    public static void selectStar(Context c,final EditText starTxt){
        String[] stars ={"Rigel","Aldebaran","Arcturus","Betelgeuse","Antares","Deneb",
                "Wezen","VY Canis Majoris","Sirius","Alpha Pegasi","Vega","Saiph","Polaris",
                "Canopus","KY Cygni","VV Cephei","Uy Scuti","Bellatrix","Naos","Pollux",
                "Achernar","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Stars Picker")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.m_star)
                .setMessage("Select the Star where the Scientist was born.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> starTxt.setText(item))
                .show();
    }

    /**
     * This method will allow us convert a string into a java.util.Date object and
     *  return it.
     */
    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method will allow us send a serialized scientist objec  to a specified
     *  activity
     */
    public static void sendScientistToActivity(Context c, Scientist scientist,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("SCIENTIST_KEY",scientist);
        c.startActivity(i);
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    public  static Scientist receiveScientist(Intent intent, Context c){
        try {
            return (Scientist) intent.getSerializableExtra("SCIENTIST_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SCIENTIST ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }
    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }

    public static DatabaseReference getDatabaseRefence() {
        return FirebaseDatabase.getInstance().getReference();
    }

}
//end

```

Now move over to the next lesson.