package main;

import hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import domain.Admin;
import domain.Game;
import domain.Level;
import domain.Player;
import domain.User;

public class Main {

	public static void main(String[] args) {
		Level easy      = new Level("easy", 	10, 10, 10);
		Level medium    = new Level("medium", 	20, 20, 20);
		Level difficult = new Level("difficult",30, 30, 30);
		HibernateUtil.save(easy);
		HibernateUtil.save(medium);
		HibernateUtil.save(difficult);
	

		// User checks		
		User u = new User ("u1", "n1", "n12", "pwd");
		HibernateUtil.save(u);
		
		Admin admin = new Admin("a1", "n2", "n22", "pwd", "933843321");
		Player player = new Player("p1", "n3", "n32", "pwd", "p1@p1.com");
		Player p2 = new Player("p2", "n4", "n42", "pwd", "p2@p2.com");
		Player p3 = new Player("p3", "n4", "n42", "pwd", "p3@p3.com");

		HibernateUtil.save(admin);
		HibernateUtil.save(player);		
		HibernateUtil.save(p2);		
		HibernateUtil.save(p3);
		
		
		Game g1 = new Game(player, easy);
		Game g2 = new Game(p2, medium);
		Game g3 = new Game(p3, difficult);
		Game g4 = new Game(p2, easy);
		
		HibernateUtil.save(g1);
		HibernateUtil.save(g2);
		HibernateUtil.save(g3);
		HibernateUtil.save(g4);
		
		p2.setOldGame(g2);
		p2.setCurrentGame(g4);
			
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Game res1 = (Game) session.get(Game.class, g1.getId());
		Game res2 = (Game) session.get(Game.class, g2.getId());
		Game res3 = (Game) session.get(Game.class, g3.getId());		
		
		
		// Check output from Game 
		String l1 = res1.getLevelName();
		String l2 = res2.getLevelName();
		String l3 = res3.getLevelName();
		
		System.out.println(l1 + " " + l2 + " " + l3);
		
		User 	res_u = (User) 	session.get(User.class, 	u.getUserName());
		Admin	res_a = (Admin) session.get(Admin.class, 	admin.getUserName());
		Player	res_p = (Player)session.get(Player.class, 	player.getUserName());
		
		String uname = res_u.getUserName();
		String aname = res_a.getUserName();
		String pname = res_p.getUserName();
		
		System.out.println(uname + " " + aname + " " + pname);
	
		Player	res_p2 = (Player)session.get(Player.class, 	p2.getUserName());
		Game oldGame = res_p2.getOldGame();
		System.out.println("oldgame level name " + oldGame.getLevelName() );
		
		
		session.close();				
	}

}
