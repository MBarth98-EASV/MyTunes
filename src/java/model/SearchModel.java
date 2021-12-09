package model;

import be.MusicModel;
import be.PlaylistModel;
import com.sun.source.tree.Tree;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.List;

public class SearchModel {

    public MusicModel getObjectFromText(List<MusicModel> inputList, String search) {
        for (MusicModel m : inputList) {
            if (m.toString().equals(search)) {
                return m;
            }
        }

        return null;
    }

    public TreeItem getTreeItem(ObservableList<TreeItem<PlaylistModel>> inputList, PlaylistModel inputPlaylist){
        for (TreeItem<PlaylistModel> node : inputList){
            if (node.getValue().getName().equals(inputPlaylist.getName())
                && node.getValue().getOrderID() == inputPlaylist.getOrderID()){
                return node;
            }
        }
        return null;
    }

}
