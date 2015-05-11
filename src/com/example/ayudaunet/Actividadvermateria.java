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
import android.widget.TextView;
import android.widget.Toast;

public class Actividadvermateria extends Activity 
{
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	TextView nombre_mat;
	private EditText[] notas = new EditText[4];
	private TextView nota_4, acumulado, definitiva;
	private CheckBox[] check = new CheckBox[4];
	String nombre_materia;
	Cursor c_par, c_mat;
	private int cod_mate, ite;
	private TablaMaterias obj_mat;
	private TablaParciales obj_par;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ver_materia);
		
		nombre_materia = getIntent().getStringExtra("materia");
		
		obj_mat = new TablaMaterias(this);
		obj_par = new TablaParciales(this);
		
		c_mat = obj_mat.DevolverIdMateria(nombre_materia);
		c_mat.moveToFirst();
		
		nombre_mat = (TextView) findViewById(R.id.nombre_materia);
		notas[0] = (EditText) findViewById(R.id.nota_1_mostrar);
		notas[1] = (EditText) findViewById(R.id.nota_2_mostrar);
		notas[2] = (EditText) findViewById(R.id.nota_3_mostrar);
		notas[3] = (EditText) findViewById(R.id.nota_4_mostrar);
		check[0] = (CheckBox) findViewById(R.id.checkBox1);
		check[1] = (CheckBox) findViewById(R.id.checkBox2);
		check[2] = (CheckBox) findViewById(R.id.checkBox3);
		check[3] = (CheckBox) findViewById(R.id.checkBox4);
		nota_4 =(TextView) findViewById(R.id.textView4);
		acumulado =(TextView) findViewById(R.id.acu_mostrar);
		definitiva =(TextView) findViewById(R.id.def_mostrar);		
		
		nombre_mat.setText(nombre_materia);
		
		check[0].setOnCheckedChangeListener(
		        new CheckBox.OnCheckedChangeListener() {
		            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		                if (isChecked) {
		                	notas[0].setEnabled(false);
		                }
		                else {
		                	notas[0].setEnabled(true);
		            }
		        }
		    });
		
		check[1].setOnCheckedChangeListener(
		        new CheckBox.OnCheckedChangeListener() {
		            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		                if (isChecked) {
		                	notas[1].setEnabled(false);
		                }
		                else {
		                	notas[1].setEnabled(true);
		            }
		        }
		    });
		
		check[2].setOnCheckedChangeListener(
		        new CheckBox.OnCheckedChangeListener() {
		            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		                if (isChecked) {
		                	notas[2].setEnabled(false);
		                }
		                else {
		                	notas[2].setEnabled(true);
		            }
		        }
		    });
		
		check[3].setOnCheckedChangeListener(
		        new CheckBox.OnCheckedChangeListener() {
		            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		                if (isChecked) {
		                	notas[3].setEnabled(false);
		                }
		                else {
		                	notas[3].setEnabled(true);
		            }
		        }
		    });
		
		MostrarNotas(c_mat.getInt(c_mat.getColumnIndex(TablaMaterias.CN_ID)));
		populateButtons();
	}

	private void MostrarNotas(int cod_materia) 
	{
		c_par = obj_par.DevolverTodoPorMateria(cod_materia);	
		c_par.moveToFirst();
				
		if(c_par.getCount() == 3)
		{
			notas[3].setVisibility(View.INVISIBLE);
			nota_4.setVisibility(View.INVISIBLE);
			check[3].setVisibility(View.INVISIBLE);
			CargarNotas(c_par);
		}
		else
		{
			CargarNotas(c_par);
		}
		CargarDefinitiva(c_par);
	}
	
	private void CargarDefinitiva(Cursor c_par2) 
	{
		int not = 0;
		double porce, acumu = 0;
		
		c_par2.moveToFirst(); 
		while (c_par2.isAfterLast() == false) 
		{ 
			porce = (c_par2.getFloat(c_par2.getColumnIndex(TablaParciales.CN_PORCENTAJE))) / 100;
			not = c_par2.getInt(c_par2.getColumnIndex(TablaParciales.CN_NOTA));
			if(not != 0)
				acumu += (porce * new TablaConversion().DevolverNumero(not));
			c_par2.moveToNext(); 
		}
		if(acumu == 0)
			acumulado.setText("0");
		else
			acumulado.setText("Acu: "+Math.rint(acumu*100)/100);
		definitiva.setText("Def: "+(long)(Math.rint(acumu*100)/100 + 0.5D));
	}
	
	private void CargarNotas(Cursor c_par2) 
	{
		int i = 0;
		
		while(c_par2.isAfterLast() == false)
		{
			notas[i].setText(""+c_par.getInt(c_par.getColumnIndex(TablaParciales.CN_NOTA)));
			check[i].setText(""+c_par.getInt(c_par.getColumnIndex(TablaParciales.CN_PORCENTAJE))+" %");
			c_par.moveToNext();
			i ++;
		}
	}

	public void onClickEditar(View v) 
	{
		c_par.moveToFirst();
		
		int j = 0;
		
		while(c_par.isAfterLast() == false)
		{
			obj_par.ModificarNota(c_par.getInt(c_par.getColumnIndex(TablaParciales.CN_ID)), Integer.parseInt(""+notas[j].getText()));
			c_par.moveToNext();
			j ++;
		}
		
		Actividadvermateria.this.finish();
		Intent i = new Intent(this, Actividadvermateria.class );
		i.putExtra("materia", nombre_materia);
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
	        Actividadvermateria.this.finish();
	        Intent i = new Intent(this, Actividadguardar.class );
	        startActivity(i);
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
