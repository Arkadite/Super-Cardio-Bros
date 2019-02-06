package com.cameroncurry.supercardiobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.cameroncurry.supercardiobros.Scenes.Hud;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public class Brick extends InteractiveTileObject {
    public Brick(IngameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(SuperCardioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(SuperCardioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}
