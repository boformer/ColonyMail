package com.github.schmidtbochum.colonymail;

import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.schmidtbochum.colonydata.ColonyDataPlugin;
import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;

public class ColonyMailPlugin extends JavaPlugin
{
	DataManager d;
	
	public void onEnable() 
	{
		d = ((ColonyDataPlugin) getServer().getPluginManager().getPlugin("ColonyData")).getDataManager();
	}
	
	public void sendMail(CPlayer sender, CPlayer receipient, String groupString, String message)
	{
		CMail mail = d.createEntityBean(CMail.class);
		
		mail.setDate(new Date());
		mail.setSender(sender);
		mail.setReceipient(receipient);
		mail.setGroupString(groupString);
		mail.setMessage(message);
		
		d.save(mail);
	}
}
