## Firebase RecyclerView and Detail Activities

In this lesson we will explore two important activities, listing or recyclerview activity as well as the Detail Activities. These are data rendering activities. They allow us show our Firebase data to the user. The recyclerview activity will render a list of firebase data using recyclerview. The detail activity will show the details for a single item. It will be shown when a single recyclerview item is clicked.

NOTE/= This lesson is part of a multi-series course we are currently covering on Firebase Realtime Database CRUD. In the course we are seing how to develop a full android application based on Firebase Realtime Database. The full app will support adding of data, fetching of data, updating of data as well as deleting data.

### How our RecyclerView Activity works.
We are working with `Scientist` as our data object. The RecyclerView activity is our listing activity. It will be called the `ScientistsActivity`. It's role is display data fetched from Firebase Realtime database in a recyclerview. The recyclerview will have alternating background colors.

The recyclerview will work with the adapter which is responsible for binding data to it as well as inflating our custom layout. The adapter will be set to the recyclerview using the `setAdapter()` method.

Every row in the recyclerview will comprise:
1. MaterialLetter Icon - To display letter icons extracted from name.
2. TextViews - To display name, description and galaxy of the scientist.

When a recyclerview row is clicked, we will open the detail activity, passing along the clicked scientist object.

The recyclerview shall be searchable via the toolbar. We will render a toolbar searchview which will allow our users to search filter our firebase data. The search and filter will be disconnected and offline.

### How the Detail Activity works
The detail activity is a static activity in that it doesn't do any processing or make any calls to Firebase. Instead it only renders the detail for our Scientist object. It is very important however because it provides us with a way of showing the user individual details of our scientist object without giving the user capability to edit.

However if the user intends to edit then he can click a button or menu item and move to the editing page.

The detail activity will be have some of the following components:
1. CollapsingToolBar
2. Floating ActionButton
3. CardViews
4. TextViews etc

### Step 1: Create Detail Activity Layout

#### (a). activity_detail.xml
This is the layout that will be inflated into our Detail Activity. It will content another layout called `detail_content.xml`. Here is the code for the `activity_detail.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:background="@color/colorPrimary"
    tools:context=".Views.DetailActivity">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/mCollapsingToolbarLayout"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMarginEnd="32dp"
            app:expandedTitleMarginStart="24dp"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <ImageView
                android:id="@+id/scientistImg"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fitsSystemWindows="true"
                android:scaleType="fitXY"
                android:src="@drawable/m_cactus"
                app:layout_collapseMode="parallax" />

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="@android:color/transparent"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light" />
            <com.google.android.material.floatingactionbutton.FloatingActionButton
                android:id="@+id/editFAB"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="top|end"
                android:layout_margin="16dp"
                android:src="@android:drawable/ic_menu_edit"
                android:tint="@android:color/white" />
        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <include layout="@layout/detail_content" />



        </LinearLayout>

    </androidx.core.widget.NestedScrollView>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

You can see we have used several material layouts like:
1. CoordinatorLayout
2. NestedScrollView
3. AppBarLayout
4. ToolBar
5. CollapsingToolBar

#### (b). detail_content.xml

We had said earlier that the `activity_detail.xml` would contain the `detail_content.xml`. Well here is the code for the detail_content.xml :

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="8dp">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="5dp"
                    android:text="Personal Details"
                    android:textAlignment="center"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"
                    android:textColor="@color/niceGreenish"
                    android:textStyle="italic" />


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
                            android:text="NAME"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/nameTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="ALBERT EINSTEIN" />

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
                            android:text="DESCRIPTION"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/descriptionTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="This is the description." />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>


                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="5dp"
                    android:text="Residency"
                    android:textAlignment="center"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"
                    android:textColor="@color/niceGreenish"
                    android:textStyle="italic" />


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
                            android:text="GALAXY"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/galaxyTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="Galaxy" />

                        <TextView
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="STAR"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/starTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="Star" />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>


                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="5dp"
                    android:text="Period"
                    android:textAlignment="center"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"
                    android:textColor="@color/niceGreenish"
                    android:textStyle="italic" />

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
                            android:text="Date Of Birth"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/dobTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="DOB" />
                        <TextView
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="Date Of Death"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/dodTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="DOD" />

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
                            android:text="CONTRIBUTIONS"
                            android:textAppearance="@style/TextAppearance.AppCompat.Title"
                            android:textColor="@color/colorAccent" />

                        <TextView
                            android:id="@+id/contributionTV"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:padding="@dimen/text_padding"
                            android:text="This Scientist is included in this list because of the huge contributions he made to Science." />

                    </LinearLayout>

                </androidx.cardview.widget.CardView>


            </LinearLayout>
        </LinearLayout>
    </ScrollView>

</androidx.cardview.widget.CardView>

```

The detail content as you can see contains:

|No.|Element|Role|
|---|-------|----|
|1.|CardView| To group our textviews.|
|2.|TextView | To render our scientist properties.|
|3.| LinearLayout | To align our cardviews and textviews vertically. |
|4.| ScrollView | To provide scrolling capability in our page. |

### Step 2: Write Detail Activity Code

#### (a). DetailActivity.java
The main role of this class will be to render the details of our scientist.

First add your imports:
```java
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
```

Then create the class, making it extend the AppCompatActivity:
```java
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
```
As you can see we've also implemented the `View.OnClickListener` interface. Thus we will override one method called `onClick` later on.

For now let's add our instance fields:
```java
    private TextView nameTV,descriptionTV,galaxyTV,starTV,dobTV,diedTV;
    private Scientist receivedScientist;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton editFAB;

```

Here are the  roles of the above instance fields:

|No.|Object|Role|
|---|------|----|
|1.|TextViews| Will render properties of our scientist like name,description etc.|
|2.|receievedScientist | Will hold the scientist object for which we are showing details.|
|3.| mCollapsingToolbarLayout | We will show a custom title in our collapsingtoolbarlayout.|
|4.| FloatingActionButton | When clicked, will open our editing page. |

Let's now proceed and initialize those widgets:
```java
    private void initializeWidgets(){
        nameTV= findViewById(R.id.nameTV);
        descriptionTV= findViewById(R.id.descriptionTV);
        galaxyTV= findViewById(R.id.galaxyTV);
        starTV= findViewById(R.id.starTV);
        dobTV= findViewById(R.id.dobTV);
        diedTV= findViewById(R.id.dodTV);
        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                getColor(R.color.white));
    }
```

After initializing the widgets we can now receive our scientist object:
```java
    private void receiveAndShowData(){
         receivedScientist= Utils.receiveScientist(getIntent(),DetailActivity.this);

```
Then show the properties in textviews:
```java
         if(receivedScientist != null){
             nameTV.setText(receivedScientist.getName());
             descriptionTV.setText(receivedScientist.getDescription());
             galaxyTV.setText(receivedScientist.getGalaxy());
             starTV.setText(receivedScientist.getStar());
             dobTV.setText(receivedScientist.getDob());
             diedTV.setText(receivedScientist.getDod());

             mCollapsingToolbarLayout.setTitle(receivedScientist.getName());
         }

    }
```

We will also override our onClick method as we had promised:
```java
    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Utils.sendScientistToActivity(this,receivedScientist,CRUDActivity.class);
            finish();
        }
    }
```

#### FULL CODE
Here is the full code for detail activity:
```java
package info.camposha.firebasedatabasecrud.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameTV,descriptionTV,galaxyTV,starTV,dobTV,diedTV;
    private Scientist receivedScientist;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton editFAB;

    private void initializeWidgets(){
        nameTV= findViewById(R.id.nameTV);
        descriptionTV= findViewById(R.id.descriptionTV);
        galaxyTV= findViewById(R.id.galaxyTV);
        starTV= findViewById(R.id.starTV);
        dobTV= findViewById(R.id.dobTV);
        diedTV= findViewById(R.id.dodTV);
        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                getColor(R.color.white));
    }
    private void receiveAndShowData(){
         receivedScientist= Utils.receiveScientist(getIntent(),DetailActivity.this);

         if(receivedScientist != null){
             nameTV.setText(receivedScientist.getName());
             descriptionTV.setText(receivedScientist.getDescription());
             galaxyTV.setText(receivedScientist.getGalaxy());
             starTV.setText(receivedScientist.getStar());
             dobTV.setText(receivedScientist.getDob());
             diedTV.setText(receivedScientist.getDod());

             mCollapsingToolbarLayout.setTitle(receivedScientist.getName());
         }

    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            Utils.sendScientistToActivity(this,receivedScientist,CRUDActivity.class);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_edit:
                Utils.sendScientistToActivity(this,receivedScientist,CRUDActivity.class);
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeWidgets();
        receiveAndShowData();
    }
}
//end


```

### Step 3 : Create Listing Activity Layout.

#### activity_scientists.xml
This will be the layout for our ScientistsActivity which is our listing activity:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".Views.ScientistsActivity">

    <ProgressBar
        android:id="@+id/mProgressBarLoad"
        style="?android:attr/progressBarStyleHorizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:indeterminate="true"
        android:indeterminateBehavior="cycle"
        android:visibility="gone" />


    <TextView
        android:id="@+id/mHeaderTxt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="5dp"
        android:text="Pioneer Scientists"
        android:textAlignment="center"
        android:textAppearance="@style/TextAppearance.AppCompat.Large"
        android:textColor="@color/colorAccent"
        android:textStyle="bold"
        tools:ignore="MissingPrefix" />


        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/mRecyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />


</LinearLayout>
```

Here are the widgets we've used abobe:

|No.|Widget|Role|
|---|------|----|
|1.|LinearLayout | To arrange its children vertically |
|2.|TextView | To show our page header or title |
|3. |RecyclerView. | Our most important widget in this layout. Will show render our list of data from Firebase.|


### Step 4: Listing Activity Code
#### (a). ScientistsActivity.java
This is our listing activity. Start by adding imports:
```java
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Helpers.FirebaseCRUDHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
```

Then create the class, making it implement some interfaces:
```java
public class ScientistsActivity extends AppCompatActivity  implements
SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {
```
Among the interfaces we've implemented include the `OnQueryTextListener` defined in the SearchView class. We will implement their methods later on.

For now let's define instance fields for this class:
```java
    private RecyclerView rv;
    public ProgressBar mProgressBar;
    private FirebaseCRUDHelper crudHelper=new FirebaseCRUDHelper();
    private LinearLayoutManager layoutManager;
    MyAdapter adapter;
```

Here are the roles of those instance fields:

|No.|Object|Role|
|---|------|----|
|1.|RecyclerView | Will render our firebase data in a list. |
|2.|ProgressBar | Will show progress bar as we download our firebase data. |
|3.| FirebaseCRUDHelper | It's instance will allow us invoke helper crud methods we had already defined, like the method for fetching firebase data.|
|4.| LinearLayoutManager | It's instance will allow us position our recyclerview cardviews. |
|5.|Adapter | It will bind data to our recyclerview |

We then proceed to initialize widgets as well as other objects:
```java
    private void initializeViews(){
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Utils.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        adapter=new MyAdapter(this,Utils.DataCache);
        rv.setAdapter(adapter);


    }
```
Among them as you can see above are LinearLayoutManager and DividerItemDecoration. We set the LinearLayoutManager to our recyclerview using the `setLayoutManager` method and DividerItemDecoration using the `addItemDecoration` method.

Then to download and bind downloaded data to our recyclerview all we need is the following method:
```java
    private void bindData(){
        crudHelper.select(this,Utils.getDatabaseRefence(),mProgressBar,rv,adapter);
    }
```

We will then override several methods. Among them is:
```java
    @Override
    public boolean onQueryTextChange(String query) {
        Utils.searchString=query;
        MyAdapter adapter=new MyAdapter(this,Utils.DataCache);
        adapter.getFilter().filter(query);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        return false;
    }
```
The `onQueryTextChange`. The above callback is invoked when a user types in our searchview. The first thing we will do is set the query to a searchString variable. That allows us hold the query in a public variable that we will be able to invoke from anywhere including the `MyAdapter` class.

We then instantiate our MyAdapter passing in the Context as well as the DataCache. That DataCache is an arraylist that will cache our data in memory. We will be performing our search and filter against that arraylist.

Take note that we are also passing in the query to `filter` method. That query will find its way in our `FilterHelper` class where we are performing our actual filtering.

#### FULL CODE
Here is the full code for `ScientistsActivity.java`:
```java
package info.camposha.firebasedatabasecrud.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Helpers.FirebaseCRUDHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class ScientistsActivity extends AppCompatActivity  implements
SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {

    private RecyclerView rv;
    public ProgressBar mProgressBar;
    private FirebaseCRUDHelper crudHelper=new FirebaseCRUDHelper();
    private LinearLayoutManager layoutManager;
    MyAdapter adapter;

    private void initializeViews(){
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        Utils.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        adapter=new MyAdapter(this,Utils.DataCache);
        rv.setAdapter(adapter);


    }

    private void bindData(){
        crudHelper.select(this,Utils.getDatabaseRefence(),mProgressBar,rv,adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.scientists_page_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_new:
                Utils.sendScientistToActivity(this,null,CRUDActivity.class);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Utils.searchString=query;
        MyAdapter adapter=new MyAdapter(this,Utils.DataCache);
        adapter.getFilter().filter(query);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientists);

        initializeViews();
        bindData();
    }

}
//end

```

That's it. Now proceed over to the next lesson.