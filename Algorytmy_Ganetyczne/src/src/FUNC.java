package src;


public class FUNC //TODO: Funkcje do zadañ.
{
	// Funkcja do zadania 1
	public static float Func_1(float x1, float x2)
	{
		//TODO: Funkcja do pierwszego algorytmu.
		return (float) (-Math.pow(x1, 2)-Math.pow(x2, 2)+2);
	}
		
	// Funkcja do zadania 2
	static double Func_2(float[] vector, int n)
	{
		//TOD: Funkcja do drugiego algorytmu.
		double res=0, A=10, N=A, pi=3.14;
		for(int i=0;i<vector.length;i++)
		{
			res+=Math.pow(vector[i],2)-A*Math.cos(2*pi*vector[i]);
		}
		res+=A*n;
		return res;
	}
}
