package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ex2 
{
	// CEL: Klasa do wczytywania danych miast.
	static class CordsDataReader 
	{
		// POLA:
		int numberOfNodes;		// Ilosc wezlow.
		int dimensionOfCoords;	// Rozmiar przestrzeni liniowej.
		int[][]coords;			// Wspolrzêdne punktów.
		
		// KONSTRUKTOR:
		public CordsDataReader(String src)
		{
			// 1. Uzyskanie wskaznika do pliku.
			File file = new File(src);
			Scanner reader=null;
			try 
			{
				reader = new Scanner(file);
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			// 4. Odczytywanie pliku.
			int currentRowInFile=0; // Obecny wiersz w pliku.
			int currentPoint=0;		// Obecny punkt w buforze punktów.
			while(reader.hasNextLine())
			{
				// 4.1 Uzyskanie zawartosci obecnej linii.
				String line=reader.nextLine();
				
				// 4.2 Sprawdzenie czy jestesmy 3 wierszu z iloscia wêz³ów.
				if(currentRowInFile==3)
				{
					// 4.2.1 Zapisanie ilosc wez³ów.
					numberOfNodes=Integer.parseInt(line.split(" ")[1]);
					
					// 4.2.2 Utworzenie bufora.
					coords=new int[numberOfNodes][2];
					
					// 4.2.3 Zapisanie wiadomosci o wymiarach.
					dimensionOfCoords=2;
				}
				// 4.3 Sprawdzenie czy minelismy juz 5 wiersz, po nim sa ju¿ wspó³rzêdne punktów.
				else if(currentRowInFile>5 && currentPoint < numberOfNodes)
				{
					// 4.3.1 Rozdzielenie linii na kilka ci¹gu znaków.
					String[]data=line.split(" "); 
				
					// 4.3.2 Uzyskanie wspó³rzêdnych punktu.
					// 4.3.2.1 Wed³ug osi X.
					coords[currentPoint][0]=(int)Double.parseDouble(data[1]);
					
					// 4.3.2.2 Wed³ug osi Y.
					coords[currentPoint][1]=(int)Double.parseDouble(data[2]);
					
					// 4.3.3 Idziemy do kolejnego punktu.
					currentPoint++;
				}
				
				currentRowInFile++;
			}
			
		}
	
		// WLASCIWOSCI:
		
		// Zwrocenie ilosci wezlow.
		public int GetNumberOfNodes()
		{
			return numberOfNodes;
		}
		
		// Zwrocenie wielkosci wymiaru przestrzeni.
		public int GetDimensionOfCoords()
		{
			return dimensionOfCoords;
		}
		
		// Zwrocenie wspolrzednych punktow.
		public int[][] GetCoords()
		{
			return coords;
		}
		
		// Drukowanie wspolrzêdnych.
		public void PrintCoords()
		{
			// 1. Ustawienie punktu startowego.
			char point = 'A';
			
			// 2. Drukowanie wspolrzednych punktów.
			for(int i=0;i<numberOfNodes;i++)
			{
				System.out.print((int)(point+i));
				for(int j=0;j<dimensionOfCoords;j++)
				{
					if(j==0)
					{
						System.out.print(" X: "+coords[i][j]);
					}
					else
					{
						System.out.print(" , Y: "+coords[i][j]);
					}
				}
				System.out.println();
			}
		}
	
		// Testowanie klasy czytnik danych pod problem komiwoja¿era.
		public static void TestCordsDataReader()
		{
			Ex2.CordsDataReader dr = new Ex2.CordsDataReader("src/berlin52tsp.sec");
			dr.PrintCoords();
		}
	}
	
	// CEL: Obliczenie odlegloœci Euklidesowej.
	static double GetDistanceEukli(int[]first, int[]second)
	{
		return Math.sqrt(Math.pow((second[0]-first[0]), 2) + Math.pow((second[1]-second[0]), 2));
	}
	
	// CEL: Obliczenie sciezki uzyskanej sciezki.
	private static double GetBestDistanceFromFile(CordsDataReader cdr, String best)
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
			route.add(Integer.parseInt(r));
		}
		
		// 5. Obliczenie dlugosci sciezki.
		double totalDistance=0;
		for(int i=1;i<route.size();i++)
		{
			totalDistance+=GetDistanceEukli(cdr.GetCoords()[i], cdr.GetCoords()[i-1]);
		}
		
		// 6. Zwrocenie dlugosci sciezki.
		return totalDistance;
	}
	
	// CEL: Obliczenie najmniejszej odleglosci drogi przez wszystkie miasta.
	public static void TheBestNeighbor(String src, String best)
	{
		// WCZYTYWANIE NAJLESZPEGO ROZWIAZANIA.
		CordsDataReader cdr = new CordsDataReader(src);
		TheBestNeighbor(cdr.GetCoords(), cdr.GetNumberOfNodes(), GetBestDistanceFromFile(cdr,best));
	}
	
	// CEL: Obliczenie najmniejszej odleg³oœci drogi przez wszystkie miasta.
	private static void TheBestNeighbor(int[][]coords, int numberOfNodes, double bestDistance)
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
	}
}
