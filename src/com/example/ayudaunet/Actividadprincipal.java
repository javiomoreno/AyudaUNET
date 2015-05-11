package com.example.ayudaunet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Actividadprincipal extends Activity
{

	private static final int NUM_ROWS = 3;
	private static final int NUM_COLS = 2;
	Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_principal);
		
		populateButtons();
		
	}
	@SuppressLint("NewApi") private void populateButtons() 
	{
		TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
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
						button.setBackgroundResource(R.drawable.logo);
					}
					else
					{
						button.setBackgroundResource(R.drawable.salir);
					}
				}
				else if(row == 1)
				{
					if(col == 0)
					{
						button.setBackgroundResource(R.drawable.tabla);
					}
					else
					{
						button.setBackgroundResource(R.drawable.cuanto);
					}
				}
				else
				{
					if(col == 0)
					{
						button.setBackgroundResource(R.drawable.guarda);
					}
					else
					{
						button.setBackgroundResource(R.drawable.calcula);
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
			 new AlertDialog.Builder(this)
		      .setIcon(android.R.drawable.ic_dialog_alert)
		      .setTitle("Salir")
		      .setMessage("Est�s seguro de Salir?")
		      .setNegativeButton(android.R.string.cancel, null)//sin listener
		      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() 
		      {
			        @Override
			        public void onClick(DialogInterface dialog, int which)
			        {
			        	Actividadprincipal.this.finish();
			        }
		      })
		      .show();
		}
		
		else if(row == 1 && col == 0)
		{
			Intent i = new Intent(this, Actividadtabla.class );
	        startActivity(i);
		}
		
		else if(row == 1 && col == 1)
		{
			Intent i = new Intent(this, Actividadcuanto.class );
	        startActivity(i);
		}
		
		else if(row == 2 && col == 0)
		{
			this.finish();
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
