package com.github.schmidtbochum.colonymail.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.Wildcard;

import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.colonydata.data.CMailGroup;
import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonydata.data.DataManager;
import com.github.schmidtbochum.colonymail.CMailGroupPagingList;
import com.github.schmidtbochum.colonymail.CMailPagingList;
import com.github.schmidtbochum.colonymail.ColonyMailPlugin;
import com.github.schmidtbochum.util.MessageManager;
import com.github.schmidtbochum.util.PagingListCache;

public class MailCommand
{
	//TODO heya
	
	
	private final DataManager d;
	private final MessageManager m;

	private final PagingListCache<CMailPagingList> mailReadCache;

	public MailCommand(ColonyMailPlugin plugin)
	{
		this.d = plugin.getDataManager();
		this.m = plugin.getMessageManager();

		mailReadCache = new PagingListCache<>();

		plugin.getServer().getPluginManager().registerEvents(mailReadCache, plugin);
	}

	@Command(identifier = "mail send", description = "Send server mails", onlyPlayers = false, permissions = {"colony.command.mail.send"})
	//mail send <player> <msg>
	public void sendMail(CommandSender sender, @Arg(name = "player") CPlayer mailReceipient, @Wildcard @Arg(name = "msg") String mailMessage)
	{
		CPlayer mailSender = d.getPlayer(sender);

		//translate or remove format codes
		if(sender.hasPermission("colony.mail.useformat"))
		{
			mailMessage = ChatColor.translateAlternateColorCodes(ColonyMailPlugin.COLOR_CHAR, mailMessage);
		}
		else
		{
			mailMessage = ChatColor.stripColor(mailMessage);
		}

		//send mail
		d.sendMail(mailSender, mailReceipient, null, mailMessage);

		m.sendMessage("mail_sent", sender);
	}
	
	@Command(identifier = "mail groupsend", description = "Send server mails to a group", onlyPlayers = false, permissions = {"colony.command.mail.groupsend"})
	//mail groupsend <group> <msg>
	public void sendGroupMail(CommandSender sender, @Arg(name = "group") CMailGroup mailGroup, @Wildcard @Arg(name = "msg") String mailMessage)
	{
		CPlayer mailSender = d.getPlayer(sender);

		//translate or remove format codes
		if(sender.hasPermission("colony.mail.useformat"))
		{
			mailMessage = ChatColor.translateAlternateColorCodes(ColonyMailPlugin.COLOR_CHAR, mailMessage);
		}
		else
		{
			mailMessage = ChatColor.stripColor(mailMessage);
		}

		//send mail
		d.sendMail(mailSender, null, mailGroup, mailMessage);

		m.sendMessage("mail_sent_group", sender);
	}

	@Command(identifier="mail read", description="Read server mails", onlyPlayers = false, permissions = {"colony.command.mail.read"})
	//mail read [page]
	public void readMail(CommandSender sender, @Arg(name = "page", def = "1") int page)
	{
		//get cached paging list
		CMailPagingList pagingList = mailReadCache.getPagingList(sender);

		if(pagingList == null)
		{
			CPlayer mailReceipient = d.getPlayer(sender);
			List<CMail> mails = d.getMails(mailReceipient);

			//cache paging list
			pagingList = new CMailPagingList(mails, m, 1, "mail read");
			mailReadCache.put(sender, pagingList);
		}

		pagingList.sendPage(sender, page);
	}
	
	@Command(identifier = "mail clear", description = "Clear your inbox", onlyPlayers = false, permissions = {"colony.command.mail.clear"})
	//mail clear
	public void clearMail(CommandSender sender)
	{
		CPlayer player = d.getPlayer(sender);
		
		//clear mails
		d.clearMail(player);
		
		m.sendMessage("inbox_cleared", sender);
	}
	
	@Command(identifier = "mail group add", description = "Add a mail group", onlyPlayers = false, permissions = {"colony.command.mail.group.add"})
	//mail group add <name>
	public void addGroup(CommandSender sender, @Arg(name = "name") String groupName)
	{
		CMailGroup mailGroup = d.getMailGroup(groupName);
		
		if(mailGroup != null) 
		{
			m.sendMessage("mail_group_already_exists", sender, mailGroup.getName());
			return;
		}
		
		mailGroup = d.createEntityBean(CMailGroup.class);
		mailGroup.setName(groupName);
		mailGroup.setMailExpirationDays(30);
		
		d.save(mailGroup);
		
		m.sendMessage("mail_group_added", sender, mailGroup.getName());
	}
	
	@Command(identifier = "mail group remove", description = "Remove a mail group", onlyPlayers = false, permissions = {"colony.command.mail.group.remove"})
	//mail group remove <name>
	public void removeGroup(CommandSender sender, @Arg(name = "name") CMailGroup mailGroup)
	{
		d.delete(mailGroup);
		
		m.sendMessage("mail_group_removed", sender, mailGroup.getName());
	}
	
	@Command(identifier = "mail group list", description = "List of mail groups", onlyPlayers = false, permissions = {"colony.command.mail.group.list"})
	//mail group list [page]
	public void listGroups(CommandSender sender, @Arg(name = "page", def = "1") int page)
	{
		CMailGroupPagingList pagingList = new CMailGroupPagingList(new ArrayList<CMailGroup>(d.getMailGroups()), m, 0, "mail group list");
		
		pagingList.sendPage(sender, page);
	}
}
