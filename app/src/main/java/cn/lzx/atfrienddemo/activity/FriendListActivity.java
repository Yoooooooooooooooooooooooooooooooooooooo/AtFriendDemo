package cn.lzx.atfrienddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lzx.atfrienddemo.R;

public class FriendListActivity extends Activity implements OnItemClickListener
{

    private static final String KEY_NAME = "name";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ListView listView = (ListView) findViewById(R.id.listview);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 20; i++)
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put(KEY_NAME, "" + i + i + i);
            data.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                data,
                android.R.layout.simple_list_item_1,
                new String[] { KEY_NAME },
                new int[] { android.R.id.text1 });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id)
    {
        Intent intent = new Intent();
        intent.putExtra(KEY_NAME, ((Map<String, String>)parent.getAdapter().getItem(position)).get(KEY_NAME));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}
