## Our Model Class

A Model class is a data object or business object class. It defines the entity we want to work with as the core of what our application is about. For example in this case we are creating Scientists CRUD App. This means we will adding,updating, reading and deleting Scientist objects to and from our Firebase Realtime Database. Thus our model class will `Scientist` class.

NOTE/= This class is part of a multi-episode course on Firebase CRUD. In the course we are developing a full android application with full CRUD capability.

That class will define properties for a single Scientist. And we obtain that single scientist by instantiating that class. The only requirement of our model class for it to be readable by Firebase is that it should have atleats an empty constructor. By readable by Firebase we mean that we will pass that class's instance to Firebase's `setValue()` method to save our data.

For example consider the below example:
```java
mDatabaseRef.child("Scientists").push().setValue(scientist)
```
In the above code we are passing a whole Scientist object to the `setValue()` method to be persisted in Firebase . The class defining that `scientist` must have an empty constructor.

### Step 1: Create Scientist Class
```java
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Scientist
```

Then make it implement the `java.io.Serializable` interface:
```java
public class Scientist  implements Serializable {
```

That interface is called a marker interface, thus we don't need to implement any method. Serializable interface will allow the instance of our Scientist class to be serializable and deseriable. We will need to do serialize it so that we can pass a whole object across activities. Then in the target activity we can deserialize it and render its fields.

### Step 2: Define instance fields

Let's now define our instance fields. These fields represent the properties of our Scientist:
```java
    private String mId;
    private String name;
    private String description;
    private String galaxy;
    private String star;
    private String dob;
    private String dod;
    private String key;
```

### Step 3: Create An Empty Constructor
```java
    public Scientist() {
        //empty constructor needed
    }
```
As we had said earlier , you need to provide an empty construcor to our model class. If you needed to make injections into your class via the constructor then you can create a seperate constructor to do that. See for example how we've created one:
```java
    public Scientist(String name,String description,String galaxy,String star, String dob,
                String dod ) {
        if (name.trim().equals("")) {
            name = "Scientist NoName";
        }
        this.name = name;
        this.description = description;
        this.galaxy = galaxy;
        this.star = star;
        this.dob = dob;
        this.dod = dod;
    }
```

### Step 4: Generate Accessor methods
We now come to generate our accessor methods. These are simply getters and setters. However there are two special ones we will talk about in a short while:
```java
    public String getId() {
        return mId;
    }
    public void setId(String id) {
        mId = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGalaxy() {
        return galaxy;
    }
    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

```

Well there is nothing special about the above accessor methods. Now add the below two:
```java
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
```
The special thing about them is the `@Exclude` annotation. This annotation is provided to us by the `com.google.firebase.database` annotation we had included in our imports. It is telling Firebase to ignore generating the `key` node while persisting our data. We have to do this because Firebase will be generating that key automatically. In fact we'll simply use the two methods to get and set that key locally but we won't be persisting it.

### FULL CODE
Here is the full code:
```java
package info.camposha.firebasedatabasecrud.Data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Scientist  implements Serializable {

    private String mId;
    private String name;
    private String description;
    private String galaxy;
    private String star;
    private String dob;
    private String dod;
    private String key;

    public Scientist() {
        //empty constructor needed
    }
    public Scientist(String name,String description,String galaxy,String star, String dob,
                String dod ) {
        if (name.trim().equals("")) {
            name = "Scientist NoName";
        }
        this.name = name;
        this.description = description;
        this.galaxy = galaxy;
        this.star = star;
        this.dob = dob;
        this.dod = dod;
    }

    public String getId() {
        return mId;
    }
    public void setId(String id) {
        mId = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGalaxy() {
        return galaxy;
    }
    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    @Override
    public String toString() {
        return getName();
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
//end

```

### Conclusion

We have created our model class. This simple class is at the center of our application because it defines the central entity we are working with. It is this class's instance that will be persisted, read and deleted. We've talked about constructors for the class, the fields as well as public accessor methods.

Now move over to the next class.
