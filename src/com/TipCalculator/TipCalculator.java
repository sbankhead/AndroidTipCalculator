package com.TipCalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class TipCalculator extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);

		Button btnLeftTip = (Button) findViewById(R.id.btLeftTip);
		Button btnMiddleTip = (Button) findViewById(R.id.btMiddleTip);
		Button btnRightTip = (Button) findViewById(R.id.btRightTip);
		EditText etCustomTip = (EditText) findViewById(R.id.etCustomTip);
		EditText etMealAmount = (EditText) findViewById(R.id.etMealAmount);

		btnLeftTip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Update(10);
			}
		});
		btnMiddleTip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Update(15);
			}
		});
		btnRightTip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Update(20);
			}
		});
		etCustomTip.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {			
				// Get the value of the custom box, make sure its > 0 and call ClickButton
				double CustomTipValue = ConvertEditTextToDouble((EditText) findViewById(R.id.etCustomTip));
				if (CustomTipValue > 0) {
					Update((int) CustomTipValue);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
		});
		etMealAmount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// Get the value of the mealAmount box, make sure its > 0 and call ClickButton
				double MealAmount = ConvertEditTextToDouble((EditText) findViewById(R.id.etMealAmount));
				double CustomTipValue = ConvertEditTextToDouble((EditText) findViewById(R.id.etCustomTip));
				if (MealAmount > 0 && CustomTipValue > 0) {
					Update((int) CustomTipValue);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calculator, menu);
		return true;
	}

	public void Update(int a_TipPercent) {
		// Get the Meal Amount from EditText field
		double mealAmount = ConvertEditTextToDouble((EditText) findViewById(R.id.etMealAmount));

		// Update the display with the new values
		SetDisplay(mealAmount, a_TipPercent);
	}

	private double ConvertEditTextToDouble(EditText a_EditText) {
		double ReturnValue = 0.00;
		try {
			ReturnValue = Double.parseDouble(a_EditText.getText().toString());
		} catch (NumberFormatException a_Exception) {
			// TODO:Handle exception meaningfully
		}
		return ReturnValue;
	}

	private double CalculateTip(double a_MealAmount, int a_TipPercent) {
		// Check if not null, convert the percent to decimal value, and multiply
		// by the meal amount
		if (a_TipPercent > 0) {
			return a_MealAmount * (a_TipPercent * .01);
		}
		return 0;
	}

	private void SetDisplay(double a_MealAmount, Integer a_TipPercent) {
		//Calculate some values to use to set the display
		String strTipAmount = String.format("%.2f", CalculateTip(a_MealAmount, a_TipPercent));
		
		//We parse back in the formatted double to get the rounded value
		double dblTipAmount = Double.parseDouble(strTipAmount); 

		// Retreive the diplay elements to modify
		EditText etTipPercent = (EditText) findViewById(R.id.etCustomTip);
		TextView tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
		TextView tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);

		//Check if the value is the same, if so, dont change anything otherwise we infinite loop with the onTextChanged listener
		String CustomTextValue = etTipPercent.getText().toString();
		if (!CustomTextValue.equalsIgnoreCase(a_TipPercent.toString())) {
			etTipPercent.setText(a_TipPercent.toString());
		}

		// Modify the display elements with the calculated values
		tvTipAmount.setText("Tip Amount: $" + strTipAmount);
		tvTotalAmount.setText(String.format("Total Amount: $%.2f", dblTipAmount + a_MealAmount));
	}
}
