## Clientside Filtering of Firebase Data

In this lesson we will learn how to filter Firebase data on the clientside. Clientside filtering is an in-memory search filter. This means we hold our data in a data structure like an arraylist and then filter from there.

NOTE/= This class is part of multi-series course we are doing right here at Camposha about FirebaseCRUD. See the course contents on the sidebar.

This is a very fast search because we don't need to make any connections to the database or online server. However this type of search is only suitable for small datasets. You don't want to be holding tens or thousands of rows of data statically throughout the lifetime of your app.

In that type of scenario you would need to perform a server side search.

### How our search will work.

1. We download Firebase Data once the first time the user visits our Listing page.
2. We hold the data statically. Rather than holding it as an instance field,we hold it as a class member. Thus the list is not garbage collected.
3. We search against that list.

### Components Required For our Search
Here are the components required to search:

|No.|Widget|Description|
|--|-------|-----------|
|1. [SearchView](/android/searchview) | It makes sense since we need a widget for entering our search terms.SearchView is required since it allows us to hook up to various events like textChanged event.|
|2.|[RecyclerView](/android/recyclerview)|This is the widget that displays or lists all our data. It also displays the search results.|

### Other Required Classes
Because this is part of a full project, here are the classes directly needed for the code here to work
1. MyAdapter - Our adapter. Will be responsible for binding search results to the recyclerview. Will also highlight our search results.
2. ScientistsActivity - Responsible of referencing our recyclerview, setting its adapter, listening to searchview events and reacting to invoke the search.

### Steps 1 - Create a FilterHelper Class

Create a FilterHelper class that extends the `android.widget.Filter`:
```java
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;


public class FilterHelper extends Filter {
```
As you can see we have provided the necessary imports including our MyAdapter as well as our model class. The class is extending the `Filter` class, which is abstract. Thus we will be required to override two classes we will talk about in a short while.

### Step 2 : Define our Class members
```java
    static List<Scientist> currentList;
    static MyAdapter adapter;
```
We then create two class members:
1. currentList - The current list that should be filtered.
2. adapter - The adapter instance which will be used for two purposes. First to pass our search results back to the MyAdapter class and secondly to the MyAdapter classes of changes in our dataset.

### Step 3 : Perform our Actual Filtering
We now come and override the first of the two methods we promised. That method is the `performFiltering()` method. It will take one parameter:
1. constraint - A CharSequence representing our search term or constraint.

So overrie the method:
```java
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
```
Then instantiate the `FilterResults` class:
```java
        FilterResults filterResults=new FilterResults();
```
That class will hold our filter results.

Then ensure our Constraint is not null:
```java
        if(constraint != null && constraint.length()>0)
        {
```
Turn that constraint into an uppercase(or lowercase) to ensure consistency:
```java
            constraint=constraint.toString().toUpperCase();
```
Create an arraylist that will hold our found filters, or rather our search results:
```java
            ArrayList<Scientist> foundFilters=new ArrayList<>();
```
Then filter:
```java
            String galaxy,name,star,description;

            //ITERATE CURRENT LIST
            for (int i=0;i<currentList.size();i++)
            {
                galaxy= currentList.get(i).getGalaxy();
                name= currentList.get(i).getName();

                //SEARCH
                if(galaxy.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }else if(name.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }
            }


```

Now set our results to our filter list:
```java
//SET RESULTS TO FILTER LIST
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
```
Otherwise if no search hit was made:
```java
}else
        {
            //NO ITEM FOUND.LIST REMAINS INTACT
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }
```
Finally we return our results:
```java
        //RETURN RESULTS
        return filterResults;
    }
```

### Step : Publishing Search Results
The last step is to publish search results back to the adapter. It is the adapter responsibility to bind data to recyclerview thus we have to pass it our search results for them to be rendered:
```java
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.scientists= (ArrayList<Scientist>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
//end
```

### Full Code

#### /Helpers/FilterHelper.java
```java
package info.camposha.firebasedatabasecrud.Helpers;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;


public class FilterHelper extends Filter {
    static List<Scientist> currentList;
    static MyAdapter adapter;
    public static FilterHelper newInstance(List<Scientist> currentList, MyAdapter adapter) {
        FilterHelper.adapter=adapter;
        FilterHelper.currentList=currentList;
        return new FilterHelper();
    }
    /*
    - Perform actual filtering.
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();

        if(constraint != null && constraint.length()>0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();

            //HOLD FILTERS WE FIND
            ArrayList<Scientist> foundFilters=new ArrayList<>();

            String galaxy,name,star,description;

            //ITERATE CURRENT LIST
            for (int i=0;i<currentList.size();i++)
            {
                galaxy= currentList.get(i).getGalaxy();
                name= currentList.get(i).getName();

                //SEARCH
                if(galaxy.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }else if(name.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }
            }

            //SET RESULTS TO FILTER LIST
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
        }else
        {
            //NO ITEM FOUND.LIST REMAINS INTACT
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }

        //RETURN RESULTS
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.scientists= (ArrayList<Scientist>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
//end


```

The lesson is part of a full project course. Please follow the course to see its usage, specifically our MyAdapter and ScientistsActivity classes.

### Conclusion

We have seen how to perform a search and filtering agains Firebase data. And we've said it is a client side search. This means we obtain all our data and hold statically and search against it. This means our search is fast but suitable for small datasets.

Now move over to the next lesson in this series.