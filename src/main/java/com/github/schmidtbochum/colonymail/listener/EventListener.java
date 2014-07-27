package com.github.schmidtbochum.colonymail.listener;

import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.colonymail.ColonyMailPlugin;
import com.github.schmidtbochum.util.MessageManager;

public class EventListener implements Listener
{
	private MessageManager m;
	private DataManager d;
	
	public EventListener(ColonyMailPlugin plugin) 
	{
		this.m = plugin.getMessageManager();
		this.d = plugin.getDataManager();
	}

	//Events
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		CPlayer player = d.getPlayer(event.getPlayer());
		List<CMail> mails = d.getMails(player);
				
		if(mails.size() > 0) m.sendMessage("mail_notification", event.getPlayer(), mails.size());
		
	}
}
