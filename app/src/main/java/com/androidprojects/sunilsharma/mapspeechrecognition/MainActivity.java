package com.androidprojects.sunilsharma.mapspeechrecognition;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.sunilsharma.mapspeechrecognition.Model.CountryDataSource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final int SPEAK_REQUEST = 10;
    TextView text_value;
    Button button_VoiceIntent;

    public static CountryDataSource countryDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_value = (TextView) findViewById(R.id.textValue);
        button_VoiceIntent = (Button) findViewById(R.id.buttonVoiceIntent);

        Hashtable<String , String> countriesAndMessages = new Hashtable<>();
        countriesAndMessages.put("India" ,
                "Welcome To India");
        countriesAndMessages.put("Canada" ,
                "Welcome To Canada. Happy Visiting");
        countriesAndMessages.put("France" ,
                "Welcome To France. Happy Visiting");
        countriesAndMessages.put("Brazil" ,
                "Welcome To Brazil. Happy Visiting");
        countriesAndMessages.put("United State" ,
                "Welcome To US. Happy Visiting");
        countriesAndMessages.put("Japan" ,
                "Welcome To Japan. Happy Visiting");

        countryDataSource = new CountryDataSource(countriesAndMessages);

        button_VoiceIntent.setOnClickListener(MainActivity.this);

        PackageManager packageManager = this.getPackageManager();
        List<ResolveInfo> listOfInformation = packageManager.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ,
                0);

        if(listOfInformation.size() > 0)
        {
            Toast.makeText(MainActivity.this ,
                    "Your Device does support Speech Recognition" ,
                    Toast.LENGTH_SHORT).show();

            listenToTheUsersVoice();
        }
        else
        {
            Toast.makeText(MainActivity.this ,
                    "Your Device does NOT support Speech Recognition" ,
                    Toast.LENGTH_SHORT).show();
        }
    }



    private void listenToTheUsersVoice()
    {
        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT , "Talk To Me!");

        /** This is for User Can Talk in Any Language That He/She Want*/
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL ,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        /** Now We Want a Maximum Result From User Speech*/
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS , 10);
        startActivityForResult(voiceIntent , SPEAK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SPEAK_REQUEST && resultCode == RESULT_OK)
        {
            /** now we have to recognize words that what user speak and conver that ino the String */
            ArrayList<String> voiceWords =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            /** Through this we we can check how accurate our word is. */
            float[] confidLevels =
                    data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);


            /** now we give output to the Screen*/
            /**
            * *int index = 0;
            for(String userWord : voiceWords)
            {
                *//** through this condition we are 100% sure that user speak something *//*
                if(confidLevels != null && index < confidLevels.length)
                {
                    *//** now we are going to print the user Word*//*
                    text_value.setText(userWord + " - " + confidLevels[index]);
                }
            }*
            */


            String countryMatchedWithUserWord = countryDataSource.
                    matchWithMinimumConfidenceLevelOfUserWords(voiceWords , confidLevels);

            Intent myMapActivity = new Intent(MainActivity.this , MapsActivity.class);
            myMapActivity.putExtra(CountryDataSource.COUNTRY_KEY , countryMatchedWithUserWord);
            startActivity(myMapActivity);



        }
        else
        {

        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v)
    {
        listenToTheUsersVoice();
    }
}
