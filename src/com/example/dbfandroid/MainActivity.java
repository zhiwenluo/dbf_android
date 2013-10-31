package com.example.dbfandroid;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.linuxense.javadbf.DBFWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
    
    public static String path =Environment.getExternalStorageDirectory() + "/rw.dbf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	ParseDbf2Map parseDbf2Map = new ParseDbf2Map();
	List<Map<String, String>> list = parseDbf2Map.getListMapFromDbf(path);
	if (list != null) {
	    for (int i = 0; i < list.size(); i++) {
		System.out.println("-------------------------------------------------------------");
		for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
		    System.out.println(entry.getKey() + " = " + entry.getValue());
		   }
	    }
	}
    }
    //DBF文件的数据添加
    public  void addItem(String[] str) throws Exception {
      DBFWriter dbfwriter = new DBFWriter(new File(path));
      Object[] obj=new Object[3];
      obj[0]="442899999811111111";
      obj[1]="123";
      obj[2]="广州软件";
      dbfwriter.addRecord(obj);
     }
}
