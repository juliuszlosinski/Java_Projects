package Game.DataStructures;

import java.util.Comparator;
import java.util.Iterator;

public class List<T> implements Iterable<T>
{
	//REGION: CLASSES AREA
	//REGION: FIELDS
	public static int totalNumberOfListInstances=0;
	//END REGION
	
	//REGION: PROPORTIES
	public static int getTotalNumberOfListInstances() 
	{
		return totalNumberOfListInstances;
	}
	//END REGION
	//END REGION
	
	//REGION: INSTANCES AREA
	//REGION: FIELDS
	protected T[]items;
	protected int n;
	protected Iterator<T> iterator=new Iterator<T>() 
	{
		//REGION: INSTANCES AREA
		//REGION: FIELDS
		int i=-1;
		//END REGION
		
		//REGION: METHODS
		@Override
		public boolean hasNext() 
		{
			if(i<n)
			{
				return true;
			}
			return false;
		}

		@Override
		public T next() 
		{
			return items[++i];
		}
		//END REGION
		//END REGION
	};
	//END REGION
	
	//REGION: CONSTRUCTORS 
	public List()
	{
		items=(T[])new Object[10];
		n=0;
	}
	
	public List(int capacity)
	{
		items=(T[])new Object[capacity];
		n=0;
	}
	//END REGION
	
	//REGION: PROPORTIES
	public int getSize()
	{
		return n;
	}
	
	public T getFirst()
	{
		return items[0];
	}
	
	public T getLast() 
	{
		return items[n-1];
	}
	
	public T getElement(int index)
	{
		return items[index];
	}
	//END REGION
	
	//REGION: METHODS
	public void Add(T item) 
	{
		if(n>items.length)
		{
			Resize((T[])new Object[n*2],0);
		}
		items[n]=item;
		n++;
	}
	
	private void Shift(int stop,int start)
	{
		if(start==stop) 
		{
			return;
		}
		else 
		{
			items[start-1]=items[start];
			Shift(stop,--start);
		}
	}
	
	
	public int FindIndex(T item)
	{
		int index=-1;
		for(int i=0;i<n;i++)
		{
			if(items[i].equals(item)) {
				index=i;
			}
		}
		return index;
	}
	
	public void Remove(T item) 
	{
		int index=FindIndex(item);
		if(index!=-1) 
		{
			ShiftDown(index);
			n--;
		}
	}
	
	private void ShiftDown(int index)
    {
        int i = index + 1;
        while (i < n)
        {
            items[i - 1] = items[i];
            i++;
        }
    }
	
	private boolean Contains(T item, int i)
	{
		if(i>n) 
		{
			return false;
		}
		else if(items[i].equals(item))
		{
			return true;
		}
		else 
		{
			return Contains(item,++i);
		}
	}
	
	public boolean Contains(T item) 
	{
		return Contains(item,0);
	}
	

	
	void Resize(T[] items,int i) 
	{
		if(i>n) 
		{
			this.items=items;
		}
		else 
		{
			items[i]=this.items[i];
			Resize(items,++i);
		}
	}
	
	public void Sort(Comparator<T>comp) 
	{
		for(int i=0;i<n;i++) 
		{
			int minIndex=i;
			T minValue=items[i];
			int j=i;
			while(j!=0)
			{
				if(comp.compare(items[j], minValue)>0) 
				{
					minValue=items[j];
					minIndex=j;
				}
				j++;
			}
			T tmp=items[i];
			items[i]=items[minIndex];
			items[minIndex]=tmp;
		}
	}
	
	private String toString(int i)
	{
		if(i==n)
		{
			return "";
		}
		else 
		{
			return items[i]+toString(++i);
		}
	}
	
	public void Print()
	{
		System.out.println(this);
	}
	
	@Override
	public String toString() 
	{
		return toString(0);
	}

	@Override
	public Iterator<T> iterator()
	{
		return iterator;
	}
	//END REGION
	//END REGION
}