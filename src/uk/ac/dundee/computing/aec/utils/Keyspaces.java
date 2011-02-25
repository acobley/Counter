package uk.ac.dundee.computing.aec.utils;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.*;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.service.*;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.*;

public final class Keyspaces {

	
	
	public Keyspaces(){
		
	}
	
	public static void SetUpKeySpaces(Cluster c){
		try{
			try{
				KeyspaceDefinition kd =c.describeKeyspace("CounterKeyspace");
				System.out.println("Keyspace: "+kd.getName());
				//System.out.println("Replication: "+kd.getReplicationFactor());
				//System.out.println("Strategy: "+kd.getStrategyClass());
			}catch(Exception et){
				System.out.println("Keyspace probably doesn't exist, tryping to create it"+et);
				List<ColumnFamilyDefinition> cfs = new ArrayList<ColumnFamilyDefinition>(); 
				BasicColumnFamilyDefinition cf = new BasicColumnFamilyDefinition(); 
				
				cf.setName("Counts");
				cf.setKeyspaceName("CounterKeyspace");
			
				ColumnFamilyDefinition cfDef = new ThriftCfDef(cf); 
				cfs.add(cfDef);
				KeyspaceDefinition ks=HFactory.createKeyspaceDefinition("CounterKeyspace","org.apache.cassandra.locator.SimpleStrategy", 1, cfs);
				c.addKeyspace(ks);
				System.out.println("Keyspace created ?");
				try{
					KeyspaceDefinition kd =c.describeKeyspace("CounterKeyspace");
					System.out.println("Keyspace: "+kd.getName());
					System.out.println("Replication: "+kd.getReplicationFactor());
					System.out.println("Strategy: "+kd.getStrategyClass());
				}catch(Exception et2){
					System.out.println("Opps no it wasn't !" +et2);
				}
				
			}
			
			
		}catch(Exception et){
			System.out.println("Other keyspace or coulm definition error" +et);
		}
		
	}
}
