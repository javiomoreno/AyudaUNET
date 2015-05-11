package BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TablaParciales 
{
	public static final String TABLE_NAME = "parciales";
	
	public static final String CN_ID = "id_parcial";
	public static final String CN_NAME = "nombre";
	public static final String CN_PORCENTAJE = "porcentaje";
	public static final String CN_NOTA = "nota";
	public static final String CN_FOREIGN_KEY_MATERIAS = "Materias_id_materia";
	
	public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
					+CN_ID+" integer primary key autoincrement, "
					+CN_NAME+" text," 
					+CN_PORCENTAJE+" integer," 
					+CN_NOTA+" integer,"
					+CN_FOREIGN_KEY_MATERIAS+" integer);"; 
	
	private DbHelper helper;
	private SQLiteDatabase DB;
	
	public TablaParciales(Context contex) 
	{
		helper = new DbHelper(contex);
		DB = helper.getWritableDatabase();
	} 
	
	public ContentValues GenerarContenedor(String nom, int porce, int nota, int id_mat)
	{
		ContentValues contenedor = new ContentValues();
		contenedor.put(CN_NAME, nom);
		contenedor.put(CN_PORCENTAJE, porce);
		contenedor.put(CN_NOTA, nota);
		contenedor.put(CN_FOREIGN_KEY_MATERIAS, id_mat);
		return contenedor;
	}
	
	private ContentValues GenerarContenedor(int nota)
	{
		ContentValues contenedor = new ContentValues();
		contenedor.put(CN_NOTA, nota);
		return contenedor;
	}
	
	public void InsertarParcial(String nom, int porce, int nota, int id_mat)
	{
		DB.insert(TABLE_NAME, null, GenerarContenedor(nom, porce, nota, id_mat));
	}
	
	public Cursor DevolverTodoPorMateria(int id_materia)
	{
		return DB.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+CN_FOREIGN_KEY_MATERIAS+"="+id_materia, null);
	}
	
	public void EliminarTodo()
	{		
		DB.execSQL("DELETE FROM "+TABLE_NAME+" ");
	}
	
	public void ModificarNota(int id_parcial, int nota)
	{
		String[] otro ={""+id_parcial};
		DB.update(TABLE_NAME, GenerarContenedor(nota), CN_ID+" =? ", otro);
	}

	
}
