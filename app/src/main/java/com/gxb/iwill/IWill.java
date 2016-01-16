package com.gxb.iwill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gxb.iwill.helper.Database;
import com.gxb.iwill.model.Goal;

import java.util.ArrayList;

public class IWill extends AppCompatActivity {

    private Database db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iwill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new Database(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Time to add a new goal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Goal> yearGoals = db.getYearGoals();
        if(yearGoals.isEmpty()) {
            addYearGoalDialog();
        } else
            Snackbar.make(fab, "tbl_goals is not empty has " + yearGoals.size() + " roes", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iwill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_yearly:
                //TODO Open edit yearly goals Possibly new activity
                break;
            case R.id.action_daily:
                //TODO Open edit daily goals Possibly new activity
                break;
            case R.id.action_medals:
                //TODO View medals earned activity
                break;
            default:
                return super.onOptionsItemSelected(item);
            //default: monthString = "Invalid month";
              //  break;
        }

        return true;
    }

    private void addYearGoalDialog(){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        Builder builder = new Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.whats_ur_goal)
                .setTitle(R.string.in_2016);



        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO make sure the text box is not empy
                // User clicked OK button
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
