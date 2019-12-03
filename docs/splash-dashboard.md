## Creating our Splash and Dashboard Activities

In this lesson we want to create our splash and dashboard activities. These will represent two screens that make our application more professional. The splash screen will allow our us display our logo, title and subtitle for our application or company. You can change the time taken to display the splash screen or remove it entirely from the project. Splash screen is not all mandatory for this project.

On the other hadn our dashboard activity is vital for our project. It gives our application a centralized location from which we can navgate our app. It will contain cardviews that can lead us to other parts of the application.

NOTE/= This lesson is part of android firebase crud course we are currently covering. We advise that you take the full course to see how to properly apply this lesson in the whole app. The course is free.

### What We are Creating
We will create three files:

|No.|File|Role|
|---|----|----|
|1.|SplashActivity.java|Contain our java code for a splash screen.|
|2.|DashboardActivity.java| Contain our java code for dashboard activity|
|3.|activity_splash.xml | Contain XML Code for our Splash Screen.|
|4.|activity_dashboard.xml | Contain XML Code for our Dashboard Screen .|
|5.|top_to_bottom.xml | Will be contained in an anim folder. Will contain our animation code for moving a widget from a top position to sliightly lower position.|
|6.|fade_in.xml| Will be contained in our anim folder. Will contain our animation code for fading in a widget.|

### How our Splash Screen Works
1. User clicks the app icon in his android device.
2. The application starts.
3. The launcher activity is our slash activity, thus it is started first.
4. We dop our logo imageview using the top_to_bottom animation.
5. Then we fade in our main title and sub title textviews.
6. We then sleep a thread for like 2 seconds.
7. Then we open our dashboard activity.
8. We kill the splash activity.

### How our Dashboard Screen works.
1. It is opened by the splash activity.
2. It makes us of a CollapsingToolbar and has an image set to it.
3. It contains 4 cardviews.
4. When the View All cardview is clicked we open the activity for viewing all our scientists in a recyclerview.
5. When the Add New cardview is clicked we open the activity for adding or registering a new scientist activity.
6. When the Another Item cardview is clicked we open a material lovely dialog.
7. When the exit button is clicked we finish the current activity, thus stopping our application.

### Step 1: Create Splash Animations

#### (a). top_to_bottom.xml
This animation, as we had said earlier will drop our imageview from a higher position to a lower position.

1. Under your res directory, create a folder called `anim`. This anim will contain our animations.
2. Create an xml file called `top_to_bottom.xml`.
3. Add the following code:

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:fromYDelta="-150%"
        android:toYDelta="0%"
        android:duration="500"
        android:repeatCount="0" />
</set>
```


#### (b). fade_in.xml
This animation will fade our two textviews, mainTitle and subTitle. It will be shown after the top to Bottom.

1. Under the `anim` folder we had created in the steps above, create a file named `fade_in.xml`.
2. Add the following code:

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:duration="1500"
        />
</set>
```

### Step 2: Create Splash Screen Layout

#### (a). activity_splash.xml

Under the `/res/layouts` folder create a file called `activity_splash` if it doesn't exist.

Then add the following code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/m_splash_screen_bg"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".Views.SplashActivity">

    <ImageView
        android:id="@+id/mLogo"
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:src="@drawable/campo" />

    <TextView
        android:id="@+id/mainTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:text="ProgrammingWizards TV"
        android:textAlignment="center"
        android:textColor="@color/colorTitleColor"
        android:textSize="24sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/subTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Watch Great Courses in YouTube in HD"
        android:textAlignment="center"
        android:textColor="@color/colorTitleColor"
        android:textSize="18sp" />

</LinearLayout>

```

In the above code we have defined a [LinearLayout](/android/linearlayout) as our root element. Inside it we have arranged the following:
1. [ImageView](/android/imageview) - To hold image used as our logo.
2. [TextViews](/android/textview) - To hold main title and subtitle of our app or company,

### Step 4: Write Splash Screen Code

#### (a). Create `SplashActivity` class
 Start by specifying our import statements.
```java
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
```
In the aboev you can see among others we have the following imports:
1. Animation  - Our base animation class to represent the animations we load from XML.
2. AnimationUtils - Class to allow us load our XML defined animations into an `android.view.animation.Animation` object.


Then make the class extend AppCompatActivity:
```java
public class SplashActivity extends AppCompatActivity {
```

#### (b). Initialize widgets
Start by declaring the widgets as instance fields:
```java
    //our splash screen views
    private ImageView mLogo;
    private TextView mainTitle, subTitle;
```

Then we initialize them using the `findViewById()` method:
```java
    private void initializeWidgets() {
        mLogo = findViewById(R.id.mLogo);
        mainTitle = findViewById(R.id.mainTitle);
        subTitle = findViewById(R.id.subTitle);
    }
```

#### (c). Show Splash Animation
Start by creating a method to show us the splash animation.
```java
    private void showSplashAnimation() {
```
Then load our top_to_bottom animation using the AnimationUtils class:
```java
        Animation animation = AnimationUtils.loadAnimation(this,
         R.anim.top_to_bottom);
```
Then start the animation:
```java
        mLogo.startAnimation(animation);
```
Now come and load our fade_in animation:
```java
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
```
Now apply them to our two textviews and start them.
```java
        mainTitle.startAnimation(fadeIn);
        subTitle.startAnimation(fadeIn);
    }
```

#### (d). Open Dashboard
Start by creating the method:
```java
    private void goToDashboard() {
```
Now instantiate our [Thread Class](/java/thread):
```java
        Thread t = new Thread() {
```
We need to overide the `run()` method:
```java
            @Override
            public void run() {
```
Then sleep our thread for 2 seconds:
```java
                try {
                    sleep(2000);
```
Now open our dashboard activity after those two seconds:
```java
                    Utils.openActivity(SplashActivity.this, DashboardActivity.class);
```
Then finish the splash activity. This ensures that user cannot navigate back to splash activity:
```java
                    finish();
                    super.run();
```
We will in the process catch any InterruptedExceptions:
```java
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
```
Don't forget to start our thread explicitly:
```java
        t.start();
    }

```

#### FULL CODE
##### (a). /Views/SplashActivity.java
```java
package info.camposha.firebasedatabasecrud.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class SplashActivity extends AppCompatActivity {

    //our splash screen views
    private ImageView mLogo;
    private TextView mainTitle, subTitle;

    /**
     * Let's initialize our widgets.
     */
    private void initializeWidgets() {
        mLogo = findViewById(R.id.mLogo);
        mainTitle = findViewById(R.id.mainTitle);
        subTitle = findViewById(R.id.subTitle);
    }
    /**
     * Let's show our Splash animation using Animation class. We fade in our widgets.
     */
    private void showSplashAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,
         R.anim.top_to_bottom);
        mLogo.startAnimation(animation);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mainTitle.startAnimation(fadeIn);
        subTitle.startAnimation(fadeIn);
    }


    /**
     * Let's go to our DashBoard after 2 seconds
     */
    private void goToDashboard() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Utils.openActivity(SplashActivity.this, DashboardActivity.class);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    /**
     * Let's Override attachBaseContext method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * Let's create our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.initializeWidgets();
        this.showSplashAnimation();
        this.goToDashboard();
    }

}
//end

```


### Step 5 : Create Dashboard Layout

#### (a). activity_dashboard.xml

Here is the code for our dashboard layout:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/holo_red_light"
    tools:context=".Views.DashboardActivity">
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/colapsingtoolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/cascaded_waterfall"
            app:contentScrim="@color/colorPrimary"
            app:expandedTitleMarginEnd="32dp"
            app:expandedTitleMarginStart="24dp"
            app:layout_scrollFlags="exitUntilCollapsed|scroll"
            app:title="Firebase CRUD">

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbarid"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/AlertDialog.AppCompat.Light" />


        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>
    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/lightgray"
            android:gravity="center"
            android:orientation="vertical"
            android:padding="10dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:clipToPadding="false"
                android:gravity="center">

                <androidx.cardview.widget.CardView
                    android:layout_width="160dp"
                    android:layout_height="190dp"
                    android:layout_margin="10dp"
                    android:clickable="true"
                    android:foreground="?android:attr/selectableItemBackground">

                    <LinearLayout
                        android:id="@+id/viewScientistsCard"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:gravity="center"
                        android:orientation="vertical">

                        <ImageView
                            android:layout_width="64dp"
                            android:layout_height="64dp"
                            android:background="@drawable/m_circle_bg_purple"
                            android:padding="10dp"
                            android:src="@drawable/m_list" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="10dp"
                            android:text="View All"
                            android:textStyle="bold" />


                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:gravity="center"
                            android:padding="5dp"
                            android:text="View all Scientists"
                            android:textColor="@android:color/darker_gray" />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>

                <androidx.cardview.widget.CardView
                    android:layout_width="160dp"
                    android:layout_height="190dp"
                    android:layout_margin="10dp"
                    android:clickable="true"
                    android:foreground="?android:attr/selectableItemBackground">

                    <LinearLayout
                        android:id="@+id/addScientistCard"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:gravity="center"
                        android:orientation="vertical">

                        <ImageView
                            android:layout_width="64dp"
                            android:layout_height="64dp"
                            android:background="@drawable/m_circle_bg_pink"
                            android:padding="10dp"
                            android:src="@drawable/m_add" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="10dp"
                            android:text="Add"
                            android:textStyle="bold" />


                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:gravity="center"
                            android:padding="5dp"
                            android:text="Add New Scientist to the Database"
                            android:textColor="@android:color/darker_gray" />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>
            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:clipToPadding="false"
                android:gravity="center"
                android:orientation="horizontal">

                <androidx.cardview.widget.CardView
                    android:layout_width="160dp"
                    android:layout_height="190dp"
                    android:layout_margin="10dp"
                    android:clickable="true"
                    android:foreground="?android:attr/selectableItemBackground">

                    <LinearLayout
                        android:id="@+id/third"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:gravity="center"
                        android:orientation="vertical">

                        <ImageView
                            android:layout_width="64dp"
                            android:layout_height="64dp"
                            android:background="@drawable/m_circle_bg_green"
                            android:padding="10dp"
                            android:src="@drawable/m_cloud_circle_black" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="10dp"
                            android:text="Another Item"
                            android:textStyle="bold" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:gravity="center"
                            android:padding="5dp"
                            android:text="You can connect another page here."
                            android:textColor="@android:color/darker_gray" />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>

                <androidx.cardview.widget.CardView
                    android:layout_width="160dp"
                    android:layout_height="190dp"
                    android:layout_margin="10dp"
                    android:clickable="true"
                    android:foreground="?android:attr/selectableItemBackground">

                    <LinearLayout
                        android:id="@+id/closeCard"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:gravity="center"
                        android:orientation="vertical">

                        <ImageView
                            android:layout_width="64dp"
                            android:layout_height="64dp"
                            android:background="@drawable/m_circle_bg_yello"
                            android:padding="10dp"
                            android:src="@drawable/m_icon_logout" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_marginTop="10dp"
                            android:text="Exit"
                            android:textStyle="bold" />


                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:gravity="center"
                            android:padding="5dp"
                            android:text="Exit the App. "
                            android:textColor="@android:color/darker_gray" />
                    </LinearLayout>
                </androidx.cardview.widget.CardView>
            </LinearLayout>


        </LinearLayout>
    </androidx.core.widget.NestedScrollView>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

You can see we are taking advantage of several material design elements like AppBar, CordinatorLayout, CollapsingToolbar etc.

We also use NestedScrollView and CardViews.

The above layout will be inflated into our Dashboard activity.

### Step 6: Write Dashboard Code

#### (a). Create Dashboard Class
Start by adding imports:
```java
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
```
Then create the class, making it derive from AppCompatActivity:
```java
public class DashboardActivity extends AppCompatActivity {
```

#### (b). Prepare our Dashboard Cards

These are the cards that when clicked will take us to other activities:
```java
    //We have 4 cards in the dashboard
    private LinearLayout viewScientistsCard;
    private LinearLayout addScientistCard;
    private LinearLayout third;
    private LinearLayout closeCard;
```
The CardViews will be wrapped by LinearLayouts.

We then initialize them:
```java
    private void initializeWidgets(){
        viewScientistsCard = findViewById(R.id.viewScientistsCard);
        addScientistCard = findViewById(R.id.addScientistCard);
        third = findViewById(R.id.third);
        closeCard = findViewById(R.id.closeCard);
```
Then listen to their click events, thus opening other activities:
```java
        viewScientistsCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        ScientistsActivity.class));
        addScientistCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        CRUDActivity.class));
        third.setOnClickListener(v -> Utils.showInfoDialog(DashboardActivity.this, "YEEES",
        "Hey You can Display another page when this is clicked"));
        closeCard.setOnClickListener(v -> finish());
    }
```

#### FULL CODE
##### (a). /Views/DashboardActivity.java
```java

package info.camposha.firebasedatabasecrud.Views;


import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    //We have 4 cards in the dashboard
    private LinearLayout viewScientistsCard;
    private LinearLayout addScientistCard;
    private LinearLayout third;
    private LinearLayout closeCard;

    /**
     * Let's initialize our cards  and listen to their click events
     */
    private void initializeWidgets(){
        viewScientistsCard = findViewById(R.id.viewScientistsCard);
        addScientistCard = findViewById(R.id.addScientistCard);
        third = findViewById(R.id.third);
        closeCard = findViewById(R.id.closeCard);

        viewScientistsCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        ScientistsActivity.class));
        addScientistCard.setOnClickListener(v -> Utils.openActivity(DashboardActivity.this,
        CRUDActivity.class));
        third.setOnClickListener(v -> Utils.showInfoDialog(DashboardActivity.this, "YEEES",
        "Hey You can Display another page when this is clicked"));
        closeCard.setOnClickListener(v -> finish());
    }
    /**
     * Let's override the attachBaseContext() method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * When the back button is pressed finish this activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * Let's override the onCreate() and call our initializeWidgets()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.initializeWidgets();
    }
}
//end
```

That's it. Now move over to the next lesson.