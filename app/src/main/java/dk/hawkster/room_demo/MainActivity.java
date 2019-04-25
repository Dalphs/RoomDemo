package dk.hawkster.room_demo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "movies_db";
    private MovieDatabase movieDatabase;
    private EditText mMovieName;
    private EditText mMovieId;
    private Button mSave;
    private Button mLoad;
    Movies movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieName = findViewById(R.id.movie_name);
        mMovieId = findViewById(R.id.movie_id);
        mSave = findViewById(R.id.save_button);
        mLoad = findViewById(R.id.load_button);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filen er gemt", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        movie = new Movies();
                        movie.setMovieName(mMovieName.getText().toString());
                        movieDatabase.daoAcces().insertOnlySingleMovie(movie);
                    }
                }).start();
            }
        });

        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Filen er indlæst", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mMovieName.setText("");
                final int fetchID = Integer.parseInt(mMovieId.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        movie = movieDatabase.daoAcces().fetchOneMoviesbyMovieId(fetchID);
                        final String name = movie == null ? "" : movie.getMovieName();
                        System.out.println(name);
                        //mMovieName.setText(movie.getMovieName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateText(name);
                            }
                        });
                    }
                }).start();

            }
        });

        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateText(String s){
        mMovieName.setText(s);
    }
}
