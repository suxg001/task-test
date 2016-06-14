package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
	System.out.println("2016-06-14 00:00:00".substring(0, 12));	
		System.out.println(simpleDateFormat.format(new Date()));
		System.out.println("2016-06-07 00:00:00".indexOf(String.valueOf(simpleDateFormat.format(new Date()))));
      if("2016-06-016 00:00:00".indexOf(String.valueOf(simpleDateFormat.format(new Date())))==0){
    	  System.out.println("-------");
      }else{
    	  System.out.println("***8888888888");
      }
	}

}
