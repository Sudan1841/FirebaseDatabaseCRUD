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

