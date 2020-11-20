import java.rmi.Naming;

public class ServidorRMI
{
	public static void main(String[] args) throws Exception
	{
		String url = "rmi://localhost/nodo";
		ClaseRMI obj = new ClaseRMI();

		Naming.rebind( url + args[0], obj);
	}
}