import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import UDP.Server;

public class TestUDP {
/*teste*/
	
	
	@Test
	public void TEST_addPlate(){
		Server s = new Server();
		int n1 = s.getNumPlates();
		assertEquals("Diferente numero de matriculas",0,n1);
		s.addLicentPlate("33-DX-95","Daniel Reis");
		s.addLicentPlate("98-11-RI","Manuela Sivla");
		s.addLicentPlate("21-MS-45","Maria Guedes");
		int n2 = s.getNumPlates();
		assertEquals("Diferente numero de matriculas",3,n2);
		Boolean resp = s.addLicentPlate("33-DX-95","Daniel Reis");
		assertEquals("Existem repetidos",false,resp);
	}
	
	@Test
	public void TEST_findPlate() {
		Server s = new Server();
		assertEquals("Diferente numero de matriculas",0,s.getNumPlates());
		s.addLicentPlate("33-DX-95","Daniel Reis");
		s.addLicentPlate("98-11-RI","Manuela Sivla");
		s.addLicentPlate("21-MS-45","Maria Guedes");
		assertEquals("Diferente numero de matriculas",3,s.getNumPlates());
		
		String mt = s.findPlate("33-DX-95");
		System.out.println(mt);
		assertEquals("dono","Daniel Reis",mt);
	}
	
	@Test
	public void TEST(){
		HashMap<String,String> database = new HashMap<String,String>();
		database.put("cenas", "cenas1");
		database.put("coisas", "coisas1");
		database.put("merda", "merda1");
		String test = database.get("cenas");
		String test1 = database.get("none");
	//	System.out.println("resultado1: "+test);
	//	System.out.println("resultado2: "+test1);
		
		
	}

}





