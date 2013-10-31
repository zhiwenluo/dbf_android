package com.example.dbfandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import android.util.Log;

public class ParseDbf2Map {
    
    public ParseDbf2Map() {
	
    }

    public  List<Map<String, String>> getListMapFromDbf(String path)
	    {
	InputStream inStream;
	List<Map<String, String>> map = null;
	try {
	    Log.e("fuck", path);
	    File file = new File(path);
	    inStream = new FileInputStream(file);
	    map = parseDbf2Map(inStream);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return map;
    }

    /**
     * 解析DBF文件
     * 
     * @param in
     * @return List<Map<String,String>> columns通过AMF编码之后的字符串
     * @throws Exception
     */
    public  List<Map<String, String>> parseDbf2Map(InputStream in)
	    throws Exception {
	Log.e("fuck", "sssssssssssssss");
	DBFReader dbfreader = new DBFReader(in);
	dbfreader.setCharactersetName("GB2312");

	List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
	Map<String, String> head = new HashMap<String, String>();
	// 解析DBF表头的信息
	Map dataType = new HashMap();
	for (int i = 0; i < dbfreader.getFieldCount(); i++) {
	    DBFField field = dbfreader.getField(i);
	    byte type = field.getDataType();
	    int decimal = 0;
	    int length = 0;
	    String formatString = "";
	    if (DBFField.FIELD_TYPE_N == type || DBFField.FIELD_TYPE_F == type) {
		decimal = field.getDecimalCount();
		length = field.getFieldLength();
		for (int j = 0; j < (length - decimal); j++) {
		    formatString += "#";
		}
		if (decimal != 0) {
		    formatString = formatString + "0.";
		}
		for (int jj = 0; jj < decimal; jj++) {
		    formatString += "0";
		}
		dataType.put("" + i, formatString);
	    }
	    if (DBFField.FIELD_TYPE_D == type) {
		formatString = "yyyy/MM/dd";
		dataType.put("" + i, formatString);
	    }

	    String name = field.getName();

	    head.put("" + i, name);
	}
	columns.add(head);
	Object[] rowObj = null;
	int k = 1;
	while ((rowObj = dbfreader.nextRecord()) != null) {
	    Map<String, String> data = new HashMap<String, String>();
	    for (int i = 0; i < rowObj.length; i++) {
		Object temp = rowObj[i];
		String dataTypeFormat = (String) dataType.get("" + i);
		if (dataTypeFormat != null) {
		    if (dataTypeFormat.indexOf("yyyy") != -1) {
			SimpleDateFormat format = new SimpleDateFormat(
				dataTypeFormat);
			temp = format.format((Date) temp);
		    } else {
			DecimalFormat format = new DecimalFormat(dataTypeFormat);
			temp = format.format(temp);
		    }
		}
		data.put(dbfreader.getField(i).getName(), temp.toString());
	    }
	    columns.add(data);
	    k++;
	}

	// 编码：
	// AmfSerializer amfSerializer = new AmfSerializer();
	// String str = amfSerializer.toAmf(columns);
	return columns;
    }
}