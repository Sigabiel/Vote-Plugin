package de.sigabiel.vote.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.sigabiel.vote.VotePlugin;

public class ConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		// Check if joined player voted while he disconnected
		if (VotePlugin.getInstance().getWaitingForReward().containsKey(e.getPlayer().getUniqueId())) {

			// Handle the vote of the Player
			VotePlugin.getInstance().voted(
					VotePlugin.getInstance().getWaitingForReward().get(e.getPlayer().getUniqueId()), e.getPlayer());

			// Remove the Player from the List so the Player wont get a reward everytime he joined
			VotePlugin.getInstance().getWaitingForReward().remove(e.getPlayer().getUniqueId());
		}
	}

}
