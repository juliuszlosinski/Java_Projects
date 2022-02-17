package src;

// CEL: Utworzenie struktury do przechowywaniu grafu.
public class Graph
{
	// REGION: Pola. ============================
	public int size;	  // Rozmiar macierzy.
	public int[][]matrix; // Macierzowa reprezetacja grafu.
	// END REGION ===============================
	
	// REGION: Kontruktory. =====================
	// CEL: Generowanie graphu.
	public Graph(int size, int[][]matrix)
	{
		this.size=size;
		this.matrix=matrix;
	}
	
	public Graph(GraphReader gr)
	{
		this.size=gr.getGraph().getSize();
		this.matrix=gr.getGraph().getMatrix();
	}
	
	// CEL: Generowanie graphu.
	public Graph(int size)
	{
		this.size=size;
		matrix=new int[size][size];
	}
	// END REGION ================================
	
	// REGION: W³aœciowœci. ======================
	public int getSize() {return size;}
	public int[][] getMatrix() {return matrix;}
	// END REGION ================================
	
	// REGION: Metody. ===========================
	// CEL: Dodanie krawêdzi.
	public void addEdge(int fromIndex, int toIndex)
	{
		matrix[fromIndex][toIndex]=1;
		matrix[toIndex][fromIndex]=1;
	}
	
	// CEL: Usuniêcie krawêdzi.
	public void removeEdge(int fromIndex, int toIndex)
	{
		matrix[fromIndex][toIndex]=0;
		matrix[toIndex][fromIndex]=0;
	}
	
	// CEL: Sprawdzenie czy jest krawêdz.
	public boolean isEdge(int fromIndex, int toIndex)
	{
		if(matrix[fromIndex][toIndex]==1)
		{
			return true;
		}
		return false;
	}
	
	// CEL: Drukowanie grafu.
	public void print()
	{
		System.out.println("Graph: ");
		for(int i=0;i<size;i++)
		{
			System.out.print(i+": ");
			for(int j=0;j<size;j++)
			{
				if(matrix[i][j]==1)
				{
					System.out.print(j+" ");
				}
			}
			System.out.println();
		}
	}
	// END REGION =================================
}
