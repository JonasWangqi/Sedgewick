//2.2.16
//page 285
//based on http://www.home.hs-karlsruhe.de/~pach0003/informatik_1/aufgaben/en/sorting.html

public class NaturalMergeSort
{
	public static <T extends Comparable<T>> void sort(T[] a)
	{
		T[] aux=(T[])new Comparable[a.length];
		naturalMergeSort(a,aux);
	}
	
	private static <T extends Comparable<T>> void naturalMergeSort(T[] a,T[] aux)
	{
		int left=0;
		int right=a.length-1;
		int leftPos=0;
		int rightPos=leftPos+1;
		boolean isSorted=false;
		
		while(!isSorted)
		{
			isSorted=true;
			left=0;
			
			while(left<right)
			{
				leftPos=left;
				while(leftPos<right&&a[leftPos].compareTo(a[leftPos+1])<=0)
					leftPos++;
				rightPos=leftPos+1;
				while(rightPos<right&&a[rightPos].compareTo(a[rightPos+1])<=0)
					rightPos++;
				if(rightPos<=right)//if it would've been entirely sorted rightPos would've been one more to the right
				{
					merge(a,aux,left,leftPos,rightPos);
					isSorted=false;
				}
				left=rightPos+1;
			}
		}
	}
	
	private static <T extends Comparable<T>> void merge(T[] a,T[] aux,int left,int middle,int right)
	{
		for(int i=left;i<=right;i++)
			aux[i]=a[i];
		int i=left;
		int j=middle+1;
		for(int k=left;k<=right;k++)
		{
			if(i>middle)
			{
				a[k]=aux[j];
				j++;
			}
			else
				if(j>right)
				{
					a[k]=aux[i];
					i++;
				}
				else
					if(aux[i].compareTo(aux[j])<=0)
					{
						a[k]=aux[i];
						i++;
					}
					else
					{
						a[k]=aux[j];
						j++;
					}
		}
	}
	
	public static void main(String[] args)
	{
		String[] a=In.readStrings();
		sort(a);
		for(int i=0;i<a.length;i++)
			StdOut.print(a[i]+" ");
	}
}
