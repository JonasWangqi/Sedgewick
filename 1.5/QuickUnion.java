//algorithm 1.5
//page 224

public class QuickUnion
{
	private int[] id;
	private int count;
	
	public QuickUnion(int size)
	{
		count=size;
		id=new int[count];
		for(int i=0;i<count;i++)
			id[i]=i;
	}
	
	public int count()
	{
		return count;
	}
	
	public boolean connected(int p, int q)
	{
		return find(p)==find(q);
	}
	
	public int find(int p)
	{
		while(p!=id[p])
			p=id[p];
		return p;
	}
	
	public void union(int p, int q)
	{
		int idP=find(p);
		int idQ=find(q);
		
		if(idP==idQ) return;
		id[idP]=idQ;
		count--;
	}
	
	public static void main(String[] args)
	{
		int size=StdIn.readInt();
		QuickUnion qu=new QuickUnion(size);
		while(!StdIn.isEmpty())
		{
			int p=StdIn.readInt();
			int q=StdIn.readInt();
			if(qu.connected(p,q))
				continue;
			qu.union(p,q);
			StdOut.println(p+" "+q);
		}
		
		StdOut.println("There are "+qu.count()+" components");
	}
}