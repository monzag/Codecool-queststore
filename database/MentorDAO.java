public class MentorDAO implements DAO {

    private final String FILEPATH = "database/mentors/";

    public MentorDAO(String basePath) {
        this.basePath = basePath
    }

    public Mentor load(String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + login + ".txt"))) {
            Mentor mentor = new Mentor();
            String rId = br.readline();
            String rLogin = br.readline();
            String rPassword = br.readline();
            String rEmail = br.readline();
            String rName = br.readline();
            String rSurname = br.readline();
            String rClassId = br.readline();

            if (rId.startswith("ID: ")) {
                mentor.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rLogin.startswith("LOGIN: ")) {
                mentor.setLogin(rLogin.substring("LOGIN: ".length()));
            }
            else {
                return null;
            }
            if (rPassword.startswith("PASSWORD: ")) {
                mentor.setPassword(rPassword.substring("PASSWORD: ".length()));
            }
            else {
                return null;
            }
            if (rEmail.startswith("EMAIL: ")) {
                mentor.setEmail(rEmail.substring("EMAIL: ".length()));
            }
            else {
                return null;
            }
            if (rName.startswith("NAME: ")) {
                mentor.setId(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rSurname.startswith("SURNAME: ")) {
                mentor.setSurname(rId.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            if (rClassId.startswith("CLASSID: ")) {
                mentor.setClassId(rClassId.substring("CLASSID: ".length()));
            }
            else {
                return null;
            }
            return mentor;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void save(Storable item) {
        try (FileWriter fw = new FileWriter(FILEPATH + item.getLogin() + ".txt")) {
            fw.write("ID: " + item.getId());
            fw.write("LOGIN: " + item.getLogin());
            fw.write("PASSWORD: " + item.getPassword());
            fw.write("EMAIL: " + item.getEmail());
            fw.write("NAME: " + item.getName());
            fw.write("SURNAME: " + item.getSurname());
            fw.write("CLASSID: " + item.getClassId());
        } catch (IOException) {
            System.out.println("Filepath not found.");
        }
    }
}
