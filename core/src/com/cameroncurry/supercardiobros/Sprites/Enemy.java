package com.cameroncurry.supercardiobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected IngameScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(IngameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, -1);
    }

    protected abstract void defineEnemy();

}