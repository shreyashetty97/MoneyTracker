package com.example.android.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton pendingLoan=(ImageButton)findViewById(R.id.pendingLoan);
        ImageButton moneyLent=(ImageButton)findViewById(R.id.moneyLent);
        ImageButton addLoan=(ImageButton)findViewById(R.id.addLoan);
        ImageButton addMoneyLent=(ImageButton)findViewById(R.id.addMoneyLent);

        pendingLoan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent nextscreen=new Intent(getApplicationContext(),PendingLoans.class);
                startActivity(nextscreen);
                Log.e("Pending Loans",".");
            }
        });

        addLoan.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                            public void onClick(View v) {
                        Intent nextscreen = new Intent(getApplicationContext(), AddLoan.class);
                        startActivity(nextscreen);
                        Log.e("Add Loan",".");
                    }
                });

        moneyLent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent nextscreen = new Intent(getApplicationContext(),MoneyLent.class);
                startActivity(nextscreen);
                Log.e("Money Lent",".");
            }
        });

        addMoneyLent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent nextscreen = new Intent(getApplicationContext(),AddMoneyLent.class);
                startActivity(nextscreen);
                Log.e("Add Money Lent",".");
            }
        });

    }
}
