 package com.github.schmidtbochum.colonymail;

import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.plugin.java.JavaPlugin;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import com.github.schmidtbochum.colonydata.ColonyDataPlugin;
import com.github.schmidtbochum.colonydata.command.CMailGroupArgumentHandler;
import com.github.schmidtbochum.colonydata.command.CPlayerArgumentHandler;
import com.github.schmidtbochum.colonydata.data.CMailGroup;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.colonymail.command.MailCommand;
import com.github.schmidtbochum.colonymail.listener.EventListener;
import com.github.schmidtbochum.util.MessageManager;

public class ColonyMailPlugin extends JavaPlugin
{
	private DataManager d;
	private MessageManager m;
	private CommandHandler commandHandler;
	
	public static final char COLOR_CHAR = '&';

	//TODO mail list formatting
	
	
	public void onEnable() 
	{
		// copy default config
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		m = new MessageManager(this, Locale.ENGLISH, ResourceBundle.getBundle("messages", Locale.ENGLISH));
		
		ColonyDataPlugin dataPlugin = (ColonyDataPlugin) getServer().getPluginManager().getPlugin("ColonyData");
		
		d = dataPlugin.getDataManager();
		
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		
		commandHandler = new CommandHandler(this);
		
		commandHandler.registerArgumentHandler(CPlayer.class, new CPlayerArgumentHandler(dataPlugin));
		commandHandler.registerArgumentHandler(CMailGroup.class, new CMailGroupArgumentHandler(dataPlugin));
		
		commandHandler.registerCommands(new MailCommand(this));
		
	}
	
	public MessageManager getMessageManager()
	{
		return m;
	}
	
	public DataManager getDataManager() 
	{
		return d;
	}
}
