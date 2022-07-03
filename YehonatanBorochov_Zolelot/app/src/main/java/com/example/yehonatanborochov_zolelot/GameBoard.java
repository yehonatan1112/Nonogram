package com.example.yehonatanborochov_zolelot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;


public class GameBoard extends View {

    private Paint paint;
    private Paint bgPaint;
    SquareActivity[][] sqr = new SquareActivity[11][11];
    int t=0;
    int size1=0; int size2=0; int size3=0; int size4=0; int countWater=0;

    public GameBoard(Context context){
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12);
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        for (int i=0; i<11; i++)
        {
            for(int j=0; j<11; j++)
            {
                int i2 = i*100, j2= j*100;
                if(i==0||j==0)
                    sqr[i][j] = new SquareBorder(i2, j2, i2+100, j2+100);
                else
                    sqr[i][j] = new SquareActivity(i2, j2, i2+100, j2+100);
            }
        }

        Random rnd = new Random();
        int size=3, count=1;
        boolean horizontal = false;
        boolean vertical = false;
        int i=0,i2=0,j=0,j2=0;
        int length = size;
        int borderLength = length+2;
        int rand = rnd.nextInt(2);
        while(count<11)
        {
            i = rnd.nextInt(10)+1;
            i2 = i;
            j = rnd.nextInt(10)+1;
            j2 = i;
            length=size;
            borderLength = length+2;
            rand = rnd.nextInt(2);

            if(rand==1)
                horizontal = true;
            else
                vertical = true;

            if(horizontal)
            {
                while(i+size>=11)
                {
                    i= rnd.nextInt((10 - 1) + 1) + 1;
                    i2 = i;
                }
            }
            else
            {
                while(j+size>=11)
                {
                    j= rnd.nextInt((10 - 1) + 1) + 1;
                    j2 = j;
                }
            }
                if(!sqr[i][j].getIsBorder()&& !sqr[i][j].getIsShip()  ){
                    if(horizontal && !sqr[i+size][j].getIsBorder() && !sqr[i+size][j].getIsShip()  ) {
                        //Builds head of ship
                        sqr[i][j] = new ShipActivity(i * 100, j * 100, (i * 100) + 100, (j * 100) + 100);
                        sqr[i][j].setIsShip(true);
                        //Builds  ship
                        while (length >= 1) {
                            sqr[i2 + 1][j] = new ShipActivity((i2 + 1) * 100, j * 100, ((i2 + 1) * 100) + 100, (j * 100) + 100);
                            sqr[i2 + 1][j].setIsShip(true);
                            i2++;
                            length--;
                        }


                        //finds the ship's borders
                        for (int k = 1; k < borderLength; k++) {
                            SetBorders(sqr, i, j);
                            i++;
                        }

                    }
                    if(vertical==true&& sqr[i][j+size].getIsBorder()==false && sqr[i][j+size].getIsShip()==false){
                        //builds head of the ship
                        sqr[i][j] = new ShipActivity(i * 100, j * 100, (i * 100) + 100, (j * 100) + 100);
                        sqr[i][j].setIsShip(true);
                        //builds the rest of the ship
                        while (length >= 1) {
                            sqr[i][j2 + 1] = new ShipActivity(i * 100, (j2 + 1) * 100, (i2 * 100) + 100, ((j2 + 1) * 100) + 100);
                            sqr[i][j2 + 1].setIsShip(true);
                            j2++;

                            length--;
                        }
                        //finds the ship's borders
                        for (int k = 1; k < borderLength; k++) {
                            SetBorders(sqr, i, j);
                            j++;
                        }
                    }
                count++;
                if(size==3&&count==2)
                    size--;
                if(size==2&&count==4)
                    size--;
                if(size==1&&count==7)
                    size--;
            }
            i=0; j=0; i2=0; j2=0;
            horizontal = false; vertical = false;
        }
        for (int k=1; k<11; k++)
        {
            for(int l=1; l<11; l++)
            {
                if(sqr[k][l] instanceof ShipActivity)
                {
                    if (sqr[0][l] instanceof SquareBorder)
                        ((SquareBorder) sqr[0][l]).addShip();
                    if (sqr[k][0] instanceof SquareBorder)
                        ((SquareBorder) sqr[k][0]).addShip();
                }
                sqr[k][l].setIsBorder(false);
            }
        }
    }
    public void solve()
    {
        for(int i=0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
                if(sqr[i][j] instanceof ShipActivity)
                    sqr[i][j].setColor("black");
                else
                    sqr[i][j].setColor("magenta");
            }
        }
        invalidate();
    }
    public void Refresh()
    {
        for(int i=0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
            sqr[i][j].setColor("white");
            }
        }
        invalidate();
    }

    public void SetBorders(SquareActivity[][] sqr, int i, int j){
        for (int k = 1; k < 11; k++){
            for (int l= 1; l < 11; l++){
                if(Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==100 && Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==100 && !(sqr[k][l] instanceof ShipActivity)) {
                    sqr[k][l].setIsBorder(true);
                }
                if(Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==100 && Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==0 && !(sqr[k][l] instanceof ShipActivity)){
                    sqr[k][l].setIsBorder(true);
                }
                if(Math.abs(sqr[k][l].getX()-sqr[i][j].getX())==100 && Math.abs(sqr[k][l].getY()-sqr[i][j].getY())==0 && !(sqr[k][l] instanceof ShipActivity)) {
                    sqr[k][l].setIsBorder(true);
                }

            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPaint(bgPaint);
        if(size1==4&&size2==3&&size3==2&&size4==1&&countWater==20)
        {
            paint.setStrokeWidth(4);
            paint.setTextSize(60);
            canvas.drawText("you won the game", 300, 300, paint);
        }
        else
        {
            for(int i=0; i<11; i++)
            {
                for(int j=0; j<11; j++)
                {
                    sqr[i][j].Draw(canvas,paint);
                    sqr[i][j].DrawBorder(canvas,paint);
                }
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5);
            paint.setTextSize(60);
            paint.setColor(Color.BLACK);
            canvas.drawText("Ships size:",40,1200,paint);
            canvas.drawText("1",50,1300,paint);
            canvas.drawText("2",50,1400,paint);
            canvas.drawText("3",50,1500,paint);
            canvas.drawText("4",50,1600,paint);
            canvas.drawText("Current situation",canvas.getWidth()-550,1200,paint);
            canvas.drawText(String.valueOf(size1) + "/4",canvas.getWidth()-550,1300,paint);
            canvas.drawText(String.valueOf(size2) + "/3",canvas.getWidth()-550,1400,paint);
            canvas.drawText(String.valueOf(size3) + "/2",canvas.getWidth()-550,1500,paint);
            canvas.drawText(String.valueOf(size4) + "/1",canvas.getWidth()-550,1600,paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int x=(int)event.getX(),y=(int)event.getY();
            for (int k = 1; k < 11; k++){
                for (int l= 1; l < 11; l++) {
                    if(sqr[k][l].getX()+100>x && sqr[k][l].getX()<x ){
                        if(sqr[k][l].getY()+100>y && sqr[k][l].getY()<y ){

                            if(sqr[k][l].getKind()==0){
                                sqr[k][l].setColor("magenta");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(false);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(false);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(false);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(false);
                            }
                            else if(sqr[k][l].getKind()==1){
                                sqr[k][l].setColor("black");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(true);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(true);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(true);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(true);
                                ((SquareBorder) sqr[0][l]).addGuess();
                                ((SquareBorder) sqr[k][0]).addGuess();
                            }
                            else if(sqr[k][l].getKind()==2){
                                sqr[k][l].setColor("white");
                                sqr[k][l].changeKind();
                                sqr[k-1][l-1].setIsBorder(false);
                                if(k+1!=11 && l+1!=11)
                                    sqr[k+1][l+1].setIsBorder(false);
                                if(l+1!=11)
                                    sqr[k-1][l+1].setIsBorder(false);
                                if(k+1!=11)
                                    sqr[k+1][l-1].setIsBorder(false);
                                ((SquareBorder) sqr[0][l]).removeGuess();
                                ((SquareBorder) sqr[k][0]).removeGuess();
                            }

                        }
                    }
                }
            }
            for (int k = 1; k < 11; k++)
            {
                for (int l = 1; l < 11; l++)
                {
                    if (sqr[k][l].getColor()=="black" && !sqr[k][l].getIsBorder())
                        sqr[k][l].setColor("red");
                    if(sqr[k][l].getColor()=="red" && !sqr[k][l].getIsBorder())
                        sqr[k][l].setColor("black");
                }
            }

            int count=0;
            int[] ship = new int[4];
            for (int l = 1; l < 11; l++)
            {
                for (int k = 1; k < 11; k++)
                {
                    if (sqr[k][l].getColor() == "black") {
                        while (sqr[k][l].getColor() == "black") {
                            count++;
                            if(k==10)
                                break;
                            k++;
                        }
                        if (count >= 5) {
                            for (int i=count; i>0;i--){
                                sqr[k-i][l].setColor("red");
                            }
                        }
                        if (1 < count && count <= 4)
                            ship[count - 1] += 1;
                        count = 0;
                    }
                    if(sqr[k][l].getColor() == "red"){
                        if(k!=10 && sqr[k+1][l].getColor() == "black")
                            sqr[k+1][l].setColor("red");
                        if(sqr[k-1][l].getColor() == "black")
                            sqr[k-1][l].setColor("red");
                        if(l!=10 && sqr[k][l+1].getColor() == "black")
                            sqr[k][l+1].setColor("red");
                        if(sqr[k][l-1].getColor() == "black")
                            sqr[k][l-1].setColor("red");
                    }
                }
            }
            for (int k = 1; k < 11; k++) {
                for (int l = 1; l < 11; l++) {
                    while(sqr[k][l].getColor()=="black") {
                        count++;
                        if(l==10)
                            break;
                        l++;

                    }
                    if (count >= 5) {
                        for (int i=count; i>0;i--){
                            sqr[k][l-i].setColor("red");
                        }
                    }
                    if(1<count && count<=4)
                        ship[count-1]+=1;


                    count=0;
                }
            }
            for (int k = 1; k < 11; k++) {
                for (int l = 1; l < 11; l++) {
                    if(sqr[k][l].getColor() == "black"){
                        if(k!=10 && l!=10){
                            if(sqr[k+1][l].getColor() != "black" && sqr[k-1][l].getColor() != "black" &&sqr[k][l+1].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l!=10 && k==10){
                            if(sqr[k-1][l].getColor() != "black" &&sqr[k][l+1].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l==10 && k!=10){
                            if(sqr[k+1][l].getColor() != "black" && sqr[k-1][l].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }
                        else if(l==10 && k==10){
                            if(sqr[k-1][l].getColor() != "black" && sqr[k][l-1].getColor() != "black"){
                                ship[0]+=1;
                            }
                        }

                    }
                }
            }

            size1=ship[0];
            size2=ship[1];
            size3=ship[2];
            size4=ship[3];
            countWater=0;
            for (int l = 1; l < 11; l++) {
                if(((SquareBorder)sqr[0][l]).isValid()){
                    countWater++;
                }
            }
            for(int i=1 ; i<11; i++){
                if(((SquareBorder)sqr[i][0]).isValid()){
                    countWater++;
                }
            }
            invalidate();
        }
        return true;
    }
}