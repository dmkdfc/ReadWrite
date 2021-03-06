package com.srm.sohamdixit.readwrite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView content;
    EditText text;
    Button ok,del;
    static String FILENAME = "test.txt";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIds();
        file = new File(getFilesDir(), FILENAME);


        try {
            if (file.createNewFile()) {
                Toast.makeText(getApplicationContext(), "File Created",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = text.getText().toString();
                text.setText("");
                readFromFile rf = new readFromFile(file);
                rf.execute(value);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(file);
                    content.setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                writer.print("");
                writer.close();
            }

        });

    }

    public void setIds() {
        content = findViewById(R.id.textView);
        text = findViewById(R.id.editText);
        ok =   findViewById(R.id.button);
        del =  findViewById(R.id.bt);
    }


    private class readFromFile extends AsyncTask<String, Integer, String> {

        File f;

        public readFromFile(File f) {
            super();
            this.f = f;
        }

        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(f, true);
                writer.append(str[0].toString());
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name);
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(sb.toString());
        }

    }
}
