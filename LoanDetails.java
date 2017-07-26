package com.example.android.moneytracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoanDetails extends AppCompatActivity {

    SQLiteDatabase db1=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);
       // Intent i =getIntent();
       // String name_id=i.getExtra("Name","Phil");
        Bundle bundle = getIntent().getExtras();
        String name_id=bundle.getString("Name");
        //Toast.makeText(getApplicationContext(),name_id, Toast.LENGTH_SHORT).show();
        try {
            db1 = openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
            Cursor c = db1.rawQuery("SELECT * FROM MoneyTable WHERE Name=?", new String[]{name_id});
            c.moveToFirst();

            String doo=c.getString(c.getColumnIndex("Date"));
            TextView DOO=(TextView)findViewById(R.id.date);
            DOO.setText(doo);

            Long amt_id=c.getLong(c.getColumnIndex("Amount"));
            TextView amtt=(TextView)findViewById(R.id.amount_value);
            String aa=amt_id.toString();
            amtt.setText(aa);

            String re = c.getString(c.getColumnIndex("Reason"));
            TextView RE = (TextView) findViewById(R.id.reason_tt);
            RE.setText(re);

            TextView na=(TextView)findViewById(R.id.name);
            na.setText(name_id);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        Button del=(Button)findViewById(R.id.delete_info);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db1.delete("MoneyTable","Name" + "=?", new String[]{ss});
                TextView tv=(TextView)findViewById(R.id.name);
                String abc=tv.getText().toString();
                db1.delete("MoneyTable","Name" + "=?", new String[]{abc});
                Toast.makeText(getApplicationContext(),"Database entry deleted", Toast.LENGTH_SHORT).show();
                Intent lis=new Intent(getApplicationContext(),PendingLoans.class);
                startActivity(lis);
                finish();
            }
        });

        Button upda=(Button)findViewById(R.id.update_info);
        upda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView)findViewById(R.id.name);
                String abc=tv.getText().toString();
                Intent nextscreen= new Intent(getApplicationContext(),UpdateClass.class);                //change to update class
                nextscreen.putExtra("Name",abc);
                Log.e("Update back to add loan",".");
                startActivity(nextscreen);
                finish();
            }
        });

        Button bck=(Button)findViewById(R.id.back);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}
