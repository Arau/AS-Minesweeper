package datainterface;

import data.AdminCtrlDB;
import data.BoxCtrlDB;
import data.GameCtrlDB;
import data.LevelCtrlDB;
import data.PlayerCtrlDB;
import data.UserCtrlDB;

public class DataControllerFactory {
	private static DataControllerFactory instance;
	private static AdminCtrl adminCtrl;
	private static BoxCtrl boxCtrl;
	private static GameCtrl gameCtrl;
	private static LevelCtrl levelCtrl;
	private static PlayerCtrl playerCtrl;
	private static UserCtrl userCtrl;
	
	
	public DataControllerFactory() {}
	
	public static DataControllerFactory getInstance() {
        if (instance == null) 
        	instance = new DataControllerFactory();
        return instance;
    }
	
	public AdminCtrl getAdminCtrl() {
		if (adminCtrl == null) 
			adminCtrl = AdminCtrlDB.getInstance();
		return adminCtrl;
	}
	
	public BoxCtrl getBoxCtrl() {
		if (boxCtrl == null) 
			boxCtrl = BoxCtrlDB.getInstance();
		return boxCtrl;
	}
	
	public GameCtrl getGameCtrl() {
		if (gameCtrl == null) 
			gameCtrl = GameCtrlDB.getInstance();
		return gameCtrl;
	}

	public LevelCtrl getLevelCtrl() {
		if (levelCtrl == null) 
			levelCtrl = LevelCtrlDB.getInstance();
		return levelCtrl;
	}
	
	public PlayerCtrl getPlayerCtrl() {
		if (playerCtrl == null) 
			playerCtrl = PlayerCtrlDB.getInstance();
		return playerCtrl;
	}
	
	public UserCtrl getUserCtrl() {
		if (userCtrl == null) 
			userCtrl = UserCtrlDB.getInstance();
		return userCtrl;
	}
}
