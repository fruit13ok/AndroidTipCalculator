package com.example.tipapp2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
//import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
//import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity
{
   DecimalFormat money;
   public TextView tvTipResult;
   public double tip;
   public double billAmount;
   public double selectedPercentage;
   public double numOfPeopleSplit;
   
   public EditText etBillAmount;
   public String billAmountStr;
   public double billAmountNum;
   
   public EditText etCustomTipPercentage;
   public String customTipPercentStr;
   public double customTipPercentNum;
   
   public EditText etSplit;
   public String numOfPeopleSplitStr;
   public double numOfPeopleSplitNum;
   
   //try to make EditText to change SeekBar too, but not work
   //logic tells me 2 call back method effect each other might be infinite loop
   public SeekBar sbCustomTipPercentage;
   
   public RelativeLayout rlTipApp;
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      init();
      listeners();

      //SetOnEditorActionListener need to press enter to take action
//      etBillAmount.setOnEditorActionListener(new OnEditorActionListener()
//      {
//         @Override
//         public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
//         {
//            if (event != null || actionId == EditorInfo.IME_ACTION_DONE)
//            {
//               tvTestListener.setText(etBillAmount.getText().toString());
//               return true;
//            }
//            else
//               return false;
//         }
//      });
   }

   public void init()
   {
      money = new DecimalFormat("$0.00");
      tvTipResult = (TextView) findViewById(R.id.tvTipResult);
      tip = 0;
      billAmount = 0;
      selectedPercentage = 10;
      numOfPeopleSplit = 1;
      
      etBillAmount = (EditText) findViewById(R.id.etBillAmount);
      billAmountStr = null;
      billAmountNum = billAmount;
      
      etCustomTipPercentage = (EditText) findViewById(R.id.etCustomTipPercentage);
      customTipPercentStr = null;
      customTipPercentNum = selectedPercentage;
      
      etSplit = (EditText) findViewById(R.id.etSplit);
      numOfPeopleSplitStr = null;
      numOfPeopleSplitNum = numOfPeopleSplit;
      
      sbCustomTipPercentage = (SeekBar) findViewById(R.id.sbCustomTipPercentage);
      sbCustomTipPercentage.setProgress(0);
      sbCustomTipPercentage.setMax(10000);
      
      rlTipApp = (RelativeLayout) findViewById(R.id.rlTipApp);
      //change background from green to red or to image
      //rlTipApp.setBackgroundColor(Color.parseColor("#FF0000"));
      rlTipApp.setBackgroundResource(R.drawable.despicable_me_minions);
   }
   
   public void listeners()
   {
      //addTextChangedListener take action just by inputing anything
      etBillAmount.addTextChangedListener(new TextWatcher()
      {
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count)
         {
            // Fires right as the text is being changed (even supplies the range of text)
         }
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {
            // Fires right before text is changing
         }
         @Override
         public void afterTextChanged(Editable s)
         {
            // Fires right after the text has changed
            if(!isEmptyString(etBillAmount))
            {
               billAmountStr = etBillAmount.getText().toString();
               billAmountNum = Double.valueOf(billAmountStr);
               billAmount = billAmountNum;
               tip = billAmount * selectedPercentage / 100 / numOfPeopleSplit;
               tvTipResult.setText(money.format(tip));
            }
            else
            {
               tvTipResult.setText(money.format(0));
            }
         }
      });
      
      etCustomTipPercentage.addTextChangedListener(new TextWatcher()
      {
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count)
         {
         }
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {
         }
         @Override
         public void afterTextChanged(Editable s)
         {
            if(!isEmptyString(etCustomTipPercentage))
            {
               customTipPercentStr = etCustomTipPercentage.getText().toString();
               customTipPercentNum = Double.valueOf(customTipPercentStr);
               selectedPercentage = customTipPercentNum;
               tip = billAmount * selectedPercentage / 100 / numOfPeopleSplit;
               tvTipResult.setText(money.format(tip));
            }
            else
            {
               tvTipResult.setText(money.format(0));
            }
         }
      });
      
      etSplit.addTextChangedListener(new TextWatcher()
      {
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count)
         {
         }
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {
         }
         @Override
         public void afterTextChanged(Editable s)
         {
            if(!isEmptyString(etSplit))
            {
               numOfPeopleSplitStr = etSplit.getText().toString();
               numOfPeopleSplitNum = Double.valueOf(numOfPeopleSplitStr);
               numOfPeopleSplit = numOfPeopleSplitNum;
               tip = billAmount * selectedPercentage / 100 / numOfPeopleSplit;
               tvTipResult.setText(money.format(tip));
            }
            else
            {
               tvTipResult.setText(money.format(0));
            }
         }
      });
      
      sbCustomTipPercentage.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
      {
         @Override
         public void onStopTrackingTouch(SeekBar seekBar)
         {
         }
         @Override
         public void onStartTrackingTouch(SeekBar seekBar)
         {
         }
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
         {
            etCustomTipPercentage.setText(Double.toString((double)progress / 100));
         }
      });
   }

   public boolean isEmptyString(EditText et)
   {
      return (et.length() == 0);
   }
   
   public void calculateTip()
   {
      if(!isEmptyString(etBillAmount))
      {
         billAmountStr = etBillAmount.getText().toString();
         billAmountNum = Double.valueOf(billAmountStr);
         tip = billAmountNum * selectedPercentage /100;
         tvTipResult.setText(money.format(tip));
      }
      else
      {
         tvTipResult.setText(money.format(0));
      }
   }
   
   public void onClick10pc(View v)
   {
      selectedPercentage = 10;
      etCustomTipPercentage.setText("10.00");
      calculateTip();
   }
   
   public void onClick15pc(View v)
   {
      selectedPercentage = 15;
      etCustomTipPercentage.setText("15.00");
      calculateTip();
   }
   
   public void onClick20pc(View v)
   {
      selectedPercentage = 20;
      etCustomTipPercentage.setText("20.00");
      calculateTip();
   }
   
   public void onClickSaveTip(View v)
   {
      String str = etCustomTipPercentage.getText().toString();
      try
      {
         FileOutputStream fOut = openFileOutput("textfile.txt", MODE_PRIVATE);
         OutputStreamWriter osw = new OutputStreamWriter(fOut);

         //---write the string to the file---
         osw.write(str);
         osw.flush(); 
         osw.close();

         //---display file saved message---
         Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();

      }
      catch (IOException ioe)
      {
         ioe.printStackTrace();
      }
   }
   
   public void onClickLoadTip(View v)
   {
      try
      {
         FileInputStream fIn = openFileInput("textfile.txt");
         InputStreamReader isr = new InputStreamReader(fIn);
            
         char[] inputBuffer = new char[100];
         String s = "";

         int charRead;
         while ((charRead = isr.read(inputBuffer))>0)
         {
            //---convert the chars to a String---
            String readString = String.copyValueOf(inputBuffer, 0, charRead);
            s += readString;

            inputBuffer = new char[100];
         }
         //---set the EditText to the text that has been read---
         etCustomTipPercentage.setText(s);

         Toast.makeText(getBaseContext(), "File loaded successfully!", Toast.LENGTH_SHORT).show();
      }
      catch (IOException ioe) {
         ioe.printStackTrace();
      }
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
}