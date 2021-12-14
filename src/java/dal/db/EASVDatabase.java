package dal.db;

import be.SongModel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private void execute(String sql)
    {
        try
        {
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet query(String sql)
    {
        try {
            Statement statement = dataSource.getConnection().createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Add song to SQL database table.
     */
    public void addSong(SongModel song)
    {
        String sql = """
                INSERT INTO Songs (title, artists, duration, source, filepath, genre, album)
                VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')
                """.formatted(song.getTitle(), song.getArtists(), song.getDuration(), song.getTag(), song.getLocation(), song.getGenre(), song.getAlbum());

       this.execute(sql);
    }

    /**
     * Add song to SQL database table.
      */
    public void addSong(String songName, String art, int dura, String sauce, String fpath, String genre, String album)
    {
        String sql = "INSERT INTO Songs (title, artists, duration, source, filepath, genre, album) VALUES ('" + songName + "', '" + art + "', '" + dura + "', '" + sauce + "', '" + fpath + "', '" + genre + "', '" + album + "')";
        this.execute(sql);
    }


    /**
     * Remove Songs from SQL database.
      */
    public void removeSong(int id)
    {
        this.execute("DELETE FROM Songs WHERE id = " + id);
    }

    public void removeSong(String songName)
    {
        this.execute("DELETE FROM Songs WHERE title LIKE '%" + songName + "%'");
    }

    /**
     * Getters from the SQL database.
     */
    public ObservableList<SongModel> getAllSongs() {return getAllSongs("SELECT * FROM Songs"); }

    public ObservableList<SongModel> getAllSongs(String sql)
    {
        try
        {
            ObservableList<SongModel> songs = FXCollections.observableArrayList();

            int songId, duration;
            String songTitle, songArtist, songLocation, songAlbum, songGenre, source;

            ResultSet result = this.query(sql);

            while (result.next())
            {
                songTitle = result.getString("title");
                songId = result.getInt("id");
                duration = result.getInt("duration");
                songArtist = result.getString("artists");
                songLocation = result.getString("filepath");
                songGenre =  result.getString("genre");
                songAlbum = result.getString("album");
                source = result.getString("source");

                songs.add(new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, duration, source, songLocation));
            }

            return songs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());

            // return empty array - garentee not null
            return FXCollections.observableArrayList();
        }
    }

 //TODO: Make methods applicable for Title and Artist which takes two parameters.

    public ListProperty<SongModel> filterEqualsParameter(String filterType, String filterType2, String filterParameter){
        String sql = null;

        if (!filterType2.equals("NONE"))
        {
            sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%' " +
                    "OR " + filterType2 + " LIKE '%" + filterParameter + "%'";
        }
        else
        {
            sql = "SELECT * FROM dbo.Songs WHERE " + filterType + " LIKE '%" + filterParameter + "%'";
        }

        return new SimpleListProperty<>(this.getAllSongs(sql));
    }


    /** Select every value in the wanted column and returns it as a list of Strings.
     * @param columnName The column to select from. The filter is determined by
     * the user in the Combobox Filter.
     * @return
     */
    public List<String> getUniqueValuesIn(String columnName)
    {
        try
        {
        List<String> searchableEntities = new ArrayList<>();
            ResultSet result = this.query("SELECT DISTINCT " + columnName + " FROM dbo.Songs ");

            while (result.next())
            {
                String chosenFilter = result.getString(1);
                if (chosenFilter == null || chosenFilter.isEmpty()){
                    chosenFilter = "N/A";
                }
                searchableEntities.add(chosenFilter);
            }

            return searchableEntities;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            // return empty array - garentee not null
            return new ArrayList<>();
        }
    }
}
