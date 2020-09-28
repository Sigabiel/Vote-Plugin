package de.sigabiel.vote;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.sigabiel.vote.voteservice.VoteService;

public class PlayerVoteEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled;

	private Player player;
	private VoteService service;

	public PlayerVoteEvent(Player player, VoteService service) {
		this.player = player;
		this.service = service;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public VoteService getService() {
		return service;
	}

	public Player getPlayer() {
		return player;
	}

}
