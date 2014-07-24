package com.github.schmidtbochum.colonymail.command;

import org.bukkit.command.CommandSender;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.Wildcard;

import com.github.schmidtbochum.colonydata.data.CPlayer;
import com.github.schmidtbochum.colonymail.ColonyMailPlugin;

public class MailCommand
{
	private ColonyMailPlugin plugin;

	public MailCommand(ColonyMailPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Command(
		identifier="mail send",
		description="Send and receive server mails", 
		onlyPlayers = false,
		permissions = {"coremail.command.mail.send"}
	)
	//mail send <player> <msg>
	public void sendSelf(
	    CommandSender sender,
	    @Arg(name="player") CPlayer receipient,
	    @Wildcard @Arg(name="msg") String message
	) 
	{
	    //TODO
		sender.sendMessage("to " + receipient.getName() + ": " + message);
	}
}
