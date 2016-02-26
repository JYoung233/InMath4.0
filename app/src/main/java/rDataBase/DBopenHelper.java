package rDataBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("NewApi")
public class DBopenHelper extends SQLiteOpenHelper {
	private static String name="Inmath8.db";//我创建的数据库的名字
    private static int version=1;
	

	public DBopenHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table note1(_id integer primary key autoincrement,title varchar(64))";
	    db.execSQL(sql);
	    String sql1="create table photo(photoname varchar(64))";
	    db.execSQL(sql1);
	    db.execSQL("insert into note1(_id,title) values('1','泰勒公式')");
	    db.execSQL("insert into photo(photoname) values('1.png')");
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
