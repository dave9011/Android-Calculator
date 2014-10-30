package com.dhernandez.calculator;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dhernandez.calculator.utils.EvaluationException;
import com.dhernandez.calculator.utils.ExpressionNode;
import com.dhernandez.calculator.utils.Parser;
import com.dhernandez.calculator.utils.ParserException;
import com.dhernandez.calculator.utils.SetVariable;

import java.util.ArrayList;


public class Calculator extends ActionBarActivity implements View.OnClickListener {

    public static final String TAG = Calculator.class.getSimpleName();
    private static final int DISPLAY_MAX_LENGTH = 40;
    public final int NUM_HISTORY_SAVED = 10;
    private final String PI_SYMBOL = "\u03C0";
    private final String SQRT_SYMBOL = "\u221A";
    private final String CUBE_ROOT_SYMBOL = "\u00b3\u221a";
    private final String LOG_BASE_2_SYMBOL = "\u2082";

    private boolean SHIFT_DOWN =false;
    private boolean CLICK_SOUND_ENABLED = true;

    private EditText displayView;
    private Button b_clear;
    private Button b_0;
    private Button b_1;
    private Button b_2;
    private Button b_3;
    private Button b_4;
    private Button b_5;
    private Button b_6;
    private Button b_7;
    private Button b_8;
    private Button b_9;
    private Button b_divide;
    private Button b_multiply;
    private Button b_add;
    private Button b_subtract;
    private Button b_period;
    private Button b_left_parenthesis;
    private Button b_right_parenthesis;
    private Button b_plusMinus;
    private Button b_equals;
    private Button b_sin;
    private Button b_cos;
    private Button b_tan;
    private Button b_scientific_notation;
    private Button b_pi;
    private Button b_backspace;

    private Button b_mod;
    private Button b_sqrt;
    private Button b_cbrt;
    private Button b_exp_2;
    private Button b_exp_3;
    private Button b_exp_n;
    private Button b_log;
    private Button b_ln;
    private Button b_reciprocal;

    private Button b_shift;

    private Editable mDisplayText;

    private ArrayList<HistoryItem> mHistoryList = new ArrayList<HistoryItem>();

    private Parser mParser;

    private String mSinText = "sin";
    private String mCosText = "cos";
    private String mTanText = "tan";

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

    private void init() {

        displayView = (EditText)findViewById(R.id.display);

        displayView.setFocusable(false);
        displayView.setClickable(true);

        mDisplayText = displayView.getText();
        mDisplayText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DISPLAY_MAX_LENGTH)});

        b_clear = (Button)findViewById(R.id.clear_button);
        b_backspace = (Button)findViewById(R.id.backspace_button);

        b_scientific_notation = (Button)findViewById(R.id.scientific_notation_button);
        b_pi = (Button)findViewById(R.id.pi_button);

        b_period = (Button)findViewById(R.id.period_button);
        b_left_parenthesis = (Button)findViewById(R.id.left_parenthesis_button);
        b_right_parenthesis = (Button)findViewById(R.id.right_parenthesis_button);

        b_shift = (Button)findViewById(R.id.shift_button);

        displayView.setOnClickListener(this);

        b_clear.setOnClickListener(this);
        b_backspace.setOnClickListener(this);

        b_scientific_notation.setOnClickListener(this);
        b_pi.setOnClickListener(this);

        b_period.setOnClickListener(this);
        b_left_parenthesis.setOnClickListener(this);
        b_right_parenthesis.setOnClickListener(this);

        b_shift.setOnClickListener(this);

        setOperatorButtonVariables();
        setFunctionButtonVariables();
        setNumberButtonVariables();

        setButtonSpecialSymbols();

        displayView.setHint(Html.fromHtml("<small><small><small>" + "Tap here to show history" + "</small></small></small>"));

        mParser = new Parser();

    }

    private void setOperatorButtonVariables(){
        b_mod = (Button)findViewById(R.id.mod_button);
        b_plusMinus = (Button)findViewById(R.id.plusMinus_button);
        b_divide = (Button)findViewById(R.id.divide_button);
        b_multiply = (Button)findViewById(R.id.multiply_button);
        b_add = (Button)findViewById(R.id.add_button);
        b_subtract = (Button)findViewById(R.id.subtract_button);
        b_equals = (Button)findViewById(R.id.equals_button);

        b_mod.setOnClickListener(this);
        b_plusMinus.setOnClickListener(this);
        b_divide.setOnClickListener(this);
        b_multiply.setOnClickListener(this);
        b_add.setOnClickListener(this);
        b_subtract.setOnClickListener(this);
        b_equals.setOnClickListener(this);
    }

    private void setFunctionButtonVariables(){
        b_sin = (Button)findViewById(R.id.sin_button);
        b_cos = (Button)findViewById(R.id.cos_button);
        b_tan = (Button)findViewById(R.id.tan_button);
        b_sqrt = (Button)findViewById(R.id.square_root_button);
        b_cbrt = (Button)findViewById(R.id.cube_root_button);
        b_exp_2 = (Button)findViewById(R.id.exponent_2_button);
        b_exp_3 = (Button)findViewById(R.id.exponent_3_button);
        b_exp_n = (Button)findViewById(R.id.exponent_n_button);
        b_log = (Button)findViewById(R.id.log_button);
        b_ln = (Button)findViewById(R.id.natural_log_button);
        b_reciprocal = (Button)findViewById(R.id.reciprocal_button);

        b_sin.setOnClickListener(this);
        b_cos.setOnClickListener(this);
        b_tan.setOnClickListener(this);
        b_sqrt.setOnClickListener(this);
        b_cbrt.setOnClickListener(this);
        b_exp_2.setOnClickListener(this);
        b_exp_3.setOnClickListener(this);
        b_exp_n.setOnClickListener(this);
        b_log.setOnClickListener(this);
        b_ln.setOnClickListener(this);
        b_reciprocal.setOnClickListener(this);
    }

    private void setNumberButtonVariables(){
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
    }

    private void setButtonSpecialSymbols(){
        b_pi.setText(PI_SYMBOL);
        b_sqrt.setText(SQRT_SYMBOL);
        b_cbrt.setText(CUBE_ROOT_SYMBOL);
    }

    @Override
    public void onClick(View view) {

        //TODO: add option to enable and disable click from within app
        if(CLICK_SOUND_ENABLED){
            view.playSoundEffect(SoundEffectConstants.CLICK);
        }

        int button_id = view.getId();

        switch(button_id) {

            case R.id.shift_button:
                changeDisplayOnShift();
                break;

            case R.id.mod_button:
                appendValue(b_mod.getText());
                break;

            case R.id.reciprocal_button: {
                String text = displayView.getText().toString();
                text = "1/(" + text + ")";
                evaluateExpression(text);
                break;
            }

            case R.id.square_root_button: {
                String text = displayView.getText().toString();
                appendValue("sqrt");
                break;
            }

            case R.id.cube_root_button:{
                String text = displayView.getText().toString();
                appendValue("cbrt");
                break;
            }

            case R.id.natural_log_button: {
                String text = displayView.getText().toString();
                appendValue("ln");
                break;
            }

            case R.id.log_button: {
                String text = displayView.getText().toString();
                handleBasedLog(text);
                break;
            }

            case R.id.exponent_2_button: {
                String text = displayView.getText().toString();
                handleExponentiation(text, "2");
                break;
            }

            case R.id.exponent_3_button: {
                String text = displayView.getText().toString();
                handleExponentiation(text, "3");
                break;
            }

            case R.id.exponent_n_button: {
                appendValue("^");
                break;
            }

            case R.id.display:
                showHistoryDialog();
                break;

            case R.id.clear_button:
                displayView.setText("");
                break;

            case R.id.backspace_button :
                int text_length = displayView.getText().length();
                if (text_length > 0){
                    String txt = displayView.getText().toString();
                    displayView.setText(txt.substring(0, txt.length()-1));
                }
                break;

            case R.id.scientific_notation_button:
                appendValue("E");
                break;

            case R.id.pi_button:
                appendValue(b_pi.getText());
                break;

            case R.id.plusMinus_button: {
                String disText = displayView.getText().toString();
                handlePlusMinus(disText);
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
                String text = displayView.getText().toString();
                appendValue(mSinText);
                break;
            }

            case R.id.cos_button: {
                String text = displayView.getText().toString();
                appendValue(mCosText);
                break;
            }

            case R.id.tan_button: {
                String text = displayView.getText().toString();
                appendValue(mTanText);
                break;
            }

            case R.id.equals_button : {
                String exprStr = displayView.getText().toString();
                if (!exprStr.isEmpty()) {
                    evaluateExpression(exprStr);
                }
                break;
            }

            default:
                break;

        }

    }

    private void handleBasedLog(String text) {
        if(SHIFT_DOWN){
            appendValue("log"+LOG_BASE_2_SYMBOL);
        } else {
            appendValue("log");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeDisplayOnShift(){

        SHIFT_DOWN = !(SHIFT_DOWN);

        if(SHIFT_DOWN){

            b_sin.setText(Html.fromHtml(mSinText + "<sup>-1</sup>"));
            b_cos.setText(Html.fromHtml(mCosText + "<sup>-1</sup>"));
            b_tan.setText(Html.fromHtml(mTanText + "<sup>-1</sup>"));
            b_log.setText("log"+LOG_BASE_2_SYMBOL);

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                b_sin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_cos.setBackgroundDrawable(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_tan.setBackgroundDrawable(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_log.setBackgroundDrawable(getResources().getDrawable(R.drawable.shifted_btn_custom));
            } else {
                b_sin.setBackground(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_cos.setBackground(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_tan.setBackground(getResources().getDrawable(R.drawable.shifted_btn_custom));
                b_log.setBackground(getResources().getDrawable(R.drawable.shifted_btn_custom));
            }

            b_shift.setBackgroundColor(getResources().getColor(R.color.button_altColor_2_pressed));

        }
        else {
            b_sin.setText(Html.fromHtml(mSinText));
            b_cos.setText(Html.fromHtml(mCosText));
            b_tan.setText(Html.fromHtml(mTanText));
            b_log.setText("log");

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                b_sin.setBackgroundDrawable(getResources().getDrawable(R.drawable.function_btn_custom));
                b_cos.setBackgroundDrawable(getResources().getDrawable(R.drawable.function_btn_custom));
                b_tan.setBackgroundDrawable(getResources().getDrawable(R.drawable.function_btn_custom));
                b_log.setBackgroundDrawable(getResources().getDrawable(R.drawable.function_btn_custom));
                b_shift.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_alt2_custom));
            } else {
                b_sin.setBackground(getResources().getDrawable(R.drawable.function_btn_custom));
                b_cos.setBackground(getResources().getDrawable(R.drawable.function_btn_custom));
                b_tan.setBackground(getResources().getDrawable(R.drawable.function_btn_custom));
                b_log.setBackground(getResources().getDrawable(R.drawable.function_btn_custom));
                b_shift.setBackground(getResources().getDrawable(R.drawable.btn_alt2_custom));
            }

        }

    }

    private void handleExponentiation(String text, String power) {
        text = "(" + text + ")^" + power;
        evaluateExpression(text);
    }

    //TODO: look to see if there is a proper way to implement this, if not then remove it
//    private void appendFunction(String text, String function) {
//        if(text.isEmpty()){
//            displayView.setText( function + "(" );
//        }
//        else {
//            displayView.setText( function + "(" + text + ")" );
//        }
//    }

    //Possibly deprecated
    private void handleTrigFunction(String text, String trigFunction) {
        if (text.isEmpty()) {
            text = trigFunction + "(";
        } else {
            text = trigFunction + "(" + text + ")";
        }
        displayView.setText("");
        appendValue(text);
    }

    private void handlePlusMinus(String disText) {
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
     * @param expression        expression to add to history list
     * @param expressionResult  result of expression to add to history list
     */
    private void addToHistory(String expression, String expressionResult) {

        if(!mHistoryList.isEmpty() && expression.equals(mHistoryList.get(0).getExpression())){
            return;
        }

        mHistoryList.add(0, new HistoryItem(expression, expressionResult));

        if(mHistoryList.size() > NUM_HISTORY_SAVED){
            mHistoryList.remove(mHistoryList.size() - 1);
        }

    }

    /**
     * Creates a custom AlertDialog that displays the history.
     *
     * Displays the expression and result of each HistoryItem object in mHistoryList using the
     * custom adapter, HistoryAdapter.
     */
    private void showHistoryDialog() {

        //String[] historyArray = mHistoryList.toArray(new String[mHistoryList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_history, null);
        builder.setView(convertView);
        ListView listView = (ListView)convertView.findViewById(R.id.list);
        listView.setEmptyView(convertView.findViewById(R.id.empty));

        HistoryAdapter adapter = new HistoryAdapter(this, mHistoryList);
        listView.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                displayView.setText("");
                appendValue(mHistoryList.get(position).getResult());
                dialog.dismiss();
            }
        });
    }

    /**
     * Computes the value of the expression using the CogPar
     * mathematical expression parser.
     *
     * @param   expStr  expression to be evaluated
     */
    private void evaluateExpression(String expStr){

        String originalExpression = expStr;

        //Variable that keeps track of whether the user used PI in the current expression
        boolean usesPI = false;

        try {

            if(expStr.contains(PI_SYMBOL)){
                expStr = expStr.replaceAll(PI_SYMBOL, "pi");
                usesPI = true;
            }
            if(expStr.contains(LOG_BASE_2_SYMBOL)){
                expStr = expStr.replaceAll(LOG_BASE_2_SYMBOL, "2 ");
            }

            ExpressionNode expr = mParser.parse(expStr);

            expr.accept(new SetVariable("pi", Math.PI));

            double resultToStore = expr.getValue();
            double resultToDisplay = (double)Math.round(resultToStore * 100000000) / 100000000;

            Log.v(TAG, "The value of " + originalExpression + " is " + expr.getValue());

            displayView.setText("");
            appendValue(resultToDisplay + "");
            addToHistory(originalExpression, resultToStore + "");

        }
        catch (ParserException e)
        {
            Log.e(TAG, e.getMessage());
            if(usesPI){
                //TODO : MAKE THIS AN ALERT DIALOG
                Toast.makeText(this,
                        "Please include operator an between " + PI_SYMBOL + " and adjacent numbers or parentheses.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to evaluate that expression!", Toast.LENGTH_SHORT).show();
            }

        }
        catch (EvaluationException e)
        {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, "Unable to evaluate that expression!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calculator, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.action_clear_history:
                mHistoryList.clear();
                Toast.makeText(this, getString(R.string.history_cleared_message), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Inner class used to store a history entry, including the expression and the expression's
     * result.
     */
    protected class HistoryItem {

        private String mExpression;
        private String mResult;

        protected HistoryItem(String expression, String result){
            mExpression = expression;
            mResult = result;
        }

        public String getExpression(){
            return mExpression;
        }

        public String getResult(){
            return mResult;
        }

    }

}
