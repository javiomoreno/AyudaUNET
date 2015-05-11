package BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TablaMaterias 
{
	public static final String TABLE_NAME = "materias";
	
	public static final String CN_ID = "id_materia";
	public static final String CN_NAME = "nombre";
	
	public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
					+CN_ID+" integer primary key autoincrement, "
					+CN_NAME+" text not null);";

	private DbHelper helper;
	private SQLiteDatabase DB;
	
	public TablaMaterias(Context contex) 
	{
		helper = new DbHelper(contex);
		DB = helper.getWritableDatabase();
	} 
	
	public ContentValues GenerarContenedor(String nom)
	{
		ContentValues contenedor = new ContentValues();
		contenedor.put(CN_NAME, nom);
		return contenedor;
	}
	
	public void InsertarMateria(String nom)
	{
		DB.insert(TABLE_NAME, null, GenerarContenedor(nom));
	}
	
	public Cursor DevolverIdMateria(String nom)
	{
		String[] columnas = new String[]{CN_ID, CN_NAME}; 
		
		return DB.query(TABLE_NAME, columnas, CN_NAME +"=?", new String[]{nom}, null, null, null);
	}
	
	public Cursor DevolverMaterias()
	{
		String[] columnas = new String[]{CN_ID, CN_NAME}; 
		
		return DB.query(TABLE_NAME, columnas, null, null, null, null, null);
	}
	
	public void EliminarTodasMaterias()
	{		
		DB.execSQL("DELETE FROM "+TABLE_NAME+" ");
	}
	
}
