package kolokwium;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;



import java.net.ServerSocket;


public class Server {
	
	
	
	public ArrayList<Pytanie> inicjalizacjaBazyPytan()

	{
		ArrayList<Pytanie> listaPytan = new ArrayList<Pytanie>();
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("bazaPytan.txt"));
			String linia;
			String pytanie = "";
			
			while(!(br.readLine().equals("end")))
			{
				
				pytanie = "";
				for(int i=0; i<5; i++)
				{
					pytanie += br.readLine()+'\n';
					
				}
				
				listaPytan.add(new Pytanie(pytanie, br.readLine().charAt(0)));
			}

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Plik nie zostal znaleziony");
		} 
		catch (IOException e) 
		{
			System.out.println("Cos jest nie tak z plikiem");
			e.printStackTrace();
		}
		return listaPytan;
		
	}
	

	
  public static void main(String args[]) throws IOException {
	  
	int maxClientsCount = 38; //maksymalna liczba osbób(klientów)
	ServerThread[] threads = new ServerThread[maxClientsCount]; //tablica watków o maksymalnym rozwiarze ilosi osob piszacych kolokwim.
    
    int portNumber = 55;  // numer portu. 
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    /*
     *  Otwieram nowy Socket z portem - portNumber
     */
    try {
      serverSocket = new ServerSocket(portNumber);
      System.out.println("Server started");
      
    } catch (IOException e) {
      System.out.println(e);
    }

   
   
    
    /*
     * Dodaje klienta jezeli sie pojawi
     */
    while (true) {
      try {
    	  Server serwer = new Server();
        clientSocket = serverSocket.accept();  // jezœli jest nowy Client
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) { // przechodzi petle az napodka wolny slot.
            (threads[i] = new ServerThread(clientSocket, threads, i, serwer.inicjalizacjaBazyPytan())).start();//
            break;
          }
        }
        
//        (new ServerThread(clientSocket, serwer.inicjalizacjaBazyPytan(), 1)).start();
        
      } catch (IOException e) {
        System.out.println(e);
      }
      
    }
  }
}
