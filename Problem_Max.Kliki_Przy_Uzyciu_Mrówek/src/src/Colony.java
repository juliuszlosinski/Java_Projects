package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

// CEL: Utworzenie struktury do przechowywania kolonii mr�wek.
public class Colony 
{
	// REGION: Pola.===================================]
	List<Ant>ants; 		 // Nasze mr�wki.
	int numberOfAnts;    // Ilo�� mr�wek.
	int numberOfNodes;   // Ilo�� wierzcho�k�w.
	double[][]feromons;     // Feromons.
	// END REGION =====================================]
	
	// REGION: Konstrutkor. ===========================]
	public Colony(int numberOfAnts, int numberOfNodes)
	{
		// 1. Utworzenie bufora z mr�wkami.
		ants=new ArrayList<Ant>(numberOfAnts);
		
		// 2. Dodawanie mr�wek do bufora.
		for(int i=0;i<numberOfAnts;i++)
		{
			ants.add(new Ant(numberOfNodes));
		}
		
		// 3. Przypisanie parametr�w.
		this.numberOfAnts=numberOfAnts;
		this.numberOfNodes=numberOfNodes;
	
		// 4. Utworzenie feromon�w.
		feromons=new double[numberOfNodes][numberOfNodes];
	}
	
	public Colony(int numberOfAnts, Graph g)
	{
		// 1. Utworzenie bufora z mr�wkami.
		ants=new ArrayList<Ant>(numberOfAnts);
		
		// 2. Dodawanie mr�wek do bufora.
		for(int i=0;i<numberOfAnts;i++)
		{
			ants.add(new Ant(g.getSize()));
		}
		
		// 3. Przypisanie parametr�w.
		this.numberOfAnts=numberOfAnts;
		this.numberOfNodes=g.getSize();
		
		// 4. Utworzenie feromon�w.
		feromons=new double[numberOfNodes][numberOfNodes];
	}
	// END REGION =====================================]

	// REGION: Wlasciwosci. ===========================]
	public List<Ant> getAnts()
	{
		return ants;
	}
	
	public int getNumberOfAnts() 
	{
		return numberOfAnts;
	}

	public int getNumberOfNodes()
	{
		return numberOfNodes;
	}

	public double[][] getFeromons()
	{
		return feromons;
	}
	// END REGION. ====================================]
	
	// REGION: Metody
	// CEL: Ustawienie mr�wek w w�z�ach pocz�tkowych.
	public void setAntsInStartNodes()
	{
		// 1. Utworzenie bufora z wierzcholkami.
		List<Integer>data=new ArrayList<Integer>();
		for(int i=0;i<numberOfNodes;i++)
		{
			data.add(i);
		}
		
		// 2. Roszada.
		Collections.shuffle(data);
		
		// 3. Ustawianie mr�wek w w�z�ach.
		int i=0;
		for(Ant a: ants)
		{
			a.addNode(data.get(i));
			i++;
		}
	}
	
	// CEL: Inicjalizacja feromon�w.
	public void initializeFeromons(Graph g)
	{
		// 1. Przechodzenie przez feromony.
		for(int i=0;i<numberOfNodes;i++)
		{
			for(int j=0;j<numberOfNodes;j++)
			{
				// 1.1 Sprawdzenie czy jest tam wog�le kraw�dz.
				if(g.isEdge(i, j))
				{
					feromons[i][j]=1;
				}
			}
		}
	}

	// CEL: Aktualizacja feromon�w.
	public void updateFeromons(Graph g, double p)
	{
		// 1. Przechodzenie przez feromony.
		for(int i=0;i<numberOfNodes;i++)
		{
			for(int j=0;j<numberOfNodes;j++)
			{
				// 1.2 Sprawdzenie czy jest tam jaki� feromon.
				if(feromons[i][j]>0)
				{
					// 1.3 Utworzenie licznika na liczbe przejsc do tego wierzcho�ka.
					int delta_tij=0;
					
					// 1.4 Zliczanie ilo�ci przej��.
					for(Ant ant: ants)
					{
						if(ant.getClique().contains(j))
						{
							delta_tij++;
						}
					}
					
					// 1.5 Aktulizowanie warto�ci feromonu.
					feromons[i][j]=(1-p)*feromons[i][j]+delta_tij;
				}
			}
		}
	}
	
	// CEL: Utworzenie klik dla ka�dej mr�wki.
	public void createCliques(Graph g, double alfa)
	{
		// 1. Przechodzenie przez wszystkie mr�wki.
		for(Ant a: ants)
		{
			// 1.2 Uzyskanie kandydat�w dla obecnej mr�wki.
			List<Integer>candidates=a.getCandidates(g);
			do
			{
				// 1.3 Losowanie prawdopobie�stwa.
				double p=Math.random();
				
				// 1.4 Obliczanie mianownika.
				double den=0.0d;
				for(int i: candidates)
				{
					den+=Math.pow(feromons[a.getCurrentNode()][i], alfa);
				}
				
				// 1.5 Obliczanie prawdopodobie�stwa.
				double lb=0;
				double rb=0;
				int nextNode=a.getCurrentNode();
				for(int i: candidates)
				{
					double res=Math.pow(feromons[a.getCurrentNode()][i], alfa)/den;
					if(p>= lb && p <= rb)
					{
						nextNode=i;
					}
					lb=rb;
					rb=res;
				}
				
				// 1.6 Przej�cie do nast�pngo wierzcho�ka.
				a.addNode(nextNode);
				
				// 1.7 Uzyskanie nowej listy z kandydatmi (bo si� zmieni�a!).
				candidates=a.getCandidates(g);
				
				// 1.8 Drukowanie ilosc kondydat�w.
				System.out.println(candidates.size());

			// Wykonuj dop�ki na lista z kandydatmi jest nie wi�ksza ni� 1 (czyli koniec kandydat�w).
			}while(candidates.size()>1);
			a.removeDuplicates();
		}
	}
	
	// CEL: Uzyskanie najlepszej mr�wki z kolonii.
	public Ant getBest()
	{
		Ant best=ants.get(0);
		for(Ant a: ants)
		{
			if(a.eval()>best.eval())
			{
				best=a;
			}
		}
		return best;
	}

	// CEL: Drukowanie mr�wek.
	public void print_ants()
	{
		int i=0;
		
		System.out.println("Ants: ");
		for(Ant a: ants)
		{
			System.out.print("Ant nr. "+i+" : ");
			System.out.println(a);
			i++;
		}
	}
	
	// CEL: Drukowanie feromon�w.
	public void print_feromons()
	{
		System.out.println("Feromons: ");
		for(int i=0;i<numberOfNodes;i++)
		{
			System.out.print("Fer nr. "+i+" : ");
			for(int j=0;j<numberOfNodes;j++)
			{
				if(feromons[i][j]!=0)
				{
					System.out.print(feromons[i][j]+" ");
				}
			}
			System.out.println();
		}
	}
	
	// CEL: Wypisanie kolonii.
	public void print()
	{
		print_ants();
		print_feromons();
	}
	// END REGION
}
