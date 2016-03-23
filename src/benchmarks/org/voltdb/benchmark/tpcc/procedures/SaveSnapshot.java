package org.voltdb.benchmark.tpcc.procedures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;


public class SaveSnapshot extends VoltProcedure {
	public VoltTable[] run(long _timestamp) {
		
		String destination = "/home/aayush/workspace/H-Store/h-store/aayush/snapshots/";
		final int block = 1;
		long timestampMin = Long.MAX_VALUE;
		int dirNo=1;
		String content="";
		for(int i=1;i<=3;i++){
			if(new File(destination+Integer.toString(i)+"/",destination+Integer.toString(i)+"/timestamp.txt").exists()){
				try {
					content = new Scanner(new File(destination+Integer.toString(i)+"/timestamp.txt")).useDelimiter("\\Z").next();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				if(timestampMin > Long.valueOf(content)){
					timestampMin = Long.valueOf(content);
					dirNo = i;
				}
			}
			else{
				dirNo = i;
				break;
			}
		}
		
		destination = destination+Integer.toString(dirNo)+"/";
		
		File dir = new File(destination);
		for(File file: dir.listFiles()) file.delete();
		String timestamp = Long.toString(_timestamp);
		String u_id = "tpcc";
		try{
			FileOutputStream fout = new FileOutputStream(destination+"timestamp.txt");
			fout.write(timestamp.getBytes());
			fout.close();
//			client.callProcedure("@SnapshotSave",destination,u_id,block).getResults();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new VoltTable[0];
		
	}
}