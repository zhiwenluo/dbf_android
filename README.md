android端的DBF文件的解析
调用如下

        public static String path =Environment.getExternalStorageDirectory() + "/rw.dbf";

        ParseDbf2Map parseDbf2Map = new ParseDbf2Map();

        List<Map<String, String>> list = parseDbf2Map.getListMapFromDbf(path);

返回的是ListMap类型

你可以这样来查看获得的内容：

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("-------------------------------------------------------------");
                for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
                   }
            }
        }
        
当需要添加数据的时候，调用此方法：

        //DBF文件的数据添加
          public  void addItem(String[] str) throws Exception {
          DBFWriter dbfwriter = new DBFWriter(new File(path));
          Object[] obj=new Object[3];
          obj[0]="zhiwenluo";
          obj[1]="github.com";
          obj[2]="author";
          dbfwriter.addRecord(obj);
         }
