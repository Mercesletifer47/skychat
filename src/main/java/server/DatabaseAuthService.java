package server;

public class DatabaseAuthService implements AuthService{
    private Database database;
    public DatabaseAuthService(Database database) {
        this.database = database;
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return database.getNickname(login, password);
    }
    @Override
    public boolean registration(String login, String password, String nickname) {
        if (database.getField("login",login) || database.getField("nickname", nickname)) {
            return false;
        }
        database.addUser(login, password, nickname);
        return true;
    }
}