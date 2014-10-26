package main;

import hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import domain.Game;
import domain.Level;

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

	}

}
