package com.example.android.moneytracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

public class AddLoan extends AppCompatActivity {

    SQLiteDatabase db1=null;
    Button sub;
    EditText name_et,reason_et,amount_et;//id_num;
    Editable d1,d2,d3,d4,d5;
    TextView edtxt,edtD;

    public DatePickerDialog datepick=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        edtxt=(TextView) findViewById(R.id.daye);
        Button b1= (Button) findViewById(R.id.daypickbut);
        try{
            b1.setOnClickListener(new OnClickListener(){

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
        name_et=(EditText)findViewById(R.id.name_id);
        reason_et=(EditText)findViewById(R.id.reasons);
        amount_et=(EditText)findViewById(R.id.amount_id);
        sub=(Button)findViewById(R.id.submit);
        edtD=(TextView)findViewById(R.id.daye);
        //id_num=(EditText)findViewById(R.id.idno);
        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Editable n=name_et.getText();
                d1=name_et.getText();
                d2=reason_et.getText();
                d3=amount_et.getText();
                //d4=id_num.getText();
                String dd=edtD.getText().toString();
                String nii=d1.toString();
                //DatabaseHelper myDbHelper = new DatabaseHelper(AddLoan.this);
                try
                {
                    int j=0,flag=0;
                    long amt=0;
                    String r="";

                    db1.execSQL("CREATE TABLE IF NOT EXISTS MoneyTable (Name TEXT PRIMARY KEY NOT NULL, Date DATE NOT NULL, Reason TEXT NOT NULL, Amount INTEGER);");
                    Cursor c=db1.rawQuery("SELECT * from MoneyTable ORDER BY Name ASC;",null);
                    if(c!=null)
                    {
                        if(c.moveToFirst())
                        do
                        {
                                String ss=c.getString(c.getColumnIndex("Name"));
                                if(nii.equals(ss))
                                {
                                    flag=1;
                                    amt=c.getLong(c.getColumnIndex("Amount"));
                                    r=c.getString(c.getColumnIndex("Reason"));
                                    db1.delete("MoneyTable","Name" + "=?", new String[]{ss});
                                    break;
                                }
                                j++;
                        }while (c.moveToNext());
                    }
                    if(flag==0)
                        db1.execSQL("INSERT INTO MoneyTable(Name,Date,Reason,Amount) VALUES("+"'"+d1+"','"+dd+"','"+d2+"',"+d3+");");
                    else if(flag==1)
                    {
                        String h=d3.toString();
                        long a=Long.parseLong(h);
                        long t_amt=a+amt;
                        String rea=d2.toString()+", "+r;
                        Editable rea_f = new SpannableStringBuilder(rea);
                        db1.execSQL("INSERT INTO MoneyTable(Name,Date,Reason,Amount) VALUES("+"'"+d1+"','"+dd+"','"+rea_f+"',"+t_amt+");");
                    }
                    Toast.makeText(getApplicationContext(), "Database Created", Toast.LENGTH_SHORT).show();
                    Intent lis=new Intent(getApplicationContext(),PendingLoans.class);
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
    public class DatePickHandler implements OnDateSetListener{
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
