package ktbyte.assistant.app.toDoList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ktbyte.assistant.app.Action;

public class AddAction extends Action{

	String[] keywords = {"add", "need"};
	double[] scores = {3, 2};

	public void doCommand(String command) {

		String task = "";
		List<String> keywordsList = Arrays.asList(keywords);
		for(String word : command.split(" ")) {
			if(!keywordsList.contains(word)) {
				task += word + " ";
			}
		}

		String[] arr = command.split(" ");
		String firstWord = arr[0];

		switch(firstWord){
		case "add":
		case "need":
			ToDoListApp.tasks.add(task);
			updateFile();
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

	private void updateFile() {
		ToDoListApp.m_taskFile.delete();   	 
		try {
			ToDoListApp.m_taskFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(ToDoListApp.m_taskFile));
			Iterator<String> iter = ToDoListApp.tasks.iterator();
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
