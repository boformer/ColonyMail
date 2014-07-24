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
		identifier="test", //The identifier is the way the command will be accessed ingame, this command will be accessed with /test
		description="This is a test command", //Writing the description of the command here makes it easier to generate help messages.
		onlyPlayers = false, //If the command can be executed by a player or not.
		permissions = {"coremail.command.mail.send"} //What permission nodes the player must have to execute this command
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
