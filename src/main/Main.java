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
		

		// We are going to ask a controller to get the level Object by name.
		// However, due to testing purposes we instantiate level directly
		
		Game g1 = new Game(easy);
		Game g2 = new Game(medium);
		Game g3 = new Game(difficult);
		HibernateUtil.save(g1);
		HibernateUtil.save(g2);
		HibernateUtil.save(g3);
		
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Game res1 = (Game) session.get(Game.class, g1.getId());
		Game res2 = (Game) session.get(Game.class, g2.getId());
		Game res3 = (Game) session.get(Game.class, g3.getId());
		session.close();
		
		
		// Check output from Game 
		String l1 = res1.getLevelName();
		String l2 = res2.getLevelName();
		String l3 = res3.getLevelName();
		
		System.out.println(l1 + " " + l2 + " " + l3);

		
		// User checks
		
		User u = new User ("u1", "n1", "n12", "pwd");
		HibernateUtil.save(u);
		
		Admin admin = new Admin("a1", "n2", "n22", "pwd", "933843321");
		HibernateUtil.save(admin);
		
		Player player = new Player("p1", "n3", "n32", "pwd", "p1@p1.com");
		HibernateUtil.save(player);

		
		User 	res_u = (User) 	session.get(User.class, 	u.getUserName());
		Admin	res_a = (Admin) session.get(Admin.class, 	admin.getUserName());
		Player	res_p = (Player)session.get(Player.class, 	player.getUserName());
		
		String uname = res_u.getUserName();
		String aname = res_a.getUserName();
		String pname = res_p.getUserName();
		
		System.out.println(uname + " " + aname + " " + pname);
		
	}

}
