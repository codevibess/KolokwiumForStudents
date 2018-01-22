package kolokwium;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
   
  private static DataInputStream in = null;
  
  public void run() {
	    /*
	     * watek pobiera dane z servera i wyœwietla  w konsoli.
	     */
	    String tempLine;
	    try {
	      while ((tempLine = in.readLine()) != null) {
	        System.out.println(tempLine);        
	      	}
	      System.out.println("Serwer zostal zamkniety!");      
	    	} catch (IOException e) {
	      System.err.println("IOException:  " + e);
	    }
	  }
  
  public static void main(String[] args)  {	 
    /* Numer portu */
    int portNumber = 55;
    /* host - localhost dzialajacy na jednej maszynie.*/
    String host = "localhost";
    /*
     * Otweram socket dla klienta oraz strumie: wejsciowy i wyœciowy.
     */
    Socket clientSocket = null; 
    Scanner scaner = null;
    PrintStream out = null;    
   
    try {
      clientSocket = new Socket(host, portNumber);
      scaner = new Scanner(System.in);
      out = new PrintStream(clientSocket.getOutputStream());
      in = new DataInputStream(clientSocket.getInputStream());    
    } catch (IOException e) {
      System.err.println(e);
    }
    /* 
     * Tworzenie watku klienta 
     * uruchomiona metoda run()
     * zczytujaca dane z servera  i wswietlajaca je w konsoli
    */                        
    try {        
        new Thread(new Client()).start();      
        /*
         * staly odczyt z konsoli
         */        
        while (true) {        
          out.println(scaner.nextLine());         
        }  
        
        /*
         * Zamykam strumienie wejœciowe / wyœciowe oraz socket.
         */
      }finally{    	  
          try {        	 
        	in.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
      }
    }
  
  
}
