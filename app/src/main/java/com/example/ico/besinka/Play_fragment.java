package com.example.ico.besinka;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Play_fragment extends android.support.v4.app.Fragment {
    int count=0;
    TextView playWordTextView;
    EditText playEditText;
    EditText et;
    Button playOkBtn,playRestBtn;
    Chronometer playChronometer;
    DBHelper dbHelper;
    String word,subWord;
    char[] wordToChar;
    RelativeLayout relativeLayout;
    Dialog customDialog;

    public Play_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_fragment, container, false);
        dbHelper=new DBHelper(view.getContext());
        playWordTextView=(TextView) view.findViewById(R.id.playWordTextView);
          playOkBtn=(Button) view.findViewById(R.id.playOkBtn);
        playRestBtn=(Button) view.findViewById(R.id.playResetBtn);
          playOkBtn.setOnClickListener(onClick);
        playRestBtn.setOnClickListener(onClick);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.drowLayout);
        playChronometer=(Chronometer) view.findViewById(R.id.playChronometer);
        playChronometer.start();
       randomWord();

        return view;
    }
    private void randomWord(){
        word=dbHelper.randomGenerateWord();
        wordToChar=word.toCharArray();
        for(int i=1;i<wordToChar.length;i++){
           if(!(wordToChar[i]==wordToChar[0]))
           {
               wordToChar[i]='-';
           }else wordToChar[i]=wordToChar[0];
        }
        subWord=String.valueOf(wordToChar);
        playWordTextView.setText(subWord);
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.playOkBtn){
            playEditText=(EditText) getView().findViewById(R.id.playEditText);
            String suppositionWord=playEditText.getText().toString();
            boolean flag=false;
            if (suppositionWord.length()>0){
                if(suppositionWord.length()==1){
                    for(int i=0;i<word.length();i++){
                    if (suppositionWord.toLowerCase().equals(word.substring(i,i+1).toLowerCase())){
                            wordToChar[i]=suppositionWord.charAt(0);
                        subWord=String.valueOf(wordToChar);
                        playWordTextView.setText(subWord);
                        flag=true;

                    }
                    }
                }else{
                 if(suppositionWord.toLowerCase().equals(word.toLowerCase())){
                   flag=true;
                 }
                }
            }else{
                Toast.makeText(getView().getContext(), "Enter word or char",
                        Toast.LENGTH_LONG).show();
            }
            if(subWord.toLowerCase().equals(word.toLowerCase())||suppositionWord.toLowerCase().equals(word.toLowerCase())){

               win();
            }
            if(!flag){
                count++;
                wrongSuppositionWord();
            }

            if(count==10){
             loseGame();

            }

        }else{
                playAgain();
            }
        }
    };
    private void loseGame(){
        playOkBtn.setEnabled(false);
        playChronometer.stop();
        playWordTextView.setText("You Lose");
    }

    private void win(){
        playChronometer.stop();
        String time=playChronometer.getText().toString();
        playWordTextView.setText("You win with "+word+" for: "+time+" m");
        playOkBtn.setEnabled(false);

        customDialog = new Dialog(getView().getContext());
        customDialog.setContentView(R.layout.custom_dialog_layout);
        customDialog.setTitle("Add score");
        customDialog.setCanceledOnTouchOutside(false);
        et = (EditText)
                customDialog.findViewById(R.id.customEditText);
        et.setHint("Enter name");

        Button okButton = (Button) customDialog.findViewById(R.id.customOkButton);
        Button cancelButton = (Button)
                customDialog.findViewById(R.id.customCancelButton);
        View.OnClickListener onCustomClick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.customOkButton) {
                    dbHelper=new DBHelper(getView().getContext());

                    String name = et.getText().toString();
                    String time1 = playChronometer.getText().toString();
                    Score s = new Score(word, name, time1, count);
                    dbHelper.addScore(s,getView().getContext());
                    customDialog.hide();
                }else{
                    customDialog.hide();
                }
            }
        };
        okButton.setOnClickListener(onCustomClick);
        cancelButton.setOnClickListener(onCustomClick);

        customDialog.show();
    }
    private void playAgain(){
        count=0;
        playOkBtn.setEnabled(true);
        relativeLayout.removeAllViews();
        randomWord();
        playChronometer.setBase(SystemClock.elapsedRealtime());
        playChronometer.start();
    }
    private void wrongSuppositionWord(){

        relativeLayout.addView(new Rectangle(getView().getContext()));

    }
    private class Rectangle extends View{
        Paint paint = new Paint();

        public Rectangle(Context context) {
            super(context);
        }
        @Override
        public void onDraw(Canvas canvas) {

            switch (count) {
                case 1:
                    paint.setColor(Color.DKGRAY);
                    canvas.drawRect(new Rect(20, 20, 45, 520), paint);
                    break;

                case 2:
                    paint.setColor(Color.DKGRAY);
                    canvas.drawRect(new Rect(20, 20, 310, 45), paint);
                    break;

                case 3:
                    paint.setColor(Color.DKGRAY);
                    canvas.save();
                    canvas.rotate(35);
                    canvas.drawRect(new Rect(110, -40, 135, 110), paint);
                    canvas.restore();
                    break;

                case 4:
                    canvas.drawRect(new Rect(285, 20, 295, 110), paint);
                    break;

                case 5:
                    paint.setColor(Color.BLACK);
                    canvas.drawOval(new RectF(230, 110, 340, 220), paint);
                    paint.setColor(Color.WHITE);
                    canvas.drawOval(new RectF(255, 138, 280, 153), paint);
                    canvas.drawOval(new RectF(290, 138, 315, 153), paint);
                    canvas.drawOval(new RectF(265, 180, 305, 188), paint);
                    break;
                case 6:
                    paint.setColor(Color.BLACK);
                    canvas.drawRect(new Rect(276, 215, 304, 330), paint);
                    break;
                case 7:
                    canvas.save();
                    canvas.rotate(16);
                    canvas.drawRect(new Rect(316, 129, 333, 244), paint);
                    canvas.restore();
                    break;
                case 8:
                    canvas.save();
                    canvas.rotate(-16);
                    canvas.drawRect(new Rect(223, 284, 240, 399), paint);
                    canvas.restore();
                    break;
                case 9:
                    canvas.save();
                    canvas.rotate(8);
                    canvas.drawRect(new Rect(316, 282, 333, 397), paint);
                    canvas.restore();
                    break;
                case 10:
                    canvas.save();
                    canvas.rotate(-8);
                    canvas.drawRect(new Rect(241, 362, 259, 477), paint);
                    canvas.restore();
                    break;

            }


        }
    }
}
