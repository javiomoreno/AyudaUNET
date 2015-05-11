package BaseDatos;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper 
{
	private static final String DB_NAME = "ayudaunet.sqlite";
	private static final int DB_SCHEME_VERSION = 1;
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(TablaMaterias.CREATE_TABLE);
		db.execSQL(TablaParciales.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}
