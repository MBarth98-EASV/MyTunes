package dal.db;

import be.SongModel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        }
        catch (SQLException e) {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSong(String table, String songName)
    {
        try {
            String sql = "DELETE FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters from the SQL database.
     */

    public int getSongIDFromName(String songName, String table)
    {
        try
        {
            int songID;

            String sql = "SELECT * FROM " + table + " WHERE title LIKE '%" + songName + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            songID = result.getInt("id");

            return songID;
        }
        catch (SQLException e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNextSongPath(int id, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE id = (SELECT MIN(id) FROM Songs WHERE id > " + id + ")";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getFilePath(int songID, String table)
    {
        try {
            String sql = "SELECT * FROM " + table + " WHERE id LIKE '%" + songID + "%'";

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String filepath = result.getString("filepath");

            return filepath;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
  
    public List<SongModel> getAllSongs()
    {
        String sql = "SELECT * FROM dbo.Songs";
        List<SongModel> songs = new ArrayList<>();

        String songTitle;
        int songId;
        String songArtist;
        String songLocation;
        String songAlbum;
        String songGenre;

        try
        {
            System.out.println("trying to get all songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");

                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, 0, "local", songLocation));
            }

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            // return empty array - garentee not null
            return new ArrayList<>();
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
        }
        catch (Exception e) {
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

 //TODO: Make methods applicable for Title and Artist which takes two parameters.

    public List<SongModel> filterEqualsParameter(String filterType, String filterParameter){
        String sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE " + filterParameter;
        List<SongModel> songs = new ArrayList<>();

        String songTitle;
        int songId;
        String songArtist;
        String songLocation;
        String songGenre;
        String songAlbum;

        try
        {
            System.out.println("trying to get all filtered songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");

                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, 0, "local", songLocation));
            }

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }


    /** Select every value in the wanted column and returns it as a list of Strings.
     * @param filterType The column to select from. The filter is determined by
     * the user in the Combobox Filter.
     * @return
     */
    public List<String> allAvailableByParameter(String filterType){
        String sql = "SELECT " + filterType + " FROM dbo.Songs " ;
        List<String> SearchEntryFilter = new ArrayList<>();

        String chosenFilter;

        try
        {
            System.out.println("trying to get all songs from the database");

            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                chosenFilter = result.getString(filterType);
                SearchEntryFilter.add(chosenFilter);
            }

            return SearchEntryFilter;
        }
        catch (Exception ex)
        {
            System.out.println("database is not available");
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }


}
