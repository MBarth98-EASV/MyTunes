package dal.db;

import be.PlaylistModel;
import be.SongModel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.didion.jwnl.data.Exc;

import java.nio.file.Path;
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
            return null;
        }
    }

    private int getSongID(SongModel song)
    {
        String sql = """
                SELECT * FROM dbo.Songs
                where filepath = '%s'
                """.formatted(song.getLocation());

        try
        {
            ResultSet result = this.query(sql);
            result.next();
            return result.getInt("id");
        }
        catch (Exception ex)
        {
            return -1;
        }
    }

    /**
     * Add song and playlist to SQL database table.
     */
    public void addSong(SongModel song)
    {
        String sql = """
                INSERT INTO Songs (title, artists, duration, source, filepath, genre, album)
                VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')
                """.formatted(song.getTitle(), song.getArtists(), (int) song.getDuration().toSeconds(), song.getTag(), song.getLocation(), song.getGenre(), song.getAlbum());

       this.query(sql);
    }

    public void add(SongModel song, PlaylistModel playlist)
    {
        if (song == null || playlist == null)
            return;

        try
        {
            String sql = """
                INSERT INTO Songs (title, artists, duration, source, filepath, genre, album)
                VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')
                """.formatted(song.getTitle(), song.getArtists(), (int) song.getDuration().toSeconds(), song.getTag(), song.getLocation(), song.getGenre(), song.getAlbum());

            this.execute(sql);

            System.out.println(playlist.getOrderID());
            System.out.println(this.getSongID(song));

            this.execute("INSERT INTO dbo.Playlist_entry (playlistID, SongID) VALUES (" + playlist.getOrderID() + ", " + this.getSongID(song) + ")");

        } catch (Exception ex) {}
    }

    public void add(String playlistName)
    {
        if (playlistName != null && !playlistName.isEmpty())
        {
            this.execute("INSERT INTO dbo.PlayList (name) VALUES ('" + playlistName + "')");
        }
    }


    /**
     *  removes a specific song from the database and the linked playlist(s)
     * */
    public void remove(PlaylistModel playlist, SongModel song)
    {
        if (song != null)
        {
            this.execute("DELETE FROM Songs WHERE id = " + song.getId());
        }
        if (playlist != null)
        {
            this.execute("DELETE FROM dbo.Playlist_entry WHERE playlistID = " + playlist.getOrderID() + " AND SongID = " + song.getId());
        }
    }

    /**
     *  Removes all songs associated with the playlist and the playlist instance in the database itself.
     * */
    public void remove(PlaylistModel playlist)
    {
        if (playlist != null)
        {
            String sql = "DELETE FROM dbo.PlayList WHERE id = " + playlist.getOrderID();
            String sqlSongs = "DELETE FROM dbo.Playlist_entry WHERE playlistID = " + playlist.getOrderID();
            this.execute(sql);
            this.execute(sqlSongs);
        }
    }

    /**
     * Updaters for songs and playlists.
     */
    public void update(SongModel song)
    {
        if (song != null)
        {
            String sql = "UPDATE dbo.Songs SET title = '" + song.getTitle() + "', artists = '"
                    + song.getArtists() + "', genre = '" + song.getGenre() + "', album = '" + song.getAlbum() + "' WHERE id = " + song.getId();
            this.execute(sql);
        }
    }

    public void update(PlaylistModel playlist)
    {
        if (playlist != null)
        {
            this.execute("UPDATE dbo.PlayList SET name='" + playlist.getName() + "' WHERE id=" + playlist.getOrderID());
        }
    }

    /**
     * Getters from the SQL database.
     */
    public ObservableList<SongModel> getAllSongs() { return getAllSongs("SELECT * FROM Songs"); }

    public ObservableList<SongModel> getAllSongs(String sql)
    {
        try
        {
            ObservableList<SongModel> songs = FXCollections.observableArrayList();

            ResultSet result = this.query(sql);

            while (result.next())
            {
                SongModel song = createSongFromDatabaseResult(result);
                if (song != null)
                {
                    songs.add(song);
                }
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

    private SongModel createSongFromDatabaseResult(ResultSet result)
    {
        try
        {
            int songId, duration;
            String songTitle, songArtist, songLocation, songAlbum, songGenre, source;

            songTitle = result.getString("title");
            songId = result.getInt("id");
            duration = result.getInt("duration");
            songArtist = result.getString("artists");
            songLocation = result.getString("filepath");
            songGenre =  result.getString("genre");
            songAlbum = result.getString("album");
            source = result.getString("source");

            if (Path.of(songLocation).toFile().isFile())
            {
               return new SongModel(songId, songTitle, songArtist, songGenre, songAlbum, duration, source, songLocation);
            }
        }
        catch (Exception ex) { }
        return null;
    }

    public ObservableList<PlaylistModel> getAllPlaylists()
    {
        ObservableList<PlaylistModel> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM dbo.PlayList";

        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int playlistID = result.getInt("id");
                String playlistName = result.getString("name");
                List<SongModel> playlistSongs = getAllSongsInPlaylist(playlistID);
                returnList.add(new PlaylistModel(playlistID, playlistSongs, 0, false, playlistName));
            }
            return returnList;

        } catch (Exception e) {
            e.printStackTrace();
            return returnList;
        }
    }


    public List<SongModel> getAllSongsInPlaylist(int playlistID)
    {
        try
        {
            ArrayList<SongModel> returnList = new ArrayList<>();

            String sql = "SELECT * FROM dbo.Playlist_entry WHERE playlistID = " + playlistID;

            ResultSet result = this.query(sql);

            List<SongModel> songs = getAllSongs();

            while (result.next())
            {
                int songId = result.getInt("SongID");

                SongModel song = songs.stream().filter(currentSong -> songId == currentSong.getId()).findFirst().orElse(null);
                if (song != null)
                {
                    returnList.add(song);
                }
            }

            return returnList;
        }
        catch (Exception ex)
        {
            return new ArrayList<>();
        }
    }


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
