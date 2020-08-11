package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParticularObjectManager {

    protected List<ParticularObject> particularObjects;

    private GameWorldState gameWorld;
    
    public ParticularObjectManager(GameWorldState gameWorld){
        
        particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld = gameWorld;
        
    }
    
    public GameWorldState getGameWorld(){
        return gameWorld;
    }
    
    public void addObject(ParticularObject particularObject){
        
        
        synchronized(particularObjects){
            particularObjects.add(particularObject);
        }
        
    }
    
    public void RemoveObject(ParticularObject particularObject){
        synchronized(particularObjects){
        
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject object = particularObjects.get(id);
                if(object == particularObject)
                    particularObjects.remove(id);

            }
        }
    }
    
    public ParticularObject getCollisionWidthEnemyObject(ParticularObject object){
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject objectInList = particularObjects.get(id);

                if(object.getTeamType() != objectInList.getTeamType() && 
                        object.getBoundForCollisionWithEnemy().intersects(objectInList.getBoundForCollisionWithEnemy())){
                    return objectInList;
                }
            }
        }
        return null;
    }
    
    public void UpdateObjects(){
        
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject object = particularObjects.get(id);
                
                
                if(!object.isObjectOutOfCameraView()) object.Update();
                
                if(object.getState() == ParticularObject.DEATH){
                    particularObjects.remove(id);
                }
            }
        }

        //System.out.println("Camerawidth  = "+camera.getWidth());
        
    }
    
    public void draw(Graphics2D g2){
        synchronized(particularObjects){
            for(ParticularObject object: particularObjects)
                if(!object.isObjectOutOfCameraView()) object.draw(g2);
        }
    }
	
}
