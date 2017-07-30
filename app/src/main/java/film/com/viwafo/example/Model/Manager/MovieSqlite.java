package film.com.viwafo.example.Model.Manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Model.Entity.Movie;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class MovieSqlite extends SQLiteOpenHelper {

    public static final String COLUMN_MOVIE_TITLE = "Movie_Name";
    public static final String COLUMN_MOVIE_VOTE_AVERAGE = "Movie_VoteAverage";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movie_Manager";
    private static final String TABLE_MOVIE = "Movie";
    private static final String COLUMN_MOVIE_ID = "Movie_Id";
    private static final String COLUMN_MOVIE_POSTERURL = "Movie_PosterURL";
    private static final String COLUMN_MOVIE_RELEASE_DATE = "Movie_ReleaseDate";
    private static final String COLUMN_MOVIE_OVERVIEW = "Movie_Overview";
    private static final String COLUMN_MOVIE_ISADULT = "Movie_IsAdult";


    private static MovieSqlite instance;

    private MovieSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MovieSqlite getInstance(Context context) {
        if (instance == null) {
            instance = new MovieSqlite(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_MOVIE + "("
                + COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," + COLUMN_MOVIE_TITLE + " TEXT,"
                + COLUMN_MOVIE_POSTERURL + " IMAGE," + COLUMN_MOVIE_RELEASE_DATE + " TEXT," + COLUMN_MOVIE_VOTE_AVERAGE + " TEXT,"
                + COLUMN_MOVIE_OVERVIEW + " TEXT," + COLUMN_MOVIE_ISADULT + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    public void addMovie(Movie student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_TITLE, student.getTitle());
        values.put(COLUMN_MOVIE_POSTERURL, student.getPosterUrl());
        values.put(COLUMN_MOVIE_RELEASE_DATE, student.getReleaseDate());
        values.put(COLUMN_MOVIE_VOTE_AVERAGE, student.getVoteAverage());
        values.put(COLUMN_MOVIE_OVERVIEW, student.getOverview());
        values.put(COLUMN_MOVIE_ISADULT, student.getIsAdult());

        db.insert(TABLE_MOVIE, null, values);

        db.close();
    }


//    public Movie getMovie(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_Movie, new String[]{COLUMN_STUDENT_ID,
//                        COLUMN_STUDENT_NAME, COLUMN_STUDENT_FAVOR}, COLUMN_STUDENT_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//        Student student = new Student(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//
//        return student;
//    }


    public List<Movie> getAllMovies() {
        List<Movie> listMovies = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setPosterUrl(cursor.getString(2));
                movie.setReleaseDate(cursor.getString(3));
                movie.setVoteAverage(cursor.getString(4));
                movie.setOverview(cursor.getString(5));
                movie.setIsAdult(cursor.getString(6));

                listMovies.add(movie);
            } while (cursor.moveToNext());
        }
        return listMovies;
    }

    public List<Movie> sortWithRank(String rank) {
        List<Movie> listMovies = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MOVIE, null, null,
                null, null, null, rank + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setPosterUrl(cursor.getString(2));
                movie.setReleaseDate(cursor.getString(3));
                movie.setVoteAverage(cursor.getString(4));
                movie.setOverview(cursor.getString(5));
                movie.setIsAdult(cursor.getString(6));
                listMovies.add(movie);
            } while (cursor.moveToNext());
        }
        return listMovies;
    }


//    public int updateStudent(Student student) {
//        Log.i(TAG, "MyDatabaseHelper.updateStudent ... " + student.getStdName());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_STUDENT_NAME, student.getStdName());
//        values.put(COLUMN_STUDENT_FAVOR, student.getStdFavor());
//
//        return db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + " = ?",
//                new String[]{String.valueOf(student.getStdId())});
//    }
//
//    public void deleteStude(Student student) {
//        Log.i(TAG, "MyDatabaseHelper.updateStudent ... " + student.getStdName());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + " = ?",
//                new String[]{String.valueOf(student.getStdId())});
//        db.close();
//    }
}
