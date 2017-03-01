package edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

import edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org.TitlesFragment;

import static android.content.Context.MODE_APPEND;

/**
 * Created by m_alrajab on 2/22/17.
 * Modified by Karthika Jayaprakash 2/28/17
 */

public class Utils  {

    public static final String SHARED_PREF_FILENAME="edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org.SHAREDFILE1";
    public static final String KEY_TITLE="Title_";
    public static final String KEY_BODY="Body_";


    public static String[] getListFromSP(Context context, String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_FILENAME,
                Context.MODE_PRIVATE);
        Map<String, ?> map=sharedPreferences.getAll();
        ArrayList<String> lst= new ArrayList<>();
        for(String str:map.keySet()){
            if(str.startsWith(key))
                lst.add((String)map.get(str));
        }
        return lst.toArray(new String[lst.size()]);
    }

    public static String[] getListFromFile(Context context, String key) {
        File filesDir = context.getFilesDir();
        long x = filesDir.getFreeSpace() / 1_000_000;//this in bytes converted to MB
        File[] filesList = filesDir.listFiles();
        ArrayList<String> listOfNotes = new ArrayList<>();

        for (File fl : filesList) {
            if(key.equals("Title_"))
                listOfNotes.add(fl.getName());//add title
            else
                listOfNotes.add(loadNote(context,fl.getName()));//add body
        }
        return listOfNotes.toArray(new String[listOfNotes.size()]);
    }

    public static long getfreeSpace(Context context) {
        File filesDir = context.getFilesDir();
        return filesDir.getFreeSpace() / 1_000_000;//this in bytes converted to MB

        }

    public static String saveNote(Context context,String title, String body)
    {
        //save note
        try{
            FileOutputStream outputStream = context.openFileOutput(title, MODE_APPEND);
            outputStream.write(body.getBytes());
            outputStream.close();
           return "File Saved";
        }catch(Exception e){

            Log.e("ERROR",e.getMessage() );
            return "Error Saving";
        }
    }
    public static String loadNote(Context context, String noteTitle) {
        String tempStr = "";


        try {
            FileInputStream inputStream = context.openFileInput(noteTitle.replace(" ", ""));
            int c;
            while ((c = inputStream.read()) != -1) {
                tempStr += Character.toString((char) c);
            }
            inputStream.close();
            return tempStr;

        } catch (Exception e) {

            Log.e("ERROR", e.getMessage());
            return "Error Loading";
        }
    }



}
