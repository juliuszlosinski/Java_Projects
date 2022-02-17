import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class LS 
{
	// ======== KLASY OGÓLNE ======== \\
	
	// Klasa do wczytywania danych.
	static class DataReader 
	{
		// Pola:
		private int numberOfNodes; // Ilosc wêz³ów.
		private int dimensionsOfPoint;	   // Wymiary.
		private float[][]coords;   // Wspó³rzêdne miast.
		
		// Konstruktor:
		public DataReader(String path)
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

			// 3. Wczytanie danych z pliku.
			
			// 3.1. Pominiêcie trzech lini.
			for(int i=0;i<3;i++)
			{
				sc.nextLine();
			}
			
			// 3.2. Uzyskanie informacji na temat ilosc wezlow/ miast.
			numberOfNodes=Integer.parseInt(sc.nextLine().split(" ")[1]);

			// 3.3. Ustawienie wymiaru punktu.
			dimensionsOfPoint=2;
			
			// 3.4. Utworzenie bufora na wspolrzedne punktów.
			coords=new float[numberOfNodes][dimensionsOfPoint];
			
			// 3.5. Pominiecie kolejnych dwóch lini.
			for(int i=0;i<2;i++)
			{
				sc.nextLine();
			}
			
			// 3.6. Wczytywanie danych do bufora.
			for(int i=0;i<52;i++)
			{
				String[]buff=sc.nextLine().split(" ");
				coords[i][0]=Float.parseFloat(buff[1]);
				coords[i][1]=Float.parseFloat(buff[2]);
			}
		}

		// Wlasciwosci:
		// Zwrocenie ilosci wezlow.
		public int GetNumberOfNodes()
		{
			return numberOfNodes;
		}
		
		// Zwrocenie rozmiaru wymiaru punktu.
		public int GetDimensionsOfPoint()
		{
			return dimensionsOfPoint;
		}
		
		// Zwrocenie wspolrzednych punktow.
		public float[][]GetCoords()
		{
			return coords;
		}

		// Drukowanie wspolrzednych punktow.
		public void PrintCoords()
		{
			for(int i=0;i<numberOfNodes;i++)
			{
				System.out.print(i+" ");
				for(int j=0;j<dimensionsOfPoint;j++)
				{
					System.out.print(coords[i][j]+" ");
				}
				System.out.println();
			}
		}
	}

	// =============================== \\
	
	// ======== METODY OGÓLNE ======== \\

	// CEL: Drukowanie Sciezki.
	public static void PrintRoute(int[]route)
	{
		System.out.println("Route: ");
		for(int i=0; i<route.length; i++)
		{
			System.out.print(route[i]+" ");
		}
		System.out.println(route[0]);
	}
	
	// CEL: Drukowanie uzyskanej sciezki wraz z jej dlugoscia.
	public static void PrintRouteWithInfo(int[]route, float[][]coords)
	{
		System.out.println("========= RESULTS ==========]");
		System.out.println("-----------]");
		System.out.println("Route:");
		for(int i=0;i<route.length;i++)
		{
			System.out.print(route[i]+" ");
		}
		System.out.print(route[0]);
		System.out.println("\n-----------]");
		System.out.println("Distance: "+Ocen(route,coords));
		System.out.println("=============================]");
	}
	
	// CEL: Oblicz d³ugoœæ ca³ej œcie¿ki.
 	public static double CalcRoute(int[]route, float[][]coords)
	{
		// 1. Obliczenie d³ugoœci œcie¿ki.
		double sum=0;
		int first, second;
		for(int i=1;i<coords.length;i++)
		{
			first=route[i-1];
			second=route[i];
			sum+=GetDistanceEukli(coords[first], coords[second]);
		}
		first=route[coords.length-1];
		second=route[0];
		sum+=GetDistanceEukli(coords[first], coords[second]);
		
		// 2. Zwrócenie lacznej dlugosci sciezki.
		return sum;
	}
	
	// CEL: Ocena rozwiazania.
	private static double Ocen(int[]Vc, float[][]coords)
	{
		return CalcRoute(Vc, coords);
	}
	
	// CEL: Zwrocenie dystansu miedzy punktami.
	private static double GetDistanceEukli(float first[], float second[])
	{
		return Math.sqrt(Math.pow(second[0]-first[0], 2)+Math.pow(second[1]-first[1], 2));
	}
	
	// CEL: Obliczenie sciezki uzyskanej sciezki.
	public static double GetBestDistanceFromFile(DataReader cdr, String best)
	{
		// 1. Utworzenie pliku.
		File f=new File(best);
		
		// 2. Utworzenie skanera.
		Scanner sc=null;
		try
		{
			sc=new Scanner(f);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		// 3. Przechodzenie do nastepnych linii.
		for(int i=0;i<4;i++)
		{
			sc.nextLine();
		}
		
		// 4. Obliczzenie dlugosci sciezki.
		ArrayList<Integer>route=new ArrayList<Integer>();
		int i=0;
		double totalDistance=0;
		while(sc.hasNextLine())
		{
			String r=sc.nextLine();
			if(r.equals("-1") || r.equals("EOF"))
			{
				break;
			}
			route.add(Integer.parseInt(r)-1);
			if(i>=1)
			{
				totalDistance+=GetDistanceEukli(cdr.GetCoords()[route.get(i-1)], cdr.GetCoords()[route.get(i)]);
			}
			i++;
		}
		totalDistance+=GetDistanceEukli(cdr.GetCoords()[route.get(0)], cdr.GetCoords()[route.get(route.size()-1)]);
		
		// 5. Zwrocenie dlugosci sciezki.
		return totalDistance;
	}
	
	// CEL: Uzyskanie œcie¿ki z pliku.
	public static int[] GetRouteFromFile(String best)
	{
		// 1. Utworzenie pliku.
		File f=new File(best);
		
		// 2. Utworzenie skanera.
		Scanner sc=null;
		try
		{
			sc=new Scanner(f);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		// 3. Przechodzenie do nastepnych linii.
		for(int i=0;i<4;i++)
		{
			sc.nextLine();
		}
		
		// 4. Utworzenie bufora na sciezke.
		ArrayList<Integer>route=new ArrayList<Integer>();
		while(sc.hasNextLine())
		{
			String r=sc.nextLine();
			if(r.equals("-1") || r.equals("EOF"))
			{
				break;
			}
			route.add(Integer.parseInt(r)-1);
		}
		
		/*
		for(int i=0;i<route.size();i++)
		{
			System.out.println(route.get(i));
		}
		*/
		
		// 5. Utworzenie sciezki.
		int[]path=new int[route.size()];
		for(int i=0;i<route.size();i++)
		{
			path[i]=route.get(i);
		}
		
		// 6. Zwrocenie dlugosci sciezki.
		return path;
	}
	
	// ================================== \\
	
	// ============ ALGORYTMY =========== \\
	
	// Algorytm przeszukiwania lokalnego.
	class Local_Search
	{
		/* Operatory:
		
		1. Zamiana dwoch miasta (swap).
		2. Przesuniêcie miasta (move).
		3. Ruch 2-OPT.
		4. Lambda - Interchange.

		*/
		
		// Typy generowania rozwizan poczatkowych.
		public static enum WRP
		{
			Order, Random
		}
		
		// Typy wybierania sasiada.
		public static enum WRZO
		{
			FirstBetter, TheBest
		}
		
		// CEL: Operator zamiany.
		private static int[] SwapOperator(int[]src)
		{
			// 0. Utworzenie bufora tymczasowego.
			int[]buff=new int[src.length];
			for(int i=0;i<buff.length;i++)
			{
				buff[i]=src[i];
			}
			
			// 1. Utworzenie obiektu do generowania liczb pseudo-losowych.
			Random gen = new Random();
			
			// 2. Generowanie dwoch pozycji losowych w buforze.
			int i = gen.nextInt(buff.length);
			int j = gen.nextInt(buff.length);
			while(i!=j)
			{
				i = gen.nextInt(buff.length);
			}

			// 3. Zamiana dwoch elementów.
			int tmp=buff[i];
			buff[i]=buff[j];
			buff[j]=tmp;
			
			// 4. Zwrocenie wyniku.
			return buff;
		}
				
		// CEL: Operator przesuniêcia.
		private static int[] MoveOperator(int[]src)
		{
			// 0. Utworzenie bufora tymczasowego.
			int[]buff=new int[src.length];
			for(int i=0;i<src.length;i++)
			{
				buff[i]=src[i];
			}
			
			// 1. Utworzenie obiektu do generowania liczb pseudo-losowych.
			Random gen=new Random();
			
			// 2. Wygenerowanie pozycji.
			int pos = gen.nextInt(buff.length-1);
			
			// 3. Wygenerowanie przesuniêcia.
			int offset = gen.nextInt(buff.length-1);
			
			int path=pos+offset;
			
			if(path>=buff.length)
			{
				path=buff.length;
			}
			
			// 4. Przesuniecie elementu o okreslon¹ iloœæ pozycji.
			for(int i=pos+1;i<path;i++)
			{
				int tmp = buff[i-1];
				buff[i-1]=buff[i];
				buff[i]=tmp;
			}
			
			// 5. Zwrocenie wyniku.
			return buff;
		}
		
		// CEL: Operator 2-OPT.
		private static int[] TwoOPTOperator(int[]src)
		{
			// 1. Utworzenie generatora do pseudo-losowych liczb.
			Random gen=new Random();
			
			// 2. Utworzenie dwóch indeksów losowych wskazuj¹cych pozycjê w buforze.
			int firstPos=gen.nextInt(src.length-1);
			int secondPos=gen.nextInt(src.length-1);
			
			// 3. Upewnienie siê, ¿e wygenerowany indeks secondPos -> jest dalej ni¿ firstPos.
			if(firstPos>secondPos)
			{
				int tmp=firstPos;
				firstPos=secondPos;
				secondPos=tmp;
			}
			
			// 4. Kopiowianie wartosci od 0 do firstPos (wed³ug kolejnoœci) do bufora z rozwi¹zaniem.
			int[]res=new int[src.length];
			for(int i=0; i<firstPos; i++)
			{
				res[i]=src[i];
			}
						
			// 5. Kopiowanie wartosci od firstPos do secondPos (wed³ug odwróconej kolejnoœci czyli bêdziemy siê cofaæ od secondPos do firstPos) do bufora z rozwi¹zaniem.
			for(int i=secondPos; i>=firstPos; i--)
			{
				res[i]=src[i];
			}
			
			// 6. Kopiowanie wartosci od secondPos + 1 do koñca bufora.
			for(int i=secondPos; i<src.length; i++)
			{
				res[i]=src[i];
			}
			
			// 7. Zwrocenie wyniku.
			return res;
		}
		
		// CEL: Operator Lambda Interchange.
		private static int[] LambdaInterchangeOperator(int[]src)
		{
			// 1. Utworzenie obiektu do generowania liczb pseudo-losowych.
			Random gen=new Random();
			
			// 2. Wygenerowanie losowej pozycji.
			int cutOffIndex=gen.nextInt(src.length-1);
			
			// 3. Utworzenie bufora wynikowego.
			int[]res=new int[src.length];
			
			// 4. Kopiowanie elementów.
			for(int i=cutOffIndex;i<src.length;i++)
			{
				res[i]=src[i];
			}
			for(int i=cutOffIndex;i>0;i--)
			{
				res[i]=src[i];
			}
		
			// 5. Zwrocenie wyniku.
			return res;
		}
		
		// CEL: Uzyskanie poczatkowej permutacji miast.
		private static int[] wybierz_rozwiazanie_poczatkowe(int numberOfNodes,WRP r)
		{
			// LEGENDA:
			// numberOfNodes - ilosc wezlow/ miast.
			// r - tryb generowania populacji poczatkowej.
			
			// 1. Generowanie buffora wypelnionymi wartosciami od 0 do numberOfNodes.
			Stack<Integer>buff=new Stack<Integer>();
			for(int i=0;i<numberOfNodes; i++)
			{
				buff.add(i);
			}
			
			// 2. Utworzenie obiektu do generowania liczb pseudo-losowych.
			Random rn=new Random();
			
			// 3. Utworzenie bufora na rozwi¹zanie.
			int[]buff_res=new int[numberOfNodes];
			
			// 4. Wypelnienie permutacja miast.
			switch(r)
			{
			case Order:
				for(int i=0;i<numberOfNodes;i++)
				{
					buff_res[i]=i;
				}
				break;
			case Random:
				for(int i=0;i<numberOfNodes;i++)
				{
					int index=rn.nextInt(buff.size());
					buff_res[i]=buff.get(index);
					buff.remove(index);
				}
				break;
			}
			
			// 5. Zwrocenie wyniku.
			return buff_res;
		}
		
		// CEL: Uzyskanie nowej permutacji z miastami -> sciezki.
		private static int[] wybierz_rozwiazanie_z_otoczenia(int[]route, float[][]coords, int maxTimes, WRZO mode)
		{
			// 2. Utworzenie bufora na tymczasowa sciezke, zeby nie nadpisywaæ obecnej sciezki.
			int[]tmpRoute=new int[route.length]; // Tymczasowa sciezka.
			for(int i=0;i<route.length;i++)
			{
				tmpRoute[i]=route[i];
			}
			
			// 2. Utworzenie parametrów pomocniczych.
			double bestDistance;	// D³ugoœæ dystansu.
			int i=0;			// Ilosc wyszukiwañ.
			
			// 3. Obliczenie obecnej dlugosci sciezki.
			bestDistance=CalcRoute(route, coords);
			
			// 4. Znajdowanie lepsze sciezki.
			while(i<maxTimes)
			{
				// 4.1. Znajdowanie indeksów lokalizacji zamiany.
				tmpRoute=TwoOPTOperator(tmpRoute);
				
				// 4.3. Obliczanie dlugosci sciezki.
				double currentDistance = CalcRoute(tmpRoute, coords);
				
				// 4.4. Sprawdzanie czy nowa dlugosc sciezki jest mniejsza niz ta obecna.
				if(currentDistance<bestDistance)
				{
					// 4.4.1 Jest mniejsza wiec ustawiamy ja jako nowa sciezke.
					bestDistance=currentDistance;
					
					switch(mode)
					{
					case TheBest:
						// 4.4.2.
						// Kontynuuj szukanie najlepszego rozwiazania.
					break;
					
					case FirstBetter:
						// 4.4.2. Zwroc te rozwiazanie.
						return tmpRoute;
					}
				}
				
				// 4.4. Przejscie do kolejnej iteracji.
				i++;
			}
			
			// 5. Zwrocenie nowej sciezki.
			return tmpRoute;
		}
		
		private static int[] wybierz_rozwiazanie_z_otoczenia_MOVE_OPERATOR(int[]route, float[][]coords, int maxTimes, WRZO mode)
		{
			// 1. Utworzenie obiektu do generowania pseudolosowych liczb.
			Random gen=new Random();
			
			// 2. Utworzenie bufora na tymczasowa sciezke, zeby nie nadpisywaæ obecnej sciezki.
			int[]tmpRoute=new int[route.length]; // Tymczasowa sciezka.
			for(int i=0;i<route.length;i++)
			{
				tmpRoute[i]=route[i];
			}
			
			// 2. Utworzenie parametrów pomocniczych.
			double bestDistance;	// D³ugoœæ dystansu.
			int i=0;			// Ilosc wyszukiwañ.
			
			// 3. Obliczenie obecnej dlugosci sciezki.
			bestDistance=CalcRoute(route, coords);
			
			// 4. Znajdowanie lepsze sciezki.
			while(i<maxTimes)
			{
				// 4.1 Stosowanie operatora przenoszenia.
				tmpRoute=MoveOperator(tmpRoute);
				
				// 4.3. Obliczanie dlugosci sciezki.
				double currentDistance = CalcRoute(tmpRoute, coords);
				
				// 4.4. Sprawdzanie czy nowa dlugosc sciezki jest mniejsza niz ta obecna.
				if(currentDistance<bestDistance)
				{
					// 4.4.1 Jest mniejsza wiec ustawiamy ja jako nowa sciezke.
					bestDistance=currentDistance;
					
					switch(mode)
					{
					case TheBest:
						// 4.4.2.
						// Kontynuuj szukanie najlepszego rozwiazania.
					break;
					
					case FirstBetter:
						// 4.4.2. Zwroc te rozwiazanie.
						return tmpRoute;
					}
				}
				
				// 4.4. Przejscie do kolejnej iteracji.
				i++;
			}
			
			// 5. Zwrocenie nowej sciezki.
			return tmpRoute;
		}
		
		private static int[] wybierz_rozwiazanie_z_otoczenia_SWAP_OPERATOR(int[]route, float[][]coords, int maxTimes, WRZO mode)
		{
			// 1. Utworzenie obiektu do generowania pseudolosowych liczb.
			Random gen=new Random();
			
			// 2. Utworzenie bufora na tymczasowa sciezke, zeby nie nadpisywaæ obecnej sciezki.
			int[]tmpRoute=new int[route.length]; // Tymczasowa sciezka.
			for(int i=0;i<route.length;i++)
			{
				tmpRoute[i]=route[i];
			}
			
			// 2. Utworzenie parametrów pomocniczych.
			double bestDistance;	// D³ugoœæ dystansu.
			int i=0;			// Ilosc wyszukiwañ.
			
			// 3. Obliczenie obecnej dlugosci sciezki.
			bestDistance=CalcRoute(route, coords);
			
			// 4. Znajdowanie lepsze sciezki.
			while(i<maxTimes)
			{
				// 4.1 Stosowanie operatory zamiany miejscami.
				tmpRoute=SwapOperator(tmpRoute);
				
				// 4.3. Obliczanie dlugosci sciezki.
				double currentDistance = CalcRoute(tmpRoute, coords);
				
				// 4.4. Sprawdzanie czy nowa dlugosc sciezki jest mniejsza niz ta obecna.
				if(currentDistance<bestDistance)
				{
					// 4.4.1 Jest mniejsza wiec ustawiamy ja jako nowa sciezke.
					bestDistance=currentDistance;
					
					switch(mode)
					{
					case TheBest:
						// 4.4.2.
						// Kontynuuj szukanie najlepszego rozwiazania.
					break;
					
					case FirstBetter:
						// 4.4.2. Zwroc te rozwiazanie.
						return tmpRoute;
					}
				}
				
				// 4.4. Przejscie do kolejnej iteracji.
				i++;
			}
			
			// 5. Zwrocenie nowej sciezki.
			return tmpRoute;
		}
	
		private static int[] wybierz_rozwiazanie_z_otoczenia_TWO_OPT_OPERATOR(int[]route, float[][]coords, int maxTimes, WRZO mode)
		{
			// 1. Utworzenie obiektu do generowania pseudolosowych liczb.
			Random gen=new Random();
			
			// 2. Utworzenie bufora na tymczasowa sciezke, zeby nie nadpisywaæ obecnej sciezki.
			int[]tmpRoute=new int[route.length]; // Tymczasowa sciezka.
			for(int i=0;i<route.length;i++)
			{
				tmpRoute[i]=route[i];
			}
			
			// 2. Utworzenie parametrów pomocniczych.
			double bestDistance;	// D³ugoœæ dystansu.
			int i=0;			// Ilosc wyszukiwañ.
			
			// 3. Obliczenie obecnej dlugosci sciezki.
			bestDistance=CalcRoute(route, coords);
			
			// 4. Znajdowanie lepsze sciezki.
			while(i<maxTimes)
			{
				// 4.1 Stosowanie operatora 2-OPT.
				tmpRoute=TwoOPTOperator(tmpRoute);
				
				// 4.3. Obliczanie dlugosci sciezki.
				double currentDistance = CalcRoute(tmpRoute, coords);
				
				// 4.4. Sprawdzanie czy nowa dlugosc sciezki jest mniejsza niz ta obecna.
				if(currentDistance<bestDistance)
				{
					// 4.4.1 Jest mniejsza wiec ustawiamy ja jako nowa sciezke.
					bestDistance=currentDistance;
					
					switch(mode)
					{
					case TheBest:
						// 4.4.2.
						// Kontynuuj szukanie najlepszego rozwiazania.
					break;
					
					case FirstBetter:
						// 4.4.2. Zwroc te rozwiazanie.
						return tmpRoute;
					}
				}
				
				// 4.4. Przejscie do kolejnej iteracji.
				i++;
			}
			
			// 5. Zwrocenie nowej sciezki.
			return tmpRoute;
		}
		
		private static int[] wybierz_rozwiazanie_z_otoczenia_LAMBDA_INTERCHANGE(int[]route, float[][]coords, int maxTimes, WRZO mode)
		{
			// 1. Utworzenie obiektu do generowania pseudolosowych liczb.
			Random gen=new Random();
			
			// 2. Utworzenie bufora na tymczasowa sciezke, zeby nie nadpisywaæ obecnej sciezki.
			int[]tmpRoute=new int[route.length]; // Tymczasowa sciezka.
			for(int i=0;i<route.length;i++)
			{
				tmpRoute[i]=route[i];
			}
			
			// 2. Utworzenie parametrów pomocniczych.
			double bestDistance;	// D³ugoœæ dystansu.
			int i=0;			// Ilosc wyszukiwañ.
			
			// 3. Obliczenie obecnej dlugosci sciezki.
			bestDistance=CalcRoute(route, coords);
			
			// 4. Znajdowanie lepsze sciezki.
			while(i<maxTimes)
			{
				// 4.1. Znajdowanie indeksów lokalizacji zamiany.
				tmpRoute=LambdaInterchangeOperator(tmpRoute);
				
				// 4.3. Obliczanie dlugosci sciezki.
				double currentDistance = CalcRoute(tmpRoute, coords);
				
				// 4.4. Sprawdzanie czy nowa dlugosc sciezki jest mniejsza niz ta obecna.
				if(currentDistance<bestDistance)
				{
					// 4.4.1 Jest mniejsza wiec ustawiamy ja jako nowa sciezke.
					bestDistance=currentDistance;
					
					switch(mode)
					{
					case TheBest:
						// 4.4.2.
						// Kontynuuj szukanie najlepszego rozwiazania.
					break;
					
					case FirstBetter:
						// 4.4.2. Zwroc te rozwiazanie.
						return tmpRoute;
					}
				}
				
				// 4.4. Przejscie do kolejnej iteracji.
				i++;
			}
			
			// 5. Zwrocenie nowej sciezki.
			return tmpRoute;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_1(WRP modeStart, WRZO modeNeighbor, DataReader dr, int maxTimes)
		{
			// 1. Utworzenie flagi.
			boolean local = false;
			
			// 2. Uzyskanie rozwiazania poczatkowego.
			int[]Vc=wybierz_rozwiazanie_poczatkowe(dr.GetNumberOfNodes(), modeStart);
			
			// 3. Ocena obecnego rozwi¹zania.
			double bestDistance=Ocen(Vc, dr.GetCoords());
			
			// 4. Szukanie najlepszeg rozwiazania.
			do
			{
				// 4.1. Generowanie nowego rozwi¹zania.
				int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
				
				// 4.2. Obliczenie sciezki na podstawie owego rozwiazania.
				double newDistance=Ocen(Vn, dr.GetCoords());
				
				// 4.3. Sprawdzanie czy nowy dystans jest lepszy od obecnego.
				if(newDistance<bestDistance)
				{
					// 4.3.1. Jest lepszy, zatem kontynuujemy szukanie kolejnego.
					bestDistance=newDistance;
					Vc=Vn;
				}
				// 4.3. Nie jest lepszy.
				else
				{
					// 4.3.2 Ustawiamy flage, ze przestajemy flage.
					local=true;
				}
			
			}while(!local);
			
			// 5. Zwrocenie rozwiazania.
			return Vc;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_1(WRP modeStart, WRZO modeNeighbor, DataReader dr, int[]Vc, int maxTimes)
		{
			// 1. Utworzenie flagi.
			boolean local = false;
			
			// 2. Ocena obecnego rozwi¹zania.
			double bestDistance=Ocen(Vc, dr.GetCoords());
			
			// 3. Szukanie najlepszeg rozwiazania.
			do
			{
				// 3.1. Generowanie nowego rozwi¹zania.
				int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
				
				// 3.2. Obliczenie sciezki na podstawie owego rozwiazania.
				double newDistance=Ocen(Vn, dr.GetCoords());
				
				// 3.3. Sprawdzanie czy nowy dystans jest lepszy od obecnego.
				if(newDistance<bestDistance)
				{
					// 3.3.1. Jest lepszy, zatem kontynuujemy szukanie kolejnego.
					bestDistance=newDistance;
					Vc=Vn;
				}
				// 3.3. Nie jest lepszy.
				else
				{
					// 3.3.2 Ustawiamy flage, ze przestajemy flage.
					local=true;
				}
			
			}while(!local);
			
			// 4. Zwrocenie rozwiazania.
			return Vc;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_1(WRP modeStart, WRZO modeNeighbor, String src, int maxTimes)
		{
			// 0. Utworzenie czytnika pliku.
			DataReader dr=new DataReader(src);
			
			// 1. Utworzenie flagi.
			boolean local = false;
			
			// 2. Uzyskanie rozwiazania poczatkowego.
			int[]Vc=wybierz_rozwiazanie_poczatkowe(dr.GetNumberOfNodes(), modeStart);
			
			// 3. Ocena obecnego rozwi¹zania.
			double bestDistance=Ocen(Vc, dr.GetCoords());
			
			// 4. Szukanie najlepszeg rozwiazania.
			do
			{
				// 4.1. Generowanie nowego rozwi¹zania.
				int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
				
				// 4.2. Obliczenie sciezki na podstawie owego rozwiazania.
				double newDistance=Ocen(Vn, dr.GetCoords());
				
				// 4.3. Sprawdzanie czy nowy dystans jest lepszy od obecnego.
				if(newDistance<bestDistance)
				{
					// 4.3.1. Jest lepszy, zatem kontynuujemy szukanie kolejnego.
					bestDistance=newDistance;
					Vc=Vn;
				}
				// 4.3. Nie jest lepszy.
				else
				{
					// 4.3.2 Ustawiamy flage, ze przestajemy flage.
					local=true;
				}
			}while(!local);
			
			// 5. Zwrocenie rozwiazania.
			return Vc;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_1(WRP modeStart, WRZO modeNeighbor, String src, int[]Vc, int maxTimes)
		{
			// 0. Utworzenie czytnika pliku.
			DataReader dr=new DataReader(src);
			
			// 1. Utworzenie flagi.
			boolean local = false;
			
			// 2. Ocena obecnego rozwi¹zania.
			double bestDistance=Ocen(Vc, dr.GetCoords());
			
			// 3. Szukanie najlepszeg rozwiazania.
			do
			{
				// 3.1. Generowanie nowego rozwi¹zania.
				int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
				
				// 3.2. Obliczenie sciezki na podstawie owego rozwiazania.
				double newDistance=Ocen(Vn, dr.GetCoords());
				
				// 3.3. Sprawdzanie czy nowy dystans jest lepszy od obecnego.
				if(newDistance<bestDistance)
				{
					// 3.3.1. Jest lepszy, zatem kontynuujemy szukanie kolejnego.
					bestDistance=newDistance;
					Vc=Vn;
				}
				// 3.3. Nie jest lepszy.
				else
				{
					// 3.3.2 Ustawiamy flage, ze przestajemy flage.
					local=true;
				}
			}while(!local);
			
			// 4. Zwrocenie rozwiazania.
			return Vc;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_2(WRP modeStary, WRZO modeNeighbor, String src, int maxTimes, int MAX)
		{
			// 0. Utworzenie czytnika pliku.
			DataReader dr=new DataReader(src);
			
			// 1. Inicjalizacja oraz deklaracja kroku.
			int t=0;
			
			// 2. Inicjalizacja najlepszego z najlepszych rozwi¹zañ.
			double theBestDistance=Double.POSITIVE_INFINITY;
			int[]V_thebest=new int[dr.GetNumberOfNodes()];
			
			// 3. Szukanie najlepszego rozwiazania.
 			do
			{
 				// 3.1. Ustawienie flagi.
				boolean local = false;
				
				// 3.2. Generowanie rozwiazania poczatkowego.
				int[]Vc=wybierz_rozwiazanie_poczatkowe(dr.GetNumberOfNodes(), modeStary);
				
				// 3.3. Obliczenie jego dystansu.
				double bestDistance=Ocen(Vc, dr.GetCoords());
				
				// 3.4. Szukanie nowego rozwiazania.
				do
				{
					// 3.4.1. Wybieranie rozwiazania z otoczenia.
					int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
					
					// 3.4.2. Obliczanie jego dystansu.
					double newDistance=Ocen(Vn, dr.GetCoords());
					
					// 3.4.3. Sprawdzenie czy jest mniejszy niz wczesniejsze poczatkowe.
					if(newDistance<bestDistance)
					{
						// 3.4.3.1. Ustawienie nowego obecnie dobrego.
						bestDistance=newDistance;
						Vc=Vn;
					}
					// 3.4.3. Nie znalezlismy.
					else
					{
						// 3.4.3.1. Ustawienie flagi wyjscia na prawde.
						local=true;
					}
				
				}while(!local);
				
				// 3.5. Sprawdzanie czy obecny Vc jest lepszy od najlepszego.
				if(theBestDistance>bestDistance)
				{
					// 3.5.1. Ustawiamy nowy najlepszy.
					theBestDistance=bestDistance;
					V_thebest=Vc;
				}
				
				// 3.6. Idziemy do kolejnej iteracji petli.
				t++;
				
			}while(t<MAX);
 			
 			// 4. Zwrocenie najlepszej sciezki/ rozwiazania.
			return V_thebest;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_2(WRP modeStary, WRZO modeNeighbor, String src, int maxTimes, int MAX, int[]Vc)
		{
			// 0. Utworzenie czytnika pliku.
			DataReader dr=new DataReader(src);
			
			// 1. Inicjalizacja oraz deklaracja kroku.
			int t=0;
			
			// 2. Inicjalizacja najlepszego z najlepszych rozwi¹zañ.
			double theBestDistance=Double.POSITIVE_INFINITY;
			int[]V_thebest=new int[dr.GetNumberOfNodes()];
			
			// 3. Szukanie najlepszego rozwiazania.
 			do
			{
 				// 3.1. Ustawienie flagi.
				boolean local = false;

				
				// 3.3. Obliczenie jego dystansu.
				double bestDistance=Ocen(Vc, dr.GetCoords());
				
				// 3.4. Szukanie nowego rozwiazania.
				do
				{
					// 3.4.1. Wybieranie rozwiazania z otoczenia.
					int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
					
					// 3.4.2. Obliczanie jego dystansu.
					double newDistance=Ocen(Vn, dr.GetCoords());
					
					// 3.4.3. Sprawdzenie czy jest mniejszy niz wczesniejsze poczatkowe.
					if(newDistance<bestDistance)
					{
						// 3.4.3.1. Ustawienie nowego obecnie dobrego.
						bestDistance=newDistance;
						Vc=Vn;
					}
					// 3.4.3. Nie znalezlismy.
					else
					{
						// 3.4.3.1. Ustawienie flagi wyjscia na prawde.
						local=true;
					}
				
				}while(!local);
				
				// 3.5. Sprawdzanie czy obecny Vc jest lepszy od najlepszego.
				if(theBestDistance>bestDistance)
				{
					// 3.5.1. Ustawiamy nowy najlepszy.
					theBestDistance=bestDistance;
					V_thebest=Vc;
				}
				
				// 3.6. Idziemy do kolejnej iteracji petli.
				t++;
				
			}while(t<MAX);
 			
 			// 4. Zwrocenie najlepszej sciezki/ rozwiazania.
			return V_thebest;
		}
		
		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_2(WRP modeStary, WRZO modeNeighbor, DataReader dr, int maxTimes, int MAX)
		{
			// 1. Inicjalizacja oraz deklaracja kroku.
			int t=0;
			
			// 2. Inicjalizacja najlepszego z najlepszych rozwi¹zañ.
			double theBestDistance=Double.POSITIVE_INFINITY;
			int[]V_thebest=new int[dr.GetNumberOfNodes()];
			
			// 3. Szukanie najlepszego rozwiazania.
 			do
			{
 				// 3.1. Ustawienie flagi.
				boolean local = false;
				
				// 3.2. Generowanie rozwiazania poczatkowego.
				int[]Vc=wybierz_rozwiazanie_poczatkowe(dr.GetNumberOfNodes(), modeStary);
				
				// 3.3. Obliczenie jego dystansu.
				double bestDistance=Ocen(Vc, dr.GetCoords());
				
				// 3.4. Szukanie nowego rozwiazania.
				do
				{
					// 3.4.1. Wybieranie rozwiazania z otoczenia.
					int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
					
					// 3.4.2. Obliczanie jego dystansu.
					double newDistance=Ocen(Vn, dr.GetCoords());
					
					// 3.4.3. Sprawdzenie czy jest mniejszy niz wczesniejsze poczatkowe.
					if(newDistance<bestDistance)
					{
						// 3.4.3.1. Ustawienie nowego obecnie dobrego.
						bestDistance=newDistance;
						Vc=Vn;
					}
					// 3.4.3. Nie znalezlismy.
					else
					{
						// 3.4.3.1. Ustawienie flagi wyjscia na prawde.
						local=true;
					}
				
				}while(!local);
				
				// 3.5. Sprawdzanie czy obecny Vc jest lepszy od najlepszego.
				if(theBestDistance>bestDistance)
				{
					// 3.5.1. Ustawiamy nowy najlepszy.
					theBestDistance=bestDistance;
					V_thebest=Vc;
				}
				
				// 3.6. Idziemy do kolejnej iteracji petli.
				t++;
				
			}while(t<MAX);
 			
 			// 4. Zwrocenie najlepszej sciezki/ rozwiazania.
			return V_thebest;
			}

		// CEL: Przeszukiwanie lokalne w celu znalezienia najlepszego rozwi¹zania.
		public static int[] LocalSearch_2(WRP modeStary, WRZO modeNeighbor, DataReader dr, int maxTimes, int MAX, int[]Vc)
		{
			// 1. Inicjalizacja oraz deklaracja kroku.
			int t=0;
			
			// 2. Inicjalizacja najlepszego z najlepszych rozwi¹zañ.
			double theBestDistance=Double.POSITIVE_INFINITY;
			int[]V_thebest=new int[dr.GetNumberOfNodes()];
			
			// 3. Szukanie najlepszego rozwiazania.
 			do
			{
 				// 3.1. Ustawienie flagi.
				boolean local = false;
				
				// 3.3. Obliczenie jego dystansu.
				double bestDistance=Ocen(Vc, dr.GetCoords());
				
				// 3.4. Szukanie nowego rozwiazania.
				do
				{
					// 3.4.1. Wybieranie rozwiazania z otoczenia.
					int[]Vn=wybierz_rozwiazanie_z_otoczenia(Vc, dr.GetCoords(), maxTimes, modeNeighbor);
					
					// 3.4.2. Obliczanie jego dystansu.
					double newDistance=Ocen(Vn, dr.GetCoords());
					
					// 3.4.3. Sprawdzenie czy jest mniejszy niz wczesniejsze poczatkowe.
					if(newDistance<bestDistance)
					{
						// 3.4.3.1. Ustawienie nowego obecnie dobrego.
						bestDistance=newDistance;
						Vc=Vn;
					}
					// 3.4.3. Nie znalezlismy.
					else
					{
						// 3.4.3.1. Ustawienie flagi wyjscia na prawde.
						local=true;
					}
				
				}while(!local);
				
				// 3.5. Sprawdzanie czy obecny Vc jest lepszy od najlepszego.
				if(theBestDistance>bestDistance)
				{
					// 3.5.1. Ustawiamy nowy najlepszy.
					theBestDistance=bestDistance;
					V_thebest=Vc;
				}
				
				// 3.6. Idziemy do kolejnej iteracji petli.
				t++;
				
			}while(t<MAX);
 			
 			// 4. Zwrocenie najlepszej sciezki/ rozwiazania.
			return V_thebest;
			}
	}
	
	// Algorytm przeszukiwania zach³annego.
	class GreedySearch_BestNeighbor
	{
		// CEL: Obliczenie najmniejszej odleglosci drogi przez wszystkie miasta.
		public static int[] TheBestNeighbor(DataReader cdr, String best)
		{
			// WCZYTYWANIE NAJLESZPEGO ROZWIAZANIA.
			return TheBestNeighbor(cdr.GetCoords(), cdr.GetNumberOfNodes(), GetBestDistanceFromFile(cdr,best));
		}

		public static int[] TheBestNeighbor(DataReader cdr)
		{
			// WCZYTYWANIE NAJLESZPEGO ROZWIAZANIA.
			return TheBestNeighbor(cdr.GetCoords(), cdr.GetNumberOfNodes());
		}
		
		// CEL: Obliczenie najmniejszej odleg³oœci drogi przez wszystkie miasta.
		private static int[] TheBestNeighbor(float[][]coords, int numberOfNodes)
		{
			// LEGENDA:
			// coords - macier (bufor buforów) przetrzymuje wspolrzedne (x, y) wekorów.
			// Numer wiersza identyfikuje punkt, a pozostale wspolrzedne identyfikuja pozostale dwie kolumny.
			// numberOfNodes - ilosc miast/ wezlow grafu.
				
			// 0. Zainicjowanie licznika.
			long startTime = System.nanoTime();
			
			// 1. Utworzenie buforu, ktory bedzie zawieral sciezke, z odwiedzonymi miastami.
			ArrayList<Integer>route=new ArrayList<Integer>();
			
			// 2. Utworzenie obiekt do generowania liczb pesudolosowych.
			Random rand = new Random();
			
			// 3. Utworzenie macierzy s¹siedztwa/ po³¹czeñ.
			double[][]matrixNeighbor=new double[numberOfNodes][numberOfNodes];
			for(int i=0;i <numberOfNodes; i++)
			{
				for(int j=0; j<numberOfNodes; j++)
				{
					// 4.1 Obliczanie dystansu miedzy miastami.
					matrixNeighbor[i][j]=GetDistanceEukli(coords[i], coords[j]);
				}
			}
		
			// 4. Utworzenie bufora, na miasta, które ju¿ odwiedzieliœmy.
			boolean[]visitedCities=new boolean[numberOfNodes]; // Prawda, znaczy, ze te miasto odwiedzilismy.
			int totalVisited=0; // Ilosc miast odwiedzonych.
			
			// 5. Utworzonie dowolnego punktu startowego.
			int currentCity = rand.nextInt(numberOfNodes);
	
			// 6. Tworzenie sciezki.
			double totalDistance=0;
			while(true)
			{
				// 6.1 Szukanie najlepszego s¹siada.
				// 6.1.1 Zdefiniowanie startowego przypadku.
				int currentBestNeighbor=Integer.MAX_VALUE;
				double currentDistance=Integer.MAX_VALUE;
				
				// 6.1.2 Przeszukiwanie macierzt s¹siedztwa.
				for(int i=0;i<numberOfNodes; i++)
				{
					// 6.1.3 Sprawadzamy czy odleglosc do danego sasiada jest mniejsza od obecnej.
					if(matrixNeighbor[currentCity][i]<currentDistance)
					{
						// 6.1.4 Sprawdzamy czy dany sasiad jest nieodwiedzony.
						if(visitedCities[i]==false)
						{
							currentBestNeighbor=i;
							currentDistance=matrixNeighbor[currentCity][i];
						}
					}
				}
				
				// 6.2 Idziemy dok kolejnego najlepszego s¹siada.
			
				// 6.2.1 Sprawdzamy czy udalo sie znalezc owego sasiada.
				if(currentBestNeighbor == Integer.MAX_VALUE)
				{
					//========== NIE UDALO SIE ZNALEZC SCIEZKI ==========\\
					System.out.println("======= FAILED ======");
					System.out.println("Such a route doesn't exist!");
					System.out.println("Visited cities: "+totalVisited);
					System.out.println("=====================");
					break;
				}
				
				// 6.2.2 Ustawiamy go jako odwiedzonego.
				visitedCities[currentBestNeighbor]=true;
	
				// 6.2.3 Dodajemy jego odleglosc do sumy wszystkich odleglosci.
				totalDistance+=currentDistance;
	
				// 6.2.4 Dodajemy jeden do lacznej sumy odwiedzonych miasta.
				totalVisited++;
				
				// 6.2.5 Dodajemy te miasto do sciezki.
				route.add(currentBestNeighbor);
				
				// 6.3 Sprwadzamy czy laczna suma odwiedzonych miast jest rowna calkowitej ilosci miast.
				if(totalVisited==numberOfNodes)
				{
					break;
				}
			}
			
			// 7. Zwrocenie wyniku.
			int[]res=new int[numberOfNodes];
			for(int i=0;i<numberOfNodes;i++)
			{
				res[i]=route.get(i);
			}
			return res;
		}

		// CEL: Obliczenie najmniejszej odleg³oœci drogi przez wszystkie miasta.
		private static int[] TheBestNeighbor(float[][]coords, int numberOfNodes, double bestDistance)
		{
			// LEGENDA:
			// coords - macier (bufor buforów) przetrzymuje wspolrzedne (x, y) wekorów.
			// Numer wiersza identyfikuje punkt, a pozostale wspolrzedne identyfikuja pozostale dwie kolumny.
			// numberOfNodes - ilosc miast/ wezlow grafu.
				
			// 0. Zainicjowanie licznika.
			long startTime = System.nanoTime();
			
			// 1. Utworzenie buforu, ktory bedzie zawieral sciezke, z odwiedzonymi miastami.
			ArrayList<Integer>route=new ArrayList<Integer>();
			
			// 2. Utworzenie obiekt do generowania liczb pesudolosowych.
			Random rand = new Random();
			
			// 3. Utworzenie macierzy s¹siedztwa/ po³¹czeñ.
			double[][]matrixNeighbor=new double[numberOfNodes][numberOfNodes];
			for(int i=0;i <numberOfNodes; i++)
			{
				for(int j=0; j<numberOfNodes; j++)
				{
					// 4.1 Obliczanie dystansu miedzy miastami.
					matrixNeighbor[i][j]=GetDistanceEukli(coords[i], coords[j]);
				}
			}
		
			// 4. Utworzenie bufora, na miasta, które ju¿ odwiedzieliœmy.
			boolean[]visitedCities=new boolean[numberOfNodes]; // Prawda, znaczy, ze te miasto odwiedzilismy.
			int totalVisited=0; // Ilosc miast odwiedzonych.
			
			// 5. Utworzonie dowolnego punktu startowego.
			int currentCity = rand.nextInt(numberOfNodes);
	
			// 6. Tworzenie sciezki.
			double totalDistance=0;
			while(true)
			{
				// 6.1 Szukanie najlepszego s¹siada.
				// 6.1.1 Zdefiniowanie startowego przypadku.
				int currentBestNeighbor=Integer.MAX_VALUE;
				double currentDistance=Integer.MAX_VALUE;
				
				// 6.1.2 Przeszukiwanie macierzt s¹siedztwa.
				for(int i=0;i<numberOfNodes; i++)
				{
					// 6.1.3 Sprawadzamy czy odleglosc do danego sasiada jest mniejsza od obecnej.
					if(matrixNeighbor[currentCity][i]<currentDistance)
					{
						// 6.1.4 Sprawdzamy czy dany sasiad jest nieodwiedzony.
						if(visitedCities[i]==false)
						{
							currentBestNeighbor=i;
							currentDistance=matrixNeighbor[currentCity][i];
						}
					}
				}
				
				// 6.2 Idziemy dok kolejnego najlepszego s¹siada.
			
				// 6.2.1 Sprawdzamy czy udalo sie znalezc owego sasiada.
				if(currentBestNeighbor == Integer.MAX_VALUE)
				{
					//========== NIE UDALO SIE ZNALEZC SCIEZKI ==========\\
					System.out.println("======= FAILED ======");
					System.out.println("Such a route doesn't exist!");
					System.out.println("Visited cities: "+totalVisited);
					System.out.println("=====================");
					break;
				}
				
				// 6.2.2 Ustawiamy go jako odwiedzonego.
				visitedCities[currentBestNeighbor]=true;
	
				// 6.2.3 Dodajemy jego odleglosc do sumy wszystkich odleglosci.
				totalDistance+=currentDistance;
	
				// 6.2.4 Dodajemy jeden do lacznej sumy odwiedzonych miasta.
				totalVisited++;
				
				// 6.2.5 Dodajemy te miasto do sciezki.
				route.add(currentBestNeighbor);
				
				// 6.3 Sprwadzamy czy laczna suma odwiedzonych miast jest rowna calkowitej ilosci miast.
				if(totalVisited==numberOfNodes)
				{
					//================= UDALO SIE ZNALEZC SCIEZKE ============\\
					System.out.println("====== SUCCESS ====");
					System.out.println("Route: ");
					System.out.println(route);
					System.out.println("Total distance: "+totalDistance);
					System.out.println("Visted cities: "+totalVisited);
					System.out.println("Worst by: "+((totalDistance-bestDistance)/bestDistance)*100+" %");
					System.out.println("Finding time: "+(System.nanoTime()-startTime)*Math.pow(10, -9)+" [s]");
					System.out.println("===================");
					break;
				}
			}
			
			// 7. Zwrocenie wyniku.
			int[]res=new int[numberOfNodes];
			for(int i=0;i<numberOfNodes;i++)
			{
				res[i]=route.get(i);
			}
			return res;
		}
	}

	// =================================== \\
}
