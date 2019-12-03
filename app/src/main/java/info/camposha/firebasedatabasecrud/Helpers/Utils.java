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
            descriptionTxt.setError("Email is Required Please!");
            return false;
        }
        if(galaxyTxt.getText() == null || galaxyTxt.getText().toString().isEmpty()){
            galaxyTxt.setError("Phone is Required Please!");
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













