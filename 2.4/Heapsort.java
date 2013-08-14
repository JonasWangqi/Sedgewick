//page 324
//algorithm 2.7

public class Heapsort
{ 
	private static <T extends Comparable<T>> void sink(T[] items,int position,int size)
	{
		while(position*2<=size)
		{
			int lookup=2*position;
			if(lookup<size&&items[lookup-1].compareTo(items[lookup])<0)
				lookup++;
			if(items[position-1].compareTo(items[lookup-1])>=0)
				break;
			exchange(items,position,lookup);
			position=lookup;
		}
	}
	
	private static <T extends Comparable<T>> void exchange(T[] items,int i,int j)
	{
		T temp=items[i-1];
		items[i-1]=items[j-1];
		items[j-1]=temp;
	}
	
	private static <T extends Comparable<T>> void sort(T[] items)
	{
		int size=items.length;
		for(int i=size/2;i>=1;i--)
			sink(items,i,size);
		while(size>1)
		{
			exchange(items,1,size--);
			sink(items,1,size);
		}
	}
	
	public static void main(String[] args)
	{
		String[] items = StdIn.readStrings();
		sort(items);
		for(int i=0;i<items.length;i++)
			StdOut.print(items[i]+" ");
		StdOut.println();
	}
}