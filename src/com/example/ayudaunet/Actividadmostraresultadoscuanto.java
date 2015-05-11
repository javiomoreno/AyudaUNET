package com.example.ayudaunet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Actividadmostraresultadoscuanto extends Activity 
{
	TablaConversion obj = new TablaConversion();
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	TextView nota, titulo, nota2, acumulado, nota3, titulo2;
	TablaConversion obj_tabla = new TablaConversion();
	double sumatoria, porce_3, aux1, aux2;
	int tipo = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_resultados_cuanto);	
		
		nota = (TextView) findViewById(R.id.resultado);
		titulo = (TextView) findViewById(R.id.titulo);
		acumulado = (TextView) findViewById(R.id.acumulado);
		nota2 = (TextView) findViewById(R.id.nota2);
		nota3 = (TextView) findViewById(R.id.nota3);
		titulo2 = (TextView) findViewById(R.id.titulo2);
		
		sumatoria = getIntent().getDoubleExtra("resultado", 0);
		porce_3 = Math.rint(getIntent().getDoubleExtra("porce_3", 0)*100)/100;
		tipo = getIntent().getIntExtra("tipo", 0);
		if(tipo == 1)
		{
			titulo2.setText("Para el Tercer Parcial debes sacar: ");
		}
		
		if(tipo == 2)
		{
			titulo2.setText("Para el Cuarto Parcial debes sacar: ");
		}
		
		CalcularCuantoFalta();
				
		populateButtons();
		
	}
	
	public void onClickAtras(View v) 
	{
		Actividadmostraresultadoscuanto.this.finish();
    }
	
	private void CalcularCuantoFalta() 
	{
		if(sumatoria < 4.5)
		{
			aux1 = (4.5 - sumatoria); 
			aux2 = Math.rint(aux1*100)/100;
			aux1 = aux2 / porce_3;
			aux2 = Math.rint(aux1*10)/10;
			if(obj.DevolverNota((float) aux2) == -1)
			{
				titulo.setText("Estas Fuera de escala..!!");
				acumulado.setText("Llevas Acumulado: "+sumatoria+" pts.");
				nota.setText("Ya no puedes pasar la materia.");
			}
			else
			{
				titulo.setText("Aun la puedes Pasar..!!");
				acumulado.setText("Llevas Acumulado: "+sumatoria+" pts.");
				if(obj.DevolverNota((float) aux2) == 0)
					nota.setText("Para el 5 menos de 7 pts.");
				else
					nota.setText("Para el 5: "+obj.DevolverNota((float) aux2)+" pts.");
				if(DejarlaMasAlta(5.5))
				{
					nota2.setText("Para el 6: "+obj.DevolverNota((float) aux2)+" pts.");
				}
				if(DejarlaMasAlta(6.5))
				{
					nota3.setText("Para el 7: "+obj.DevolverNota((float) aux2)+" pts.");
				}
			}
		}
		else if(sumatoria >= 4.5 && sumatoria < 5.5)
		{
			titulo.setText("Ya La Pasaste..!!");
			aux1 = (5.5 - sumatoria);
			aux2 = Math.rint(aux1*100)/100;
			aux1 = aux2 / porce_3;
			aux2 = Math.rint(aux1*10)/10;
			acumulado.setText("Levas Acumulado: "+sumatoria+" pts.");
			if(obj.DevolverNota((float) aux2) == 0)
				nota.setText("Para el 6 menos de 7 pts.");
			else
				nota.setText("Para el 6: "+obj.DevolverNota((float) aux2)+" pts.");
			if(DejarlaMasAlta(6.5))
			{
				nota2.setText("Para el 7: "+obj.DevolverNota((float) aux2)+" pts.");
			}
			if(DejarlaMasAlta(7.5))
			{
				nota3.setText("Para el 8: "+obj.DevolverNota((float) aux2)+" pts.");
			}
		}
		else if(sumatoria >= 5.5 && sumatoria < 6.5)
		{
			titulo.setText("Ya La Pasaste..!!");
			aux1 = (6.5 - sumatoria);
			aux2 = Math.rint(aux1*100)/100;
			aux1 = aux2 / porce_3;
			aux2 = Math.rint(aux1*10)/10;
			acumulado.setText("Levas Acumulado: "+sumatoria+" pts.");
			if(obj.DevolverNota((float) aux2) == 0)
				nota.setText("Para el 7 menos de 7 pts.");
			else
				nota.setText("Para el 7: "+obj.DevolverNota((float) aux2)+" pts.");
			if(DejarlaMasAlta(7.5))
			{
				nota2.setText("Para el 8: "+obj.DevolverNota((float) aux2)+" pts.");
			}
			if(DejarlaMasAlta(8.5))
			{
				nota3.setText("Para el 9: "+obj.DevolverNota((float) aux2)+" pts.");
			}
		}
		else if(sumatoria >= 6.5 && sumatoria < 7.5)
		{
			titulo.setText("Ya La Pasaste..!!");
			aux1 = (7.5 - sumatoria);
			aux2 = Math.rint(aux1*100)/100;
			aux1 = aux2 / porce_3;
			aux2 = Math.rint(aux1*10)/10;
			acumulado.setText("Levas Acumulado: "+sumatoria+" pts.");
			if(obj.DevolverNota((float) aux2) == 0)
				nota.setText("Para el 8 menos de 7 pts.");
			else 
				nota.setText("Para el 8: "+obj.DevolverNota((float) aux2)+" pts.");
			if(DejarlaMasAlta(8.5))
			{
				nota2.setText("Para el 9: "+obj.DevolverNota((float) aux2)+" pts.");
			}
		}
		else if(sumatoria >= 7.5 && sumatoria < 8.5)
		{
			titulo.setText("Ya La Pasaste..!!");
			aux1 = (8.5 - sumatoria);
			aux2 = Math.rint(aux1*100)/100;
			aux1 = aux2 / porce_3;
			aux2 = Math.rint(aux1*10)/10;
			acumulado.setText("Levas Acumulado: "+sumatoria+" pts.");
			if(obj.DevolverNota((float) aux2) == 0)
				nota.setText("Para el 9 menos de 7 pts.");
			else
				nota.setText("Para el 9: "+obj.DevolverNota((float) aux2)+" pts.");
		}
	}

	private boolean DejarlaMasAlta(double doub) 
	{
		aux1 = (doub - sumatoria);
		aux2 = Math.rint(aux1*100)/100;
		aux1 = aux2 / porce_3;
		aux2 = Math.rint(aux1*10)/10;
		if(obj.DevolverNota((float) aux2) != -1 )
			return true;
		else
			return false;
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
	        Actividadmostraresultadoscuanto.this.finish();
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
