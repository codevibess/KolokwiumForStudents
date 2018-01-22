package kolokwium;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


class ServerThread extends Thread {
	  private DataInputStream in = null;
	  private PrintStream out = null;
	  private Socket clientSocket = null;
	  private ServerThread[] threads;
	  private int id;
	  public String name;
	  public int counter;
	  int i = 0;
	  
	  myJDBC myJDBC = new myJDBC();
	  ArrayList<Pytanie> listaPytan;//++
	  
	  
	  /*
	   * Konstruktor klasy ServerThread
	   */
	public ServerThread(Socket clientSocket, ServerThread[] threads, int id, ArrayList<Pytanie> listaPytan) {//
	    this.clientSocket = clientSocket;
	    this.threads = threads;
	    this.listaPytan = listaPytan;
	    this.id = id;
	  }
	  
	public int getCounter() {
	
		return counter;
	}
	public void setCounter(){
		++counter;
		
	}
	  
	  


	public void run() {
		
	    ServerThread[] threads = this.threads;

	    try {
	      /*
	       * Strumienie wejsciowe i wyjsciowe
	       */
	      in = new DataInputStream(clientSocket.getInputStream());
	      out = new PrintStream(clientSocket.getOutputStream());
	          	     	      
	      out.println("Twoj NIU: ");
	      
	      /*pobieram imie*/
	      name = in.readLine(); 
	      PrintWriter nameOdpowiedz = new PrintWriter(new BufferedWriter(new FileWriter("bazaOdpowiedzi.txt",true)));
    	  nameOdpowiedz.println(name);
    	  nameOdpowiedz.flush();
    	  nameOdpowiedz.close();
    	  
	      /*
	       * Sprawdzamy czy login nie jest zajety, uzytkowik musi wprowadzac login az trafi na wolny
	       */
	      for (int i = 0; i < threads.length; i++) {
	          while (threads[i] != null && i != id && threads[i].name.equals(this.name)) { //
	        	  out.println("Student z tym NIU już napisał test! Wprowadz inny:");
	        	  name = in.readLine();
	        	  
	          }
	      }
	      
	      out.println("Zaczynaj kolokwium:");
	      
	      
	     
	     
	      Iterator<Pytanie> iterator = listaPytan.iterator();
			Pytanie aktualnePytanie=null;
			String odpowiedz=null;
	     
	      while (true) {
//	        String line = in.readLine(); jaksjo ce byde to treba natyskaty enter pislya koznogo pytania
//	        if (line.equals("exit")) {
//	          break;          
//	          
//	        }	        
	        if(iterator.hasNext() == false)
			{
				out.println("KONIEC KOLOKWIUM");
				out.print("Twój wynik : ");
				out.print(getCounter() + " punktów");
				myJDBC.setDataSql(name, counter);
//				out.flush();
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("wyniki.txt",true)));
				
				String wynik ="Student z numerem NIU: " + name + " napisal kolokwium na   " + getCounter() + " p";
				
				pw.println(wynik);
				pw.flush();
				pw.close();				
				break;				
			}
			
	        
			aktualnePytanie = iterator.next();
			out.print(aktualnePytanie.getPytanie());
//			out.println("end");
			out.flush();				
			odpowiedz = in.readLine();
			PrintWriter pww = new PrintWriter(new BufferedWriter(new FileWriter("bazaOdpowiedzi.txt",true)));
			
//			String odpowiedzS = odpowiedz;
			i++;
			pww.println(odpowiedz);
			pww.flush();
			pww.close();
			
			if(aktualnePytanie.getPoprawnaOdpowiedz() == odpowiedz.charAt(0)){
				out.println("Bardzo dobrze!");
				setCounter();
				
				
				
			}else{
				out.println("ZLE, POPRAWNA ODPOWIEDZ, TO :" + aktualnePytanie.getPoprawnaOdpowiedz());	
			}
	      }
	      	      /*
	       * Zwalanie slotu w tablicy po opuszczeniu kolokwium
	       */	      
//	      threads[id] = null;	       

	      /*
	       * Zamykanie strumieni wejsciowych i wyjsciowych.
	       */
	      in.close();
	      out.close();
	      clientSocket.close();
	    
	      
	    } catch (IOException e) {
	    	
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}
