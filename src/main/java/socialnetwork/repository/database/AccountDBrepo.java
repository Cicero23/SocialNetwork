package socialnetwork.repository.database;


import socialnetwork.domain.Account;
import socialnetwork.domain.Invitatie;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDate;

public class AccountDBrepo{
    private String url;
    private String username;
    private String password;


    public AccountDBrepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }



    public Account findOne(String username1,String password1) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){
            String sqlSelect = "SELECT * FROM accounts WHERE username = ? AND password = ?";
            PreparedStatement statementSelectAccount = connection.prepareStatement(sqlSelect);
            statementSelectAccount.setString(1,username1);
            statementSelectAccount.setString(2,password1);
            ResultSet resultSet = statementSelectAccount.executeQuery();
            if(resultSet.next()){
                Long id1 = resultSet.getLong("id_account");
                Long is_user = resultSet.getLong("id_user");
                Account account = new Account(username1,password1,is_user);
                account.setId(id1);
                return account;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /*
    public AccountDBrepo save(AccountDBrepo entity) {
        try(
                Connection connection = DriverManager.getConnection(url,username,password);
        ){

            String sqlInsert = "INSERT INTO account(username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
            statementInsert.setLong(1, entity.getId_from());
            statementInsert.setLong(2, entity.getId_to());
            statementInsert.setObject(3, entity.getData());
            statementInsert.setLong(4, entity.getStare());
            statementInsert.executeUpdate();
            return null;

        }
        catch (SQLException ex){
            return entity;
        }
    }
    */

}
