package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// CEL: Utworzenie struktury do przechowywania mrówki.
public class Ant 
{
	// REGION: Pola. =========================================]
	private int maxNumberOfNodes;  // Maksymalna iloœæ wierzcho³ków. 
	private List<Integer>clique;   // Klika.
	// END REGION ============================================]
	
	// REGION: Konstruktor. ==================================]
	public Ant(int maxNumberOfNodes)
	{
		// 1. Przypisanie parametrów.
		this.maxNumberOfNodes=maxNumberOfNodes;
		clique=new ArrayList<Integer>(maxNumberOfNodes);
	}
	
	public Ant()
	{
		// 1. Przypisanie parametrów.
		clique=new ArrayList<Integer>();
		maxNumberOfNodes=100000;
	}
	// END REGION ============================================]
	
	// REGION: Wlasciwosci.===================================]
	public int getMaxNumberOfNodes() 
	{
		return maxNumberOfNodes;
	}

	// CEL: Zwrócenie bufora, który zawiera klikê. 
	public List<Integer> getClique()
	{
		return clique;
	}
	// END REGION =============================================]
	
	// REGION: Metody. ========================================]
	public void safe_addNode(int node)
	{
		if(!clique.contains(node))
		{
			clique.add(node);
		}
	}
	
	// CEL: Dodanie wêz³a do kliki.
	public void addNode(int node)
	{
		clique.add(node);
	}
	
	// CEL: Zwrócenie wartoœci funkcji przystosowania.
	public int eval()
	{
		return clique.size();
	}
	
	// CEL: Zwrócenie obecnej pozycji mrówki/ obecnego wêz³a.
	public int getCurrentNode()
	{
		return clique.get(clique.size()-1);
	}
	
	// CEL: Zwrócenie mo¿liwych opcji.
	public List<Integer> getCandidates(Graph g)
	{
		// 1. Utworzenie pustego na dostêpne wêz³y.
		List<Integer> nodes= new ArrayList<Integer>();
		
		// 2. Przechodzenie przez wszystkie wierzcho³ki w tym buforze.
		for(int node: clique)
		{
			// 2.1. Uzyskanie po³¹czeñ.
			int[]connections=g.getMatrix()[node];
			
			// 2.2. Sprawdzanie po³¹czeñ.
			for(int i=0; i<connections.length; i++)
			{
				// 2.2.1 Sprawdzanie czy jest polaczenie i czy juz nie jest na liscie.
				if(connections[i]==1 && !nodes.contains(i) && !clique.contains(i))
				{
					// 2.2.1.1 Sprawdzenie czy inne wierzcho³ki maj¹ z nimi po³¹czenie.
					boolean contains=true;
					for(int no: clique)
					{
						if(g.getMatrix()[no][i]!=1)
						{
							contains=false;
						}
					}
					if(contains)
					{
						nodes.add(i);
					}
				}
			}
		}
		
		// 3. Zwrócenie mo¿liwych opcji.
		return nodes;
	}
	
	// CEL: Drukowanie mo¿liwych po³¹czeñ.
	public void print_candidates(Graph g)
	{
		List<Integer>nodes=getCandidates(g);
		System.out.println("Connections: ");
		for(int i:nodes)
		{
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	// CEL: Drukowanie mrówki.
	public void print()
	{
		System.out.println("Eval: "+this.eval());
		System.out.println(this);
	}
	
	// CEL: Unormowania kliki.
	public void removeDuplicates()
	{
		List<Integer>cl=new ArrayList<Integer>();
		for(int i: clique)
		{
			if(!cl.contains(i))
			{
				cl.add(i);
			}
		}
		clique=cl;
	}
	
	
	// CEL: Nadpisanie metody, która zostanie wywo³ana do wydruku.
	@Override
	public String toString()
	{
		removeDuplicates();
		String res="Eval: "+this.eval()+"\n"+"Clique: ";
		for(int i : clique)
		{
			res+=i+" -> ";
		}
		return res+="\n";
	}
	// END REGION ===============================================]
}
