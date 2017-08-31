package database;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ArtifactDAO implements DAO {

    private final String FILEPATH = "database/artifacts/";

    public ArtifactDAO() {
    }

    public Artifact load(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + id + ".txt"))) {
            Artifact artifact = new Artifact();
            String rId = br.readline();
            String rName = br.readline();
            String rDescription = br.readline();
            String rValue = br.readline();
            String rType = br.readline();

            if (rId.startswith("ID: ")) {
                artifact.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rName.startswith("NAME: ")) {
                artifact.setLogin(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rDescription.startswith("DESCRIPTION: ")) {
                artifact.setDescription(rDescription.substring("DESCRIPTION: ".length()));
            }
            else {
                return null;
            }
            if (rValue.startswith("VALUE: ")) {
                artifact.setValue(Integer.parseInt(rValue.substring("VALUE: ".length())));
            }
            else {
                return null;
            }
            if (rType.startswith("TYPE: ")) {
                artifact.setType(rType.substring("TYPE: ".length()));
            }
            else {
                return null;
            }
            return artifact;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    
}
