package dal;

import be.SongModel;
import javafx.beans.property.ListProperty;
import javafx.scene.control.TableView;

public class Utility
{
    public static void bind(TableView<SongModel> table, ListProperty<SongModel> list)
    {
        if (table.itemsProperty().isBound())
        {
            table.itemsProperty().unbind();
        }

        table.itemsProperty().bind(list);
    }
}
