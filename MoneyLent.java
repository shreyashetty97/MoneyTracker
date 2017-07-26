package com.example.android.moneytracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MoneyLent extends AppCompatActivity {

    SQLiteDatabase db1=null;
    int j=0;
    TextView noDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_lent);
        db1=openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS LentTable (Name TEXT PRIMARY KEY NOT NULL, Date DATE NOT NULL, Reason TEXT NOT NULL, Amount INTEGER);");
        Cursor c=db1.rawQuery("SELECT * from LentTable ORDER BY Name ASC;",null);
        if(c!=null)
        {
            if(c.moveToFirst())
                do {
                    final String d1=c.getString(c.getColumnIndex("Name"));
                    Long d2=c.getLong(c.getColumnIndex("Amount"));
                    String temp=d2.toString();
                    Button bt=new Button(MoneyLent.this);
                    ViewGroup tablelayout=(ViewGroup)findViewById(R.id.table3);
                    bt.setText(temp);
                    TextView tv=new TextView(MoneyLent.this);
                    tv.setText(d1);
                    tv.setTextColor(Color.parseColor("#00CCFF"));
                    tv.setGravity(Gravity.CENTER);
                    bt.setBackgroundColor(Color.parseColor("#CCCCCC"));
                    bt.setPadding(0, 3, 0, 0);
                    TableRow row;
                    TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 10, 0, 0);
                    row = new TableRow( this );
                    tablelayout.addView( row );
                    row.setLayoutParams(lp);
                    int px=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,140,getResources().getDisplayMetrics());
                    int px1=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,getResources().getDisplayMetrics());
                    row.addView(tv,px,px1);
                    row.addView(bt,px,px1);
                    // registerForContextMenu(bt);
                    bt.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent nextscreen= new Intent(getApplicationContext(),LentDetails.class);  //change
                            nextscreen.putExtra("Name",d1);
                            Log.e("Loan details",".");
                            startActivity(nextscreen);
                            finish();
                        }
                    });
                    j++;
                }while(c.moveToNext());
        }
        if(j==0)
        {
            noDet=new TextView(MoneyLent.this);
            ViewGroup linearlayout=(ViewGroup)findViewById(R.id.newbutton);
            noDet.setText("No Accounts to view");
            noDet.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            noDet.setTextColor(Color.WHITE);
            noDet.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            linearlayout.addView(noDet);
        }
    }
}
