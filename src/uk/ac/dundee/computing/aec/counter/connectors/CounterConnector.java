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
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;

import me.prettyprint.hector.api.mutation.*;

 

import uk.ac.dundee.computing.aec.stores.*;
import uk.ac.dundee.computing.aec.utils.Convertors;



public class CounterConnector {

	public CounterConnector(){
		
	}
	
	public CountStore incrementCounter(){
		
		CountStore Count= new CountStore();
		
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
			LongSerializer le = LongSerializer.get();
			UUIDSerializer ue = UUIDSerializer.get();
			Mutator<String> m = HFactory.createMutator(ko,se);
		
		long lValue=1;
        
        // Create a new V2 query
        RangeSlicesQuery<String,String, Long> s=createRangeSlicesQuery(ko,se, se, le);
        s.setColumnFamily("Counts");
        s.setKeys("Counter1","Counter1"); //Set the Key
        s.setRange("Value", "Value", false, 1); //Set the range of columns (we want them all) 
        QueryResult<OrderedRows<String,String, Long>> r2 = s.execute();
        OrderedRows<String, String, Long> rows = r2.get();
        ColumnSlice<String, Long> slice;
	    
        for (Row<String,String, Long> row2 : rows) {
       	 
       	 System.out.println("key "+row2.getKey());
       	 slice = row2.getColumnSlice();
       	 for (HColumn<String, Long> column : slice.getColumns()) {
       		 lValue=column.getValue().longValue();
       		 lValue++;
       		 System.out.println("\t" + column.getName() + "\t ==\t" +column.getValue());

       	 }
       }
       //Its been incremented, put it back again now
        String columnName = "Value";
        String key="Counter1";
        m.insert(key, "Counts",
        		HFactory.createColumn(columnName, lValue,se,le));
        
      
   	 	Count.setCount(lValue);
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
		return Count;
	}
	
}
