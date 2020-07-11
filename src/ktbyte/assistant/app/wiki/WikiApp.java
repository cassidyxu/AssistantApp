package ktbyte.assistant.app.wiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ktbyte.assistant.app.Action;
import ktbyte.assistant.app.App;
import ktbyte.assistant.app.wiki.WikiApp;

public class WikiApp extends App{

	static ArrayList<String> history = new ArrayList<String>();

	static File m_historyFile;

	public WikiApp() {
		WikiApp.m_historyFile = new File("History");
		//String absolutePath = m_taskFile.getAbsolutePath();
		if (WikiApp.m_historyFile.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(WikiApp.m_historyFile));

				String st;

				while ((st = br.readLine()) != null) {
					WikiApp.history.add(st);
				}
				br.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Action> getActions() {
		return Arrays.asList(new GetWikiAction(), new HistoryAction());
	}
}
