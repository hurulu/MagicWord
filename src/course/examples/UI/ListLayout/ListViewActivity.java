package course.examples.UI.ListLayout;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends ListActivity {
	
	final ArrayList<String> wordList = new ArrayList<String>();
	public ArrayAdapter<String> adapter = null;
	
	public void refreshUi() {
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        final Button button = (Button) findViewById(R.id.button);
        final EditText addWord = (EditText) findViewById(R.id.addWord);
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    	//final ArrayList<String> wordList = new ArrayList<String>();
    	adapter = new ArrayAdapter<String>(this, R.layout.list_item,wordList);
        
        final Object[] keys = prefs.getAll().keySet().toArray();
        
        for ( int i = 0;i < keys.length; i++ ) {
        	wordList.add(0,keys[i].toString());
        }
        
		//setListAdapter(new ArrayAdapter<Object>(this, R.layout.list_item,
				//getResources().getStringArray(R.array.colors)));
		//		keys
		//		));
		
		
		//lv.setTextFilterEnabled(true);
        ListView lv = getListView();
		refreshUi();
        //setListAdapter(adapter);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Add the new word to the word list
				String newWord = addWord.getText().toString();
				if (prefs.contains(newWord)) {
					Toast toast = Toast.makeText(getApplicationContext(),"\""+newWord+"\" is already in", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					//button.setText("already has"+newWord);
				} else {
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt(newWord, 0);
					editor.commit();
					Toast toast = Toast.makeText(getApplicationContext(),"\""+newWord+"\" is added", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					//button.setText();
					wordList.add(0,newWord);
					refreshUi();
				}
				//Clear the word 
				addWord.setText("");
			}
		});
        

        
        

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					//MediaPlayer mediaPlayer = new MediaPlayer();
				    final String word = (String) ((TextView) view).getText();
				    try {
				    	//Uri uri = Uri.parse("android.resource://course.examples.UI.ListLayout/raw/"+word);
				    	Uri uri = Uri.parse("http://translate.google.com.au/translate_tts?ie=UTF-8&q="+word+"&tl=en-us");
						MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
				    	//mediaPlayer.setDataSource(getBaseContext(), uri);
						//mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						//mediaPlayer.prepare();
						mediaPlayer.start();
				    } 
				    catch(Exception e) {
				    	;
				    }
				    

			}
			
		});
		
		
	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.option_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.deleteAll:
	            deleteAll();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
		
	}

	private void deleteAll() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		wordList.clear();
		refreshUi();
	}
	
}