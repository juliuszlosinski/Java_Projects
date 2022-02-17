package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ex1 
{
	// Klasa do czytania plikow.
	static class DataReader
	{
		// POLA:
		private int B; 		// Pojemnosc pojemnika.
		private int n; 		// Liczba obiektow.
		private int popt;	// Najlepsza liczba pojemnikow wykorzystana.
		private int[]si;	// Rozmiary obiektow.
		
		// KONSTRUTKOR:
		public DataReader(String source)
		{
			// 1. Uzyskanie pliku.
			File file = new File(source);
			Scanner reader=null;
			try 
			{
				reader = new Scanner(file);
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			// 3. Utworzenie tablicy.
			int i=0;
			int j=0;
			// 2. Wczytywanie pliku.
			while(reader.hasNextLine())
			{
				if(i==0)
				{
					// 2.1 Uzyskanie pierwszych atrybutów.
					String[] data=reader.nextLine().split(" ");
					B=Integer.parseInt(data[0]);
					n=Integer.parseInt(data[1]);
					popt=Integer.parseInt(data[2]);
					
					// 2.2 Utworzenie tablicy o podanym rozmiarze.
					si=new int[n];
					i++;
				}
				else
				{
					si[j++]=Integer.parseInt(reader.nextLine());
				}
			}
		}

		// WLASCIWOSCI:
		public int getB() {
			return B;
		}

		public int getN() {
			return n;
		}

		public int getPopt() {
			return popt;
		}

		public int[] getSi() {
			return si;
		}

		// METODY:
		// Drukowanie uzyskanych informacji.
		public void Print()
		{
			System.out.println("---------------- Parametry -----------------]");
			System.out.println("Pojemnosc pojemnika (B): "+B);
			System.out.println("Liczba obiektow (n): "+n);
			System.out.println("Liczba poj. naj. wykorz.: "+popt);
			System.out.println("Wielkosci obietkow: ");
			for(int i=0;i<n;i++)
			{
				System.out.println(si[i]);
			}
			System.out.println("-------------------------------------------]");
		}
	}
	
	// Drzewo Wyszukiwañ Binarnych ~ klasa pomocnicza.
	static class BST
	{
		// Klasa wezla.
		public class Node
		{
			// Pola:
			Node left; 		// Wskaznik do lewego wezla.
			Node right;		// Wskaznik do prawego wezla.
			int key;		// Klucz.
			int value;		// Wartosc.
			
			// Konstruktor.
			Node(int key, int value, Node left, Node right)
			{
				this.key=key;
				this.left=left;
				this.right=right;
				this.value=value;
			}
		}
		
		// Korzen.
		Node head;
		
		// Konstruktor.
		public BST()
		{
			head=null;
		}
		
		// Dodawanie elmenentu do drzewa.
		public void Add(int key, int value)
		{
			if(head==null)
			{
				head=new Node(key, value, null, null);
			}
			else
			{
				Add(head,key, value);
			}
		}
		
		// Dodawanie elementu do drzewa.
		private void Add(Node current,int key, int value)
		{
			if(current.value>value)
			{
				if(current.left==null)
				{
					current.left=new Node(key, value, null, null);
				}
				else
				{
					Add(current.left, key, value);
				}
			}
			else if(current.value<value)
			{
				if(current.right==null)
				{
					current.right=new Node(key, value, null, null);
				}
				else
				{
					Add(current.right,key, value);
				}
			}
		}
		
		// Zwrocenie wezla z najwieksza wartoscia.
		public Node getBest()
		{
			return getBest(head);
		}
		
		// Zwrocenie wezla z najwieksza wartoscia.
		private Node getBest(Node current)
		{
			if(current.right==null)
			{
				return current;
			}
			return getBest(current.right);
		}
		
		// Zwrocenie wezla z najmniejsza wartoscia.
		public Node getWorst()
		{
			return getWorst(head);
		}
		
		// Zwrocenie wezla z najmniejsza wartoscia.
		private Node getWorst(Node current)
		{
			if(current.left==null)
			{
				return current;
			}
			return getWorst(current.left);
		}
	}
	
	// Testowanie wczytywania plikow.
	public static void TestReadedData()
	{
		DataReader rd=new DataReader("src/binpack1.txt");
		rd.Print();
	}
	
	// Next-Fit =========================================
	public static void Test_Next_Fit()
	{
		DataReader dr = new DataReader("src/binpack1.txt");
		Next_Fit(dr.getB(), dr.getN(), dr.getPopt(), dr.getSi());
	}
	
	public static void Test_Next_Fit(String src)
	{
		DataReader dr = new DataReader(src);
		Next_Fit(dr.getB(), dr.getN(), dr.getPopt(), dr.getSi());
	}
	
	public static void Next_Fit(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
		
		// 1. Zdefiniowanie buforów pomocniczych.
		int[]objects=new int[n]; // Pozycje obiektow w pude³kach.
		int[]boxes=new int[n]; 	 // Pude³ek.
		
		// 2. Zdefiniowanie indeksów pomocniczych.
		int currentBox=0;		// Dane pude³ko.
		
		// 3. Dodawanie okiektów do pojemników.
		for(int currentObject=0;currentObject<n;)
		{
			// 3.1 Sprawdzenie czy obiekt miesci sie w pude³ku.
			if(boxes[currentBox]+si[currentObject]<=B)
			{
				objects[currentObject]=currentBox;
				boxes[currentBox]+=si[currentObject];
				currentObject++;
			}
			// 3.2 Nie zmiesci sie w pude³ku.
			else
			{
				currentBox++;
			}
		}
		
		// 4. Wypisanie komunikatu.
		System.out.println("======== Next-Fit =======");
		System.out.println("Output: ----------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+currentBox);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(currentBox-popt)/(float)popt+" %");
		System.out.println("------------------------]");
		System.out.println("=========================");
	}
	// ==================================================
	
	// First-Fit ========================================
	public static void Test_First_Fit()
	{
		DataReader dr=new DataReader("src/binpack1.txt");
		First_Fit(dr.getB(),dr.getN(), dr.getPopt(), dr.getSi());
	}
	
	public static void Test_First_Fit(String src)
	{
		DataReader dr=new DataReader(src);
		First_Fit(dr.getB(),dr.getN(), dr.getPopt(), dr.getSi());
	}
	
	public static void First_Fit(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
	
		// 1. Zdefiniowanie buforow pomocniczych.
		int[]objects=new int[n]; // Obiekty.
		int[]boxes = new int[n]; // Pudelka.
		
		int openedBoxes=0; 		// Otwarte pudelka.
		
		for(int currentObject=0;currentObject<n;currentObject++)
		{
			// 0. Flaga, ktora znaczy czy element zosta³ umieszczony.
			boolean placed=false;
			
			// 1. Szukanie miejsca w pudelkach, które zosta³y wczeœniej otwarte.
			for(int i=0; i<openedBoxes;i++)
			{
				// 1.1 Sprawdzamy czy element mo¿na tam umieœciæ.
				if(boxes[i]+si[currentObject]<=B)
				{
					// 1.1.1 Wkladamy wage do pudelka,
					boxes[i]+=si[currentObject];
					
					// 1.1.2 Przypisujemy ten element do pude³ka.
					objects[currentObject]=i;
					
					// 1.1.3 Konczymy przeszukiwania, poniewaz juz wlozylismy ten obiekt.
					placed=true;
					break;
				}
			}
			
			// 2. Jezeli nie umiescilismy tego obiektu.
			if(!placed)
			{
				// 2.1 Nie ma miejsca w pude³kach, które zosta³y wczeœniej otworzone.
				//	  Zatem otwieramy nowe pude³ko i tam wk³adamy obiekt.
				openedBoxes++;
				boxes[openedBoxes]+=si[currentObject];
				objects[currentObject]=openedBoxes;
			}
		}
			
		System.out.println("======== First-Fit ========");
		System.out.println("Output: ------------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+openedBoxes);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(openedBoxes-popt)/(float)popt+" %");
		System.out.println("--------------------------]");
		System.out.println("===========================");
	}
	// ==================================================

	// Best-Fit =========================================
	public static void Test_Best_Fit()
	{
		DataReader dt=new DataReader("src/binpack1.txt");
		Best_Fit(dt.getB(),dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Test_Best_Fit(String src)
	{
		DataReader dt=new DataReader(src);
		Best_Fit(dt.getB(),dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Best_Fit(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
	
		// 1. Zdefiniowanie buforow pomocniczych.
		int[]objects=new int[n]; // Obiekty.
		int[]boxes=new int[n];   // Pude³ka.
		
		int openedBoxes=0;		 // Uzyte pudelka.
		
		// 2. Umieszczanie elementow w pudelkach.
		for(int currentObject=0;currentObject<n; currentObject++)
		{
			// 0. Flag do sprawdzania czy element zostal umieszczony.
			boolean placed=false;
			
			// 2.1 Sprawdzanie czy obiekt zmiesci sie w czesniej otwartych pude³kach.
			BST bst=new BST();
			for(int i=0;i<openedBoxes;i++)
			{
				// 2.1.1 Czy mozna tam element umiescic.
				if(boxes[i]+si[currentObject]<=n)
				{
					// Mozna, czyli dodajemy to pudelko do zbioru z wolnymi pudelkami.
					bst.Add(i, boxes[i]);
				}
			}
			
			// 2.2 Dodawanie obiektu do pudelka, ktory jest najbardziej zaladowany.
			try{int box=bst.getBest().key;
			boxes[box]+=si[currentObject];
			objects[currentObject]=box;
			placed=true;
			}catch(Exception e)
			{
				
			}
			
			// 2.3 Element nie zostal wlozony, czyli wkladamy go do nowego pojemnika.
			if(!placed)
			{
				openedBoxes++;
				boxes[openedBoxes]+=si[currentObject];
				objects[currentObject]=openedBoxes;
			}
		}
		
		// 3. Wypisanie wyników.
		System.out.println("======== Best-Fit ========");
		System.out.println("Output: ------------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+openedBoxes);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(openedBoxes-popt)/(float)popt+" %");
		System.out.println("--------------------------]");
		System.out.println("===========================");
	}
	// ==================================================
	
	// Worst-Fit ========================================
	public static void Test_Worst_Fit()
	{
		DataReader dt=new DataReader("src/binpack1.txt");
		Worst_Fit(dt.getB(),dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Test_Worst_Fit(String src)
	{
		DataReader dt=new DataReader(src);
		Worst_Fit(dt.getB(),dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Worst_Fit(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
	
		// 1. Zdefiniowanie buforow pomocniczych.
		int[]objects=new int[n]; // Obiekty.
		int[]boxes=new int[n];   // Pude³ka.
		
		int openedBoxes=0;		 // Uzyte pudelka.
		
		// 2. Umieszczanie elementow w pudelkach.
		for(int currentObject=0;currentObject<n; currentObject++)
		{
			// 0. Flag do sprawdzania czy element zostal umieszczony.
			boolean placed=false;
			
			// 2.1 Sprawdzanie czy obiekt zmiesci sie w czesniej otwartych pude³kach.
			BST bst=new BST();
			for(int i=0;i<openedBoxes;i++)
			{
				// 2.1.1 Czy mozna tam element umiescic.
				if(boxes[i]+si[currentObject]<=n)
				{
					// Mozna, czyli dodajemy to pudelko do zbioru z wolnymi pudelkami.
					bst.Add(i, boxes[i]);
				}
			}
			
			// 2.2 Dodawanie obiektu do pudelka, ktory jest najbardziej zaladowany.
			try{int box=bst.getWorst().key;
			boxes[box]+=si[currentObject];
			objects[currentObject]=box;
			placed=true;
			}catch(Exception e)
			{
				
			}
			
			// 2.3 Element nie zostal wlozony, czyli wkladamy go do nowego pojemnika.
			if(!placed)
			{
				openedBoxes++;
				boxes[openedBoxes]+=si[currentObject];
				objects[currentObject]=openedBoxes;
			}
		}
		
		// 3. Wypisanie wyników.
		System.out.println("======== Worst-Fit ========");
		System.out.println("Output: ------------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+openedBoxes);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(openedBoxes-popt)/(float)popt+" %");
		System.out.println("--------------------------]");
		System.out.println("===========================");
	}
	// ==================================================
	
	// First-Fit-Decreasing =============================
	public static void Test_First_Fit_Decreasing()
	{
		DataReader dt=new DataReader("src/binpack1.txt");
		First_Fit_Decreasing(dt.getB(), dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Test_First_Fit_Decreasing(String src)
	{
		DataReader dt=new DataReader(src);
		First_Fit_Decreasing(dt.getB(), dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void First_Fit_Decreasing(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
		
		// 0. Sortowanie obiektow.
		for(int i=0;i<n;i++)
		{
			for(int j=1;j<n;j++)
			{
				if(si[j-1]<si[j])
				{
					int tmp=si[j-1];
					si[j-1]=si[j];
					si[j]=tmp;
				}
			}
		}
		
		// 1. Zdefiniowanie buforow pomocniczych.
		int[]objects=new int[n]; // Obiekty.
		int[]boxes = new int[n]; // Pudelka.
		
		int openedBoxes=0; 		// Otwarte pudelka.
		
		for(int currentObject=0;currentObject<n;currentObject++)
		{
			// 0. Flaga, ktora znaczy czy element zosta³ umieszczony.
			boolean placed=false;
			
			// 1. Szukanie miejsca w pudelkach, które zosta³y wczeœniej otwarte.
			for(int i=0; i<openedBoxes;i++)
			{
				// 1.1 Sprawdzamy czy element mo¿na tam umieœciæ.
				if(boxes[i]+si[currentObject]<=B)
				{
					// 1.1.1 Wkladamy wage do pudelka,
					boxes[i]+=si[currentObject];
					
					// 1.1.2 Przypisujemy ten element do pude³ka.
					objects[currentObject]=i;
					
					// 1.1.3 Konczymy przeszukiwania, poniewaz juz wlozylismy ten obiekt.
					placed=true;
					break;
				}
			}
			
			// 2. Jezeli nie umiescilismy tego obiektu.
			if(!placed)
			{
				// 2.1 Nie ma miejsca w pude³kach, które zosta³y wczeœniej otworzone.
				//	  Zatem otwieramy nowe pude³ko i tam wk³adamy obiekt.
				openedBoxes++;
				boxes[openedBoxes]+=si[currentObject];
				objects[currentObject]=openedBoxes;
			}
		}
			
		System.out.println("=== First-Fit-Decreasing ==");
		System.out.println("Output: ------------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+openedBoxes);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(openedBoxes-popt)/(float)popt+" %");
		System.out.println("--------------------------]");
		System.out.println("===========================");
	}
	// ==================================================
	
	// First-Fit-Increasing =============================
	public static void Test_First_Fit_Increasing()
	{
		DataReader dt=new DataReader("src/binpack1.txt");
		First_Fit_Increasing(dt.getB(), dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void Test_First_Fit_Increasing(String src)
	{
		DataReader dt=new DataReader(src);
		First_Fit_Increasing(dt.getB(), dt.getN(), dt.getPopt(), dt.getSi());
	}
	
	public static void First_Fit_Increasing(int B, int n, int popt, int[] si)
	{
		// LEGEND:
		// B 	- pojemnosc pudelek.
		// n 	- ilosc obiektow.
		// popt - najmniejsza ilosc uzytych obiektow.
		// si 	- rozmiary obiektow.
		
		// 0. Sortowanie obiektow.
		for(int i=0;i<n;i++)
		{
			for(int j=1;j<n;j++)
			{
				if(si[j-1]>si[j])
				{
					int tmp=si[j-1];
					si[j-1]=si[j];
					si[j]=tmp;
				}
			}
		}
		
		// 1. Zdefiniowanie buforow pomocniczych.
		int[]objects=new int[n]; // Obiekty.
		int[]boxes = new int[n]; // Pudelka.
		
		int openedBoxes=0; 		// Otwarte pudelka.
		
		for(int currentObject=0;currentObject<n;currentObject++)
		{
			// 0. Flaga, ktora znaczy czy element zosta³ umieszczony.
			boolean placed=false;
			
			// 1. Szukanie miejsca w pudelkach, które zosta³y wczeœniej otwarte.
			for(int i=0; i<openedBoxes;i++)
			{
				// 1.1 Sprawdzamy czy element mo¿na tam umieœciæ.
				if(boxes[i]+si[currentObject]<=B)
				{
					// 1.1.1 Wkladamy wage do pudelka,
					boxes[i]+=si[currentObject];
					
					// 1.1.2 Przypisujemy ten element do pude³ka.
					objects[currentObject]=i;
					
					// 1.1.3 Konczymy przeszukiwania, poniewaz juz wlozylismy ten obiekt.
					placed=true;
					break;
				}
			}
			
			// 2. Jezeli nie umiescilismy tego obiektu.
			if(!placed)
			{
				// 2.1 Nie ma miejsca w pude³kach, które zosta³y wczeœniej otworzone.
				//	  Zatem otwieramy nowe pude³ko i tam wk³adamy obiekt.
				openedBoxes++;
				boxes[openedBoxes]+=si[currentObject];
				objects[currentObject]=openedBoxes;
			}
		}
			
		System.out.println("=== First-Fit-Increasing ==");
		System.out.println("Output: ------------------]");
		System.out.println("Number of objects: "+n);
		System.out.println("Capacity of boxes: "+B);
		System.out.println("Used boxes: "+openedBoxes);
		System.out.println("Popt: "+popt);
		System.out.println("Worst: "+100*(openedBoxes-popt)/(float)popt+" %");
		System.out.println("--------------------------]");
		System.out.println("===========================");
	}
	// ==================================================	
}
