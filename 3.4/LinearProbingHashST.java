//algorithm 3.6(470)
//3.4.19(482)

import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class LinearProbingHashST<Key extends Comparable<Key>,Value>
{
	private int keyCount;
	private int capacity;
	private int logCapacity;
	private Key[] keys;
	private Value[] values;
	
	private static final int INITIAL_LOG_CAPACITY=3;
	private static final int INITIAL_CAPACITY=8;
	private static final int[] PRIMES={
		//3,4,5,6,7,8,9,10
		//8,16,32,64,128,256,512,1024... logCapacity-3
		7, 13, 19, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
		32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
		8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
		536870909, 1073741789, 2147483647};
	
	public LinearProbingHashST()
	{
		this(INITIAL_CAPACITY,INITIAL_LOG_CAPACITY);
	}
	
	public LinearProbingHashST(int capacity,int logCapacity)
	{
		this.capacity=capacity;
		this.logCapacity=logCapacity;
		keys=(Key[])new Comparable[capacity];
		values=(Value[])new Object[capacity];
	}
	
	private int hash(Key key)
	{
		//to ensure all bits are taken into consideration
		return (key.hashCode()&0x7fffffff)%PRIMES[logCapacity-3];
	}
	
	private void resize(int newCapacity)
	{
		int newLogCapacity;
		if(newCapacity<capacity) newLogCapacity=logCapacity-1;
		else newLogCapacity=logCapacity+1;
		
		LinearProbingHashST<Key,Value> temp=new LinearProbingHashST<Key,Value>(newCapacity,newLogCapacity);
		for(int i=0;i<keys.length;i++)
			if(keys[i]!=null) temp.put(keys[i],values[i]);
		keys=temp.keys;
		values=temp.values;
		capacity=newCapacity;
		logCapacity=newLogCapacity;
	}
	
	public void put(Key key,Value value)
	{
		if(value==null) delete(key);
		if(keyCount>=capacity/2) resize(capacity*2);
		
		int i=0;
		for(i=hash(key);keys[i]!=null;i=(i+1)%capacity)
			if(keys[i].equals(key))
			{
				values[i]=value;
				return;
			}
		keys[i]=key;
		values[i]=value;
		keyCount++;
	}
	
	public Value get(Key key)
	{
		for(int i=hash(key);keys[i]!=null;i=(i+1)%capacity)
			if(keys[i].equals(key)) return values[i];
		return null;
	}
	
	public void delete(Key key)
	{
		if(!contains(key)) return;
		
		int i=hash(key);
		while(!keys[i].equals(key)) i=(i+1)%capacity;
		keys[i]=null;
		values[i]=null;
		keyCount --;
		
		i=(i+1)%capacity;
		while(keys[i]!=null)
		{
			Key tempKey=keys[i];
			Value tempValue=values[i];
			keys[i]=null;
			values[i]=null;
			keyCount--;
		
			put(tempKey,tempValue);
			i=(i+1)%capacity;
		}
		if(keyCount>INITIAL_CAPACITY&&logCapacity>INITIAL_LOG_CAPACITY&&keyCount<=capacity/8) resize(capacity/2);
	}
	
	public int size() {return keyCount; }
	
	public boolean isEmpty() {return size()==0;}

	public boolean contains(Key key){return get(key)!=null;}
	
	public Iterable<Key> keys()
	{
		Queue<Key> queue=new Queue<Key>();
		for(int i=0;i<keys.length;i++)
			if(keys[i]!=null) queue.enqueue(keys[i]);
		
		return queue;
	}
	
	public static void main(String[] args)
	{
		Scanner input=new Scanner(new BufferedInputStream(System.in));
		PrintWriter output=new PrintWriter(new OutputStreamWriter(System.out),true);
	
		LinearProbingHashST<String,Integer> hash=new LinearProbingHashST<String,Integer>();
		for(int i=0;input.hasNext();i++)
		{
			String s=input.next();
			hash.put(s,i);
		}
		
		for(String s:hash.keys())
			output.println(s+" "+hash.get(s));
			
		output.println("deleting S");
		hash.delete("S".intern());
		for(String s:hash.keys())
			output.println(s+" "+hash.get(s));
	}
}