import java.rmi.Naming;

public class ClienteRMI
{
	static int N = 4;
	static final String URL = "rmi://localhost/nodo";

	public static void main(String[] args) throws Exception
	{
	 	int[][] A = new int[N][N];
	 	int[][] B = new int[N][N];
	 	int[][] C = new int[N][N];

	 	for(int i = 0; i < N; i++)
	 	{
	 		for(int j = 0; i < N; j++)
	 		{
	 			A[i][j] = 2 * i - j;
	 			B[i][j] = 2 * i + j;
	 			C[i][j] = 0;
	 		}
	 	}

	 	B = trasnponeMatriz(B);

	 	InterfaceRMI r1 = (InterfaceRMI)Naming.lookup(URL + "0");
	 	int[][] C1 = r1.multiplicaMatrices(parteMatriz(A,0), parteMatriz(B,0),N);

	 	InterfaceRMI r2 = (InterfaceRMI)Naming.lookup(URL + "1");
	 	int[][] C2 = r2.multiplicaMatrices(parteMatriz(A,0), parteMatriz(B,N/2),N);

	 	InterfaceRMI r3 = (InterfaceRMI)Naming.lookup(URL + "2");
	 	int[][] C3 = r3.multiplicaMatrices(parteMatriz(A,N/2), parteMatriz(B,0),N);

	 	InterfaceRMI r4 = (InterfaceRMI)Naming.lookup(URL + "3");
	 	int[][] C4 = r4.multiplicaMatrices(parteMatriz(A,N/2), parteMatriz(B,N/2),N);

		acomodaMatriz(C,C1,0,0);
		acomodaMatriz(C,C2,0,N/2);
		acomodaMatriz(C,C3,N/2,0);
		acomodaMatriz(C,C4,N/2,N/2);

		if(N == 4)
		{
			muestraMatriz(A);
			muestraMatriz(B);
			muestraMatriz(C);

			System.out.println("Checksum: " + calculaChecksum(C));
		}
		else
			System.out.println("Checksum: " + calculaChecksum(C));	
	}

	static int[][] trasnponeMatriz(int[][] A)
	{
		for(int i = 0; i < N; i ++)
		{
			for(int j = 0; j < i; j++)
			{
				int x = A[i][j];
				A[i][j] = A[j][i];
				A[j][i] = x;
			}
		}

		return A;
	} 

	static int[][] parteMatriz(int[][] A, int inicio)
	{
		int[][] M = new int[N/2][N];
		
		for(int i = 0; i < N/2; i++)
			for(int j = 0; j < N; j++)
				M[i][j] = A[i + inicio][j];

		return M;
	}

	static void acomodaMatriz(int[][] C,int[][] A,int renglon,int columna)
	{
  		for (int i = 0; i < N/2; i++)
    		for (int j = 0; j < N/2; j++)
      			C[i + renglon][j + columna] = A[i][j];
	}

	static double calculaChecksum(int[][] A)
	{
		double checksum = 0;

		for (int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)
				checksum += A[i][j];

		return checksum;
	}

	static void muestraMatriz(int[][] A)
	{
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				System.out.println(A[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}
}