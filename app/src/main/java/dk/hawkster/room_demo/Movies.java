package dk.hawkster.room_demo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Movies {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int movieId;
    private String movieName;

    public Movies() {
    }

    @NonNull
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
