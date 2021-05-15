package testers;

public class Tester
{
	private static void Test_01()
	{
		/* Testowanie mechanizmow algorytmow genetycznych:
		String path="projekt1.txt";
		Task[] P1=AG_POZ.CreateChromoson(path);
		Task[] P2=AG_POZ.CreateChromoson(path);
		Task[] S1=new Task[P1.length];
		Task[] S2=new Task[P2.length];
		AG_POZ.OX(P1, P2, S1, S2);
		System.out.println("P1:------]");
		AG_POZ.PrintChromoson(P1);
		System.out.println("P2:------]");
		AG_POZ.PrintChromoson(P2);
		System.out.println("S1:------]");
		AG_POZ.PrintChromoson(S1);
		System.out.println("S2:------]");
		AG_POZ.PrintChromoson(S2);
		*/
	}
	
	public static void main(String[]args)
	{
		// 1. Sciezka do pliku.
		String path="projekt1.txt";
		
		// 2. Uzyskanie najlepszej kolejnoœci wykonywania zadañ przy pomocy algorytmu genetycznego przy wykorzystanu PMX.
		//    Wypisywanie dany wyjsciowych jest wykonywane w tym algorytmie do wskazanych plików.
		Task[]res=AG_POZ.AG(path,100, 1000);
		
		// 3. Wypisanie kolejnosci wykonywania zadan.
		System.out.println("Kolejnosc wykonywania zadan:");
		AG_POZ.PrintChromoson(res);
		
		// 4. Wypisywanie sredniego czasu oczekiwania.
		System.out.println("Sredni czas oczekiwania: "+AG_POZ.Function(res));
	}
}
