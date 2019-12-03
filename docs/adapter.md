## Our RecyclerView Adapter

It is time to come create our adapter class. This class is important since it allows us inflate custom views and bind data to them once it is applied to our [recyclerview](/android/recyclerview).

NOTE/= This lesson is part of a multi-episode on Firebase CRUD Full App Development Course. In the free course we are creating a full application with Firebase Realtime database backend.

This adapter will be used by the following classes:
1. FilterHelper - Class that allows us perform search and filtering of our firebase data.
2. ScientistActivity - The class where our recyclerview will be referenced and our adapter set.

### What our Adapter will do

This adapter class will have the following responsibilities.
1. Inflate our custom row model into view objects that recyclerview will recycle.
2. Receive a list of scientist objects and bind them to the inflated views.
3. Define a view holder class that will hold the widgets that have been inflated.
4. Return a `Filter` instance to allow us search our data.
5. Highlight our search results.

### Step 1: Create MyAdapter class
We start by adding our imports:
```java
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import info.camposha.firebasedatabasecrud.Helpers.FilterHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import info.camposha.firebasedatabasecrud.Views.DetailActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.searchString;
```

Then create our class:
```java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
implements Filterable {
```
You can see the class is extending the `RecyclerView.Adapter`. The generic parameter is a `MyAdapter.ViewHolder`, meaning a `ViewHolder` class defined in this same `MyAdapter` class.

Then we have made it implement the `Filterable` instance. This will help with our filtering our recyclerview data.

### Step 2: Define Instance Fields
```java
    private final Context c;
    private final int mBackground;
    private final int[] mMaterialColors;
    public List<Scientist> scientists;
    private List<Scientist> filterList;
    private FilterHelper filterHelper;
```
You can see we are defining objects and data types that will allow us hold different values. The Context object will allow us hold the received Context object. The background will represent our material letter icon background while the `mMaterialColors` is an array that will hold for us the different material colors we will load from color resource file.

The scientists list will hold the list of all scientists received via the constructor. The `filterList` will hold the filter results that need to be displayed in our recyclerview.

### Step 3: Define our ItemClickListener
It's just an interface containing an abstract method that will be our event handler:
```java
    interface ItemClickListener {
        void onItemClick(int pos);
    }

```

### Step 4: Create our ViewHolder class.
We are creating it as an inner class, meaning it's contained in our MyAdapter class:
```java
    public class ViewHolder extends RecyclerView.ViewHolder implements
     View.OnClickListener {
```
You can see it's deriving from RecyclerView.ViewHolder class and implementing the `View.OnClickListener`. Thus there are some other things that we will need to do later on.

However first let's define the class's instance fields:
```java
        private final TextView nameTxt;
        private final TextView mDescriptionTxt;
        private final TextView galaxyTxt;
        private final MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
```

The first of those things we promised is supply a constructor that:
1. Receives a View object
2. Passes that received view to the super class

```java
        ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nameTxt = itemView.findViewById(R.id.mNameTxt);
            mDescriptionTxt = itemView.findViewById(R.id.mDescriptionTxt);
            galaxyTxt = itemView.findViewById(R.id.mGalaxyTxt);
            itemView.setOnClickListener(this);
        }

```

The second is to implement the `onClick` method:
```java
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
```
Then finish up that ViewHolder class:
```java
        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
```

### Step 5: Create our MyAdapter Constructor
```java
    public MyAdapter(Context mContext, List<Scientist> scientists) {
        this.c = mContext;
        this.scientists = scientists;
        this.filterList = scientists;
        TypedValue mTypedValue = new TypedValue();
        c.getTheme().resolveAttribute(R.attr.selectableItemBackground,
         mTypedValue, true);
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }
```

### Step 6: Override our onCreateViewHolder
This is where we will inflate our custom row model:
```java
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

```
We've used the LayoutInflater class to inflate the model layout into a view object. We've then set the the background color of that view object using the `setBackgroundResource()` method, passing in our background integer. We've then returned that ViewHolder's instance, passing in the inflated view object to the constructor.

### Step 7: Override the onBindViewHolder
Add the following code:
```java
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
```
That onBindViewHolder is receiving two objects, a ViewHolder instance as well as an integer representing the position of the item that needs to be bound.

First we need to use that position to obtain the current scienist:
```java
        //get current scientist
        final Scientist s = scientists.get(position);
```
Then bind data to our widgets:
```java
        //bind data to widgets
        holder.nameTxt.setText(s.getName());
        holder.mDescriptionTxt.setText(s.getDescription());
        holder.galaxyTxt.setText(s.getGalaxy());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
            mMaterialColors.length)]);
        holder.mIcon.setLetter(s.getName());
```
 You can see above that we obtaining the properties of our scientist object and binding them to their respective textviews. As for the material letter icon we are setting it a random background color. The letter icons will be obtained from the name.

Now to set an alternating row background colors to our recyclerview all we need is the following code:
```java
        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }

```
We are checking if the position is divisible by 2 using the modulus operator. If so we set a light-greyish background color.

### Step 8: Highlighting Our Search Results
We said ouf Firebase App will be supporting search filter. Thus we first will be highlighting our search results.

In your onBindViewHolder add the followng code:
```java
        //get name and galaxy
        String name = s.getName().toLowerCase(Locale.getDefault());
        String galaxy = s.getGalaxy().toLowerCase(Locale.getDefault());
```
We've simply obtained our name and galaxy properties from our Scientist object and converted them to lowercase characters.
Those are the two fields against which we will be searching.

Then add the following code:
```java
        //highlight name text while searching
        if (name.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = name.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }
```
We are checking if our name contains our search string. If so we are using Spannable class to set a foreground color.

Then we do the same thing to our Galaxy with the following code:
```java
        //highligh galaxy text while searching
        if (galaxy.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = galaxy.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.galaxyTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.galaxyTxt.setText(spanString);
        }
```

### Step 9: Listening to RecyclerView Click Events
Add the following code:
```java
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Utils.sendScientistToActivity(c, s,
         DetailActivity.class));
    }

```
The above code listens to click events for our recyclerview item and and invokes a method called `sendScientistToActivity()` from our `Utils` class. We are opening a detail activity, in the process passing along the clicked Scientist object. We've used lambda expression above which requires Java8 enabled.

Also, return the number of scientists that will be bound to the recyclerview:
```java
    @Override
    public int getItemCount() {
        return scientists.size();
    }

```

### Step 10: Return a Filter object
Add the following code to finish our adapter:
```java
    @Override
    public Filter getFilter() {
        if(filterHelper==null){
            filterHelper=FilterHelper.newInstance(filterList,this);
        }
        return filterHelper;
    }
}
//end
```
In the above code we are returning a Filter instance. Filter is an abstract class defined in the `android.widget` package that allows us perform filtering and publish results. The above method is being overriden since earlier on we had implemented the `Filterable` interface.

We are returning a `FilterHelper` instance since our FilterHelper class is inheriting from the `android.widget.Filter` class.


### FULL CODE
(a). /Data/MyAdapter.java
Our MyAdapter class is contained in the Data package of our project.
Here is the full code:
```java
package info.camposha.firebasedatabasecrud.Data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import info.camposha.firebasedatabasecrud.Helpers.FilterHelper;
import info.camposha.firebasedatabasecrud.Helpers.Utils;
import info.camposha.firebasedatabasecrud.R;
import info.camposha.firebasedatabasecrud.Views.DetailActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.searchString;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
implements Filterable {

    private final Context c;
    private final int mBackground;
    private final int[] mMaterialColors;
    public List<Scientist> scientists;
    private List<Scientist> filterList;
    private FilterHelper filterHelper;

    interface ItemClickListener {
        void onItemClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
     View.OnClickListener {
        private final TextView nameTxt;
        private final TextView mDescriptionTxt;
        private final TextView galaxyTxt;
        private final MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nameTxt = itemView.findViewById(R.id.mNameTxt);
            mDescriptionTxt = itemView.findViewById(R.id.mDescriptionTxt);
            galaxyTxt = itemView.findViewById(R.id.mGalaxyTxt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
    public MyAdapter(Context mContext, List<Scientist> scientists) {
        this.c = mContext;
        this.scientists = scientists;
        this.filterList = scientists;
        TypedValue mTypedValue = new TypedValue();
        c.getTheme().resolveAttribute(R.attr.selectableItemBackground,
         mTypedValue, true);
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final Scientist s = scientists.get(position);

        //bind data to widgets
        holder.nameTxt.setText(s.getName());
        holder.mDescriptionTxt.setText(s.getDescription());
        holder.galaxyTxt.setText(s.getGalaxy());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
            mMaterialColors.length)]);
        holder.mIcon.setLetter(s.getName());

        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }

        //get name and galaxy
        String name = s.getName().toLowerCase(Locale.getDefault());
        String galaxy = s.getGalaxy().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (name.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = name.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (galaxy.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = galaxy.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.galaxyTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.galaxyTxt.setText(spanString);
        }

        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Utils.sendScientistToActivity(c, s,
         DetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return scientists.size();
    }

    @Override
    public Filter getFilter() {
        if(filterHelper==null){
            filterHelper=FilterHelper.newInstance(filterList,this);
        }
        return filterHelper;
    }
}
//end
```

Now move over to the next lesson.