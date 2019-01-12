package services.community;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2r.commons.dao.JdbcEntityState;
import l2r.gameserver.Config;
import l2r.gameserver.data.htm.HtmCache;
import l2r.gameserver.handler.bbs.CommunityBoardManager;
import l2r.gameserver.handler.bbs.ICommunityBoardHandler;
import l2r.gameserver.model.Player;
import l2r.gameserver.model.actor.instances.player.ShortCut;
import l2r.gameserver.model.items.ItemInstance;
import l2r.gameserver.network.serverpackets.InventoryUpdate;
import l2r.gameserver.network.serverpackets.ShortCutRegister;
import l2r.gameserver.network.serverpackets.ShowBoard;
import l2r.gameserver.network.serverpackets.components.SystemMsg;
import l2r.gameserver.scripts.Functions;
import l2r.gameserver.scripts.ScriptFile;

public  class Augmentation extends Functions implements ScriptFile, ICommunityBoardHandler
{
	private static final Logger _log = LoggerFactory.getLogger(Augmentation.class);

	@Override
	public String[] getBypassCommands()
	{
		// TODO Auto-generated method stub
		return  new String[] {"_bbsaugm"};
	}

	@Override
	public void onBypassCommand(Player player, String bypass)
	{
		if(bypass.startsWith("_bbsaugm"))
		{
			StringTokenizer st2 = new StringTokenizer(bypass, ";");
			String[] mBypass = st2.nextToken().split(":");
			String html = null;
			html = HtmCache.getInstance().getNotNull(Config.BBS_HOME_DIR + "pages/augment.htm", player);
			ItemInstance item = player.getActiveWeaponInstance();
			int id = Integer.parseInt(mBypass[1]);
			
			if(item == null || item.isAugmented() || !item.canBeAugmented(player, false))
			{
				return;
			}
			if(!player.getInventory().destroyItemByItemId(57, 1))
			{
				player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			}
			else
			{
				player.sendPacket(SystemMsg.INCORRECT_ITEM_COUNT);
			}
			
			boolean equipped = false;
			if(equipped = item.isEquipable())
			{
				player.getInventory().unEquipItem(item);
			}
			item.setAugmentationId((id << 16) + 3640);
			item.setJdbcState(JdbcEntityState.UPDATED);
			item.update();
			if(equipped)
			{
				player.getInventory().equipItem(item);
			}
			player.sendPacket(new InventoryUpdate().addModifiedItem(item));
			for(ShortCut sc: player.getAllShortCuts())
			{
			if(sc.getId() == item.getObjectId() && sc.getType() == ShortCut.TYPE_ITEM)
			{
				player.sendPacket(new ShortCutRegister(player,sc));
			}
			player.sendChanges();
			}
			ShowBoard.separateAndSend(html, player);
		}
	}

	@Override
	public void onWriteCommand(Player player, String bypass, String arg1, String arg2, String arg3, String arg4,String arg5)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad()
	{
		// TODO Auto-generated method stub
		Augmentation._log.info("CommunityBoard: ArgumManager loaded.");
        CommunityBoardManager.getInstance().registerHandler((ICommunityBoardHandler)this);
	}

	@Override
	public void onReload()
	{
		// TODO Auto-generated method stub
		CommunityBoardManager.getInstance().removeHandler((ICommunityBoardHandler)this);
	}

	@Override
	public void onShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}