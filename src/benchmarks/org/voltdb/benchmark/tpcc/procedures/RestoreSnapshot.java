package org.voltdb.benchmark.tpcc.procedures;

import java.io.FileInputStream;

import org.voltdb.*;
import org.voltdb.client.Client;

public class RestoreSnapshot extends VoltProcedure{
	
	/*private final Client client;
	public RestoreSnapshot(Client client) {
		this.client = client;
	}*/
	public VoltTable[] run(long _timestamp)  {
		
		VoltTable[] results = null;
		String destination = "/home/aayush/workspace/H-Store/h-store/aayush/snapshots/";
		String u_id = "tpcc";
		int dir_no = 1;
		long time_diff = Long.MAX_VALUE;
		try{
			for(int i=1;i<=3;i++){
				FileInputStream fin = new FileInputStream(destination+Integer.toString(i)+"/timestamp.txt");
				int j=0;
				String _time="";
				while((j=fin.read())!=-1){
					_time = _time + (char)j;
				}
				fin.close();
				long timestamp = Long.parseLong(_time);
				if(timestamp<=_timestamp && _timestamp-timestamp<=time_diff){
					dir_no = i;
					time_diff = _timestamp-timestamp;
				}
			}
//			results = client.callProcedure("@SnapshotRestore",destination+Integer.toString(dir_no)+"/",u_id,1).getResults();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		for(int i=0;i<results.length;i++){
			VoltTable table = results[i];
		    for (int r=0;r<table.getRowCount();r++) {
		        VoltTableRow row = table.fetchRow(r);
		        System.out.printf("Node %d Site %d restoring " + "table %s.\n",row.getLong("HOST_ID"), row.getLong("SITE_ID"),
		              row.getString("TABLE"));
		     }
		}
		return new VoltTable[0];
	}

}
