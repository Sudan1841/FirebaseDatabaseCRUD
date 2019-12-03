## ADD READ UPDATE DELETE

In this lesson we will write re-usable methods that will allow us to perform full CRUD operations against Firebase Realtime Database. Firebase Realtime Database we had said is a cloud-based realtime database by Google that allows us to store text content in json format.

The stored data can then be synced across different devices. To work with any database or storage service, you need to be able to perform CRUD. CRUD stands for:
1. C - Create or Insert.
2. R - Read or Select.
3. U - Update or Edit
4. D - Delete or Remove

These are the basic operations you need to work on any data. And normally these are the operations you need to create a full application like we are doing in this course.

NOTE/= This lesson is part of our Android Firebase Full CRUD course. We recommend you take the full course, it's free. However you can still follow and use the code here without taking the course.

### Why CRUD?
CRUD we have said stands for Create Read Update and Delete. We need to do these because they are what you need to create a full application. You will always need to persist your data in a database, read that data, update it when necessary and delete it when not required.

### Why Firebase Realtime Database
Firebase Realtime Database is Google's flagship cloud-based database. Firebase realtime database allows us save data in the cloud and sync the data across a variety of devices, no matter the platform.

It also offers offline capability, persisting data on the disk thus we don't haven't to connected at all times to view our data.

### What We are doing

In this lesson we will write instance methods that will allow us perform CRUD operations. The methods once defined can be called from anywhere within our app. The only requirement is that we pass the appropriate parameters.

Writing methods in a seperate class provides us with re-usability thus avoiding duplications throughout our app.

### Step 1 : Create FirebaseCRUDHelper class
Start by adding imports in our `/Helpers/FirebaseCRUDHelper.java`:
```java
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Views.ScientistsActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.DataCache;

```
Then define the class:
```java
public class FirebaseCRUDHelper {

```

### Step 2: How to add/insert into Firebase
Let's create a method that allows us to insert or add data to Firebase, let's call the method `insert()`:
```java
    public void insert(final AppCompatActivity a,
                          final DatabaseReference mDatabaseRef,
                          final ProgressBar pb, final Scientist scientist) {
```
You can see we are providing the following parameters:

|No.|Parameter|Role|
|---|---------|----|
|1.|[AppCompatActivity](/android/appcompatactivity)|We will use this to provide our context. Context is required when showing our info [dialog](/android/dialog) and opening another [activity](/android/activity).|
|2.|DatabaseReference|This is the pointer to our Firebase Realtime Database.It points us to the json node representing our database.|
|3.|[ProgressBar](/android/progressbar)|This will allow us to show user progress feedback as we post our data to Firebase.|
|4.|Scientist|This is the data object we want to post to Firebase Database.|

The first step is to validate our data object:
```java
        if (scientist == null) {
            Utils.showInfoDialog(a,"VALIDATION FAILED","Scientist is null");
            return;
        }
```
As you can see if the scientist is null then we show an error dialog then exit.

Otherwise we start by showing our progressbar:
```java
                Utils.showProgressBar(pb);
```

Then:
```java
            mDatabaseRef.child("Scientists").push().setValue(scientist).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.hideProgressBar(pb);

                        if(task.isSuccessful()){
                            Utils.openActivity(a, ScientistsActivity.class);
                            Utils.show(a,"Congrats! INSERT SUCCESSFUL");
                        }else{
                            Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                            getMessage());
                        }
                    }

                });
```
We have invoked the `child()` method of our FirebaseDatabase instance. In that method we have passed `Scientists`, think of this as the name of the json node that will store our scientists. Or think of it as our table name. Then we invoke the `push()` method which is responsible for pushing our data and returning a key. We have also invoked the`setValue()`. Thie is the method that sets the data that we want to push to Firebase Realtime database. We have passed a full object. The only requirement for that object to be processed by Firebase is to have an empty constructor. Firebase will generate fields based on the properties of the data object.

Obviously we need to follow the progress of the operation. Thus we add a completion listener event handler. This will tell us when the operation is complete so that we can react to our result. In this case we hide our progressbar. Then we check if the task was successful using the `isSuccessful()` method. if we were successful we open a listing activity where our data will be loaded. If we encountered a failure we will show a dialog with the exception message. That exception we get it from the `getException()` of our `Task` class. We show it's message by invoking the `getMessage()` method.

### Step 2: How to read/select/retrieve From Firebase

In the second step we see how to read or select data from Firebase Realtime database. So we define the method:
```java
    public void select(final AppCompatActivity a, DatabaseReference db,
                                         final ProgressBar pb,
                                         final RecyclerView rv,MyAdapter adapter) {
```
This time round we have two new parameters being passed into the method:

|No.|Parameter|Role|
|---|---------|----|
|1.|[RecyclerView](/android/recyclerview) |Our adapterview. Will be used to render our Firebase data.|
|2.|MyAdapter| Our recyclerview adapter. Will be used to inflate custom recyclerview layouts and bind data to them.|

We start by showing our progressbar:
```java
        Utils.showProgressBar(pb);
```
We use our database reference to invoke the child method, passing our data location:
```java
        db.child("Scientists")
```
Then attach the ValueEventListener:
```java
 db.child("Scientists").addValueEventListener(new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //.....proceed here
```

You can see we are passing in an annonymous class to our ValueEventListener. Then we override the `onDataChange` which gets raised when our data changes. The onDataChange method takes in a DataSnapshot object which gives us a snapshot of our data. Now all we need to do is to work with that DataSnapshot.

However first we are going to clear our memory cache:
```java
            DataCache.clear();
```
That DataCache object will be our memory cache. Basically it will cache our data in memory throughout the lifetime of our application. Thus we don't need to make calls to our Firebase realtime database every now and then unless we want for example when we make an edit. However be aware that Firebase Realtime database also provides a simple caching of our data offline. However we have no control of that cache. For example we are not able to search it without making calls to the cloud. That's why we have our DataCache. All our searches will be against this DataCache, which is simply an ArrayList object.

Once our cache has been cleared we will check if we have any data in our DataSnapshot:
```java
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    //...continue

```
You can see we use the `exists()` method to check if our DataSnapshot actually exists. Then we've counted it's children using the `getChildrenCount()` method and then compared it to zero.

If the above comparison returns true: we proceed to process our data:
```java
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Scientist scientist = ds.getValue(Scientist.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }
```
We've looped through our DataSnapshot's children, obtaining the current DataSnapshot object. We've then proceeded and obtained the `Scientist` object using the `getValue()` method.

Then obtained the key for that DataSnapshot and assigned it to our `Scientist` object.

Then we've added the scientist to our DataCache.

Then we notify our adapter that it's data source has changed:
```java
                    adapter.notifyDataSetChanged();
```
Then we hide our progressbar and smoothscroll our recyclerview to the last added Firebase data:
```java
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.hideProgressBar(pb);
                            rv.smoothScrollToPosition(DataCache.size());
                        }
                    });
```

When the onCancelled method is raised as we attempt to fetch our data, we will show the exception message in a dialog:
```java
@Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                Utils.hideProgressBar(pb);
                Utils.showInfoDialog(a,"CANCELLED",databaseError.getMessage());
            }
```

### Step 3: How to Update/Edit Firebase Data

Let's now come and see how we can update Firebase Realtime Database data. Update means you already have an existing data and you want to make changes. The important part for you to be able to update is to have the key for the node you want to update. Say you want to update a Scientist object, well you need to have the key for that scientist. Well but how do get that key?

You need to retrieve that key when fetching/reading/selecting your data. If you go back to the previous step where we were selecting data you will see that we were getting our key from our DataSnaptsshot object. Here's how we did it:
```java
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Scientist scientist = ds.getValue(Scientist.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }
```
You can see the `ds.getKey()` which is giving us a key object that then we attach to our Scientist object. So when designing your model class you need to accomodate a key field. However in your accessor methods you make sure you exclude it from being sent to Firebase:
```java
public class Scientist{
    //more fields
    private String key;

     @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
```
You exclude it using the `exclude` annotation defined in `com.google.firebase.database` package.

Now let's proceed and create the update method:
```java
    public void update(final AppCompatActivity a,
                       final DatabaseReference mDatabaseRef,
                       final ProgressBar pb,
                       final Scientist oldScientist,
                       final Scientist newScientist) {
```

You can see that now we are passing two Scientist objects:
1. oldScientist - Scientist before being updated.
2. newScientist - Scientist after updating.

It is the same same object we pass those two so that we can obtain old and new data easily.

We start by checking if we have an object to update in the first place:
```java
            if(oldScientist == null){
				Utils.showInfoDialog(a,"VALIDATION FAILED","Old Scientist is null");
				return;
			}
```
Then as usual show our progressbar:
```java
        Utils.showProgressBar(pb);
```
Then:
```java
            mDatabaseRef.child("Scientists").child(oldScientist.getKey()).setValue(
                newScientist)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Utils.hideProgressBar(pb);

                            if(task.isSuccessful()){
                                Utils.show(a, oldScientist.getName() + " Update Successful.");
                                Utils.openActivity(a, ScientistsActivity.class);
                            }else {
                                Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                                getMessage());
                            }
                        }
                    });
```
Look at what we've done. First we've referenced the node to be updated which is our Scientists node. This will contain a list of scientists. Then we've invoked another `child()` method. This now gives us a particular scientist provided we supply the key as we've done. Then we simply invoke the setValue() method,passing in the updated scientist object.

As usual we are listening to completion then checking for success of the task using the `isSuccessful()` method.

That's it we've updated our data.

### Step 4: How to delete/remove Firebase Data

Let's now come and see how to delete Firebase Realtime data. Deleting is important because it allows you to free up space for more objects. Again, the most important step in deletion is to have a key for the scientist you intend to delete. Without that key you won't know which node to delete.

The key was set when retrieving our data.

First let's create the method to delete:
```java
    public void delete(final AppCompatActivity a, final DatabaseReference mDatabaseRef,
                       final ProgressBar pb, final Scientist selectedScientist) {
```
That method, among other parameters is taking a scientist object we've called selectedScientist. It is the data object you want to delete from Firebase.

We start by showing a progressbar:
```java
        Utils.showProgressBar(pb);
```

Then we obtain the key for that scientist:
```java
        final String selectedScientistKey = selectedScientist.getKey();
```

Here's how you delete from Firebase:
```java
    mDatabaseRef.child("Scientists").child(selectedScientistKey).removeValue().
        addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Utils.hideProgressBar(pb);

                if(task.isSuccessful()){
                    Utils.show(a, selectedScientist.getName() + " Successfully Deleted.");
                    Utils.openActivity(a, ScientistsActivity.class);
                }else{
                    Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().getMessage());
                }
            }
        });
```
We've referenced our root node containing all our Scientists. Then passed the key to identify the one we want to delete. To delete it we simply call the `removeValue()` method. We then listen to completion events and react to them appropriately.

### FULL CODE

#### (a). /Helpers/FirebaseCRUDHelper.java
```java
package info.camposha.firebasedatabasecrud.Helpers;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.camposha.firebasedatabasecrud.Data.MyAdapter;
import info.camposha.firebasedatabasecrud.Data.Scientist;
import info.camposha.firebasedatabasecrud.Views.ScientistsActivity;

import static info.camposha.firebasedatabasecrud.Helpers.Utils.DataCache;

public class FirebaseCRUDHelper {

    public void insert(final AppCompatActivity a,
                          final DatabaseReference mDatabaseRef,
                          final ProgressBar pb, final Scientist scientist) {
        //check if they have passed us a valid scientist. If so then return false.
        if (scientist == null) {
            Utils.showInfoDialog(a,"VALIDATION FAILED","Scientist is null");
            return;
        } else {
            //otherwise try to push data to firebase database.
                Utils.showProgressBar(pb);
                //push data to FirebaseDatabase. Table or Child called Scientist will be
                // created.
                mDatabaseRef.child("Scientists").push().setValue(scientist).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.hideProgressBar(pb);

                        if(task.isSuccessful()){
                            Utils.openActivity(a, ScientistsActivity.class);
                            Utils.show(a,"Congrats! INSERT SUCCESSFUL");
                        }else{
                            Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                            getMessage());
                        }
                    }

                });
        }
    }

    public void select(final AppCompatActivity a, DatabaseReference db,
                                         final ProgressBar pb,
                                         final RecyclerView rv,MyAdapter adapter) {
        Utils.showProgressBar(pb);

        db.child("Scientists").addValueEventListener(new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Scientist scientist = ds.getValue(Scientist.class);
                        scientist.setKey(ds.getKey());
                        DataCache.add(scientist);
                    }

                    adapter.notifyDataSetChanged();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Utils.hideProgressBar(pb);
                            rv.smoothScrollToPosition(DataCache.size());
                        }
                    });
                }else {
                    Utils.show(a,"No more item found");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                Utils.hideProgressBar(pb);
                Utils.showInfoDialog(a,"CANCELLED",databaseError.getMessage());
            }
        });
    }

    public void update(final AppCompatActivity a,
                       final DatabaseReference mDatabaseRef,
                       final ProgressBar pb,
                       final Scientist oldScientist,
                       final Scientist newScientist) {

			if(oldScientist == null){
				Utils.showInfoDialog(a,"VALIDATION FAILED","Old Scientist is null");
				return;
			}

        Utils.showProgressBar(pb);
            mDatabaseRef.child("Scientists").child(oldScientist.getKey()).setValue(
                newScientist)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Utils.hideProgressBar(pb);

                            if(task.isSuccessful()){
                                Utils.show(a, oldScientist.getName() + " Update Successful.");
                                Utils.openActivity(a, ScientistsActivity.class);
                            }else {
                                Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                                getMessage());
                            }
                        }
                    });
    }

    public void delete(final AppCompatActivity a, final DatabaseReference mDatabaseRef,
                       final ProgressBar pb, final Scientist selectedScientist) {
        Utils.showProgressBar(pb);
        final String selectedScientistKey = selectedScientist.getKey();
        mDatabaseRef.child("Scientists").child(selectedScientistKey).removeValue().
        addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Utils.hideProgressBar(pb);

                if(task.isSuccessful()){
                    Utils.show(a, selectedScientist.getName() + " Successfully Deleted.");
                    Utils.openActivity(a, ScientistsActivity.class);
                }else{
                    Utils.showInfoDialog(a,"UNSUCCESSFUL",task.getException().getMessage());
                }
            }
        });

    }
}
//end


```

### Conclusion

We have seen how perform all the CRUD operations in Firebase Realtime Database. We've said the code has been written as part of a full app development free course that you can take. However we've also said that you can incorportate the above code easily in your code. All you need do is copy it into your project, then invoke the method you want and pass it the required parameters.

Now proceed to the next lesson.
