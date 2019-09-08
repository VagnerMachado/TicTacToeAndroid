package edu.ncc.machado.tictactoe2;

/**
 *  This is Vagner Machado's Tic Tac Toe App. Proper description is placed above each method
 *  and above important pieces of code, with the aim to explain what is happening at that point.
 *  The goal of this project is to build an efficient Tic Tac Toe game. This app has a text view,
 *  nine buttons corresponding to the tic tac toe game buttons and Action Bar.In the action bar, two items are
 *  found: Reset and Colors. The reset buttons clears all entries and set the color for the entries to
 *  be black. The Colors item allows the player to set a color for the Symbol being played next. Toasts
 *  will pop up throughout the game and display the color changes, the winner or a tie.
 *
 *   Vagner Machado
 *   N00820127
 *   Date Due: 10/11
 */

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

import edu.ncc.machado.tictactoe2.R;

/**
 * MainActivity class - the class that contains the declarations of variables and methods used in the TicTacToe App
 * implements: View.OnClickListener interface: to handle clicks on Views
 *             AdapterView.OnItemSelectedListener interface: to handle clicks on an Spinner Item
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //the buttons
    private Button buttons[];

    //to get the player's names
    private static final String PLAYER1 = "player1";
    private static final String PLAYER2 = "player2";

    private final static String TAG = "DEBUGGING";

    //color values
    private int x;
    private int o;

    // x and o, p or q, ..
    private String evenChar;
    private String oddChar;

    //controls the displayed X or O, and used to figure out the winner and text color
    private int counter;

    //Bundle data: button chars and current chars used
    private String[] stringData;

    //winner status
    private boolean winnerFound;

    //the id for the first of nine buttons
    int id;

    //Bundle data: current colors and counter
    int[] intData;

    //the player's names
    String player1, player2;

    /**
     * on create method - where the assignments occur whe program heads to onResumed
     *
     * @param savedInstanceState - A Bundle with key-pair data
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "inside on Create");

        Intent intent = getIntent();
        player1 = intent.getStringExtra(PLAYER1);
        player2 = intent.getStringExtra(PLAYER2);

        String display = player1 + " & " + player2;
        ((TextView)findViewById(R.id.textView)).setText(display);

        //variables for the text color
        x = Color.BLACK;
        o = Color.BLACK;
        counter = 0;

        //Bundle data
        intData = new int[3];
        intData[0] = x;
        intData[1] = o;
        intData[2] = counter;

        //used if the screen is rotated after a winner is found.
        //and keeps user from clicking extra buttons after rotation
        winnerFound = false;

        //the array with the button's text to be sent to the Bundle
        stringData = new String[11];

        //default char set
        evenChar = "X";
        oddChar = "O";

        //char set to be sent to bundle
        stringData[9] = oddChar;
        stringData[10] = evenChar;//default char set


        // instantiates an array with 9 buttons
        buttons = new Button[9];
        id = R.id.button1;

        //assigns the buttons based on id from activity_main and turns on click listener and increases the id.
        for (int i = 0; i < buttons.length; i++) {
            stringData[i] = "";
            buttons[i] = (Button) findViewById(id);
            buttons[i].setOnClickListener(this);
            id++;
        }

        //create a toolbar and links it to the XML toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //places the icon on the Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    /**
     * onCreateOptionsMenu - method implemented for the app to display an Action
     *bar with Menu Options in it.
     * @param menu - the menu used as option
     * @return - always true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "inside onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //these lines deal with the spinner addition to the app
        MenuItem spinnerItem = menu.findItem(R.id.color_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(spinnerItem);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return true;
    }

    /**
     * onOptionItemSelected method - deals with the clicks on the action bar
     * @param item - the item clicked in the action bar
     * @return - true
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "inside on Options");
        // Handle item selection by id
        switch (item.getItemId()) {
            //case it's the reset button: all buttons are clickable and cleared. All game variables are set to
            //instantiated state
            case R.id.reset_action:
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setClickable(true);
                    buttons[i].setText("");
                    stringData[i] = ""; // Bundle data
                }
                // resets the color and counter
                counter = 0;
                o = Color.BLACK;
                x = Color.BLACK;

                //Bundle data
                intData[0] = x;
                intData[1] = o;
                intData[2] = counter;

                //winner status
                winnerFound = false;

                //resets the chars
                evenChar = "X";
                oddChar = "O";

                //Bundle data
                stringData[9] = oddChar;
                stringData[10] = evenChar;

                // resets the Action Bar to original format in case you click Colors and you see the
                //Spinner but select no color, then click in Overflow and Reset.
                invalidateOptionsMenu();
                break;

            //Displays quick info about the game in overflow. The double toast extends the display time
            case R.id.overflow_default:
                Toast toast = Toast.makeText(getApplicationContext(), R.string.about_info, Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 1200);
                toast.show();
                Toast toastA = Toast.makeText(getApplicationContext(), R.string.about_info, Toast.LENGTH_LONG);
                toastA.setGravity(0, 0, 1200);
                toastA.show();
                break;

            //Case the user clicks o the change the letter alert dialog
            case R.id.change_letters:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.change_letters);
                String[] choices = {"X and O", "P and Q", "Y and Z"}; //I tried to use string resources but it was not accepting
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int option) {
                        switch (option)
                        {
                            //the character used are X and O
                            case 0:
                                evenChar = "X";
                                oddChar = "O";

                                //bundle data
                                stringData[9] = oddChar;
                                stringData[10] = evenChar;

                                //reassignment
                                for (int i = 0; i < buttons.length; i++) {
                                    if (buttons[i].getText().equals("P") || buttons[i].getText().equals("Y"))
                                    {  buttons[i].setText(evenChar);
                                    stringData[i] = evenChar;}
                                    else if (buttons[i].getText().equals("Q") || buttons[i].getText().equals("Z"))
                                    {  buttons[i].setText(oddChar);
                                        stringData[i] = oddChar;}
                                }
                                break;

                            // the character used are P and Q
                            case 1:
                                evenChar = "P";
                                oddChar = "Q";

                                //bundle data
                                stringData[9] = oddChar;
                                stringData[10] = evenChar;

                                //reassignment
                                for (int i = 0; i < buttons.length; i++) {
                                    if (buttons[i].getText().equals("X") || buttons[i].getText().equals("Y"))
                                    { buttons[i].setText(evenChar);
                                        stringData[i] = evenChar;}
                                    else if (buttons[i].getText().equals("Z") || buttons[i].getText().equals("O"))
                                    {buttons[i].setText(oddChar);
                                        stringData[i] = oddChar;}
                                }
                                break;
                            // the characters used are Y and Z
                            case 2:
                                evenChar = "Y";
                                oddChar = "Z";

                                //bundle data
                                stringData[9] = oddChar;
                                stringData[10] = evenChar;

                                //reassignment
                                for (int i = 0; i < buttons.length; i++) {
                                    if (buttons[i].getText().equals("X") || buttons[i].getText().equals("P"))
                                    {buttons[i].setText(evenChar);
                                        stringData[i] = evenChar;}
                                    else if (buttons[i].getText().equals("O") || buttons[i].getText().equals("Q"))
                                    { buttons[i].setText(oddChar);
                                        stringData[i] = oddChar;}
                                }
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return true;
    }

    /**
     * onClick method - specifies what happens when the user taps on a button:
     * 1- if the button is already used, nothing happens, counter is not increased
     * 2- the first tap is designed to be an even move. Even moves get and "X" text as default to display
     * 3- the odd moves cause "0" to display as default
     * For the case 2 or 3, the counter gets increased.
     * <p>
     * If a winner is found before the nine moves are used, the game will display the winner name
     * otherwise it will be a tie at the end, and proper display shown.
     *
     * @param v - the View being clicked
     */
    public void onClick(View v) {

        //the first, third, fifth (...) click get an 'X', the counter is increased and cannot click on it anymore
        if (counter % 2 == 0) {
            ((Button) v).setTextColor(x);
            ((Button) v).setText((evenChar));
            v.setClickable(false);
            stringData[v.getId() - id + 9] = evenChar;
            counter++;
            intData[2] = counter;

            //otherwise the buttons displays an 'O', the counter is increased and cannot click on it anymore
        } else {
            ((Button) v).setTextColor(o);
            ((Button) v).setText((oddChar));
            v.setClickable(false);
            stringData[v.getId() - id + 9] = oddChar;
            counter++;
            intData[2] = counter;

        }

        //here we check for a winner after the minimal amount of moves is executed
        if (counter > 4 && counter < 10) {
            //the winning possibilities - just a few so i did not design a loop to check it.
            if ((!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[1].getText()) && buttons[0].getText().equals(buttons[2].getText())) ||
                    (!buttons[3].getText().equals("") && buttons[3].getText().equals(buttons[4].getText()) && buttons[3].getText().equals(buttons[5].getText())) ||
                    (!buttons[6].getText().equals("") && buttons[6].getText().equals(buttons[7].getText()) && buttons[6].getText().equals(buttons[8].getText())) ||
                    (!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[3].getText()) && buttons[0].getText().equals(buttons[6].getText())) ||
                    (!buttons[1].getText().equals("") && buttons[1].getText().equals(buttons[4].getText()) && buttons[1].getText().equals(buttons[7].getText())) ||
                    (!buttons[2].getText().equals("") && buttons[2].getText().equals(buttons[5].getText()) && buttons[2].getText().equals(buttons[8].getText())) ||
                    (!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText())) ||
                    (!buttons[2].getText().equals("") && buttons[2].getText().equals(buttons[4].getText()) && buttons[2].getText().equals(buttons[6].getText()))) {

                //this boolean will be usd to keep user from clicking on buttons if screen is rotated after winner is found
                winnerFound = true;

                // if a winning combination is found, all buttons cannot be clicked
                for (int i = 0; i < buttons.length; i++)
                    buttons[i].setClickable(false);

                //if the counter is even, '0' wins and Toast is displayed with the name of the player
                if (counter % 2 == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Good job, you won " + player2+ "!", Toast.LENGTH_LONG);
                    toast.setGravity(0, 0, 1200);
                    toast.show();
                }

                //if the counter is odd, 'X' wins and Toast is displayed with the name of the player
                else if (counter % 2 != 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Good job, you won " + player1 + "!", Toast.LENGTH_LONG);
                    toast.setGravity(0, 0, 1200);
                    toast.show();
                }

                //if all 9 moves are made and no winner is found, the game is a tie and a Toast is displayed
            } else if (counter == 9) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.tie, Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 1200);
                toast.show();
            }
        }
    }

    @Override
    /**
     * onItemSelected method - defines what happens when user selects an item on spinner
     * @param parent - the parent for the AdapterView
     * @param view - the View object being used
     * @param position - the choice position in the string array
     *  @param id - the id of the view?
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //based on the position of the string array: Case 0 says to "Pick a Color:"

        switch (position) {
            //case 1 executes if the color is green
            case 1:
                // player1's character will be green if the counter is even
                if (counter % 2 == 0) {
                    for (int i = 0; i < buttons.length; i++) {
                        //all evenChar buttons become green and color for evenChar is set to green
                        if (buttons[i].getText().equals(evenChar))
                            buttons[i].setTextColor(Color.GREEN);
                    }
                    x = Color.GREEN;
                    intData[0] = x; // bundle data

                    // this toast will display what symbol and color was changed
                    Toast toast = (Toast.makeText(getApplicationContext(), evenChar + " is green.", Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();

                    // if the counter is odd then the oddChar is set to green
                } else {
                    for (int i = 0; i < buttons.length; i++) {
                        //all oddChar buttons become green and color for oddChar is set to green
                        if (buttons[i].getText().equals(oddChar))
                            buttons[i].setTextColor(Color.GREEN);
                    }
                    o = Color.GREEN;
                    intData[1] = o; // bundle data

                    // this toast will display what symbol and color was changed
                    Toast toast = (Toast.makeText(getApplicationContext(), oddChar + " is green." , Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();
                }
                //CHANGES THE ACTION BAR TO ITS ORIGINAL FORMAT
                invalidateOptionsMenu();
                break;

            //case 2 is active if the color picked is yellow
            case 2:
                // if the counter is even, evenChar becomes yellow
                if (counter % 2 == 0) {
                    //turns all evenChar yellow and sets the color for future evenChar to be yellow
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i].getText().equals(evenChar))
                            buttons[i].setTextColor(Color.YELLOW);
                    }
                    x = Color.YELLOW;
                    intData[0] = x; // bundle data

                    //This toast displays the symbol and color that changed
                    Toast toast = (Toast.makeText(getApplicationContext(), evenChar + " is yellow." , Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();

                    // if the counter is odd, oddChar symbol becomes yellow
                } else {
                    //turns all oddChar symbols yellow and sets the color for future oddChar to be yellow
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i].getText().equals(oddChar))
                            buttons[i].setTextColor(Color.YELLOW);
                    }
                    o = Color.YELLOW;
                    intData[1] = o; // bundle data


                    //This toast displays the symbol and color that got changed
                    Toast toast = (Toast.makeText(getApplicationContext(), oddChar + " is yellow.", Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();

                }
                //CHANGES THE ACTION BAR TO ITS ORIGINAL FORMAT
                invalidateOptionsMenu();
                break;

            //case 3 is for the color blue
            case 3:

                //if the counter is even the color for evenChar symbols will be blue
                if (counter % 2 == 0) {
                    //turns all evenChar symbols blue and sets color for the future evenChar symbols blue
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i].getText().equals(evenChar))
                            buttons[i].setTextColor(Color.BLUE);
                    }
                    x = Color.BLUE;
                    intData[0] = x; // bundle data

                    //This toast display the symbol and color that got changed
                    Toast toast = (Toast.makeText(getApplicationContext(), evenChar + " is blue.", Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();

                    //if the counter is odd, the 0 symbol is turned blue
                } else {
                    //turns all the oddChar symbols blue and sets the color for future oddChar to be blue
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i].getText().equals(oddChar))
                            buttons[i].setTextColor(Color.BLUE);
                    }
                    o = Color.BLUE;
                    intData[1] = o;

                    //This toast displays the symbol and the color that got changed
                    Toast toast = (Toast.makeText(getApplicationContext(), oddChar + " is blue." , Toast.LENGTH_SHORT));
                    toast.setGravity(0, 0, 1200);
                    toast.show();
                }
                //CHANGES THE ACTION BAR TO ITS ORIGINAL FORMAT
                invalidateOptionsMenu();
                break;
        }
    }

    @Override
    /**
     * onNothingSelected method - Callback method to be invoked when the selection disappears from this view.
     */
    public void onNothingSelected(AdapterView<?> parent) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "In onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "In onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In onDestroy");
    }

    @Override
    /**
     * onSaveInstanceState method - sends all instance data necessary to recreate
     * the game in landscape mode. All gathered data is sent to a Bundle object.
     * @param outState - the bundle with the instance data saved
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "in onSaveInstanceState");

        //sends into bundle
        outState.putBoolean("winnerFound", winnerFound);
        outState.putStringArray("stringData", stringData);
        outState.putIntArray("intData", intData);

        //this has to be the last declaration
        super.onSaveInstanceState(outState);
    }

    /**
     * onRestoreInstanceState method - redefines the state of the current
     * Tic Tac Toe game defined at onSaveInstanceState method. The parameter
     * bundle has the items used to restore the game
     *
     * @param inState - the Bundle objec received with the game variables.
     */
    public void onRestoreInstanceState(Bundle inState)
    {
        Log.d(TAG, "in onRestoreInstanceState");
        //This has to be the first statement
        super.onRestoreInstanceState(inState);

        //restores the winner Status
        winnerFound = inState.getBoolean("winnerFound");

        //restores the color for the characters used and the current counter
        int[] intDataRestore = inState.getIntArray("intData");
        x = intDataRestore[0];
        o = intDataRestore[1];
        counter = intDataRestore[2];

        // Bundle variables are reloaded
        intData[0] = x;
        intData[1] = o;
        intData[2] = counter;

        //restores instance data used and places them back in the Bundle
        String[] data = inState.getStringArray("stringData");
        oddChar = data[9];
        evenChar = data[10];
        //bundle data
        stringData[9] = data[9];
        stringData[10] = data[10];

        //button state reassignment
        for (int i = 0; i < 9; i++)
        {
            // reloads the button info back into the bundle
          stringData[i] = data[i]; //Bundle data

            //if screen is spun after the winner is found, blanks spaces are not clickable
        if (winnerFound && data[i].equals(""))
        {   // keeps user from clicking on blank buttons after winner is found and screen is rotated
            buttons[i].setClickable(false);
        }
            else
            //restores the even chars to their previous status
            if(data[i].equals(evenChar))
             {
             buttons[i].setText(data[i]);
             buttons[i].setTextColor(x);
             buttons[i].setClickable(false);
             }
             else
                //restores the odd chars to their previous status
                if (data[i].equals(oddChar))
                {
                    buttons[i].setText(data[i]);
                    buttons[i].setTextColor(o);
                    buttons[i].setClickable(false);
                }
        }

    }
}

