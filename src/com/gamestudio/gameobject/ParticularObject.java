package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import com.gamestudio.effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject {

    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;
    
    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2;
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;
    private int state = ALIVE;
    
    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;
    
    private int damage;
    
    private int direction;
    
    protected Animation behurtForwardAnim, behurtBackAnim;
    
    private int teamType;
    
    private long startTimeNoBeHurt;
    private long timeForNoBeHurt;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorldState gameWorld){

        // posX and posY are the middle coordinate of the object
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setMass(mass);
        setBlood(blood);
        
        direction = RIGHT_DIR;

    }
    
    public void setTimeForNoBehurt(long time){
        timeForNoBeHurt = time;
    }
    
    public long getTimeForNoBeHurt(){
        return timeForNoBeHurt;
    }
    
    public void setState(int state){
        this.state = state;
    }
    
    public int getState(){
        return state;
    }
    
    public void setDamage(int damage){
            this.damage = damage;
    }

    public int getDamage(){
            return damage;
    }

    
    public void setTeamType(int team){
        teamType = team;
    }
    
    public int getTeamType(){
        return teamType;
    }
    
    public void setMass(float mass){
        this.mass = mass;
    }

    public float getMass(){
            return mass;
    }

    public void setSpeedX(float speedX){
        this.speedX = speedX;
    }

    public float getSpeedX(){
        return speedX;
    }

    public void setSpeedY(float speedY){
        this.speedY = speedY;
    }

    public float getSpeedY(){
        return speedY;
    }

    public void setBlood(int blood){
        if(blood>=0)
                this.blood = blood;
        else this.blood = 0;
    }

    public int getBlood(){
        return blood;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getWidth(){
        return width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public float getHeight(){
        return height;
    }
    
    public void setDirection(int dir){
        direction = dir;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public abstract void attack();
    
    
    public boolean isObjectOutOfCameraView(){
        if(getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
                getPosX() - getGameWorld().camera.getPosX() < -50
            ||getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                    ||getPosY() - getGameWorld().camera.getPosY() < -50)
            return true;
        else return false;
    }
    
    public Rectangle getBoundForCollisionWithMap(){
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth()/2));
        bound.y = (int) (getPosY() - (getHeight()/2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damgeEat){
        setBlood(getBlood() - damgeEat);
        state = BEHURT;
        hurtingCallback();
    }

    @Override
    public void Update(){
        switch(state){
            case ALIVE:
                
                // note: SET DAMAGE FOR OBJECT NO DAMAGE
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if(object!=null){
                    
                    
                    if(object.getDamage() > 0){

                        // switch state to fey if object die
                        
                        
                        System.out.println("eat damage.... from collision with enemy........ "+object.getDamage());
                        beHurt(object.getDamage());
                    }
                    
                }
                
                
                
                break;
                
            case BEHURT:
                if(behurtBackAnim == null){
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if(getBlood() == 0)
                            state = FEY;
                    
                } else {
                    behurtForwardAnim.Update(System.nanoTime());
                    if(behurtForwardAnim.isLastFrame()){
                        behurtForwardAnim.reset();
                        state = NOBEHURT;
                        if(getBlood() == 0)
                            state = FEY;
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                
                break;
                
            case FEY:
                
                state = DEATH;
                
                break;
            
            case DEATH:
                
                
                break;
                
            case NOBEHURT:
                System.out.println("state = nobehurt");
                if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt)
                    state = ALIVE;
                break;
        }
        
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);
    
    public void hurtingCallback(){};
	
}
