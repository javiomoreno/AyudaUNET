package com.example.ayudaunet;

import BaseDatos.TablaMaterias;
import BaseDatos.TablaParciales;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Actividadagregarnuevamateria extends Activity
{

	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	TablaMaterias obj_mat;
	TablaParciales obj_par;
	int numero_colun;
	Cursor c;
	
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	
	EditText nombre_materia, porcentaje_1, porcentaje_2, porcentaje_3, porcentaje_4;
	CheckBox check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_nueva_materia);
		
		nombre_materia = (EditText) findViewById(R.id.nombre_materia);
		porcentaje_1 = (EditText) findViewById(R.id.porcentaje_parcial_1);
		porcentaje_2 = (EditText) findViewById(R.id.porcentaje_parcial_2);
		porcentaje_3 = (EditText) findViewById(R.id.porcentaje_parcial_3);
		porcentaje_4 = (EditText) findViewById(R.id.porcentaje_parcial_4);
		check = (CheckBox) findViewById(R.id.checkBox1);

	    check.setOnCheckedChangeListener(
	        new CheckBox.OnCheckedChangeListener() {
	            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	                if (isChecked) {
	                	porcentaje_4.setEnabled(true);
	                }
	                else {
	                	porcentaje_4.setEnabled(false);
	            }
	        }
	    });
		
		populateButtons();
	}
	
	public void onClickGuardarMateria(View v) 
	{
		int suma = 0;
		obj_mat = new TablaMaterias(this);
		obj_par = new TablaParciales(this);
		
		if(nombre_materia.getText().toString().equals("") || porcentaje_1.getText().toString().equals("") || porcentaje_2.getText().toString().equals("") || porcentaje_3.getText().toString().equals(""))
		{
			Toast.makeText(this, "Los Campos son Obligatorios", Toast.LENGTH_SHORT).show();
		}
		
		else
		{
			if(porcentaje_4.getText().toString().equals(""))
			{
				suma = Integer.parseInt(porcentaje_1.getText().toString()) + Integer.parseInt(porcentaje_2.getText().toString()) + Integer.parseInt(porcentaje_3.getText().toString());
				if(suma == 100)
				{
					Guardar_Materia();
					obj_par.InsertarParcial("Parcial 1", Integer.parseInt(porcentaje_1.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					obj_par.InsertarParcial("Parcial 3", Integer.parseInt(porcentaje_2.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					obj_par.InsertarParcial("Parcial 2", Integer.parseInt(porcentaje_3.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					Volver();
				}
				else
					Toast.makeText(Actividadagregarnuevamateria.this, "La Suma de los porcentajes debe ser igual a 100.!", Toast.LENGTH_SHORT).show();				
			}
			else
			{
				suma = Integer.parseInt(porcentaje_1.getText().toString()) + Integer.parseInt(porcentaje_2.getText().toString()) + Integer.parseInt(porcentaje_3.getText().toString()) + Integer.parseInt(porcentaje_4.getText().toString());
				if(suma == 100)
				{
					Guardar_Materia();
					obj_par.InsertarParcial("Parcial 1", Integer.parseInt(porcentaje_1.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					obj_par.InsertarParcial("Parcial 2", Integer.parseInt(porcentaje_2.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					obj_par.InsertarParcial("Parcial 3", Integer.parseInt(porcentaje_3.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					obj_par.InsertarParcial("Parcial 4", Integer.parseInt(porcentaje_4.getText().toString()), 0, Integer.parseInt(c.getString(numero_colun)));
					Volver();
				}
				else
					Toast.makeText(Actividadagregarnuevamateria.this, "La Suma de los porcentajes debe ser igual a 100.!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void Guardar_Materia() 
	{
		obj_mat.InsertarMateria(nombre_materia.getText().toString());
		c = obj_mat.DevolverIdMateria(nombre_materia.getText().toString());
		c.moveToFirst();
		numero_colun = c.getColumnIndex(TablaMaterias.CN_ID);		
	}

	private void Volver()
	{
		Actividadagregarnuevamateria.this.finish();
		Intent i = new Intent(this, Actividadguardar.class );
        startActivity(i);
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
						button.setBackgroundResource(R.drawable.cuanto_titulo);
					else
						button.setBackgroundResource(R.drawable.atras);
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
	        Actividadagregarnuevamateria.this.finish();
	        Intent i = new Intent(this, Actividadguardar.class );
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
