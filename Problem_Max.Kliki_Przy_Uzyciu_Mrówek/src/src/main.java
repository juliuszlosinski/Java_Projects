package src;

public class main 
{
	// CEL: Utworzenie algorytmu do znalezienia maksymalnej kliki przy pomocy kolonii mrówek.
	public static Ant ACO(int numberOfAnts, String path ,int MAX_ITER, double alfa, double p)
	{
		// 1. Uzyskanie grafu.
		Graph graph=new GraphReader(path).getGraph();
		
		// 2. Utworzenie kolonii mrówek.
		Colony colony=new Colony(numberOfAnts, graph.getSize());
		
		// 3. Inicjalizacja feromonów
		colony.initializeFeromons(graph);

		// 4. Pêtla zewnêtrzna.
		for(int i=0; i<MAX_ITER; i++)
		{
			// 4.0 Wypisanie iteracji.
			System.out.println("Starting Iteration nr."+(i+1)+":");
			
			// 4.1 Umieszczenie mrówek w pewnych wezlach pocz¹tkowych.
			System.out.println("Ants are being placed in the start nodes...");
			colony.setAntsInStartNodes();
			System.out.println("Ants are placed in the start nodes!");
			
			// 4.2 Tworzenie klik.
			System.out.println("Ants are creating cliques...");
			colony.createCliques(graph, alfa);
			System.out.println("Ants created cliques!");

			// 4.3 Aktualizacja feromonów.
			System.out.println("Feromons are being updated...");
			colony.updateFeromons(graph, p);
			System.out.println("Feromons are updated!");
			
			// 4.3
			System.out.println("Ending iteration nr."+(i+1)+".");
		}
		
		// 5. Zwrócenie najlepszej mrówki.
		return colony.getBest();
	}
	
	public static void main(String[]args)
	{
		String src="src/C125.9.clq.txt";
		Ant best=ACO(5,src, 20, 1.0d, 0.5d);
		System.out.println(best);
	}
}
