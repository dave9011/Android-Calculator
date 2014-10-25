package com.dhernandez.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhernandez.calculator.utils.EvaluationException;
import com.dhernandez.calculator.utils.ExpressionNode;
import com.dhernandez.calculator.utils.Parser;
import com.dhernandez.calculator.utils.ParserException;
import com.dhernandez.calculator.utils.SetVariable;


public class Calculator extends ActionBarActivity implements View.OnClickListener {

    public static final String TAG = Calculator.class.getSimpleName();

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
    protected Button b_equals;

    protected Editable displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //Set activity background color
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.calculator_background) );

        //Initialize the member view variables
        init();

//        displayView = (EditText)findViewById(R.id.display);
//        displayView.setOnFocusChangeListener( new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//
//                Log.v(TAG, "focus changed -- display - " + hasFocus);
//
//                if( hasFocus == false && ((EditText)view).getText().toString().isEmpty() ){
//                    displayView.setHint("");
//                }
//
//            }
//        });

    }

    private void init() {

        displayView = (EditText)findViewById(R.id.display);

        displayText = displayView.getText();
        displayText.setFilters( new InputFilter[]{ new InputFilter.LengthFilter(DISPLAY_MAX_LENGTH)} );

        b_clear = (Button)findViewById(R.id.clear_button);

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

        b_clear.setOnClickListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings || super.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int button_id = view.getId();

        switch(button_id) {

            case R.id.clear_button:
                displayText.clear();
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

            case R.id.equals_button :

                String answer = evaluateExpression();
                if(answer != null){
                    displayText.clear();
                    displayText.append(answer);
                }

                break;

            default:
                break;

        }

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
