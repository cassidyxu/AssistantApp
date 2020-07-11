package ktbyte.assistant.app.wiki;

import ktbyte.assistant.Assistant;
import ktbyte.assistant.app.Action;
import ktbyte.assistant.app.Response;
import ktbyte.assistant.app.wiki.WikiApp;

public class HistoryAction extends Action{

	String[] keywords = {"history"};
	double[] scores = {3};

	public void doCommand(String command) {
		Assistant assistant = Assistant.getInstance();

		String[] arr = command.split(" ");
		String firstWord = arr[0];

		switch(firstWord){
		case "history":
			assistant.displayItem(new Response("search history: " + WikiApp.history));
			break;
		default:
			break;
		}

	}

	public double getLikelihood(String command) {
		double score = 0;
		for(int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			if(command.contains(keyword)) {
				score += scores[i];
			}
		}
		return score;
	}

}
