package uk.ac.dundee.computing.aec.counter.connectors;


import java.util.Date;

import uk.ac.dundee.computing.aec.utils.*;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.*;
import static me.prettyprint.hector.api.factory.HFactory.createRangeSlicesQuery;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
 
//import me.prettyprint.hector.api.beans.KeyspaceOperator;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.cassandra.serializers.StringSerializer;


import me.prettyprint.hector.api.mutation.*;


import uk.ac.dundee.computing.aec.stores.*;
import uk.ac.dundee.computing.aec.utils.Convertors;


public class DataConnector {
	
	public DataConnector(){
		
	}
	
	public void CreateData(){
		
		System.out.println("Create Data");
		Cluster c=null; //V2
		try{
			
			c=CassandraHosts.getCluster();
			
		}catch (Exception et){
			System.out.println("Data Connector  Can't Connect"+et);
			
		}
		
		
			
		try {
			
			
			System.out.println("Writing Data");
			ConsistencyLevelPolicy mcl = new MyConsistancyLevel();
			Keyspace ko = HFactory.createKeyspace("CounterKeyspace", c);  //V2
			ko.setConsistencyLevelPolicy(mcl);
			StringSerializer se = StringSerializer.get();
			Mutator<String> m = HFactory.createMutator(ko,se);
			
			
			for (int i=0; i <10000;i++){
				//We are going to write 10000 rows
				String Key=""+i;
				String Value="SomeValue"+i;
				 String columnName = "SomeColumnName"+i;
				 //Value under key is same as key
				 m.insert(Key, "Counts",
			        		HFactory.createStringColumn(columnName, Value));
				// System.out.println("Data "+ Key+ " : "+columnName+ " : "+Value);
			}
			
			
		}finally{
			/*
			try{
				
				CassandraHosts.releaseClient(client);
			}catch(Exception et){
				System.out.println("Pool can't be released");
				return null;
			}
			*/
		}    
		return;
	}

	public String GetRandomKey(){
		
		//System.out.println("Get Random Key");
		String Value="";
		
		Cluster c; //V2
		try{
			
			c=CassandraHosts.getCluster();
			
		}catch (Exception et){
			System.out.println("get Articles Posts Can't Connect"+et);
			return null;
		} 
		
		try{
			ConsistencyLevelPolicy mcl = new MyConsistancyLevel();
			Keyspace ko = HFactory.createKeyspace("CounterKeyspace", c);  //V2
			ko.setConsistencyLevelPolicy(mcl);
			StringSerializer se = StringSerializer.get();
			int Random= (int)(9999.0 * Math.random());
			String sRandom=""+Random;			
			OrderedRows<String, String, String> rows = null;
			ColumnSlice<String, String> slice=null;
			try{

				//retrieve  data
				RangeSlicesQuery<String,String, String> s=createRangeSlicesQuery(ko,se, se, se);
				
				s.setColumnFamily("Counts");
				
				s.setKeys(sRandom, sRandom); //Set the Key
				s.setRange("", "", false, 5); //Set the range of columns (we want them all) 
				QueryResult<OrderedRows<String,String, String>> r2 = s.execute();
				rows = r2.get();
			}catch(Exception et){
				System.out.println("Cant make Query on Article connector"+et);
				return null;
			}
			for (Row<String,String, String> row2 : rows) {
		    	
		      //System.out.println("key "+row2.getKey());
		      //System.out.flush();
		      slice = row2.getColumnSlice();
		  
		      for (HColumn<String, String> column : slice.getColumns()) {
		        
		    	  	String Name=column.getName();
         		 	String cValue=column.getValue();
         		 	//System.out.println("Name: "+Name+" : "+Value);
         		 	
         		 		Value=Name;
           		
        		 }	
	        
		      }
		}finally{
			/*
			try{
				
				CassandraHosts.releaseClient(client);
			}catch(Exception et){
				System.out.println("Pool can't be released");
				return null;
			}
			*/
		}    
	return Value;
		
		
		
		
	}
	
	
	
}

