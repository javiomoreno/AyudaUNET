package com.example.ayudaunet;

import org.w3c.dom.Text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Actividadtabla extends Activity 
{
	EditText campo;
	TextView valor_tabla;
	TablaConversion obj = new TablaConversion();
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabla_convercion);
		
		campo = (EditText) findViewById(R.id.editText1);
		valor_tabla = (TextView) findViewById(R.id.textView1);
		populateButtons();
	}
	
	public void onClickConvertir(View v) 
	{
		valor_tabla.setText("");
    	String textoOriginal = campo.getText().toString();
    	if(isNumeric(textoOriginal))
    	{
    		if(Integer.parseInt(textoOriginal) >= 0 && Integer.parseInt(textoOriginal) <= 100)
    		{
	    		float resultado = obj.DevolverNumero(Integer.parseInt(textoOriginal));
	    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputMethodManager.hideSoftInputFromWindow(campo.getWindowToken(), 0);
	            valor_tabla.setVisibility(1);
	            valor_tabla.setText("El resultado es: "+resultado);
    		}
    		else
    			Toast.makeText(this, "Debe ser un número entre 0 y 100", Toast.LENGTH_SHORT).show();
    	}
    	else
    		Toast.makeText(this, "El Texto Ingresado no es un Número ", Toast.LENGTH_SHORT).show();
    }
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	@SuppressLint("NewApi") private void populateButtons() 
	{
		TableLayout table = (TableLayout) findViewById(R.id.tableTablaConversion);
		for (int row = 0; row != NUM_ROWS; row++) 
		{
			TableRow tableRow = new TableRow(this); 
			tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
			table.addView(tableRow);
			for (int col = 0; col != NUM_COLS; col++)
			{
				final int FINAL_COL = col; 
				final int FINAL_ROW = row;
				Button button = new Button(this);
				button.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f)); 
			
				if(row == 0)
				{
					if(col == 0)
					{
						button.setBackgroundResource(R.drawable.tabla_titulo);
					}
					else
					{
						button.setBackgroundResource(R.drawable.atras);
					}
				}
				
				button.setOnClickListener(new View.OnClickListener()
				{
					@Override public void onClick(View v) 
					{ 
						gridButtonClicked(FINAL_COL, FINAL_ROW); 
					}
				});
				
				tableRow.addView(button); buttons[row][col] = button;
			}
		}
	}
	@SuppressLint("NewApi") private void gridButtonClicked(int col, int row)
	{
		// Lock Button Sizes:
		lockButtonSizes(); 
		if(row == 0 && col == 1)
		{
	        Actividadtabla.this.finish();
		}
		
		else if(row == 1 && col == 0)
		{
			Intent i = new Intent(this, Actividadtabla.class );
	        startActivity(i);
		}
	}
	
	private void lockButtonSizes()
	{
		for (int row = 0; row != NUM_ROWS; row++)
		{
			for (int col = 0; col != NUM_COLS; col++)
			{
				Button button = buttons[row][col];
				int width = button.getWidth(); 
				button.setMinWidth(width); 
				button.setMaxWidth(width);
				int height = button.getHeight(); 
				button.setMinHeight(height);
				button.setMaxHeight(height);
			}
		}
	}

}
