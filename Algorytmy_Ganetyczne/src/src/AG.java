package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class AG
{
	//REGION: Metody do tworzenia oraz drukowania chromosonu/ genów ------------------------------------------]
	// Generowanie liczby pseudolosowych.
	public static Random rand=new Random();  
	
	// Sprawdzanie czy dane wartosc w wektorze nie przekracza barier.
	public static boolean IsOutOfBarrier(float[]vector, float ai, float bi)
	{
		//TODO: Sprawdz czy podana wartosc w wektorze danych nalezy do przedzialu [ai,bi].
		for(int i=0;i<vector.length;i++)
		{
			if(vector[i]<ai || vector[i]>bi)
			{
				return true;
			}
		}
		return false;
	}   
	
	// Uzyskanie d³ugoœci jednej zmiennej ~ d³ugoœci jednego genu.
	public static int GetMi(int ai, int bi, int d)
	{
		//TODO: Oblicz mi.
		return (int) Math.round((Math.log((bi-ai)*Math.pow(10, d)) / Math.log(2))); 
	}
	
	// Uzyskanie d³ugoœci jednej zmiennej ~ d³ugoœci jednego genu.
	public static int GetMi(float ai, float bi, int d)
	{
			//TODO: Oblicz mi.
			return (int) Math.round((Math.log((bi-ai)*Math.pow(10, d)) / Math.log(2))); 
	}
	
	// Uzyskanie chromosonu.
	public static int[] GetChromoson(int N)
	{
		return new int[N];
	}
	
	// Kodowanie np. chromosonu.
	public static void Code(int vector[])
	{
		//TODO: Zakoduj binarnie.
		for(int i=0;i<vector.length;i++)
		{
			vector[i]=rand.nextInt(2);
		}
	}
	
	// Uzyskanie odkodowanej postaci chromosonu. 
	static float[] Decode(int vector[], int mi, int N,float ai, float bi)
	{
		//TODO: Wykonaj nastêpuj¹ce operacje:
		// 1. Utworz tablice ze zmiennymi -  N ~ ilosc zmiennych.
		String[] X=new String[N];
		float[]x=new float[N];
		//System.out.println("DECODE: dla N:"+mi*N);
		// 2. Wyzeruj wartosci.
		for(int i=0;i<N;i++)
		{
			X[i]="";
		}
		// 3. Co mi dodawaj do aktualnej zmiennej 0 lub 1 - mi ~ dlugosc zminnej.
		int step=0;
		int current=0;
		for(int i=0;i<vector.length;i++)
		{
			if(step==mi)
			{
				step=0;
				current++;
			}
			X[current]+=vector[i];
			step++;
		}
		// 4. Konwertuj na typ ca³kowity.
		for(int i=0;i<X.length;i++)
		{
			int value=Integer.parseInt(X[i],2);
			x[i]=(float) (ai+value*(bi-ai)/Math.pow(2,mi)-1);
		}
		return x;
	}
	
	// Drukowanie dziesiêtne wartosc z wektora.
	public static void PrintDecVariables(float vector[])
	{
		//TODO: Drukuj wartosci w systemie dziesiêtnym.
		for(int i=0;i<vector.length;i++)
		{
			System.out.println("X"+i+":" +vector[i]+"\n");
		}
	}
	
	// Drukowanie pinarne wartoœci z wektora.
	public static void PrintBin(int vector[])
	{
		//TODO: Drukuj binarnie.
		System.out.println("\n");
		for(int i=0;i<vector.length;i++)
		{
			System.out.print(vector[i]+" ");
		}
		System.out.println("\n");
	}

	// Drukowanei bez podzia³u na wiersza.
	public static void PrintInlineDecVariables(int vector[])
	{
		//TODO: Drukuj wartosci w systemie dziesiêtnym.
		for(int i=0;i<vector.length;i++)
		{
			System.out.print(vector[i]+" ");
		}
		System.out.println("\n");
	}
	//END REGION-------------------------------------------------------------------------------------------------]
	
	//REGION: Metody do populacji--------------------------------------------------------------------------------]
	// Generowanie populacji.
	static int[][] GeneratePopulation(int minLengthOfVariable,int numberOfVariables, int N)
	{
		//TODO: Stwórz populacje.
		int mi = minLengthOfVariable; // D³ugoœæ jednej zmiennej.
		int n = numberOfVariables; // Iloœæ zmiennych w chromosonie.
		int totalLength=n*mi; // Ca³kowita d³ugoœæ chromosonu.
		int[][]populacja=new int[N][totalLength];
		for(int i=0;i<N;i++)
		{
			//System.out.println(N);
			AG.Code(populacja[i]);
		}
		return populacja;
	}
	
	static int[][]GeneratePopulation(int lengthOfChromoson, int N)
	{
		//TODO: Stwórz populacjê.
		int[][]populacja=new int[N][lengthOfChromoson]; // Utworzenie populacji.
		for(int i=0;i<N;i++)
		{
			AG.Code(populacja[i]); // Kodowanie chromosonu.
		}
		return populacja; // Zwróæ populacjê.
	}
		
	// Drukowanie populacji w postaci binarnej.
	static void PrintPopulationBin(int[][]population)
	{
		//TODO: Wydrukuj populacjê w postaci binarnej.
		System.out.println("---------------Drukowanie populacji (POSTAC NIE ODKODOWANA)------------]");
		for(int i=0;i<population.length;i++)
		{
			System.out.print("Osobnik nr."+(i+1));
			AG.PrintBin(population[i]);
		}
		System.out.println("-----------------------------------------------------------------------]");
	}
		
	// Drukowanie populacji w postaci odkodowanej.
	static void PrintPopulationDecoded(int[][]population,int mi, int n, float ai, float bi)
	{
		//TODO: Wydrukuj populacje w postaic odkodowanej.
		System.out.println("-----------Drukowanie populacji (POSTAÆ ODKOWANA)--------------]");
		// mi ~ d³ugoœæ zmiennej w chormosonie.
		// n ~ iloœæ zmiennych w chromosonie.
		for(int i=0;i<population.length;i++)
		{
			System.out.println("Osobnik nr."+(i+1)+"------------]\n");
			AG.PrintDecVariables(AG.Decode(population[i], mi, n,ai,bi));
		}
		System.out.println("---------------------------------------------------------------]");
	}
	
	static float[][] DecodePopulation(int[][]population, int mi,int n, float ai, float bi)
	{
		//TODO: Zdekoduj populacjê.
		float[][]results=new float[population.length][mi*n];
		//System.out.println(population.length);
		for(int i=0;i<population.length;i++)
		{
			results[i]=AG.Decode(population[i], mi, n, ai, bi);
		}
		return results;
	}
	//END REGION-------------------------------------------------------------------------------------------------]
	
	//REGION: Selekcja ~ Ruletka--------------------------------------------------------------------------------------------]
	// Wybieranie losowego chromosonu z populacji za pomoca ruletki dla funkcji 1.
	static int[] Roll_Chromoson_FUNC1(int[][]P, int mi, int nV, int N)
	{
		//TODO: Uzyskaj osobnika, który losowo zostanie wybrany za pomoc¹ 0-100%.
		
		// mi ~ iloœæ bitów potrzebnych na zmienn¹ w osobniku.
		// nV ~ iloœæ zmiennych w chromosonie.
		// N ~ iloœæ osobników w populacji.
		
		int dl = mi*nV; // D³ugoœæ chromosonu.
		float[][]D_P1=AG.DecodePopulation(P, mi, nV,-2,2); // Zdekodowana postaæ
		float[]Function_Results=new float[N]; // Wyniki funkcji.
		float[]procents=new float[N]; // Procenty w funkcji.
		float sum=0; // Suma wszystkich funkcji.
		
		//1. Uzyskaj wartosci funkcji przystosowania dla zdekodowanej populacji oraz utwórz ich wszystkich.
		
		float DELTA=20; // Sta³a.
		
		for(int i=0;i<N;i++)
		{
			Function_Results[i]=FUNC.Func_1(D_P1[i][0], D_P1[i][1]); //TODO: Uzyskanie wartosci funckji.
			Function_Results[i]+=DELTA;
			sum+=Function_Results[i];
		}
		
		//2. Ustawianie procentów wyników oraz pozycji ciêcia.
		
		for(int i=0;i<Function_Results.length;i++)
		{
			procents[i]=Function_Results[i]/sum;
			//System.out.println("Osobnik nr."+(i+1)+":");
			//System.out.println("Procent: "+procents[i]*100+"%");
		}
		
		//3. Uzyskaj osobnika.
		
		Random rand=new Random(); 
		float selected=rand.nextFloat(); // Wybranie losowego %.
		int index=0; // Pocz¹tkowy index.
		float LB=0; // Lewa bariera.
		float RB=procents[index]; // Prawa bariera.
		int i=0; // Aktualny chromoson.
		
		while(index<N)
		{
			//System.out.println("LB: "+LB+", RB: "+RB+", PR: "+selected);
			if(selected >LB && selected < RB)
			{
				return P[i];
			}
			LB=RB;
			RB=LB+procents[index++];
			i++;
		}
		return P[0];
	}
	
	// Wybieranie losowego chromosonu z populacji za pomoca ruletki dla funkcji 2.
	static int[] Roll_Chromoson_FUNC2(int[][]P, int mi, int nV, int N)
	{
		//TODO: Uzyskaj osobnika, który losowo zostanie wybrany za pomoc¹ 0-100%.
		
		// mi ~ iloœæ bitów potrzebnych na zmienn¹ w osobniku.
		// nV ~ iloœæ zmiennych w chromosonie.
		// N ~ iloœæ osobników w populacji.
		
		int dl = mi*nV; // D³ugoœæ chromosonu.
		float[][]D_P1=AG.DecodePopulation(P, mi, nV, -5.21f, 5.21f); // Zdekodowana postaæ
		float[]Function_Results=new float[N]; // Wyniki funkcji.
		float[]procents=new float[N]; // Procenty w funkcji.
		float sum=0; // Suma wszystkich funkcji.
		
		//1. Uzyskaj wartosci funkcji przystosowania dla zdekodowanej populacji oraz utwórz ich wszystkich.
		
		float DELTA=20; // Sta³a.
		
		for(int i=0;i<N;i++)
		{
			Function_Results[i]=(float) FUNC.Func_2(D_P1[i],10); //TODO: Uzyskanie wartosci funckji.
			Function_Results[i]+=DELTA;
			sum+=Function_Results[i];
		}
		
		//2. Ustawianie procentów wyników oraz pozycji ciêcia.
		
		for(int i=0;i<Function_Results.length;i++)
		{
			procents[i]=Function_Results[i]/sum;
			//System.out.println("Osobnik nr."+(i+1)+":");
			//System.out.println("Procent: "+procents[i]*100+"%");
		}
		
		//3. Uzyskaj osobnika.
		
		Random rand=new Random(); 
		float selected=rand.nextFloat(); // Wybranie losowego %.
		int index=0; // Pocz¹tkowy index.
		float LB=0; // Lewa bariera.
		float RB=procents[index]; // Prawa bariera.
		int i=0; // Aktualny chromoson.
		
		while(index<N)
		{
			//System.out.println("LB: "+LB+", RB: "+RB+", PR: "+selected);
			if(selected >LB && selected < RB)
			{
				return P[i];
			}
			LB=RB;
			RB=LB+procents[index++];
			i++;
		}
		return P[0];
	}
	//END REGION-------------------------------------------------------------------------------------------------]
	
	//REGION: Operatory genetyczne ------------------------------------------------------------------------------]
	//Krzy¿owanie wielo punktowe:
	public static void GetSonsMultiCross(int P1[], int P2[], int S1[], int S2[])
	{
		//TODO: Utwórz potomków za pomoc¹ krzy¿owania wielopunktowego.
		int first=rand.nextInt(P1.length-1);
		int second=rand.nextInt(P1.length-1);
		while(second==first)
		{
			second=rand.nextInt(P1.length-1);
		}
		if(second<first)
		{
			int tmp=second;
			second=first;
			first=tmp;
		}
		// ZLOZONOSC CALOSCI: O(n)
		for(int i=0;i<first;i++)
		{
			S1[i]=P1[i];
			S2[i]=P2[i];
		}
		for(int i=first;i<second;i++)
		{
			S1[i]=P2[i];
			S2[i]=P1[i];
		}
		for(int i=second;i<P1.length;i++)
		{
			S1[i]=P1[i];
			S2[i]=P2[i];
		}
	}

	// Krzy¿owania jedno punktowe:
	public static void GetSonsOneCross(int P1[], int P2[], int S1[], int S2[])
	{
		//TODO: Utwórz potomków.
		int placeToCut=rand.nextInt(P1.length-1);
		for(int i=0;i<placeToCut;i++)
		{
			S1[i]=P1[i];
			S2[i]=P2[i];
		}
		for(int i=placeToCut;i<P1.length;i++)
		{
			S1[i]=P2[i];
			S2[i]=P1[i];
		}
	}
	
	// Mutacja:
	public static float Mute(int vector[], float pMAX)
	{
		//TOD: Dokonaj mutacji wektora.
		int liczbaMutacji=0;
		for(int i=0;i<vector.length;i++)
		{
			float p = rand.nextFloat();
			if(p<pMAX)
			{
				liczbaMutacji++;
				if(vector[i]==1)
				{
					vector[i]=0;
				}
				else
				{
					vector[i]=1;
				}
			}
		}
		/*
		System.out.println("\n[-------------------]");
		System.out.println("Ilosc mutacji: "+liczbaMutacji);
		System.out.println("[-------------------]");
		*/
		return liczbaMutacji;
	}
	
	// Permutacja:
	public static void Permute(int vector[],int min, int max)
	{
		//TODO: Wykonaj permutacje na zbiorze.
		Stack<Integer> visited=new Stack<Integer>();
		for(int i=0;i<vector.length;i++)
		{
			while(true)
			{
				int number=rand.nextInt((max+1)-min)+min;
				if(visited.contains(number)==false)
				{
					vector[i]=number;
					visited.push(number);
					break;
				}
			}
		}
	}
	
	// Krzy¿owanie z czêœciowym odwzorowaniem "PMX":
	public static void PMX(int P1[], int P2[], int S1[], int S2[])
	{
		// 1. Ustawienie barier.
		int FIRST_BOUND=rand.nextInt(P1.length);
		int SECOND_BOUND=rand.nextInt(P1.length);
		while(FIRST_BOUND!=SECOND_BOUND)
		{
			SECOND_BOUND=rand.nextInt(P1.length);
		}
		if(FIRST_BOUND>SECOND_BOUND)
		{
			SECOND_BOUND=FIRST_BOUND;
		}
		// 2. Dokonowynie zamian.
		// a) Zamiana œrodka.
		Stack<Integer> P1_USED=new Stack<Integer>();
		Stack<Integer> P2_USED=new Stack<Integer>();
		Stack<Integer> used=new Stack<Integer>();
		for(int i=FIRST_BOUND;i<SECOND_BOUND;i++)
		{
			S1[i]=P2[i];
			S2[i]=P1[i];
			P1_USED.add(P2[i]);
			P2_USED.add(P1[i]);
			used.add(P1[i]);
			used.add(P2[i]);
		}
		// b) Zamiana lewej strony liczbami, które nie zosta³y u¿yte.
		for(int i=0;i<FIRST_BOUND;i++)
		{
			int number=P1[i];
			if(used.contains(number)==false)
			{
				S1[i]=number;
			}
			number=P2[i];
			if(used.contains(number)==false)
			{
				S2[i]=number;
			}
		}
		// c) Zamiana prawej strony liczbami, które nie zosta³y u¿yte.
		for(int i=SECOND_BOUND;i<P1.length;i++)
		{
			int number=P1[i];
			if(used.contains(number)==false)
			{
				S1[i]=number;
			}
			number=P2[i];
			if(used.contains(number)==false)
			{
				S2[i]=number;
			}
		}
		// d) Zamiana liczb o liczbami o które wyst¹pi³y w odwzrowaniu.
		for(int i=0;i<P1.length;i++)
		{
			if(P1[i]==0)
			{
				P1[i]=P1_USED.pop();
			}
			if(P2[i]==0)
			{
				P2[i]=P2_USED.pop();
			}
		}
	}
	
	// Inwersji:
	public static void Inverse(int S1[])
	{
		//TODO: Inwersja ci¹gu permutowanego.
		int index=rand.nextInt(S1.length-1);
		int tmp=S1[index+1];
		S1[index+1]=S1[index];
		S1[index]=tmp;
		System.out.println("Miejsce mutacji: "+index+"\n");
	}
	//END REGION-------------------------------------------------------------------------------------------------]

	//REGION: Funkcje przystosowania:
	public static float Calc_Func1(int[]bin_chromoson, int mi, int n)
	{
		//TODO: Oblicz funkcje przystosowania dla chromosonu w postaci binarnej.
		float[]decoded_chromoson=AG.Decode(bin_chromoson, mi, n, -2, 2);
		return FUNC.Func_1(decoded_chromoson[0], decoded_chromoson[1]);
	}
	//END REGION-------------------------------------------------------------------------------------------------]
	
	//REGION: Testowanie ~ Utworzenie algorytmu genetycznego:
	public static void Start_GA(int size_of_population)
	{
		//TODO: Uzyskanie najlepszego rozwiazania/Wykonywanie algorytmu genetycznego.
		int[] best_solution;
		try {
			best_solution = GA_Algorithm_FUNC1(size_of_population,1000);
			System.out.println("Best chromoson: ");
			AG.PrintBin(best_solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int[] GA_Algorithm_FUNC1(int size_of_population, int ev) throws IOException
	{
		//TODO: Zostanie wykorzystana funkcja nr.1
		int d = 5; // Dokladnosc miejsc po przecinku.
		int ai = -2; // Ograniczenie funkcji lewo stronne.
		int bi = 2; // Ograniczenie funkcji prawo stronne.
		int mi = AG.GetMi(ai, bi, d); // Obliczanie minimalnej d³ugoœci zmiennej w chromosonie.
		int n = 2; // Ilosc zmiennych w chromosonie.
		int i=0; // Startowa ilosc ewaluacji.
		int index_of_best_chromoson=0; // Indeks chromosonu, który ma najlepsze rozwiazanie.
		Random rand=new Random(); // Obiekt do generowania pseudo-losowych liczb rzeczywistych.
		
		FileWriter best_solutions=new FileWriter("Y_best_step.txt"); // Plik z najlepszymi funkcjami przystosowania.
		FileWriter delta_ev=new FileWriter("X_delta_ev.txt"); // Plik z przyrostem delta ev.
		
		//TODO: 1. Inicjacja czyli wybór poczatkowej populacji chromosonow.
		int[][]population=AG.GeneratePopulation(mi, n, size_of_population); // Utworzenie populacji.
		
		while(true)
		{
			//TODO: 2. Ocena przystosowania chromosonów w populacji.
			float best_solution=0; // Maksymalna wartosc funkcji ~ wynik najlepszy.
			index_of_best_chromoson=0; // Indeks chromosonu, który ma najlepsze rozwiazanie.
			for(int j=0;j<size_of_population;j++)
			{
				float current_solution=AG.Calc_Func1(population[j],mi,n);
				if(current_solution > best_solution)
				{
					best_solution=current_solution;
					index_of_best_chromoson=j;
				}
			}
			if(i%50==0) // Dodawanie najlepszej wartosci funkcji przystosowania co 50 ev.
			{
				System.out.println(best_solution);
				best_solutions.append(best_solution+"\n"); 
				delta_ev.append(i+"\n");
			}
			//TODO: 3. Waruenk zatrzymania.
			if(i>=ev) //TODO: TAK
			{
				break;
			}
			else // TODO: NIE
			{
				//TODO: 4. Selekcja chromosonów.
				for(int j=0;j<size_of_population;j++)
				{
					population[j]=AG.Roll_Chromoson_FUNC1(population, mi, n, size_of_population);
				}
				//TODO: 5. Zastosowanie operatorów genetycznych.
				//TODO: a) Krzy¿owanie.
				for(int j=0;j<size_of_population;j++)
				{
					float rand_value=rand.nextFloat();
					if(rand_value<=0.6f)
					{
						int rand_index_of_chromoson=rand.nextInt(size_of_population);
						int S1[]=new int[n*mi];
						int S2[]=new int[n*mi];
						AG.GetSonsMultiCross(population[j], population[rand_index_of_chromoson], S1, S2);
						population[j]=S1;
						population[rand_index_of_chromoson]=S2;
					}
				}
				//TODO: b) Mutacja.
				for(int j=0;j<size_of_population;j++)
				{
					AG.Mute(population[j], 0.02f);
				}
				i++;
			}
		}
		delta_ev.close(); // Zamkniecie pliku: X_delta_ev
		best_solutions.close(); // Zamkniecie pliku: Y_najlepszymi_wartosciami_funkcji.
		return population[index_of_best_chromoson];
	}
	//END REGION-------------------------------------------------------------------------------------------------]
}
