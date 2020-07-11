package ktbyte.assistant.app.toDoList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ktbyte.assistant.app.Action;
import ktbyte.assistant.app.App;

public class ToDoListApp extends App{

	static ArrayList<String> tasks = new ArrayList<String>();

	static File m_taskFile;

	public ToDoListApp() {
		ToDoListApp.m_taskFile = new File("ToDoList");
		//String absolutePath = m_taskFile.getAbsolutePath();
		if (ToDoListApp.m_taskFile.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(ToDoListApp.m_taskFile));

				String st;

				while ((st = br.readLine()) != null) {
					ToDoListApp.tasks.add(st);
				}
				br.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<Action> getActions(){
		return Arrays.asList(new AddAction(), new RemoveAction(), new DisplayAction());
	}

}
