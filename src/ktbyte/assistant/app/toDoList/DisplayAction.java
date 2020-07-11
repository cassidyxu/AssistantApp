package ktbyte.assistant.app.toDoList;

import ktbyte.assistant.Assistant;
import ktbyte.assistant.app.Action;
import ktbyte.assistant.app.Response;

public class DisplayAction extends Action{

	String[] keywords = {"display", "show"};
	double[] scores = {3, 3};

	public void doCommand(String command) {
		Assistant assistant = Assistant.getInstance();

		String[] arr = command.split(" ");
		String firstWord = arr[0];

		switch(firstWord){
		case "display":
		case "show":
			assistant.displayItem(new Response("To-Do List: " + ToDoListApp.tasks));
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
