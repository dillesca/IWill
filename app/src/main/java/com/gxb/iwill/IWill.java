package com.gxb.iwill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
            //TODO add dialog for adding
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Time to add a new goal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Goal> yearGoals = db.getYearGoals();
        if(yearGoals.isEmpty()) {
            addYearGoalDialog();
        } else {
            updateViews();
        }


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
            case R.id.action_add_yearly:
                addYearGoalDialog();
                break;
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
        final EditText editTextView = new EditText(this); //Text user types in

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle(R.string.in_2016)
                .setView(editTextView);

        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO make sure the text box is not empy

                String description = editTextView.getText().toString();
                if(description.length() > 0){
                    db.insertGoal(description, "Year");
                    updateViews();
                } else
                    Snackbar.make(editTextView, R.string.please_fill_out, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                // User clicked OK button
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateViews() {
        // Fill in text for year goals
        TextView tv = (TextView) findViewById(R.id.tv_year_goals);
        tv.setText("");
        tv.setMovementMethod(new ScrollingMovementMethod());

        for(Goal goal : db.getYearGoals())
            tv.append(goal.getDescription()+"\n\n");

        final Layout layout = tv.getLayout();
        if(layout != null){
            int scrollDelta = layout.getLineBottom(tv.getLineCount() - 1)
                    - tv.getScrollY() - tv.getHeight();
            if(scrollDelta > 0)
                tv.scrollBy(0, scrollDelta);
        }

    }
}
