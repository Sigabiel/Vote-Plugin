package de.sigabiel.vote.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.sigabiel.vote.VotePlugin;
import de.sigabiel.vote.VoteUser;
import de.sigabiel.vote.voteservice.VoteService;

public class VoteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {

			Player p = (Player) sender;

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
			p.sendMessage("§7----------------------------------");
			p.sendMessage("§7Vote with the following links to get a Reward");
			p.sendMessage(" ");

			// List every direct link to the Vote Website
			for (VoteService service : VotePlugin.getInstance().getVoteServices()) {
				p.sendMessage("§7>> " + service.getVoteURL());
			}

			if (!VotePlugin.getInstance().getVoteUser().containsKey(p.getUniqueId())) {
				VotePlugin.getInstance().getVoteUser().put(p.getUniqueId(), new VoteUser(p.getUniqueId(), p.getName()));
			}

			VotePlugin.getInstance().getVoteUser().get(p.getUniqueId()).resetMinutes();

		}
		return false;
	}

}
