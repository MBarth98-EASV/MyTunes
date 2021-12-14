package dal;

import dal.db.EASVDatabase;

public class DataAccessLayer
{
    EASVDatabase db;

    public DataAccessLayer()
    {
        db = new EASVDatabase();
    }


    // todo: combine db and local



    // todo remove db references outside of this class

    // todo remove local file references outside of this class

    // todo update entities through this class ?
    // todo store data in-use in this class ?

}
