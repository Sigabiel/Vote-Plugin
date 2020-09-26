package de.sigabiel.vote;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.sigabiel.vote.commands.VoteCommand;
import de.sigabiel.vote.listener.ConnectionListener;
import de.sigabiel.vote.voteservice.VoteManager;

public class VotePlugin extends JavaPlugin {

	public static String PREFIX = "§7[§cSERVER§7] >> ";

	private static VotePlugin instance;

	private VoteManager voteManager;

	// List which stores all the players that voted but didn't get their reward
	private ArrayList<UUID> waitingForReward = new ArrayList<>();

	@Override
	public void onEnable() {
		instance = this;

		if (getConfig().contains("prefix"))
			PREFIX = getConfig().getString("prefix");

		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new ConnectionListener(), this);

		getCommand("vote").setExecutor(new VoteCommand());

		voteManager = new VoteManager();
	}

	@Override
	public void onDisable() {
		save();
	}

	public void voted(Player p) {
		// TODO Write what should happen when the Player voted
		// (This function is called everytime the player voted on any platform)

		p.sendMessage(PREFIX + "You voted");
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
	}

	public void save() {
		getConfig().set("prefix", PREFIX);
		saveConfig();
	}

	public ArrayList<UUID> getWaitingForReward() {
		return waitingForReward;
	}

	public VoteManager getVoteManager() {
		return voteManager;
	}

	public static VotePlugin getInstance() {
		return instance;
	}

}
