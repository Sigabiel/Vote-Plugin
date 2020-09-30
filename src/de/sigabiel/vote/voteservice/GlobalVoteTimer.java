package de.sigabiel.vote.voteservice;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.sigabiel.vote.VotePlugin;
import de.sigabiel.vote.VoteUser;

public class GlobalVoteTimer implements Runnable {

	private VotePlugin plugin;

	private int day;
	private Calendar calendar;

	public GlobalVoteTimer(VotePlugin plugin) {
		this.plugin = plugin;

		// saves current day
		calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	public void run() {

		calendar.setTimeInMillis(System.currentTimeMillis());
		int today = calendar.get(Calendar.DAY_OF_WEEK);

		// checks if the day changed
		if (day != today) {
			day = today;

			// resets all user data
			plugin.getVoteUser().clear();
		}

		for (VoteUser user : plugin.getVoteUser().values()) {

			if (user.getMinutes() >= VotePlugin.MAX_MINUTES)
				continue;

			user.addMinute();

			for (VoteService service : plugin.getVoteServices()) {

				// checks if player already voted for the service
				if (user.getVoted().contains(service))
					continue;

				if (plugin.getVoteManager().hasVotedAndNotClaimed(service, user.getName())) {

					Player p = Bukkit.getPlayer(user.getUUID());

					// Check if Player is online
					if (p != null) {

						// Give the Player his reward
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								plugin.voted(service, p);
							}
						});

						user.getVoted().add(service);
					} else

						// Add the Player to the waiting-to-rejoin queue
						plugin.getWaitingForReward().put(user.getUUID(), service);
				}

			}

		}

	}

}
