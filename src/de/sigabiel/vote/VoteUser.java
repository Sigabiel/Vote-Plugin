package de.sigabiel.vote;

import java.util.ArrayList;
import java.util.UUID;

import de.sigabiel.vote.voteservice.VoteService;

public class VoteUser {

	private UUID uuid;
	private String name;
	private int minutes;
	private ArrayList<VoteService> voted = new ArrayList<>();

	public VoteUser(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	public UUID getUUID() {
		return uuid;
	}

	public ArrayList<VoteService> getVoted() {
		return voted;
	}

	public String getName() {
		return name;
	}

	public int getMinutes() {
		return minutes;
	}

	public void addMinute() {
		minutes++;
	}

	public void resetMinutes() {
		minutes = 0;
	}

}
