package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import com.gamestudio.effect.Animation;
import com.gamestudio.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BlueFire extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public BlueFire(float x, float y, GameWorldState gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("bluefire");
        backBulletAnim.flipAllImage();
    }

    
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
            // TODO Auto-generated method stub
        if(getSpeedX() > 0){
            if(!forwardBulletAnim.isIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3){
                forwardBulletAnim.setIgnoreFrame(0);
                forwardBulletAnim.setIgnoreFrame(1);
                forwardBulletAnim.setIgnoreFrame(2);
            }
                
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3){
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                backBulletAnim.setIgnoreFrame(2);
            }
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
            // TODO Auto-generated method stub
        if(forwardBulletAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0))
            setPosX(getPosX() + getSpeedX());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet set behurt for enemy");
        }
    }

    @Override
    public void attack() {}

}
