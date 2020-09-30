package de.sigabiel.vote.voteservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VoteManager {

	/**
	 * 
	 * Checks if the player has voted for the specific service
	 * 
	 * @param service Service which should be checked
	 * @param name    Name of the player that should be checked
	 * @return returns if the player voted and dind't claim it
	 */
	public boolean hasVotedAndNotClaimed(VoteService service, String name) {

		// Get content of the Checkwebsite using the link from the enum
		String content = getContent(String.format(service.getCheckVoteURL(), service.getKey(), name));
		boolean hasVoted = content.equals("1");

		if (hasVoted) {
			// Player voted so the server is claiming the vote
			claimVote(service, name);
		}

		return hasVoted;

	}

	/**
	 * Calls the website of the service to claim the vote.
	 * 
	 * @param service Service which the vote should be claimed on
	 * @param name    Name of the Player
	 * 
	 * 
	 */
	private void claimVote(VoteService service, String name) {

		// Generate the claim link by using the service and name parameter
		String link = String.format(service.getClaimVoteURL(), service.getKey(), name);

		// Call the Website
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();
			con.setConnectTimeout(5000);
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

			con.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get page content
	 * 
	 * @param link The link the method should get the content of
	 * @return returns the content of the link as a String
	 */
	private String getContent(String link) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();
			con.setConnectTimeout(5000);
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

			con.connect();

			Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			while (scanner.hasNext()) {
				sb.append(scanner.next());
			}

			String out = sb.toString();

			return out;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
