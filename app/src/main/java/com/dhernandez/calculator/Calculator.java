package com.dhernandez.calculator;

import android.app.AlertDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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


//TODO: clean up drawable folders (unused apptheme resources)

public class Calculator extends ActionBarActivity implements View.OnClickListener {

    public static final String TAG = Calculator.class.getSimpleName();
    public static final int NUM_HISTORY_SAVED = 10;
    public static final String PI_SYMBOL = "\u03C0";
    public static final String SQRT_SYMBOL = "\u221A";
    protected static final int DISPLAY_MAX_LENGTH = 40;
    protected boolean SHIFT_DOWN =false;
    protected boolean CLICK_SOUND_ENABLED = true;
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

    protected Button b_ans;
    protected Button b_keyboard;
    protected Button b_mod;
    protected Button b_sqrt;
    protected Button b_exp_2;
    protected Button b_exp_3;
    protected Button b_exp_n;
    protected Button b_log;
    protected Button b_ln;
    protected Button b_reciprocal;

    protected Button b_shift;

    protected Editable displayText;

    protected ArrayList<HistoryItem> mHistoryList = new ArrayList<HistoryItem>();

    protected Parser mParser;

    private String sinText = "sin";
    private String cosText = "cos";
    private String tanText = "tan";
    private String arcsinText = Html.fromHtml(sinText + "<sup>-1</sup>").toString();
    private String arccosText = Html.fromHtml(cosText + "cos<sup>-1</sup>").toString();
    private String arctanText = Html.fromHtml(tanText + "tan<sup>-1</sup>").toString();


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

        //TODO: remove this and member variable, and onClickListner switch case, also uncomment line below that disables display
        b_keyboard = (Button)findViewById(R.id.keyboardButton);
        b_keyboard.setOnClickListener(this);


        //TODO:make display view NOT editable in xml
        displayView.setFocusable(true);
        displayView.setClickable(true);

        //displayView.setFocusable(false);
        //displayView.setClickable(true);

        displayText = displayView.getText();
        displayText.setFilters( new InputFilter[]{ new InputFilter.LengthFilter(DISPLAY_MAX_LENGTH)} );

        b_clear = (Button)findViewById(R.id.clear_button);
        b_backspace = (Button)findViewById(R.id.backspace_button);

        b_eulers = (Button)findViewById(R.id.eulers_button);
        b_pi = (Button)findViewById(R.id.pi_button);

        b_period = (Button)findViewById(R.id.period_button);
        b_left_parenthesis = (Button)findViewById(R.id.left_parenthesis_button);
        b_right_parenthesis = (Button)findViewById(R.id.right_parenthesis_button);

        b_ans = (Button)findViewById(R.id.ans_button);
        b_shift = (Button)findViewById(R.id.shift_button);

        displayView.setOnClickListener(this);

        b_clear.setOnClickListener(this);
        b_backspace.setOnClickListener(this);

        b_eulers.setOnClickListener(this);
        b_pi.setOnClickListener(this);

        b_period.setOnClickListener(this);
        b_left_parenthesis.setOnClickListener(this);
        b_right_parenthesis.setOnClickListener(this);

        b_ans.setOnClickListener(this);
        b_shift.setOnClickListener(this);

        setOperatorButtonVariables();
        setFunctionButtonVariables();
        setNumberButtonVariables();

        setButtonSpecialSymbols();

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

    protected void setButtonSpecialSymbols(){
        b_pi.setText(PI_SYMBOL);
        b_sqrt.setText(SQRT_SYMBOL);
    }

    @Override
    public void onClick(View view) {

        //TODO: add this option
        if(CLICK_SOUND_ENABLED){
            view.setSoundEffectsEnabled(true);
            view.playSoundEffect(SoundEffectConstants.CLICK);
        }

        int button_id = view.getId();

        switch(button_id) {

            //TODO: add cases for new buttons

            case R.id.shift_button:
                handleShift();
                break;

            case R.id.square_root_button: {
                String text = displayView.getText().toString();
                appendFunction(text, "sqrt");
                break;
            }

            case R.id.natural_log_button: {
                String text = displayView.getText().toString();
                appendFunction(text, "ln");
                break;
            }

            case R.id.log_button: {
                String text = displayView.getText().toString();
                appendFunction(text, "log");
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

            //TODO: finish this
            case R.id.exponent_n_button:
                displayView.setText(Html.fromHtml("x<sup>n</sup>"));
                break;

            //TODO: remove this
            case R.id.keyboardButton:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                break;

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

            case R.id.eulers_button:
                appendValue(b_eulers.getText());
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
                appendFunction(text, sinText);
                break;
            }

            case R.id.cos_button: {
                String text = displayView.getText().toString();
                appendFunction(text, cosText);
                break;
            }

            case R.id.tan_button: {
                String text = displayView.getText().toString();
                appendFunction(text, tanText);
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

    protected void handleShift(){

        SHIFT_DOWN = !(SHIFT_DOWN);

        if(SHIFT_DOWN){
            b_sin.setText(arcsinText);
            b_sin.setBackgroundColor(getResources().getColor(R.color.button_on_shift_color));

            b_cos.setText(arccosText);
            b_cos.setBackgroundColor(getResources().getColor(R.color.button_on_shift_color));

            b_tan.setText(arctanText);
            b_tan.setBackgroundColor(getResources().getColor(R.color.button_on_shift_color));
        }
        else {
            b_sin.setText(Html.fromHtml(sinText));
            b_sin.setBackgroundColor(getResources().getColor(R.color.function_btn_default));

            b_cos.setText(Html.fromHtml(cosText));
            b_cos.setBackgroundColor(getResources().getColor(R.color.function_btn_default));

            b_tan.setText(Html.fromHtml(tanText));
            b_tan.setBackgroundColor(getResources().getColor(R.color.function_btn_default));
        }

    }

    private void handleExponentiation(String text, String power) {
        text = "(" + text + ")^" + power;
        evaluateExpression(text);
    }

    private void appendFunction(String text, String function) {
        if(text.isEmpty()){
            displayView.setText( function + "(" );
        }
        else {
            displayView.setText( function + "(" + text + ")" );
        }
    }

    //TODO: delete this, no longer necessary with appendFunction
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
    protected void showHistoryDialog() {

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
    protected void evaluateExpression(String expStr){

        String originalExpression = expStr;

        //Variable that keeps track of whether the user used PI in the current expression
        boolean usesPI = false;

        try {

            if(expStr.contains(PI_SYMBOL)){
                expStr = expStr.replaceAll(PI_SYMBOL, "pi");
                usesPI = true;
            }

            ExpressionNode expr = mParser.parse(expStr);

            expr.accept(new SetVariable("pi", Math.PI));

            String result = expr.getValue()+"";

            Log.v(TAG, "The value of the expression is " + expr.getValue());

            displayView.setText("");
            appendValue(result);
            addToHistory(originalExpression, result);

        }
        catch (ParserException e)
        {
            Log.e(TAG, e.getMessage());
            if(usesPI){
                //TODO : MAKE THIS AN ALERT DIALOG
                Toast.makeText(this,
                        "Please include operator (+,-,/,*) between " + PI_SYMBOL + " and adjacent numbers or parentheses.",
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
