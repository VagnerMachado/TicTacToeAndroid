package edu.ncc.machado.tictactoe2;

/**
 *  This is Vagner Machado's Tic Tac Toe App. Proper description is placed above each method
 *  and above important pieces of code, with the aim to explain what is happening at that point.
 *  The goal of this project is to build an efficient Tic Tac Toe game. This activity has a text view that
 *  asks for the players names, two labels and two text fields. Users should only be able to enter the game
 *  after setting two names and clicking on the 'Let's Go!" button.

 *
 *   Vagner Machado
 *   N00820127
 *   Date Due: 10/11
 */


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * FirstPage class - the class that contains the declarations of variables and methods used to
 * retrieve and pass the user names to an intent
 * implements View.OnClickListener interface: to handle clicks on the 'Let's Go' button
 */
public class FirstPage extends AppCompatActivity implements View.OnClickListener
{
    private static final String PLAYER1 = "player1";
    private static final String PLAYER2 = "player2";


    @Override
    /**
     * on create method - where the assignments occur whe program heads to onResume
     * @param savedInstanceState - A Bundle with key-pair data
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);


        // adds a toolbar to activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.initial_toolbar);
        setSupportActionBar(myToolbar);

        //places the game icon on the Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

       // getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon(R.drawable.ic_action_name);

        //links the button, instantiated as a local variable because Studio suggested to improve efficiency
        Button button = (Button) findViewById(R.id.button_go);
        button.setOnClickListener(this);
    }

    /**
     * onCreateOptionMenu method - enables the inflation of an Action Bar in the activity.
     * @param menu the menu to be used for the activity
     * @return - always true
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //inflates the menu
        getMenuInflater().inflate(R.menu.first_page_menu, menu);
        return true;
    }

    /**
     * onOptionItemSelected method - handles the click on the Action Bar
     * @param item - the item clicked in the Action Bar
     * @return - always true
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        // when user clicks on 'About' quick info about the game is displayed on a toast
        //the toast is shown twice to give enough time for user to read it.
        Toast toast = Toast.makeText(getApplicationContext(), R.string.about_info, Toast.LENGTH_LONG);
        toast.setGravity(0, 0, 0);
        toast.show();
        Toast toast2 = Toast.makeText(getApplicationContext(), R.string.about_info, Toast.LENGTH_LONG);
        toast2.setGravity(0, 0, 0);
        toast2.show();
        return true;
    }
    @Override
    /**
     * onClick method - handles the clicks on the Tic Tac Toe buttons
     * @param v - the view being clicked
     */
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.button_go:
                String a =(((EditText) findViewById(R.id.editText1)).getText().toString()).trim();
                String b =(((EditText) findViewById(R.id.editText2)).getText().toString()).trim();
                if (a.length() == 0 || b.length()== 0 )
                 {
                  // if nothing is in both text fields, a toast is displayed with a name
                     Toast toast = Toast.makeText(getApplicationContext(), R.string.no_name, Toast.LENGTH_LONG);
                     toast.setGravity(0, 0, 0);
                     toast.show();
                 }
                else {
                    //sets the players' names and sends them into intent
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(PLAYER1, a);
                    intent.putExtra(PLAYER2, b);
                    startActivity(intent);
                }
        }
    }
}
