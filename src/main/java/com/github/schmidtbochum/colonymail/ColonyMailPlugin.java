 package com.github.schmidtbochum.colonymail;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.plugin.java.JavaPlugin;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import com.github.schmidtbochum.colonydata.ColonyDataPlugin;
import com.github.schmidtbochum.colonydata.command.CPlayerArgumentHandler;
import com.github.schmidtbochum.colonydata.data.CMailGroup;
import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.colonymail.command.MailCommand;
import com.github.schmidtbochum.util.MessageManager;

public class ColonyMailPlugin extends JavaPlugin
{
	private DataManager d;
	private MessageManager m;
	private CommandHandler commandHandler;

	
	public void onEnable() 
	{
		// copy default config
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		m = new MessageManager(this, Locale.ENGLISH, ResourceBundle.getBundle("messages", Locale.ENGLISH));
		
		ColonyDataPlugin dataPlugin = (ColonyDataPlugin) getServer().getPluginManager().getPlugin("ColonyData");
		
		d = dataPlugin.getDataManager();
		
		commandHandler = new CommandHandler(this);
		
		commandHandler.registerArgumentHandler(CPlayer.class, new CPlayerArgumentHandler(dataPlugin));
		
		commandHandler.registerCommands(new MailCommand(this));
		
	}
	
	public void sendMail(CPlayer sender, CPlayer receipient, CMailGroup playerGroup, String message)
	{
		CMail mail = d.createEntityBean(CMail.class);
		
		mail.setDate(new Date());
		mail.setSender(sender);
		mail.setReceipient(receipient);
		mail.setMailGroup(playerGroup);
		mail.setMessage(message);
		
		d.save(mail);
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
