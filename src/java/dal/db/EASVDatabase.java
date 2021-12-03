package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EASVDatabase
{
    private SQLServerDataSource dataSource;

    public EASVDatabase()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("CSe21A_29_MyTunes_3");
        dataSource.setUser("CSe21A_29");
        dataSource.setPassword("itsikkerhed");
        dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException
    {
        EASVDatabase databaseConnector = new EASVDatabase();

        try (Connection connection = databaseConnector.getConnection())
        {
            System.out.println("Is it open? " + !connection.isClosed());
        }
    }

    public int getSongIDFromName(String songName) {
        try
        {
            int songID;

            String sql = "SELECT * FROM Songs WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            songID = result.getInt("id");

            return songID;
        }
        catch (SQLException e)
        {
            return 0;
        }
    }

    public String getSongNameFromID(int songID)
    {
        try
        {
            String songName;

            String sql = "SELECT * FROM Songs WHERE id LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            songName = result.getString("title");

            return songName;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "ID not found";
        }
    }

     public String getArtists(int songID)
     {
         try
         {
             String sql = "SELECT * FROM Songs WHERE id LIKE '%" + songID + "%'";

             Statement statement = dataSource.getConnection().createStatement();
             ResultSet result = statement.executeQuery(sql);

             result.next();
             String artists = result.getString("artists");

             return artists;
         }
         catch (SQLException e)
         {
             e.printStackTrace();
             return "";
         }
     }

    public String getArtists(String songName)
    {
        try
        {
            String sql = "SELECT * FROM Songs WHERE title LIKE '%" + songName + "%'";

             Statement statement = dataSource.getConnection().createStatement();
             ResultSet result = statement.executeQuery(sql);

             result.next();
             String artists = result.getString("artists");

             return artists;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }


    public String getFilePath(String songName)
    {
        try {
            String sql = "SELECT * FROM Songs WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFilePath(int songID)
    {
        try {
            String sql = "SELECT * FROM Songs WHERE title LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

}
