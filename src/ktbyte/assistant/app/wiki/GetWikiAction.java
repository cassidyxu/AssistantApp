package ktbyte.assistant.app.wiki;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import ktbyte.assistant.Assistant;
import ktbyte.assistant.app.Action;
import ktbyte.assistant.app.Response;
import ktbyte.assistant.app.wiki.WikiApp;

public class GetWikiAction extends Action {

	String[] keywords = { "wiki", "search", "wikipedia" };
	double[] scores =   { 3, 3, 3 };

	@Override
	public void doCommand(String command) {

		//List<String> words = Arrays.asList(command.split(" "));
		//ArrayList<String> wordsArray = new ArrayList<String>(words);

		int index = command.indexOf(" ");


		HttpRequest req = null;
		//wordsArray.remove(0);
		String searchWord = command.substring(index+1);



		req = Unirest.get("https://en.wikipedia.org/w/api.php")
				.queryString("action", "query")
				.queryString("list", "search")
				.queryString("srsearch", searchWord)
				.queryString("format", "json");


		try {
			JsonNode node = req.asJson().getBody();
			System.out.println(node);
			handleResult(node, searchWord);
		} catch (UnirestException e) {
			System.out.println("request error occurred");
		}

	}

	@Override
	public double getLikelihood(String command) {
		double score = 0;
		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			if (command.contains(keyword)) {
				score += scores[i];
			}
		}
		return score;
	}

	private static void handleResult(JsonNode node, String searchWord) {

		Assistant assistant = Assistant.getInstance();

		JSONObject json = node.getObject();

		JSONObject query = json.optJSONObject("query");
		String description = query.optJSONArray("search").optJSONObject(0).optString("snippet");

		description = description.replaceAll("<span class=\"searchmatch\">", "");
		description = description.replaceAll("</span>", "");

		System.out.println(description);
		assistant.displayItem(
				new Response(searchWord + ": " + description));

		WikiApp.history.add(searchWord);
		updateFile();

	}

	private static void updateFile() {
		WikiApp.m_historyFile.delete();   	 
		try {
			WikiApp.m_historyFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(WikiApp.m_historyFile));
			Iterator<String> iter = WikiApp.history.iterator();
			while (iter.hasNext()) {
				bw.write(iter.next());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
