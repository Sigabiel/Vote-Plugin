package de.sigabiel.vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.sigabiel.vote.commands.VoteCommand;
import de.sigabiel.vote.listener.ConnectionListener;
import de.sigabiel.vote.voteservice.GlobalVoteTimer;
import de.sigabiel.vote.voteservice.VoteManager;
import de.sigabiel.vote.voteservice.VoteService;

public class VotePlugin extends JavaPlugin {

	public static String PREFIX = "§7[§cSERVER§7] >> ";
	public static int MAX_MINUTES = 5;

	private static VotePlugin instance;

	private VoteManager voteManager;

	// List which stores all the players that voted but didn't get their reward
	private HashMap<UUID, VoteService> waitingForReward = new HashMap<>();

	// Stores voteservices from config
	private ArrayList<VoteService> voteServices = new ArrayList<>();

	private HashMap<UUID, VoteUser> voteUser = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		// Load config

		saveDefaultConfig();

		if (getConfig().contains("prefix"))
			PREFIX = getConfig().getString("prefix");

		if (getConfig().contains("maxminutes"))
			MAX_MINUTES = getConfig().getInt("maxminutes");

		if (getConfig().isConfigurationSection("service.")) {
			for (String name : getConfig().getConfigurationSection("service.").getKeys(false)) {

				String path = "service." + name + ".";

				String key = getConfig().getString(path + "key");
				String voteURL = getConfig().getString(path + "voteurl");
				String claimedVoteURL = getConfig().getString(path + "claimedvoteurl");
				String checkVoteURL = getConfig().getString(path + "checkvoteurl");

				voteServices.add(new VoteService(name, key, voteURL, checkVoteURL, claimedVoteURL));
			}
		}

		// Register event and command

		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new ConnectionListener(), this);

		getCommand("vote").setExecutor(new VoteCommand());

		voteManager = new VoteManager();

		// start global vote timer
		getServer().getScheduler().runTaskTimerAsynchronously(this, new GlobalVoteTimer(this), 100, 1500);
	}

	public void voted(VoteService service, Player p) {
		Bukkit.getPluginManager().callEvent(new PlayerVoteEvent(p, service));
	}

	public HashMap<UUID, VoteService> getWaitingForReward() {
		return waitingForReward;
	}

	public ArrayList<VoteService> getVoteServices() {
		return voteServices;
	}

	public VoteManager getVoteManager() {
		return voteManager;
	}

	public HashMap<UUID, VoteUser> getVoteUser() {
		return voteUser;
	}

	public static VotePlugin getInstance() {
		return instance;
	}

}
