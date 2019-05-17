package kcat.org.findsamething;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScoreDao {

	ScoreSQLiteOpenHelper db;

	public ScoreDao(Context context) {
		db = ScoreSQLiteOpenHelper.getInstance(context);
	}

	public void close() {
		db.close();
	}

	public void insert(int score, String writeDate) {
		ContentValues values = new ContentValues();

		values.put("score", score);
		values.put("write_date", writeDate);

		// System.out.println("testScore"+score);
		long rowId = db.insert("SCORE_TABLE", values);
		if (rowId < 0) {
			throw new SQLException("Fail At Insert");
		}
	}

	public void insertHard(int score, String writeDate) {
		ContentValues values = new ContentValues();

		values.put("score", score);
		values.put("write_date", writeDate);

		// System.out.println("testScore"+score);
		long rowId = db.insert("SCORE_TABLE_HARD", values);
		if (rowId < 0) {
			throw new SQLException("Fail At Insert");
		}

	}

	public ArrayList<ScoreDto> select() {
		ArrayList<ScoreDto> list = new ArrayList<ScoreDto>();
		list = db.select();
		return list;

	}

	public ArrayList<ScoreDto> selectHard() {
		ArrayList<ScoreDto> list = new ArrayList<ScoreDto>();
		list = db.selectHard();
		return list;

	}

	public int getBestScore() {
		int bestScore = db.getBestScore();
		return bestScore;

	}

	public int getBestScoreHard() {
		int bestScore = db.getBestScoreHard();
		return bestScore;

	}

}
