package database;

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
            String rId = br.readline();
            String rName = br.readline();
            String rDescription = br.readline();
            String rReward = br.readline();

            if (rId.startswith("ID: ")) {
                quest.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rName.startswith("NAME: ")) {
                quest.setLogin(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rDescription.startswith("DESCRIPTION: ")) {
                quest.setPassword(rDescription.substring("DESCRIPTION: ".length()));
            }
            else {
                return null;
            }
            if (rReward.startswith("REWARD: ")) {
                quest.setPassword(rReward.substring("REWARD: ".length()));
            }
            else {
                return null;
            }
            return quest;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
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
