package com.github.schmidtbochum.colonymail;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.schmidtbochum.colonydata.data.CMailGroup;
import com.github.schmidtbochum.util.MessageManager;
import com.github.schmidtbochum.util.SimplePagingList;

public class CMailGroupPagingList extends SimplePagingList<CMailGroup>
{
	
	public CMailGroupPagingList(List<CMailGroup> elementList, MessageManager m, int expirationMinutes, String pagingCommand)
	{
		super(elementList, m, expirationMinutes, pagingCommand, "page_mailgroup_header", "page_mailgroup_no_elements");
	}

	@Override
	public void sendElement(CommandSender receipient, CMailGroup element)
	{
		getMessageManager().sendMessage("page_item_unordered", receipient, element.getName());
	}
}
