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

    protected final int DISPLAY_MAX_LENGTH = 20;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        getSupportActionBar().hide();

        //Set activity background color
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.calculator_background) );

        //Initialize the member view variables
        init();

    }

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
                displayText.clear();
                break;

            case R.id.backspace_button :
                int text_length = displayText.length();
                if (text_length > 0){
                    displayText.delete(text_length-1, text_length);
                }
                break;

            case R.id.eulers_button:
                displayText.append(b_eulers.getText());
                break;

            case R.id.pi_button:
                displayText.append(b_pi.getText());
                break;

            //TODO:finish this cdode for plus minus
            //***ADD CHECK FOR ARRAYOUTOFBOUNDSEXCEPTION for insert method
            case R.id.plusMinus_button:
                if(displayText.length() == 0){
                    displayText.append("-");
                }
                if(displayText.charAt(0) == '-'){
                    Toast.makeText(this, "negative", Toast.LENGTH_SHORT);
                } else if(displayText.charAt(0) == '+') {
                    Toast.makeText(this, "positive", Toast.LENGTH_SHORT);
                } else {

                }
                break;

            case R.id.zero_button :
                displayText.append(b_0.getText());
                break;

            case R.id.one_button :
                displayText.append(b_1.getText());
                break;

            case R.id.two_button :
                displayText.append(b_2.getText());
                break;

            case R.id.three_button :
                displayText.append(b_3.getText());
                break;

            case R.id.four_button :
                displayText.append(b_4.getText());
                break;

            case R.id.five_button :
                displayText.append(b_5.getText());
                break;

            case R.id.six_button :
                displayText.append(b_6.getText());
                break;

            case R.id.seven_button :
                displayText.append(b_7.getText());
                break;

            case R.id.eight_button :
                displayText.append(b_8.getText());
                break;

            case R.id.nine_button :
                displayText.append(b_9.getText());
                break;

            case R.id.add_button :
                displayText.append(b_add.getText());
                break;

            case R.id.subtract_button :
                displayText.append(b_subtract.getText());
                break;

            case R.id.multiply_button :
                displayText.append(b_multiply.getText());
                break;

            case R.id.divide_button :
                displayText.append(b_divide.getText());
                break;

            case R.id.period_button :
                displayText.append(b_period.getText());
                break;

            case R.id.left_parenthesis_button :
                displayText.append(b_left_parenthesis.getText());
                break;

            case R.id.right_parenthesis_button:
                displayText.append(b_right_parenthesis.getText());
                break;

            case R.id.sin_button:
                String sinText = displayText.toString();
                if(sinText.isEmpty()){
                    sinText = "sin(";
                } else {
                    sinText = "sin(" + sinText + ")";
                }
                displayText.clear();
                displayText.append(sinText);
                break;

            case R.id.cos_button:
                String cosText = displayText.toString();
                if(cosText.isEmpty()){
                    cosText = "cos(";
                } else {
                    cosText = "cos(" + cosText + ")";
                }
                displayText.clear();
                displayText.append(cosText);
                break;

            case R.id.tan_button:
                String tanText = displayText.toString();
                if(tanText.isEmpty()){
                    tanText = "cos(";
                } else {
                    tanText = "cos(" + tanText + ")";
                }
                displayText.clear();
                displayText.append(tanText);
                break;

            case R.id.equals_button :

                String answer = evaluateExpression();
                if(answer != null){
                    displayText.clear();
                    displayText.append(answer);
                    addToHistory(answer);
                }

                break;

            default:
                break;

        }

    }

    private void addToHistory(String newHistoryItem) {

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
                        displayText.clear();
                        displayText.append(mHistoryList.get(position));
                    }
               }
        );
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    protected String evaluateExpression(){

        String exprStr = displayText.toString();

        Parser parser = new Parser();
        try
        {
            ExpressionNode expr = parser.parse(exprStr);
            expr.accept(new SetVariable("pi", Math.PI));
            Log.v(TAG, "The value of the expression is "+expr.getValue());
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

}
