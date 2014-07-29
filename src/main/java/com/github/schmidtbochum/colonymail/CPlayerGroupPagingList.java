package com.github.schmidtbochum.colonymail;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.schmidtbochum.colonydata.data.CPlayerGroup;
import com.github.schmidtbochum.util.MessageManager;
import com.github.schmidtbochum.util.SimplePagingList;

public class CPlayerGroupPagingList extends SimplePagingList<CPlayerGroup>
{
	
	public CPlayerGroupPagingList(List<CPlayerGroup> elementList, MessageManager m, int expirationMinutes, String pagingCommand)
	{
		super(elementList, m, expirationMinutes, pagingCommand, "page_playergroup_header", "page_playergroup_no_elements");
	}

	@Override
	public void sendElement(CommandSender receipient, CPlayerGroup element)
	{
		getMessageManager().sendMessage("page_item_unordered", receipient, element.getName());
	}
}
