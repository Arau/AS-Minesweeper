package main;

import java.util.List;

import domain.Admin;
import domain.Level;
import domain.Player;
import domain.User;
import domaincontrollers.PlayUseCase;

public class Main {

	public static void main(String[] args) throws Exception {
		Level easy      = new Level("easy", 	10, 10, 10);
		Level medium    = new Level("medium", 	20, 20, 20);
		Level difficult = new Level("difficult",30, 30, 30);		
	

		User u = new User ("u1", "n1", "n12", "pwd");		
		Admin admin = new Admin("a1", "n2", "n22", "pwd", "933843321");
		Player player = new Player("p1", "n3", "n32", "pwd", "p1@p1.com");
		Player p2 = new Player("p2", "n4", "n42", "pwd", "p2@p2.com");
		Player p3 = new Player("p3", "n4", "n42", "pwd", "p3@p3.com");
		

		PlayUseCase playUC = new PlayUseCase();
		
		String username = "p1";
		if (playUC.login(username, "pwd")) {
			List<String> levels = playUC.retrieveLevels();
			playUC.startGame(username, levels.get(1));
		}
	}
}
