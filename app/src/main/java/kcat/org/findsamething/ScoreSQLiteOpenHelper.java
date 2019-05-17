package kcat.org.findsamething;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreSQLiteOpenHelper extends SQLiteOpenHelper {

	private static ScoreSQLiteOpenHelper instance;
	private static SQLiteDatabase db;

	public static final ScoreSQLiteOpenHelper getInstance(Context context) {
		initialize(context);
		return instance;
	}

	private static void initialize(Context context) {
		// TODO Auto-generated method stub

		if (instance == null) {
			instance = new ScoreSQLiteOpenHelper(context);
			try {
				db = instance.getWritableDatabase();
			} catch (SQLiteException e) {
				e.printStackTrace();
			}
		}
	}

	public ScoreSQLiteOpenHelper(Context context) {
		super(context, "SCORE_DB", null, 4);
		// TODO Auto-generated constructor stub
	}

	public ScoreSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public long insert(String table, ContentValues values) {
		return db.insert(table, null, values);
	}

	public long insertHard(String table, ContentValues values) {
		return db.insert(table, null, values);
	}

	public ArrayList<ScoreDto> select() {
		ArrayList<ScoreDto> list = new ArrayList<ScoreDto>();

		db = instance.getReadableDatabase();

		Cursor c = db.query("score_table", null, null, null, null, null,
				"score desc", null);

		while (c.moveToNext()) {
			ScoreDto scoreDto = new ScoreDto();
			int id = c.getColumnIndex("_id");
			scoreDto.setId(id);
			int score = c.getInt(c.getColumnIndex("score"));
			scoreDto.setScore(score);
			String writeDate = c.getString(c.getColumnIndex("write_date"));
			scoreDto.setWriteDate(writeDate);
			list.add(scoreDto);
		}

		return list;

	}

	public ArrayList<ScoreDto> selectHard() {
		ArrayList<ScoreDto> list = new ArrayList<ScoreDto>();

		db = instance.getReadableDatabase();

		Cursor c = db.query("score_table_hard", null, null, null, null, null,
				"score desc", null);

		while (c.moveToNext()) {
			ScoreDto scoreDto = new ScoreDto();
			int id = c.getColumnIndex("_id");
			scoreDto.setId(id);
			int score = c.getInt(c.getColumnIndex("score"));
			scoreDto.setScore(score);
			String writeDate = c.getString(c.getColumnIndex("write_date"));
			scoreDto.setWriteDate(writeDate);
			list.add(scoreDto);
		}

		return list;

	}

	public int getBestScore() {
		int bestScore = 0;
		db = instance.getReadableDatabase();
		
		Cursor c =db.rawQuery("select max(score) from score_table", null);
		
		
//		Cursor c = db.query("score_table", null, null, null, null, null,
//				"score desc", null);
		while (c.moveToNext()) {
		bestScore =c.getInt(0);
		}
		return bestScore;
	}

	
	public int getBestScoreHard() {
		int bestScore = 0;
		db = instance.getReadableDatabase();
		
		Cursor c =db.rawQuery("select max(score) from score_table_hard", null);
		
		
//		Cursor c = db.query("score_table", null, null, null, null, null,
//				"score desc", null);
		while (c.moveToNext()) {
		bestScore =c.getInt(0);
		}
		return bestScore;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE SCORE_TABLE ("
				+ "_id INTEGER PRIMARY KEY autoincrement, " + "score INTEGER, "
				+ "write_date text,time INTEGER);";

		String sql2 = "CREATE TABLE SCORE_TABLE_HARD ("
				+ "_id INTEGER PRIMARY KEY autoincrement, " + "score INTEGER, "
				+ "write_date text,time INTEGER);";

		String sql3 = "CREATE TABLE ACHIEVEMENT_TABLE ("
				+ "_id INTEGER PRIMARY KEY autoincrement, " + "number INTEGER, "
				+ "name text);";
		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		String sql = "drop table if exists SCORE_TABLE";
		db.execSQL(sql);
		String sql2 = "drop table if exists SCORE_TABLE_HARD";
		db.execSQL(sql2);
		String sql3 = "drop table if exists ACHIEVEMENT_TABLE";
		db.execSQL(sql3);
		onCreate(db);
	}

}
