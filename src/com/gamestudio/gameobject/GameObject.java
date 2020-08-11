package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;

public abstract class GameObject {

	private float posX;
	private float posY;
	
	private GameWorldState gameWorld;
	
	public GameObject(float x, float y, GameWorldState gameWorld){
		posX = x;
		posY = y;
		this.gameWorld = gameWorld;
	}
	
	public void setPosX(float x){
		posX = x;
	}
	
	public float getPosX(){
		return posX;
	}
	
	public void setPosY(float y){
		posY = y;
	}
	
	public float getPosY(){
		return posY;
	}
	
	public GameWorldState getGameWorld(){
		return gameWorld;
	}
	
	public abstract void Update();
	
}
