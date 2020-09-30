package de.sigabiel.vote.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.sigabiel.vote.VotePlugin;
import de.sigabiel.vote.voteservice.VoteService;

public class VoteCommand implements CommandExecutor {

	// Stores all players who are voting right now
	protected ArrayList<UUID> idle = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		// Check if the CommandExecutor is a Player
		if (sender instanceof Player) {

			// Send player introductions of how to vote
			Player p = (Player) sender;

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
			p.sendMessage("§7----------------------------------");
			p.sendMessage("§7Vote with the following links to get a Reward");
			p.sendMessage(" ");

			// List every direct link to the Vote Website
			for (VoteService service : VotePlugin.getInstance().getVoteServices()) {
				p.sendMessage("§7>> " + service.getVoteURL());
			}

			// Check if the server is already waiting for his vote
			if (idle.contains(p.getUniqueId())) {
				return false;
			}

			// Initialize a new timer instance of the player which waits for him to vote
			new VoteIdleTimer(idle, p.getUniqueId(), p.getName());

		}
		return false;
	}

	public class VoteIdleTimer {

		// duration counts how many times the server checked if the player voted.
		private int duration = 0;

		// Stores player data
		private UUID uuid;
		private String username;

		// Reference to the original idle list

		// Stores all VoteServices the server is checking for
		private ArrayList<VoteService> iteratingServices = new ArrayList<>();

		// Cache list
		private List<VoteService> deleteCache = new ArrayList<>();

		public VoteIdleTimer(ArrayList<UUID> idle, UUID uuid, String username) {
			this.uuid = uuid;
			this.username = username;

			startTimer();
		}

		private void startTimer() {

			// Loads every active service in the checking List
			for (VoteService service : VotePlugin.getInstance().getVoteServices()) {
				iteratingServices.add(service);
			}

			// Adding Player to the checking Queue
			idle.add(uuid);

			new BukkitRunnable() {

				@Override
				public void run() {

					deleteCache.clear();

					// Iterate through every active Service
					for (VoteService service : iteratingServices) {

						// Check if the current service wasn't claimed
						if (VotePlugin.getInstance().getVoteManager().hasVotedAndNotClaimed(service, username)) {
							Player p = Bukkit.getPlayer(uuid);

							// Check if Player is online
							if (p != null) {

								// Give the Player his reward
								VotePlugin.getInstance().getServer().getScheduler()
										.scheduleSyncDelayedTask(VotePlugin.getInstance(), new Runnable() {

											@Override
											public void run() {
												VotePlugin.getInstance().voted(service, p);

											}
										});

								deleteCache.add(service);
							} else

								// Add the Player to the waiting-to-rejoin queue
								VotePlugin.getInstance().getWaitingForReward().put(uuid, service);

						}
					}

					// Delete all services the player voted for from the service list
					for (VoteService service : deleteCache) {
						iteratingServices.remove(service);
					}

					// Check if the code was ran 5 or more times
					if (duration >= 5) {
						Player p = Bukkit.getPlayer(uuid);

						// check if Player is online
						if (p != null) {

							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
							p.sendMessage("");
							p.sendMessage(VotePlugin.PREFIX + "Time's over!");
							p.sendMessage(VotePlugin.PREFIX + "The Server is not waiting for your votes anymore");
							p.sendMessage(VotePlugin.PREFIX
									+ "If you're still trying to vote type /vote to reactivate the queue");
						}

						// stop the queue
						idle.remove(uuid);
						cancel();
					}

					duration++;

				}
			}.runTaskTimerAsynchronously(VotePlugin.getInstance(), 0, 1500); // Runs every minute
		}

	}

}
