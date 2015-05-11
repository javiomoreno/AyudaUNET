package com.example.ayudaunet;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Actividadcuanto extends Activity
{

	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	EditText porcentaje_1, porcentaje_2, porcentaje_3, nota_1, nota_2, nota_3;
	TablaConversion obj = new TablaConversion(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cuanto_falta);
		
		porcentaje_1 = (EditText) findViewById(R.id.porcentaje_1);
		porcentaje_2 = (EditText) findViewById(R.id.porcentaje_2);
		porcentaje_3 = (EditText) findViewById(R.id.porcentaje_3);
		nota_1 = (EditText) findViewById(R.id.nota_1);
		nota_2 = (EditText) findViewById(R.id.nota_2);
		nota_3 = (EditText) findViewById(R.id.nota_3);
				
		populateButtons();
	}
	
	public void onClickCalcular(View v) 
	{
		int suma_porcentaje = 0;
		double resultado = 0;
		
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		if(porcentaje_1.getText().toString().equals("") || porcentaje_2.getText().toString().equals("") || nota_1.getText().toString().equals("") || nota_2.getText().toString().equals(""))
		{
			Toast.makeText(this, "Los Campos N°1 y N°2 son Obligatorios", Toast.LENGTH_SHORT).show();
		}
		else
		{
			if(porcentaje_3.getText().toString().equals(""))
			{
				suma_porcentaje = (Integer.parseInt(porcentaje_1.getText().toString()) + Integer.parseInt(porcentaje_2.getText().toString()));
	    		if(suma_porcentaje > 0 && suma_porcentaje < 100)
	    		{
	    			if((Integer.parseInt(nota_1.getText().toString()) >= 0 && Integer.parseInt(nota_1.getText().toString()) <= 100) && (Integer.parseInt(nota_2.getText().toString()) >= 0 && Integer.parseInt(nota_2.getText().toString()) <= 100 ))
	    			{
	    				Intent i = new Intent (Actividadcuanto.this, Actividadmostraresultadoscuanto.class);	    				
	    				resultado = CalcularNota(Float.parseFloat(porcentaje_1.getText().toString()), Float.parseFloat(porcentaje_2.getText().toString()), Integer.parseInt(nota_1.getText().toString()), Integer.parseInt(nota_2.getText().toString()));
	    				i.putExtra("resultado", resultado);
	    				double porcent_3 = (100 - (Float.parseFloat(porcentaje_1.getText().toString())+ Float.parseFloat(porcentaje_2.getText().toString()))) / 100;
	    				i.putExtra("porce_3", porcent_3);
	    				i.putExtra("tipo", 1);
	    				startActivity(i);
	    			}
	    			else
	    				Toast.makeText(this, "Las notas deben estar entre 0 y 100", Toast.LENGTH_SHORT).show();
	    		}
	    		else
	    			Toast.makeText(this, "La suma de los porcentajes debe ser entre 0 y 100", Toast.LENGTH_SHORT).show();
			}
			else
			{	
				suma_porcentaje = (Integer.parseInt(porcentaje_1.getText().toString()) + Integer.parseInt(porcentaje_2.getText().toString()) + Integer.parseInt(porcentaje_3.getText().toString()));
	    		if(suma_porcentaje > 0 && suma_porcentaje < 100)
	    		{
	    			if(nota_3.getText().toString().equals(""))
	    			{
	    				Toast.makeText(this, "El campo N°3 es Obligatorio", Toast.LENGTH_SHORT).show();
	    			}
	    			else
	    			{
		    			if((Integer.parseInt(nota_1.getText().toString()) >= 0 && Integer.parseInt(nota_1.getText().toString()) <= 100) && (Integer.parseInt(nota_2.getText().toString()) >= 0 && Integer.parseInt(nota_2.getText().toString()) <= 100 ) && (Integer.parseInt(nota_3.getText().toString()) >= 0 && Integer.parseInt(nota_3.getText().toString()) <= 100 ))
		    			{
		    				Intent i = new Intent (Actividadcuanto.this, Actividadmostraresultadoscuanto.class);	    				
		    				resultado = CalcularNota2(Float.parseFloat(porcentaje_1.getText().toString()), Float.parseFloat(porcentaje_2.getText().toString()), Float.parseFloat(porcentaje_3.getText().toString()), Integer.parseInt(nota_1.getText().toString()), Integer.parseInt(nota_2.getText().toString()), Integer.parseInt(nota_3.getText().toString()));
		    				i.putExtra("resultado", resultado);
		    				double porcent_3 = (100 - (Float.parseFloat(porcentaje_1.getText().toString())+ Float.parseFloat(porcentaje_2.getText().toString()) + Float.parseFloat(porcentaje_3.getText().toString()))) / 100;
		    				i.putExtra("porce_3", porcent_3);
		    				i.putExtra("tipo", 2);
		    				startActivity(i);		    				
		    			}
		    			else
		    				Toast.makeText(this, "Las notas deben estar entre 0 y 100", Toast.LENGTH_SHORT).show();
	    			}
	    		}
	    		else
	    			Toast.makeText(this, "La suma de los porcentajes debe ser entre 0 y 100", Toast.LENGTH_SHORT).show();
	    	}
		}
    }
	
	private double CalcularNota(float por_1, float por_2, int not_1, int not_2) 
	{
		double porce_1, porce_2, sumatoria;
		porce_1 = por_1 / 100;
		porce_2 = por_2 / 100;
		
		sumatoria = (porce_1 * obj.DevolverNumero(not_1)) + (porce_2 * obj.DevolverNumero(not_2));
		return Math.rint(sumatoria*100)/100;
	}
	
	private double CalcularNota2(float por_1, float por_2, float por_3, int not_1, int not_2, int not_3) 
	{
		double porce_1, porce_2, porce_3, sumatoria;
		porce_1 = por_1 / 100;
		porce_2 = por_2 / 100;
		porce_3 = por_3 / 100;
		
		sumatoria = (porce_1 * obj.DevolverNumero(not_1)) + (porce_2 * obj.DevolverNumero(not_2)) + (porce_3 * obj.DevolverNumero(not_3));
		return Math.rint(sumatoria*100)/100;
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
						button.setBackgroundResource(R.drawable.cuanto_titulo);
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
	        Actividadcuanto.this.finish();
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
