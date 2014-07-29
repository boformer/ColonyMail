package com.github.schmidtbochum.colonymail.paging;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.schmidtbochum.colonydata.data.CMail;
import com.github.schmidtbochum.util.MessageManager;
import com.github.schmidtbochum.util.SimplePagingList;

public class CMailPagingList extends SimplePagingList<CMail>
{
	public CMailPagingList(List<CMail> elementList, MessageManager m, int expirationMinutes, String pagingCommand)
	{
		super(elementList, m, expirationMinutes, pagingCommand, "page_mail_header", "page_mail_no_elements");
	}

	@Override
	public void sendElement(CommandSender receipient, CMail element)
	{
		if(element.getPlayerGroup() == null) 
		{
			getMessageManager().sendMessage("page_mail_template", receipient, element.getSender().getName(), element.getMessage());
		}
		else
		{
			getMessageManager().sendMessage("page_mail_group_template", receipient, element.getPlayerGroup().getName(), element.getSender().getName(), element.getMessage());
		}
	}
}
