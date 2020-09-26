package de.sigabiel.vote.voteservice;

public enum VoteServices {
	MINECRAFTMP("YOURKEY", "https://minecraft-mp.com/server/YOURESERVER",
			"https://minecraft-mp.com/api/?object=votes&element=claim&key=%s&username=%s",
			"https://minecraft-mp.com/api/?action=post&object=votes&element=claim&key=%s&username=%s", true),
	MINECRAFTSERVEREU("YOURKEY", "https://minecraft-server.eu/server/YOURESERVER",
			"https://minecraft-server.eu/api/v1/?object=votes&element=claim&key=%s&username=%s",
			"https://minecraft-server.eu/api/v1/?action=post&object=votes&element=claim&key=%s&username=%s", true);

	// Service key which allows the server get all the vote information
	private final String key;

	// URL's for displaying, checking and claiming the vote
	private final String voteURL;
	private final String checkVoteURL;
	private final String claimVoteURL;

	// toggles if the service is active or not
	private final boolean active;

	private VoteServices(String key, String voteURL, String checkVoteURL, String claimVoteURL, boolean active) {
		this.key = key;
		this.voteURL = voteURL;
		this.checkVoteURL = checkVoteURL;
		this.claimVoteURL = claimVoteURL;
		this.active = active;
	}

	public String getKey() {
		return key;
	}

	public boolean isActive() {
		return active;
	}

	public String getVoteURL() {
		return voteURL;
	}

	public String getClaimVoteURL() {
		return claimVoteURL;
	}

	public String getCheckVoteURL() {
		return checkVoteURL;
	}
}
