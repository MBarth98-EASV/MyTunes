package model;

import CustomComponent.AutoCompleteTextField;
import be.MusicModel;
import be.PlaylistModel;
import be.SongModel;
import com.sun.source.tree.Tree;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {

    private MusicModel getObjectFromText(List<MusicModel> inputList, String search) {
        for (MusicModel m : inputList) {
            if (m.toString().equals(search)) {
                return m;
            }
        }

        return null;
    }

    private TreeItem getTreeItem(ObservableList<TreeItem<PlaylistModel>> inputList, PlaylistModel inputPlaylist){
        for (TreeItem<PlaylistModel> node : inputList){
            if (node.getValue().getName().equals(inputPlaylist.getName())
                && node.getValue().getOrderID() == inputPlaylist.getOrderID()){
                return node;
            }
        }
        return null;
    }

    public void filterEqualsSearch(List<MusicModel> alldata, TableView tableView, TreeTableView treeTableView, AutoCompleteTextField textField){
        //TODO: Set table to all songs
        MusicModel m = getObjectFromText(alldata, textField.getText());
        if (m.getType().equals("[SONG]")){
            tableView.getSelectionModel().select((SongModel) m);
            tableView.scrollTo((SongModel) m);
        }
        if (m.getType().equals("[PLAYLIST]")){
            TreeItem treeItem = getTreeItem(treeTableView.getRoot().getChildren(), (PlaylistModel) m);
            treeTableView.getSelectionModel().select(treeItem);
            int index = treeItem.getParent().getChildren().indexOf(treeItem);
            treeTableView.scrollTo(index);
        }
    }

    public void filterEqualsArtist(){
        //TODO: Set table to have songs that include query
    }

    public void filterEqualsAlbum(){
        //TODO: set table to have songs that include query
    }


    public void filterEqualsGenre(){
        //TODO: set table to have songs that include query
    }


    public void filterEqualsArtistTitle(){
        //TODO: set table to have songs that include query
    }

    public List<String> allAvailableArtist(){
        ArrayList<String> test = new ArrayList<>();
        test.add("test");
        test.add("test1");
        test.add("test2");
        test.add("test3");
        return test;
    }

    public List<String> allAvailableAlbums(){
        ArrayList<String> test = new ArrayList<>();
        test.add("test");
        test.add("test1");
        test.add("test2");
        test.add("test3");
        return test;
    }

    public List<String> allAvailableGenre(){
        ArrayList<String> test = new ArrayList<>();
        test.add("test");
        test.add("test1");
        test.add("test2");
        test.add("test3");
        return test;
    }

    public List<String> allAvailableTitleArtist(){
        ArrayList<String> test = new ArrayList<>();
        test.add("test");
        test.add("test1");
        test.add("test2");
        test.add("test3");
        return test;
    }




}
