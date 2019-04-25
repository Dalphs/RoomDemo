package dk.hawkster.room_demo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DaoAcces daoAcces();
}
