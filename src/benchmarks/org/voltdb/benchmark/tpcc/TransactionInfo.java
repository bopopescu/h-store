package org.voltdb.benchmark.tpcc;

import java.util.Set;

import org.json.simple.JSONArray;

public class TransactionInfo {
	
	public String name;
	public long id;
	public long startTime,endTime;
	public Set<String> tbset;
	public JSONArray params;
}
