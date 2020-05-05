import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Rebel_Dalek_Escape extends PApplet {

// Created by Junad Ahmed during Coding Bootcamp 2016 at City University London studying Computer Science
// Aspects of code taken from examples provided on the moodle, and Crappy Bird by KTByte
// Not complete as in it doesn't get more points by collecting the gems. There's a lot of unused files and images.



SoundFile blast, die, exterminate, hit, goodScore, message2;

float xPos;
float yPos;
float vx;
float vy; 
PImage dalek, space, pipe, gem, asteroid, dalek2, dalek1, dalekDead;
float floor, sky, velocityJump, jumpState;
float rectX, rectY;
float obstacleX, obstacleY;
float dalekX, dalekY;

int lives;
boolean offScreen, inPlayMode, collided, blasting, collected, gemXmet, gemYmet;

float blastCounter, randomExterminate, killerCounter;

int wx[] = new int[2];
int wy[] = new int[2];
int score = 0;
int highScore =0;
int falling = 0;  
int ufoX, ufoY, ufoFill;


public void setup() {
  
  //fullScreen();
  xPos = width;
  yPos= 210;
  vx = -2;
  vy=0;
  velocityJump=2;
  jumpState=0;
  rectX= width;
  rectY= floor+280;
  dalekX= 100;
  dalekY= height/2;
  floor = height-50;

  //images
  dalek = loadImage("dalek acc clear.png");
  dalek2 = loadImage("dalek lives 2.png");
  dalek1 = loadImage("dalek lives 1.png");
  dalekDead = loadImage("exterminated.png");
  space = loadImage("giant space.png");
  pipe = loadImage("pipe.png");
  gem = loadImage("omnitrix.png");
  asteroid = loadImage("dalek asteroid.png");


  offScreen = false;
  inPlayMode = true;
  collided = false;// checks if collided or not
  blasting = false;
  collected = false;
  gemXmet = false;
  gemYmet = false;

  lives=3;
  blastCounter = 40; //keeps the blast on for longer
  killerCounter = 500; ////my attempt to avoid instant death
  ufoX = 400;
  ufoY = 300;
  ufoFill = 255;//testing if shot or not
  vx = -2;
  obstacleX = 450;
  obstacleY = 200;

  //sound
  blast =new SoundFile(this, "blast.wav") ;
  die = new SoundFile(this, "buddy_off.wav");
  exterminate =new SoundFile(this, "exterminate.wav") ; 
  hit = new SoundFile (this, "mgs_alert.wav");
  goodScore = new SoundFile (this, "good_dalek.wav");
  message2 = new SoundFile (this, "seek_locate_annihilate.wav");

  keyPressed();
  //mousePressed();
}


public void draw() {
  //randomSeed(1234);
  image(space, 0, 0);
  //image(vortexBackground, 0, 0);
  stroke(255);
  strokeWeight(1);



  //fill(ufoFill);
  //rect(ufoX, ufoY, 50, 50);

  randomExterminate = random(3.00f);

  //line(0, floor, width, floor);
  fill(0);

  //xPos = xPos + vx;
  //yPos= yPos + vy;

  if (inPlayMode)
  {
    rectX = rectX+ vx;
    dalekY = dalekY + vy;
    vy=vy+0.15f; //dalek gravity
    //if (dalekY> floor-90) {
    //  dalekY=floor-90;
    //  vy=0;
    //}

    if (blasting) {
      dalekBlast();

      //exterminate.play();
      blastCounter --;



      //while (blasting){
      //  if (dalekY == ufoY){
      //  ufoFill = 0;
      //  println("Shooted");
      //}
      //}


      //if (dalekY == ) {

      //}
    }

    if (blastCounter == 0) {
      blasting = false;
      blastCounter = 40;
      stroke(255);
      strokeWeight(1);
    }
    //for (xPos = 500; xPos >=0; xPos= xPos-50){
    //if (xPos ==-50) {
    //  xPos = 500;
    //}

    if (rectX == -50) {
      rectX = width; // scrolling rectangle
      offScreen=true;
      //drawShape(random(1.00));
    }

    //if (offScreen == true){
    //  drawShape(random(3.00));
    //  offScreen = false;
    //  println(random(3.00));
    //}


    //rect(xPos, 250, 50, 50);


    //rect(rectX, rectY, 50, 70); // pipe ish
    int i; // these 3 draw the circle in the pipes and tries to stop 2 circles being drawn
    i = 0;

    drawGuide();







    for (i = 0; i < 2; i++) {
      image(pipe, wx[i], wy[i] - (pipe.height/2+ 500)); //draw the pipes
      image(pipe, wx[i], wy[i] + (pipe.height/2 - 200));

      //text(wy[i], wx[i], wy[i]);

      if (wx[i] < -20) { // scrolling and reappearing pipes
        wy[i] = (int)random(200, height-200);
        wx[i] = width;
        collected = false; //to readd the gem
      }
      if (dalekX == wx[i]) { // tryna get the score to work. works but double
        score= score+1; // treats one pipe as 2 for some reason. So
      }

      if ((abs(dalekX-wx[i])<25 && abs(dalekY-wy[i])>100)) {// crappy bird code. Collision
        if (!collided) {
          lives = lives-1;
          wy[i] = (int)random(200, height-200); // moves the pipe you hit to the width
          wx[i] = width; //does cause multiple pipes if hit
          killerCounter = killerCounter-1; //useless rn
          hitSound();
          collided = true;
          collided = false;
        }
        //else {
        if (dalekY>height||dalekY<0) {
          if (!collided) {
            lives = 0;
            collided = true;
            collided = false; 
            falling = 1;
          }
          //text("Falling!", width/2, height/2);
        } else {
          collided = false;
          killerCounter=500; //my attempt to avoid instant death. Doesnt work
        }
      }

      wx[i] -= 5; //speed the pipes scroll
      vx =-5; // move the obstacle **
    }

    //}         

    if (dalekX == obstacleX) {
      if (dalekY == obstacleY) {
        if (!collided) {
          gameOver();
        }
      }
    }



    //if (dalekY>height||dalekY<0||(abs(dalekX-wx[i])<25 && abs(dalekY-wy[i])>100)) {// crappy bird code
    //  if (!collided) {
    //    lives = lives-1;
    //    wy[i] = (int)random(200, height-200); // moves the pipe you hit to the width
    //    wx[i] = width; //does cause multiple pipes if hit
    //    killerCounter = killerCounter-1;
    //    collided = true;
    //    collided = false;
    //  } else {
    //    collided = false;
    //    killerCounter=500; //my attempt to avoid instant death. Doesnt work
    //  }
    //}

    //println("Score : " + score);
    fill(255);
    textSize(20);
    text("Score: "+ score/2, width-120, 30); 
    text("Lives: "+ lives, width - 120, 50);


    //image(dalek, 100, yPos, 90, 90);
    drawDalek();
    //println(mouseX, mouseY);
    //collision();
    //println("Lives: " + lives);

    if (randomExterminate <= 0.009f) {
      exterminate.play();
    }
    if (randomExterminate >= 0.01f) {
      if (randomExterminate <= 0.015f) {
        message2.play();
      }
    }
    //println(randomExterminate);
    shooting();
    shootingCollision();
    gameOver();


    //hitSound();
    //if (key== 'r'){
    //  setup(); 
    //  draw();
    // }

    if (!inPlayMode) {
      gameOver();
    }
  }
}

public void drawDalek() {
  image(dalek, dalekX, dalekY, 90, 90);
  if (lives == 2) {
    image(dalek2, dalekX, dalekY, 90, 90);
  } else {
    if (lives == 1) {
      image(dalek1, dalekX, dalekY, 90, 90);
    }
    if (lives == 0 || lives <0) {
      image(dalekDead, dalekX, dalekY, 90, 90);
    }
  }
}

public void dalekBlast() {
  stroke(0, 100, 200);
  strokeWeight(4);
  line(dalekX+81, dalekY+35, dalekX+width, dalekY+38);
}

public void drawGuide() { // circle inside pipe gap
  stroke(255);
  strokeWeight(1);
  int i=0;
  if (dalekX+-50 == (wx[i]+6) +- 50) {// this doesnt work man!!!
    gemXmet = true;
  }
  if (dalekY+-50 == (wy[i] + 50) +- 50) {
    gemYmet = true;
  }
  if (gemXmet && gemYmet == true) {
    collected = true;
    text("Got it", CENTER, CENTER);
  }
  


  if (!collected) {
    image(gem, wx[i] +6, wy[i] + 50, 30, 30);
  }
}

public void keyPressed() {

  if (key == ' ') {
    vy = -5.5f;//how high he jumps

    //yPos = yPos-velocityJump;
  }

  if (key == 'z') {
    blasting = true; 

    blast.play();
  }
  if (key== 'r') {
    //frameRate(60);
    //setup(); 
    //draw();
    inPlayMode = true;
    println('r');
  }
}

public void mousePressed() {
  vy = -5.5f;
}
//void collision()
//{
//  if (dist (dalekX, dalekY, rectX, rectY) < 75) {
//    fill(255);
//    //lives= lives-0.0095;
//    if (!collided) {
//      lives = lives-1;
//      collided = true;
//    }

//    //println("Game over");
//    //text("Game Over", 200, 200);
//  } else {
//    collided = false;
//  }
//}
public void gameOver() {
  if (lives==0 || lives <=0) {
    if (falling == 1) {
      lives=0;
      textSize(30);
      fill(255);
      text("ALL WE HAD TO DO WAS GO", width/2-210, height/2);
      text("THROUGH THE DAMN PIPES CJ!!", width/2-230, height/2 + 30); 
      text("Final Score: " + score/2, width/2-100, height/2 + 60); 
      die.play();
      frameRate(0);
      //if (score > 3){ // tryna use different sound if higher score, 
      //  goodScore.play(); // but it plays to fast like the alert sound file
      //}  // when you hit the pipe
      //else{
      ////  die.play();}
      //goodScore.play(); }
      if (lives == -1) {
        lives = 0;
      }
    } else {
      //inPlayMode = false;
      lives=0;
      textSize(30);
      fill(255);
      text("YOU HAVE BEEN EXTERMINATED!!", width/2-240, height/2);
      text("Final Score: " + score/2, width/2-100, height/2 + 30); 
      //die.play();
      //goodScore.play();
      if (score > 82) { // tryna use different sound if higher score, 
        goodScore.play(); // but it plays to fast like the alert sound file
      }  // when you hit the pipe
      else {
        die.play();
      }
      //goodScore.play(); }
      noLoop();
      //frameRate(0);
      if (lives == -1) {
        lives = 0;
      }
    }
  }





  //if (key== 'r'){
  //  frameRate(60);
  // setup(); 
  // draw();
  // inPlayMode = true;
  //}
}


//void drawShape(float randomShape){
//  if (randomShape<=1){
//      rect(rectX, rectY, 50, 70);
//  }
//  if (randomShape>=1){
//      print("triangle");
//  }
//}
public void hitSound() {
  if (lives>=1) { //stops the sound playing at game over
    hit.play();
  }
}

public void menu()
{
  fill(100, 0, 50);
  rect(width/2, height/2, 100, 100);
  text( "Retry", width/2, height/2);
}


public void shooting() {
  fill(100);
  image(asteroid, obstacleX, obstacleY, 100, 75);
  obstacleX = obstacleX + vx;
  if (obstacleX <= -50) {
    obstacleX = 550;
    obstacleY = random(height);
  }
}

public void shootingCollision() {
  if (key=='z') {
    if (dalekY == obstacleY +-50) {
      println("Shot!");
    } else {
      //println("missed");
    }
    if (abs(dalekX + obstacleX) >dalekX && abs(dalekY- obstacleY) >= 10) { //shoot the meteor
    println("HIT!!");
    //text( "Retry", width/2, height/2);
  }
  else{
    obstacleX = 550;}// move the meteor when hit
    //obstacleY = random(height); // still randomize y position, otherwise it will stay the same place
  }
  
  

  //vx -=5;
}
  public void settings() {  size(500, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Rebel_Dalek_Escape" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
