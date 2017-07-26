package com.example.android.moneytracker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.text.SpannableStringBuilder;
import android.widget.DatePicker;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;
public class UpdateLent extends AppCompatActivity {

    SQLiteDatabase db1=null;
    Button upd;
    EditText reason_et,amount_et;//id_num;
    Editable d1,d2,d3,d4,d5;
    TextView edtxt,edtD,name_txt;

    public DatePickerDialog datepick=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lent);
        Bundle bundle = getIntent().getExtras();
        final String name_id=bundle.getString("Name");
        name_txt=(TextView)findViewById(R.id.name_id);
        name_txt.setText(name_id);
        edtxt=(TextView) findViewById(R.id.daye);
        Button b1= (Button) findViewById(R.id.daypickbut);
        try{
            b1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    String date=edtxt.getText().toString();
                    if (date !=null && !date.equals(""))
                    {
                        StringTokenizer st= new StringTokenizer(date,"-");
                        String day=st.nextToken();
                        String month=st.nextToken();
                        String year=st.nextToken();
                        datepick = new DatePickerDialog(v.getContext(),(OnDateSetListener) new DatePickHandler(), Integer.parseInt(year),Integer.parseInt(month)-1, Integer.parseInt(day));
                    }
                    else
                    {
                        datepick = new DatePickerDialog(v.getContext(), (OnDateSetListener) new DatePickHandler(), Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    }
                    datepick.show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
        }

        db1=openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);

        reason_et=(EditText)findViewById(R.id.reasons);
        amount_et=(EditText)findViewById(R.id.amount_id);
        upd=(Button)findViewById(R.id.update_id);
        edtD=(TextView)findViewById(R.id.daye);
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d2=reason_et.getText();
                d3=amount_et.getText();
                String dd=edtD.getText().toString();
                //String nii=d1.toString();
                try{
                    int j=0,flag=0;
                    long amt=0;
                    String r="";
                    db1.execSQL("CREATE TABLE IF NOT EXISTS LentTable (Name TEXT PRIMARY KEY NOT NULL, Date DATE NOT NULL, Reason TEXT NOT NULL, Amount INTEGER);");
                    Cursor c=db1.rawQuery("SELECT * from LentTable ORDER BY Name ASC;",null);
                    if(c!=null)
                    {
                        if(c.moveToFirst())
                            do {
                                String ss=c.getString(c.getColumnIndex("Name"));
                                if(name_id.equals(ss))
                                {
                                    flag=1;
                                    amt=c.getLong(c.getColumnIndex("Amount"));
                                    r=c.getString(c.getColumnIndex("Reason"));
                                    db1.delete("LentTable","Name" + "=?", new String[]{ss});
                                    break;
                                }
                                j++;
                            }while (c.moveToNext());
                    }
                    if(flag==0)
                        db1.execSQL("INSERT INTO LentTable(Name,Date,Reason,Amount) VALUES("+"'"+name_id+"','"+dd+"','"+d2+"',"+d3+");");
                    else if(flag==1)
                    {
                        String h=d3.toString();
                        long a=Long.parseLong(h);
                        long t_amt=a;
                        String rea=d2.toString();
                        Editable rea_f = new SpannableStringBuilder(rea);
                        db1.execSQL("INSERT INTO LentTable(Name,Date,Reason,Amount) VALUES("+"'"+name_id+"','"+dd+"','"+rea_f+"',"+t_amt+");");
                    }
                    Toast.makeText(getApplicationContext(), "Database Created", Toast.LENGTH_SHORT).show();
                    Intent lis=new Intent(getApplicationContext(),MoneyLent.class);
                    startActivity(lis);
                    finish();
                }
                catch (Exception e)
                {
                    System.out.println(e);
                    Toast.makeText(getApplicationContext(), "Inadequate details..\nEnter Again"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public class DatePickHandler implements DatePickerDialog.OnDateSetListener {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            int months = month+1;
            if((months<10)&&(day<10))
                edtxt.setText(year + "-0" + (months) + "-0" + day);
            else if((months<10)&&(day>10))
                edtxt.setText(year + "-0" + (months) + "-" + day);
            else if((months>10)&&(day<10))
                edtxt.setText(year + "-" + (months) + "-0" + day);
            else
                edtxt.setText(year + "-" + (months) + "-" + day);
            datepick.hide();
        }
    }
}
