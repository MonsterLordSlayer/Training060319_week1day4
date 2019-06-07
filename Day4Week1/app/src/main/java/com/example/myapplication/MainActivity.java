package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
class Evaluation{
    int pos = -1, ch;
    public String str;
    Evaluation(String st){
        str=st;
    }
    void nextChar() {
        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
        if (pos>-1 && pos<str.length())System.out.println(str.charAt(pos));
    }

    boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    double parse() {
        nextChar();
        double x = parseExpression();
        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
        return x;
    }

    double parseExpression() {
        double x = parseTerm();
        for (;;) {
            if      (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }

    double parseTerm() {
        double x = parseFactor();
        for (;;) {
            if      (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else return x;
        }
    }

    double parseFactor() {
        if (eat('+')) return parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = pos;
        if (eat('(')) { // parentheses
            x = parseExpression();
            eat(')');

        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers

            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, pos));

        } else {
            throw new RuntimeException("Unexpected: " + (char)ch);
        }



        return x;
    }
}
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    Button clearstuff;
    Button button1 ;
    Button button2 ;
    Button button3 ;
    Button button4 ;
    Button button5 ;
    Button button6 ;
    Button button7 ;
    Button button8 ;
    Button button9 ;
    Button button0 ;
    Button buttonplus ;
    Button buttonmin ;
    Button buttonmul ;
    Button buttondiv ;
    Button buttoneq ;
    Button buttondot ;
    String current;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result=findViewById(R.id.result);
        clearstuff=findViewById(R.id.nclear);
        clearstuff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                result.setText("");
            }
        });
        button1 = findViewById(R.id.n1);
        button1.setOnClickListener(this);

        button2 = findViewById(R.id.n2);
        button2.setOnClickListener(this);

        button3 = findViewById(R.id.n3);
        button3.setOnClickListener(this);

        button4 = findViewById(R.id.n4);
        button4.setOnClickListener(this);

        button5 = findViewById(R.id.n5);
        button5.setOnClickListener(this);

        button6 = findViewById(R.id.n6);
        button6.setOnClickListener(this);

        button7 = findViewById(R.id.n7);
        button7.setOnClickListener(this);

        button8 = findViewById(R.id.n8);
        button8.setOnClickListener(this);

        button9 = findViewById(R.id.n9);
        button9.setOnClickListener(this);

        button0 = findViewById(R.id.n0);
        button0.setOnClickListener(this);

        buttondot=findViewById(R.id.ndot);
        buttondot.setOnClickListener(this);

        buttonplus = findViewById(R.id.nplus);
        buttonplus.setOnClickListener(this);
        buttonmin = findViewById(R.id.nminus);
        buttonmin.setOnClickListener(this);
        buttonmul = findViewById(R.id.nmul);
        buttonmul.setOnClickListener(this);
        buttondiv = findViewById(R.id.ndiv);
        buttondiv.setOnClickListener(this);
        buttoneq = findViewById(R.id.ndeq);

        buttoneq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                double temp=eval(result.getText().toString());
                result.setText(""+temp);
            }
        });

    }
    public void onClick(View v) {
        // Code here executes on main thread after user presses button
        result.setText(result.getText().toString()+((Button) v).getText());
        current=result.getText().toString();

    }
    public static double eval(final String str) {
        return new Evaluation(str).parse();
    }

}
