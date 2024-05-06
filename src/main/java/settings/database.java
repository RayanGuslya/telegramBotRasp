package settings;

import org.glassfish.grizzly.streams.StreamInput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class    database {
    private Properties properties = new Properties();
    public String getUsernameById(int id) {
        String username = null;

        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(properties.getProperty("database.url"),
                properties.getProperty("database.login"), properties.getProperty("database.pass"))) {
            String sqlQuery = "SELECT name FROM telegrambot.usersbot WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    username = resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }

        return username;
    }

//    public String getUsers(String sqlGet){
//        String username = null;
//        try(InputStream input = new FileInputStream("src/main/resources/application.properties")){
//            properties.load(input);
//        }catch (FileNotFoundException e){
//            throw new RuntimeException(e);
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//        try {
//            Connection connection = DriverManager.getConnection(properties.getProperty("database.url"),
//                    properties.getProperty("database.login"),properties.getProperty("database.pass"));
//            try {
//
//                Statement statement = connection.createStatement();
//                //statement.executeUpdate(add);
//                ResultSet resultSet = statement.executeQuery(sqlGet);
//                if (!connection.isClosed()){
//                    System.out.println("бд открыто");
//                    while (resultSet.next()){
//                        System.out.println(resultSet.getString("name"));
//                        return resultSet.getString("name");
//                    }
//
//                }
//            }catch (SQLException e) {//обрабатывает повторяющих пользователей
//                System.out.println("повторяющий пользователь");
//            }
//
//
//            connection.close();
//            if (connection.isClosed()){
//                System.out.println("база данных закрыта");
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//            System.out.println("error");
//        }
//        return username != null ? username : "нету";
//    }


    public void settingsDatabase(String add){
        try(InputStream input = new FileInputStream("src/main/resources/application.properties")){
            properties.load(input);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(properties.getProperty("database.url"),
                    properties.getProperty("database.login"),properties.getProperty("database.pass"));
            try {

            Statement statement = connection.createStatement();
            statement.executeUpdate(add);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM telegrambot.usersbot");
            if (!connection.isClosed()){
                    System.out.println("бд открыто");
                    while (resultSet.next()){
//                        System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " "
//                                + resultSet.getString(3) + " " +resultSet.getString(4));
                    }

                }
            }catch (SQLException e) {//обрабатывает повторяющих пользователей
                System.out.println("повторяющий пользователь");
            }


            connection.close();
            if (connection.isClosed()){
                System.out.println("база данных закрыта");
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("error");
        }
    }
}
