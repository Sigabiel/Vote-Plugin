package de.sigabiel.vote.voteservice;

public class VoteService {

	private String name;

	// Service key which allows the server get all the vote information
	private String key;

	// URL's for displaying, checking and claiming the vote
	private String voteURL;
	private String checkVoteURL;
	private String claimVoteURL;

	public VoteService(String name, String key, String voteURL, String checkVoteURL, String claimVoteURL) {
		this.name = name;
		this.key = key;
		this.voteURL = voteURL;
		this.checkVoteURL = checkVoteURL;
		this.claimVoteURL = claimVoteURL;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public String getVoteURL() {
		return voteURL;
	}

	public String getCheckVoteURL() {
		return checkVoteURL;
	}

	public String getClaimVoteURL() {
		return claimVoteURL;
	}

}
