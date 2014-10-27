package com.dhernandez.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhernandez.calculator.utils.EvaluationException;
import com.dhernandez.calculator.utils.ExpressionNode;
import com.dhernandez.calculator.utils.Parser;
import com.dhernandez.calculator.utils.ParserException;
import com.dhernandez.calculator.utils.SetVariable;

import java.util.ArrayList;


public class Calculator extends ActionBarActivity implements View.OnClickListener {

    public static final String TAG = Calculator.class.getSimpleName();
    public static final int NUM_HISTORY_SAVED = 10;
    public static final String PI_SYMBOL = "\u03C0";
    protected static final int DISPLAY_MAX_LENGTH = 40;
    protected EditText displayView;
    protected Button b_clear;
    protected Button b_0;
    protected Button b_1;
    protected Button b_2;
    protected Button b_3;
    protected Button b_4;
    protected Button b_5;
    protected Button b_6;
    protected Button b_7;
    protected Button b_8;
    protected Button b_9;
    protected Button b_divide;
    protected Button b_multiply;
    protected Button b_add;
    protected Button b_subtract;
    protected Button b_period;
    protected Button b_left_parenthesis;
    protected Button b_right_parenthesis;
    protected Button b_plusMinus;
    protected Button b_equals;
    protected Button b_sin;
    protected Button b_cos;
    protected Button b_tan;
    protected Button b_eulers;
    protected Button b_pi;
    protected Button b_backspace;

    protected Editable displayText;

    protected ArrayList<String> mHistoryList = new ArrayList<String>();

    protected Parser mParser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Log.d(TAG, "calculator - CREATED");

        getSupportActionBar().hide();

        //Set activity background color
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.calculator_background) );

        init();

    }


    /**
     * Initialize the member view variables and set their onClick listener to this class
     */
    private void init() {

        displayView = (EditText)findViewById(R.id.display);

        displayView.setFocusable(false);
        displayView.setClickable(true);

        displayText = displayView.getText();
        displayText.setFilters( new InputFilter[]{ new InputFilter.LengthFilter(DISPLAY_MAX_LENGTH)} );

        b_backspace = (Button)findViewById(R.id.backspace_button);

        b_clear = (Button)findViewById(R.id.clear_button);

        b_sin = (Button)findViewById(R.id.sin_button);
        b_cos = (Button)findViewById(R.id.cos_button);
        b_tan = (Button)findViewById(R.id.tan_button);
        b_eulers = (Button)findViewById(R.id.eulers_button);
        b_pi = (Button)findViewById(R.id.pi_button);
        b_pi.setText(PI_SYMBOL);

        b_plusMinus = (Button)findViewById(R.id.plusMinus_button);
        b_0 = (Button)findViewById(R.id.zero_button);
        b_1 = (Button)findViewById(R.id.one_button);
        b_2 = (Button)findViewById(R.id.two_button);
        b_3 = (Button)findViewById(R.id.three_button);
        b_4 = (Button)findViewById(R.id.four_button);
        b_5 = (Button)findViewById(R.id.five_button);
        b_6 = (Button)findViewById(R.id.six_button);
        b_7 = (Button)findViewById(R.id.seven_button);
        b_8 = (Button)findViewById(R.id.eight_button);
        b_9 = (Button)findViewById(R.id.nine_button);
        b_divide = (Button)findViewById(R.id.divide_button);
        b_multiply = (Button)findViewById(R.id.multiply_button);
        b_add = (Button)findViewById(R.id.add_button);
        b_subtract = (Button)findViewById(R.id.subtract_button);
        b_equals = (Button)findViewById(R.id.equals_button);
        b_period = (Button)findViewById(R.id.period_button);
        b_left_parenthesis = (Button)findViewById(R.id.left_parenthesis_button);
        b_right_parenthesis = (Button)findViewById(R.id.right_parenthesis_button);

        displayView.setOnClickListener(this);
        b_clear.setOnClickListener(this);
        b_backspace.setOnClickListener(this);
        b_sin.setOnClickListener(this);
        b_cos.setOnClickListener(this);
        b_tan.setOnClickListener(this);
        b_eulers.setOnClickListener(this);
        b_pi.setOnClickListener(this);
        b_plusMinus.setOnClickListener(this);
        b_0.setOnClickListener(this);
        b_1.setOnClickListener(this);
        b_2.setOnClickListener(this);
        b_3.setOnClickListener(this);
        b_4.setOnClickListener(this);
        b_5.setOnClickListener(this);
        b_6.setOnClickListener(this);
        b_7.setOnClickListener(this);
        b_8.setOnClickListener(this);
        b_9.setOnClickListener(this);
        b_divide.setOnClickListener(this);
        b_multiply.setOnClickListener(this);
        b_add.setOnClickListener(this);
        b_subtract.setOnClickListener(this);
        b_equals.setOnClickListener(this);
        b_period.setOnClickListener(this);
        b_left_parenthesis.setOnClickListener(this);
        b_right_parenthesis.setOnClickListener(this);

        mParser = new Parser();

    }

    @Override
    public void onClick(View view) {

        int button_id = view.getId();

        switch(button_id) {

            case R.id.display:
                if(mHistoryList != null && !mHistoryList.isEmpty() ){
                    showHistoryDialog();
                } else {
                    Toast.makeText(this, "No history to show", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.clear_button:
                displayView.setText("");
                break;

            case R.id.backspace_button :
                int text_length = displayView.getText().length();
                if (text_length > 0){
                    String txt = displayView.getText().toString();
                    displayView.setText(txt.substring(0, txt.length()));
                }
                break;

            case R.id.eulers_button:
                appendValue(b_eulers.getText());
                break;

            case R.id.pi_button:
                appendValue(b_pi.getText());
                break;

            case R.id.plusMinus_button: {

                String disText = displayView.getText().toString();

                if (disText.isEmpty()) {
                    appendValue("-");
                }
                else if (disText.charAt(0) == '-') {
                    if(disText.length() == 1){
                        displayView.setText("");
                    } else {
                        displayView.setText( disText.substring(1) );
                    }
                } else if (disText.charAt(0) == '+') {
                    if(disText.length() == 1){
                        displayView.setText("-");
                    } else {
                        displayView.setText("-" + disText.substring(1) );
                    }
                } else {
                    disText = "-" + disText;
                    displayView.setText(disText);
                }
                break;

            }

            case R.id.zero_button :
                appendValue(b_0.getText());
                break;

            case R.id.one_button :
                appendValue(b_1.getText());
                break;

            case R.id.two_button :
                appendValue(b_2.getText());
                break;

            case R.id.three_button :
                appendValue(b_3.getText());
                break;

            case R.id.four_button :
                appendValue(b_4.getText());
                break;

            case R.id.five_button :
                appendValue(b_5.getText());
                break;

            case R.id.six_button :
                appendValue(b_6.getText());
                break;

            case R.id.seven_button :
                appendValue(b_7.getText());
                break;

            case R.id.eight_button :
                appendValue(b_8.getText());
                break;

            case R.id.nine_button :
                appendValue(b_9.getText());
                break;

            case R.id.add_button :
                appendValue(b_add.getText());
                break;

            case R.id.subtract_button :
                appendValue(b_subtract.getText());
                break;

            case R.id.multiply_button :
                appendValue(b_multiply.getText());
                break;

            case R.id.divide_button :
                appendValue(b_divide.getText());
                break;

            case R.id.period_button :
                appendValue(b_period.getText());
                break;

            case R.id.left_parenthesis_button :
                appendValue(b_left_parenthesis.getText());
                break;

            case R.id.right_parenthesis_button:
                appendValue(b_right_parenthesis.getText());
                break;

            case R.id.sin_button: {
                String sinText = displayView.getText().toString();
                if (sinText.isEmpty()) {
                    sinText = "sin(";
                } else {
                    sinText = "sin(" + sinText + ")";
                }
                displayView.setText("");
                appendValue(sinText);
                break;
            }

            case R.id.cos_button: {
                String cosText = displayView.getText().toString();
                if (cosText.isEmpty()) {
                    cosText = "cos(";
                } else {
                    cosText = "cos(" + cosText + ")";
                }
                displayView.setText("");
                appendValue(cosText);
                break;
            }

            case R.id.tan_button: {
                String tanText = displayView.getText().toString();
                if (tanText.isEmpty()) {
                    tanText = "cos(";
                } else {
                    tanText = "cos(" + tanText + ")";
                }
                displayView.setText("");
                appendValue(tanText);
                break;
            }

            case R.id.equals_button : {
                String exprStr = displayView.getText().toString();
                if (!exprStr.isEmpty()) {
                    String answer = evaluateExpression(exprStr);
                    if (answer != null) {
                        displayView.setText("");
                        appendValue(answer);
                        addToHistory(answer);
                    }
                }
                break;
            }

            default:
                break;

        }

    }

    /**
     * Append the string passed in to the display.
     *
     * @param str  the string to append
     */
    private void appendValue(CharSequence str){
        displayView.append(str);
    }

    /**
     * Add the last computed value to the front of history list. The last item in the list will be
     * removed whenever the list size exceeds the capacity specified in the constant NUM_HISTORY_SAVED
     *
     * @param newHistoryItem  new value to add to history list
     */
    private void addToHistory(String newHistoryItem) {

        if(!mHistoryList.isEmpty() && newHistoryItem.equals(mHistoryList.get(0))){
            return;
        }

        mHistoryList.add(0, newHistoryItem);

        if(mHistoryList.size() > NUM_HISTORY_SAVED){
            mHistoryList.remove(mHistoryList.size() - 1);
        }

    }

    protected void showHistoryDialog() {

        String[] historyArray = mHistoryList.toArray(new String[mHistoryList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.history_dialog_title)
               .setItems(historyArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        displayView.setText("");
                        appendValue(mHistoryList.get(position));


                        //TODO: decide if I want to clear the field or just append the chosen result to it
                        //*if you decide to clear you must save last result in order to use it with next expression
                    }
               }
        );
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * Computes the value of the expression using the CogPar
     * mathematical expression parser.
     *
     * @param   expStr  expression to be evaluated
     * @return          string containing the value of the expression
     */
    protected String evaluateExpression(String expStr){

        /* TODO : finish implementing PI, make sure it is well tested for all possible inputs

                e.g. 2pi | pi | pi2 | pitan | tanpi
        */

        try {
            Log.d(TAG, "original -> " + expStr);
            expStr = expStr.replaceAll("(\\w)"+PI_SYMBOL+"(\\w)", "$1*pi*$2");
            expStr = expStr.replaceAll("(\\w)"+PI_SYMBOL, "$1*pi");
            expStr = expStr.replaceAll(PI_SYMBOL+"(\\w)", "pi*$1");
            expStr = expStr.replaceAll("^"+PI_SYMBOL+"$", "pi");
            Log.d(TAG, "new -> " + expStr);
            ExpressionNode expr = mParser.parse(expStr);

            expr.accept(new SetVariable("pi", Math.PI));

            Log.v(TAG, "The value of the expression is " + expr.getValue());
            return expr.getValue()+"";
        }
        catch (ParserException e)
        {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Unable to evaluate that expression!", Toast.LENGTH_SHORT).show();
        }
        catch (EvaluationException e)
        {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Unable to evaluate that expression!", Toast.LENGTH_SHORT).show();
        }

        return null;

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "calculator - PAUSED");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "calculator - RESTARTED");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "calculator - STOPPED");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "calculator - DESTROYED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "calculator - RESUMED");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "calculator - started (NOT restart!!)");
    }
}
