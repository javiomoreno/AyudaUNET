package com.example.ayudaunet;

import org.w3c.dom.Text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import BaseDatos.*;

public class Actividadguardar extends Activity
{
	private static final int NUM_ROWS = 1;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	private TablaMaterias obj_mat;
	private TablaParciales obj_par;
	private Cursor materias, parciales;
	int filas_materias = 0;

	Button n_materia;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardar_notas);
			
		n_materia = (Button) findViewById(R.id.n_materia);
		
		populateButtons();
		
		obj_mat = new TablaMaterias(this);
		obj_par = new TablaParciales(this);
		
		materias = obj_mat.DevolverMaterias();
		if(materias.moveToFirst())
		{
			n_materia.setVisibility(View.VISIBLE);
			filas_materias = materias.getCount();			
			populateMaterias();
		}
		else
			n_materia.setVisibility(View.INVISIBLE);
	}
	
	public void onClickNuevoSemestre(View v) 
	{
		if(materias.moveToFirst())
    	{
			new AlertDialog.Builder(this)
		      .setIcon(android.R.drawable.ic_dialog_alert)
		      .setTitle("Borrar")
		      .setMessage("Seguro desea borrar todo.?")
		      .setNegativeButton(android.R.string.cancel, null)//sin listener
		      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() 
		      {
			        @Override
			        public void onClick(DialogInterface dialog, int which)
			        {
			        	obj_mat.EliminarTodasMaterias();
				    	obj_par.EliminarTodo();
			    		Intent i = new Intent(Actividadguardar.this, Actividadcargarnuevosemestre.class );
			            startActivity(i);
			            Actividadguardar.this.finish();
			        }
		      })
		      .show();
    	}
		else
		{
			Intent i = new Intent(Actividadguardar.this, Actividadcargarnuevosemestre.class );
            startActivity(i);
		}
			
	}
	
	public void onClickNuevoMateria(View v)
	{
		Intent i = new Intent(this, Actividadagregarnuevamateria.class );
        startActivity(i);
        this.finish();
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
	        this.finish();
	        Intent i = new Intent(this, Actividadprincipal.class );
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
	
	@SuppressLint("NewApi") private void populateMaterias() 
	{
		TableLayout table = (TableLayout) findViewById(R.id.tablaMaterias);
		double porce, acumulado;
		int nota, i = 0;
		for (int row = 0; row != (filas_materias+1); row++) 
		{
			TableRow tableRow = new TableRow(this); 
			acumulado = 0;
			tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
			table.addView(tableRow);
			for (int col = 0; col != 3; col++)
			{
				TextView texto = new TextView(this);
				if(row == 0)
				{
					texto.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Large);
					if(col == 0)
					{
						texto.setHeight(40);
						texto.setText("Nombre Materia");
					}
					else if(col == 1)
						texto.setText("Acu.");
					else if(col == 2)
						texto.setText("Def.");
				}
				else
				{
					texto.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
					if(col == 0)
					{
						final String Materia = materias.getString(materias.getColumnIndex(TablaMaterias.CN_NAME));
						texto.setMaxEms(10);
						texto.setEllipsize(TruncateAt.END);
						texto.setHorizontallyScrolling(false);
					    texto.setSingleLine();
						texto.setText(Materia);
						texto.setHeight(40);
						texto.setOnClickListener(new View.OnClickListener()
						{
							@Override public void onClick(View v) 
							{ 
								gridMateriaClicked(Materia); 
							}

							private void gridMateriaClicked(String string) 
							{
								Toast.makeText(Actividadguardar.this, "Materia Seleccionada: "+string, Toast.LENGTH_SHORT).show();
								Intent i = new Intent(Actividadguardar.this, Actividadvermateria.class);
								i.putExtra("materia", string);
			    				startActivity(i);
							}
						});
					}
					else if(col == 1)
					{
						
						parciales = obj_par.DevolverTodoPorMateria(materias.getInt(materias.getColumnIndex(TablaMaterias.CN_ID)));
						parciales.moveToFirst(); 
						while (parciales.isAfterLast() == false) 
						{ 
							porce = (parciales.getFloat(parciales.getColumnIndex(TablaParciales.CN_PORCENTAJE))) / 100;
							nota = parciales.getInt(parciales.getColumnIndex(TablaParciales.CN_NOTA));
							if(nota != 0)
								acumulado += (porce * new TablaConversion().DevolverNumero(nota));
							parciales.moveToNext(); 
						}
						if(acumulado == 0)
							texto.setText("0");
						else
							texto.setText(""+Math.rint(acumulado*100)/100);
						materias.moveToNext();
					}
					else if(col == 2)
						texto.setText(""+(long)(Math.rint(acumulado*100)/100 + 0.5D));
				}
				tableRow.addView(texto);
				
			}
		}
	}

}
