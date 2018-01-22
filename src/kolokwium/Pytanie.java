package kolokwium;



public class Pytanie {
	
	private String pytanie;
	private char poprawnaOdpowiedz;
	
	@Override
	public String toString() {
		return "Pytanie"+"\n"+ "pytanie=" + pytanie + ", poprawnaOdpowiedz=" + poprawnaOdpowiedz +'\n';
	}

	public Pytanie(String pytanie, char poprawnaOdpowiedz)
	{
		this.pytanie = pytanie;
		this.poprawnaOdpowiedz = poprawnaOdpowiedz;
	}

	
	
	public String getPytanie() {
		
		return pytanie;
	}

	public char getPoprawnaOdpowiedz() {
		return poprawnaOdpowiedz;
	}

	

}
