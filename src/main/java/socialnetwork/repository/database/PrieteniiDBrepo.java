package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.memory.InMemoryRepository;


import java.sql.*;
import java.time.LocalDate;


public class PrieteniiDBrepo extends InMemoryRepository<Tuple<Long,Long>, Prietenie> {
    private String url;
    private String username;
    private String password;

    public PrieteniiDBrepo(String url, String username, String password, Validator<Prietenie> validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        loadData();
    }

    void loadData(){
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement statementSelectUsers = connection.prepareStatement("SELECT * FROM prietenii");
                ResultSet resultSet = statementSelectUsers.executeQuery()
        ){
            while(resultSet.next()){
                Long id1 = resultSet.getLong("id_user1");
                Long id2 = resultSet.getLong("id_user2");
                LocalDate ld = resultSet.getDate("data").toLocalDate();
                Prietenie prietenie = new Prietenie(ld);
                prietenie.setId(new Tuple<Long,Long>(id1,id2));
                try {
                    super.save(prietenie);
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
    public Prietenie save(Prietenie entity) {
        super.validator.validate(entity);
        try(
                Connection connection = DriverManager.getConnection(url,username,password);

        ){
            String sqlInsert = "INSERT INTO prietenii(id_user1,id_user2,data) VALUES (?, ?, ?)";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setLong(1, entity.getId().getLeft());
            statementInsert.setLong(2, entity.getId().getRight());
            statementInsert.setObject(3, entity.getDate());
            statementInsert.executeUpdate();
            return super.save(entity);
        }
        catch (SQLException ex){
            return  entity;
        }

    }

    @Override
    public Prietenie delete(Tuple<Long, Long> id) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            try {
                String sqlDelete= "DELETE FROM prietenii WHERE id_user1 = ? AND id_user2 = ?";
                PreparedStatement statementDelete = connection.prepareStatement(sqlDelete);
                statementDelete.setLong(1,id.getLeft());
                statementDelete.setLong(2,id.getRight());
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
