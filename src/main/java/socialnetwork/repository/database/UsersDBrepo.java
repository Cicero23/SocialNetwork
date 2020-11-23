package socialnetwork.repository.database;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository;

import java.sql.*;

public class UsersDBrepo extends InMemoryRepository<Long,Utilizator> {
    private String url;
    private String username;
    private String password;


    public UsersDBrepo(String url, String username, String password, Validator<Utilizator> validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        loadData();
    }

    void loadData(){
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement statementSelectUsers = connection.prepareStatement("SELECT * FROM users");
                ResultSet resultSet = statementSelectUsers.executeQuery()
        ){
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                try {
                    super.save(utilizator);
                }
                catch (ValidationException e){
                    continue;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Utilizator save(Utilizator entity) {
        super.validator.validate(entity);
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            try {
                String sqlInsert = "INSERT INTO users(first_name, last_name) VALUES (?, ?)";
                PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setString(1, entity.getFirstName());
                statementInsert.setString(2, entity.getLastName());
                statementInsert.executeUpdate();
                String sqlSelectId = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
                PreparedStatement statementSelectId = connection.prepareStatement(sqlSelectId);
                statementSelectId.setString(1, entity.getFirstName());
                statementSelectId.setString(2, entity.getLastName());
                ResultSet resultSet = statementSelectId.executeQuery();
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    entity.setId(id);
                    Utilizator e = super.save(entity);
                    return e;
                }

                return entity;
            }
            catch (SQLException e) {
                return entity;

            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public Utilizator delete(Long id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            try {
                String sqlDelete = "DELETE FROM users WHERE id = ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setLong(1, id);
                statementDelete.executeUpdate();
                return super.delete(id);
            }
            catch (SQLException e){
                return null;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }


}
