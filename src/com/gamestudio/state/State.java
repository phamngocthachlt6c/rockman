/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.userinterface.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author phamn
 */
public abstract class State {
    
    protected GamePanel gamePanel;
    
    public State(GamePanel gamePanel) {
       this.gamePanel = gamePanel; 
    }
    
    public abstract void Update();
    public abstract void Render();
    public abstract BufferedImage getBufferedImage();
    
    public abstract void setPressedButton(int code);
    public abstract void setReleasedButton(int code);
}
