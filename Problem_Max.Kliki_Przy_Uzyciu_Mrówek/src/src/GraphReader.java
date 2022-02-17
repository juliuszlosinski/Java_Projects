package src;

import java.io.File;
import java.util.Scanner;

// CEL: Utworzenie struktury do wczytywania grafu z pliku.
public class GraphReader
{
	// REGION: Pola. =============================]
	Graph graph;		// Reprezentacja grafu.
	// END REGION	==============================]
	
	// REGION: Konstruktor. ======================]
	public GraphReader(String path)
	{
		// 1. Uzyskanie wskaznika do pliku.
		File f=new File(path);	
		
		// 2. Utworzenie czytnika do tego pliku.
		Scanner sc=null;
		try {
			sc=new Scanner(f);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Wczytywanie pliku nie powiod³o siê!");
		}

		// 3. Pominiêcie linii.
		for(int i=0;i<17; i++)
		{
			sc.nextLine();
		}
		String[]data=sc.nextLine().split(" ");
		graph=new Graph(Integer.parseInt(data[data.length-1]));

		// 4. Pominiêcie linii.
		for(int i=0;i<7;i++)
		{
			sc.nextLine();
		}
		
		// 5. Wczytywanie krawêdzi.
		while(sc.hasNext())
		{
			String[]buf=sc.nextLine().split(" ");
			graph.addEdge(Integer.parseInt(buf[1])-1, Integer.parseInt(buf[2])-1);
		}
	}
	// END REGION ================================]
	
	// REGION: Wlasciwosci.=======================]
	public Graph getGraph()
	{
		return graph;
	}
	// END REGION ================================]
}
