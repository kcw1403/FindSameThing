package kcat.org.findsamething;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
public class ImageSelectNumPlayer extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		RadioGroup rg = (RadioGroup)findViewById(R.id.ImageRbg);
		
		
		setContentView(R.layout.image_select_num_player);

		findViewById(R.id.selectP1).setOnClickListener(this);
		findViewById(R.id.selectP2).setOnClickListener(this);
		findViewById(R.id.callMenu).setOnClickListener(this);
		
	
	
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(this,MainActivity.class);
		overridePendingTransition(0,0);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RadioGroup rg = (RadioGroup)findViewById(R.id.ImageRbg);
		if(v.getId()==R.id.selectP1){
			if(rg.getCheckedRadioButtonId()==R.id.ImageEasy){
				Intent intent = new Intent(this,Image1Player.class);
				startActivity(intent);
			}
			if(rg.getCheckedRadioButtonId()==R.id.ImageHard){
				Intent intent = new Intent(this,Image1PlayerHard.class);
				startActivity(intent);
			}
		}
		
		if(v.getId()==R.id.selectP2){
			if(rg.getCheckedRadioButtonId()==R.id.ImageEasy){
				Intent intent = new Intent(this,Image2Player.class);
				startActivity(intent);
			}
			if(rg.getCheckedRadioButtonId()==R.id.ImageHard){
				Intent intent = new Intent(this,Image2PlayerHard.class);
				overridePendingTransition(0,0);
				startActivity(intent);
			}
		}
		if(v.getId()==R.id.callMenu){
			Intent intent = new Intent(this,MainActivity.class);
			overridePendingTransition(0,0);
			startActivity(intent);
			finish();
		}
	}
}
