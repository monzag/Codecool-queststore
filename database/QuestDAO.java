package database;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class QuestDAO implements DAO {

    private final String FILEPATH = "database/quests/";

    public QuestDAO() {
    }

    public Quest load(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + id + ".txt"))) {
            Quest quest = new Quest();
            String rId = br.readLine();
            String rName = br.readLine();
            String rDescription = br.readLine();
            String rReward = br.readLine();

            if (rId.startsWith("ID: ")) {
                quest.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rName.startsWith("NAME: ")) {
                quest.setLogin(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rDescription.startsWith("DESCRIPTION: ")) {
                quest.setDescription(rDescription.substring("DESCRIPTION: ".length()));
            }
            else {
                return null;
            }
            if (rReward.startsWith("REWARD: ")) {
                quest.setReward(Integer.parseInt(rReward.substring("REWARD: ".length())));
            }
            else {
                return null;
            }
            return quest;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public ArrayList<Quest> loadAll() {
        ArrayList<Quest> questList = new ArrayList<>();

        File[] files = new File(FILEPATH).listFiles();

        for (File file : files) {
            int dotPosition = file.getName().lastIndexOf(".");
            questList.add(load(file.getName().substring(0, dotPosition)));
        }
        return questList;
    }

    public void save(Quest quest) {
        try (FileWriter fw = new FileWriter(FILEPATH + quest.getId() + ".txt")) {
            fw.write("ID: " + quest.getId());
            fw.write("NAME: " + quest.getName());
            fw.write("DESCRIPTION: " + quest.getDescription());
            fw.write("REWARD: " + quest.getReward());
        } catch (IOException e) {
            System.out.println("Filepath not found.");
        }
    }

}
