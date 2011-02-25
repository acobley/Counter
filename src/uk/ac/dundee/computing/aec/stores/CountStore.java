package uk.ac.dundee.computing.aec.stores;

public class CountStore {
	private long i=0;
	public CountStore(){
		
	}
	
	public void  setCount(long i){
		this.i=i;
	}
	
	public long getCount(){
		return i;
	}
	
}
