package com.github.schmidtbochum.colonymail.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.Wildcard;

import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.colonymail.ColonyMailPlugin;
import com.github.schmidtbochum.colonymail.CMailPagingList;
import com.github.schmidtbochum.util.MessageManager;
import com.github.schmidtbochum.util.PagingListCache;

public class MailCommand
{
	private final ColonyMailPlugin plugin;
	private final DataManager d;
	private final MessageManager m;
	
	private final PagingListCache<CMailPagingList> mailReadCache;

	public MailCommand(ColonyMailPlugin plugin)
	{
		this.plugin = plugin;
		this.d = plugin.getDataManager();
		this.m = plugin.getMessageManager();
		
		mailReadCache = new PagingListCache<>();
		
		plugin.getServer().getPluginManager().registerEvents(mailReadCache, plugin);
	}

	@Command(
		identifier="mail send",
		description="Send server mails", 
		onlyPlayers = false,
		permissions = {"colony.command.mail.send"}
	)
	//mail send <player> <msg>
	public void sendMail(
	    CommandSender sender,
	    @Arg(name="player") CPlayer mailReceipient,
	    @Wildcard @Arg(name="msg") String mailMessage
	) 
	{
	    CPlayer mailSender = d.getPlayer(sender);
		
	    if(!sender.hasPermission("colony.mail.useformat")) 
	    {
	    	mailMessage = ChatColor.stripColor(mailMessage);
	    }
	    else 
	    {
	    	mailMessage = ChatColor.translateAlternateColorCodes(ColonyMailPlugin.COLOR_CHAR, mailMessage);
	    }
		
		plugin.sendMail(mailSender, mailReceipient, null, mailMessage);
		
		m.sendMessage("mail_sent", sender);
		
		//TODO debug
		sender.sendMessage(mailSender.getName() + "to " + mailReceipient.getName() + ": " + mailMessage);
	}
	
	@Command(
			identifier="mail read",
			description="Read server mails", 
			onlyPlayers = false,
			permissions = {"colony.command.mail.read"}
	)
	//mail read [page]
	public void readMail(
	    CommandSender sender,
	    @Arg(name="page", def = "1") int page
	) 
	{
	    CMailPagingList pagingList = mailReadCache.getPagingList(sender);
		
		if(pagingList == null) 
		{
			CPlayer mailReceipient = d.getPlayer(sender);
			List<CMail> mails = d.getMails(mailReceipient);	
			
			pagingList = new CMailPagingList(mails, m, 2, "mail read");
			mailReadCache.put(sender, pagingList);
		}

		pagingList.sendPage(sender, page);
	}

}
