package org.voltdb.benchmark.tpcc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;																																																																																																																																								

public class RemoveTransaction {
	
	public Vector<TransactionInfo> parseTraceLog() {
		
		BufferedReader br = null;
		String mappingsText = "";
		Vector<TransactionInfo> trInfo = new Vector<TransactionInfo>();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("src/benchmarks/org/voltdb/benchmark/tpcc/tpcc.mappings"));
			while ((sCurrentLine = br.readLine()) != null) {
				mappingsText += sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
	    }
		
		Map<String, List<String> > dictionary = new HashMap<String, List<String> >();
		
		JSONParser parser = new JSONParser();	
	      try{
	         Object obj = parser.parse(mappingsText);
	         JSONObject object = (JSONObject)obj;
				
	         JSONArray array = (JSONArray)object.get("MAPPINGS");
	         System.out.println(array.size());
	         for(int i=0; i<array.size(); i++){
	        	 JSONObject obj1 = (JSONObject)array.get(i);
	        	 
	        	 String database = obj1.get("STATEMENT_COLUMN").toString();
	        	 database = database.replace("':'", "$$");
	        	 database = database.replace("{", "");
	        	 database = database.replace("}", "");
	        	 database = database.replace("'", "");
	        	 int position = database.indexOf("$");
	        	 database = database.substring(0, position);
	        	 
	        	 String name = obj1.get("STATEMENT").toString();
	        	 name = name.replace("':'", "$$");
	        	 name = name.replace("{", "");
	        	 name = name.replace("}", "");
	        	 name = name.replace("'", "");
	        	 
	        	 List<String> value = dictionary.get(name);
	        	 if(value != null){
	        		 int sz = value.size();
	        		 for(int j=0;j<sz;j++){
	        			 if(value.get(j).matches(database)){
	        				 break;
	        			 }
	        			 if(j==sz-1){
	        				 value.add(database);
	        			 }
	        		 }
	        		 dictionary.put(name, value);
	        	 }
	        	 else{
	        		 List<String> value1 = new ArrayList<String>();
	        		 value1.add(database);
	        		 dictionary.put(name, value1);
	        	 }
	        	 
	         }
	         System.out.print(dictionary.size());
	         System.out.println();
	         for (Map.Entry<String, List<String> > entry : dictionary.entrySet()) {
	        	    String key = entry.getKey();
	        	    List<String> value = entry.getValue();
//	        	    System.out.print(key+" : "+value);
//	        	    System.out.println();
	        	}

	        
	      }catch(ParseException pe){
			
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	      }
	      
	      br = null;
	      int cur = 0;
	      try {
	    	  String sCurrentLine;
	    	  br = new BufferedReader(new FileReader("resources/tracelog/trace-0"));
	    	  while ((sCurrentLine = br.readLine()) != null) {
	    		  
	    		  try{
	    		         Object obj = parser.parse(sCurrentLine);
	    		         JSONObject object = (JSONObject)obj;
	    		         String name = object.get("NAME").toString();
	    		         String txnID = object.get("TXN_ID").toString();
	    		         String stTime = object.get("START").toString();
	    		         String endTime = object.get("STOP").toString();
	    		         JSONArray params = (JSONArray)object.get("PARAMS");
	    		         
	    		         JSONArray array = (JSONArray)object.get("QUERIES");
	    		         int sz = array.size();
	    		         Set<String> set = new HashSet<String>();
	    		         Set<String> tbSet = new HashSet<String>();
    		        	 List<String> tb = new ArrayList<String>();
	    		         for(int i=0;i<sz;i++){
	    		        	 JSONObject obj1 = (JSONObject)array.get(i);
	    		        	 String database = obj1.get("NAME").toString();
	    		        	 database = database.replace("':'", "$$");
	    		        	 database = database.replace("{", "");
	    		        	 database = database.replace("}", "");
	    		        	 database = database.replace("'", "");
	    		        	 int position = database.indexOf("$");
	    		        	 String temp = database.substring(position+2, database.length());
//	    		        	 System.out.println(temp);
	    		        	 if(temp.toLowerCase().contains("insert") || temp.toLowerCase().contains("delete") || temp.toLowerCase().contains("update")){
	    		        		 set.add(database);
	    		        	 }
	    		        		 
	    		         }
//	    		         
	    		         Iterator iterator = set.iterator(); 
	    		         
	    		         // check values
	    		         while (iterator.hasNext()){
//	    		        	 System.out.println(iterator.next());
		    		         List<String> st =  dictionary.get(iterator.next());
		    		         for(int j=0;j<st.size();j++){
		    		        	 tbSet.add(st.get(j));
		    		         }
	    		         }
	    		         System.out.print(txnID+" "+stTime+" "+endTime+" ");
	    		         System.out.print(tbSet);
	    		         System.out.println();
	    		        
//	    		         Make some data structure and add all this element
	    		         TransactionInfo tr = new TransactionInfo();
	    		         tr.name = name;
	    		         tr.startTime = Long.parseLong(stTime);
	    		         tr.endTime = Long.parseLong(endTime);
	    		         tr.id = Long.parseLong(txnID);
	    		         tr.tbset = tbSet;
	    		         tr.params = params;
	    		         trInfo.add(tr);
	    		         
	    		  }catch(ParseException pe){
	    				
	    		         System.out.println("position: " + pe.getPosition());
	    		         System.out.println(pe);
	    		      }
	    		  
	    		  cur++;
//	    	      if(cur == 10)
//	    	    	  break;
	    		  
	    	  }
	      } catch (IOException e) {
	    	  e.printStackTrace();
		   	}
	      
	      
	      System.out.println(trInfo.size());
	      return affectedTransaction(trInfo);
			
	}
	
	private Vector<TransactionInfo> affectedTransaction(Vector<TransactionInfo> trInfo){
		Vector<TransactionInfo> affectedTr = new Vector<TransactionInfo>();
		if(trInfo.size()==0)
			return affectedTr;
		Random r = new Random();
		int idx = r.nextInt(trInfo.size());
		
		for(int i=0;i<trInfo.size();i++){
			Set<String> intersection = new HashSet<String>(trInfo.elementAt(i).tbset);
			intersection.retainAll(trInfo.elementAt(idx).tbset);
			if(intersection.size()==0){
				affectedTr.add(trInfo.elementAt(i));
			}
		}
		
		Iterator<TransactionInfo> itr = affectedTr.iterator();
		
		System.out.println("------Malicious transaction------");
		System.out.print(trInfo.elementAt(idx).id);
		System.out.print(" , ");
		System.out.print(trInfo.elementAt(idx).startTime);
		System.out.print(" , ");
		System.out.print(trInfo.elementAt(idx).endTime);
		System.out.print(" , ");
		System.out.println(trInfo.elementAt(idx).tbset);
		System.out.println("------UnAffected transactions------");
		while(itr.hasNext()){
			TransactionInfo trTmp = itr.next();
			System.out.print(trTmp.name);
			System.out.print(" , ");
			/*System.out.print(trTmp.id);
			System.out.print(" , ");
			System.out.print(trTmp.startTime);
			System.out.print(" , ");
			System.out.print(trTmp.endTime);
			System.out.print(" , ");*/
			System.out.println(trTmp.tbset);
			/*System.out.print(" , ");
			System.out.println(trTmp.params);*/
		}
		
		return affectedTr;
	}
}

