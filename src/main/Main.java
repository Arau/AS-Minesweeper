package main;

import java.util.List;

import domain.Admin;
import domain.Game;
import domain.Level;
import domain.Player;
import domain.User;
import domaincontrollers.LoginUseCase;
import domaincontrollers.RetrieveLevelsUseCase;

public class Main {

	public static void main(String[] args) throws Exception {
		Level easy      = new Level("easy", 	10, 10, 10);
		Level medium    = new Level("medium", 	20, 20, 20);
		Level difficult = new Level("difficult",30, 30, 30);		
	

		// User checks		
		User u = new User ("u1", "n1", "n12", "pwd");		
		Admin admin = new Admin("a1", "n2", "n22", "pwd", "933843321");
		Player player = new Player("p1", "n3", "n32", "pwd", "p1@p1.com");
		Player p2 = new Player("p2", "n4", "n42", "pwd", "p2@p2.com");
		Player p3 = new Player("p3", "n4", "n42", "pwd", "p3@p3.com");
		
		
		Game g1 = new Game(1, player, easy);
//		Game g2 = new Game(2, p2, medium);
//		Game g3 = new Game(3, p3, difficult);
//		Game g4 = new Game(4, p2, easy);
		
//		SessionFactory sf = HibernateUtil.getSessionFactory();
//		Session session = sf.openSession();
//		
//		session.close();			
		
		LoginUseCase logUC = new LoginUseCase();
		boolean log = logUC.login(player.getUserName(), "pwd");
		if (log) System.out.println("Logged!");
		
		RetrieveLevelsUseCase levUC = new RetrieveLevelsUseCase();
		List<String> levelNames = levUC.retrieveLevels();
		for (String l: levelNames) {
			System.out.println(l);
		}
	}
}
