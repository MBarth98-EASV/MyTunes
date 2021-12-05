package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EASVDatabase {
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

        try (Connection connection = databaseConnector.getConnection()) {
            System.out.println("Is it open? " + !connection.isClosed());
        }
    }


    /**
     * Add song to SQL database table.
      */
    public void addSong(String table, String songName, String art, int dura, String sauce, String fpath)
    {

        String sql = "INSERT INTO " + table + " (title, artists, duration, source, filepath) VALUES ('" + songName + "', '" + art + "', '" + dura + "', '" + sauce + "', '" + fpath + "')";

        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Remove Songs from SQL database.
      */
    public void removeSong(String table, int id)
    {
        try {
            String sql = "DELETE FROM " + table + " WHERE id LIKE '%" + id + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSong(String table, String songName)
    {
        try {
            String sql = "DELETE FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters from the SQL database.
     */
    public int getSongIDFromName(String songName, String table)
    {
        try {
            int songID;

            String sql = "SELECT * FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            songID = result.getInt("id");

            return songID;
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getSongNameFromID(int songID, String table)
    {
        try {
            String songName;

            String sql = "SELECT * FROM " + table + " WHERE id LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            songName = result.getString("title");

            return songName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getArtists(int songID, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE id LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String artists = result.getString("artists");

            return artists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getArtists(String songName, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String artists = result.getString("artists");

            return artists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFilePath(String songName, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFilePath(int songID, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE title LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList getSongList(String table)
    {
        try
        {
            ArrayList<String> songsList = new ArrayList<>();

            String sql = "SELECT * FROM " + table;

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                songsList.add(String.valueOf(result));
            }

            return songsList;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updater for the SQL database.
     */

    public void updateSong(String table, String column, String condition)
    {
        try {
            String sql = "SELECT * FROM " + table + " SET " + column + " WHERE LIKE '%" + condition + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSong(String table, String column, int condition)
    {
        try {
            String sql = "SELECT * FROM " + table + " SET " + column + " WHERE LIKE '%" + condition + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Database sorter
     */

    public void sortList(String table, String column, String order)
    {
        try
        {
        String sql = "SELECT * FROM " + table + " ORDER BY " + column + order;

        Statement statement = dataSource.getConnection().createStatement();
        statement.executeQuery(sql);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}