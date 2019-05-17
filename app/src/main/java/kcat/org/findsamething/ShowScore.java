package kcat.org.findsamething;

import java.util.ArrayList;



import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowScore extends Activity {

	private ScoreDao scoreDao;

	ArrayAdapter<ScoreDto> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_score);
		scoreDao = new ScoreDao(getApplicationContext());

		
		ArrayList<ScoreDto> list = scoreDao.select();
		ArrayList<ScoreDto> listHard = scoreDao.selectHard();

		for (int i = 0; i < list.size() && i < 10; i++) {


			String tt = String.valueOf(list.get(i).getScore());
			int id = getResources().getIdentifier("easyRank" + (i + 1), "id",
					this.getPackageName());
			TextView easyRank = (TextView) findViewById(id);
			easyRank.setText((i + 1) + ". " + tt);
		}
		for (int i = 0; i < listHard.size() && i < 10; i++) {

			String tt2 = String.valueOf(listHard.get(i).getScore());
			int id = getResources().getIdentifier("hardRank" + (i + 1), "id",
					this.getPackageName());
			TextView hardRank = (TextView) findViewById(id);
			hardRank.setText((i + 1) + ". " + tt2);
		}

	}
}
