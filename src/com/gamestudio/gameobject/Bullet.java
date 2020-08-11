package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import java.awt.Graphics2D;

public abstract class Bullet extends ParticularObject {

    public Bullet(float x, float y, float width, float height, float mass, int damage, GameWorldState gameWorld) {
            super(x, y, width, height, mass, 1, gameWorld);
            setDamage(damage);
    }
    
    public abstract void draw(Graphics2D g2d);

    public void Update(){
        super.Update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.beHurt(getDamage());
            System.out.println("Bullet set behurt for enemy");
        }
    }
    
}
