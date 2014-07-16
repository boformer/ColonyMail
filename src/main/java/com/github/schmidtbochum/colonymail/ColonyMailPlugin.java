package com.github.schmidtbochum.colonymail;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.schmidtbochum.colonydata.ColonyDataPlugin;
import com.github.schmidtbochum.colonydata.data.CPlayerGroup;
import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.util.MessageManager;

public class ColonyMailPlugin extends JavaPlugin
{
	DataManager d;
	MessageManager m;

	
	public void onEnable() 
	{
		// copy default config
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		m = new MessageManager(this, Locale.ENGLISH, ResourceBundle.getBundle("messages", Locale.ENGLISH));
		
		d = ((ColonyDataPlugin) getServer().getPluginManager().getPlugin("ColonyData")).getDataManager();
	}
	
	public void sendMail(CPlayer sender, CPlayer receipient, CPlayerGroup playerGroup, String message)
	{
		CMail mail = d.createEntityBean(CMail.class);
		
		mail.setDate(new Date());
		mail.setSender(sender);
		mail.setReceipient(receipient);
		mail.setPlayerGroup(playerGroup);
		mail.setMessage(message);
		
		d.save(mail);
	}
	
	public MessageManager getMessageManager()
	{
		return m;
	}
}
