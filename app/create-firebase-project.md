## Creating and Adding Firebase App to Android

In this lesson we will see how to create our android studio as well as firebase app. Then we will add the firebase app to android studio. We will see two techniques of creating and adding the firebase app to android studio. Then we will also see the libraries we will use in the app we are building.

Note that this lesson is part of an Android Firebase Realtime Database CRUD course we are currently covering. In the course we are creating a full Android app, powered by Firebase realtime database that supports:
1. Adding Data to Firebase Realtime database.
2. Retrieving Data from Firebase
3. Updating Existing firebase data.
4. Deleting data from firebase data.

### Objectives of this Lesson
After this lesson, you will be able to:
1. Create Android Studio Project and Add Firebase to it in two ways.
2. Use several cool libraries in your project.

This lesson as  we've said is part of a multi-episode course series.

### Step 1: Create Android Studio Project

1. Start your android studio.
2. Invoke the New project dialog.
3. Type your project name and choose its location.
4. Check boxes that ask us if our app supports androidx and instant app features.

You should have a brand new project in your android studio.

### Step 2: Add Firebase  To your android studio
There are two ways:
1. Using Android studio firebase assistant.
2. Directly Creating firebase app in firebase console then downloading google-services.json and adding to app folder.

PLEASE WATCH THE VIDEO IF YOU ARE NOT SURE HOW TO DO ANY OF THE ABOVE.

### Step 3: Add Gradle dependencies

Here are our gradle dependencies:
```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    testImplementation 'junit:junit:4.12'
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'

    implementation 'com.google.firebase:firebase-database:18.0.0'

    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.github.ivbaranov:materiallettericon:0.2.3'
    implementation "android.helper:datetimepickeredittext:1.0.0"
    implementation 'io.github.inflationx:calligraphy3:3.1.0'
    implementation 'io.github.inflationx:viewpump:1.0.0'
    implementation 'com.yarolegovich:lovely-dialog:1.1.0'
}

```

Here are some of the dependencies we've added:

|No.|Name|Role|
|---|----|----|
|1.|AppCompat|Our main androidx appcompat library which will give us access to appcompatactivity as well as other classes.|
|2.|RecyclerView| Androidx recyclerview to give us the adapterview we will use to render our Firebase data.|
|3.|CardView| To represent a single recyclerview row or item.The recyclerview will thus comprise several cardviews. Moreover we use cardviews in our dashboard screen, in our detail activity and in our crud activity.|
|4.|Firebase Database | Well our Firebase realtime database.|
|5.|MaterialLetterIcon| To show name initials icons in our recyclerview rather than images.|
|6.|DateTimePickerEditText | We use it to pick dates that will be saved in Firebase database.|
|7.|Calligraphy and ViewPump| To allow us load custom fonts for our app.|
|8.|LoveLyDialog | To allow us use lovely standard as well as choice dialogs.|

### Full Code
Here is the full code for this lesson:
#### (a). build.gradle (app)
The app level build.gradle:
```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 28
    defaultConfig {
        applicationId "info.camposha.firebasedatabasecrud"
        minSdkVersion 15
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    testImplementation 'junit:junit:4.12'
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'

    implementation 'com.google.firebase:firebase-database:18.0.0'

    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.github.ivbaranov:materiallettericon:0.2.3'
    implementation "android.helper:datetimepickeredittext:1.0.0"
    implementation 'io.github.inflationx:calligraphy3:3.1.0'
    implementation 'io.github.inflationx:viewpump:1.0.0'
    implementation 'com.yarolegovich:lovely-dialog:1.1.0'
}

apply plugin: 'com.google.gms.google-services'
```
#### (a). build.gradle (project)
The project level build.gradle:
```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.4.0'
        classpath 'com.google.gms:google-services:3.2.0'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

Above you can see we have added our google-services classpath. Please don't forget it. You may use later versions of it.