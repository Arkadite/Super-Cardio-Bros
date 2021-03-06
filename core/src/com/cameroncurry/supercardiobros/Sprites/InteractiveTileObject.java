package com.cameroncurry.supercardiobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(IngameScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        //set body position, scaled to given pixel
        bodyDef.position.set( (bounds.getX() + bounds.getWidth() / 2) / SuperCardioBros.PIXELS_PER_METER, (bounds.getY() + bounds.getHeight() / 2) / SuperCardioBros.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        shape.setAsBox(bounds.getWidth() / 2 / SuperCardioBros.PIXELS_PER_METER, bounds.getHeight() / 2 / SuperCardioBros.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }
    public abstract void onHeadHit();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * SuperCardioBros.PIXELS_PER_METER / 16),
                (int)(body.getPosition().y * SuperCardioBros.PIXELS_PER_METER / 16));
    };
}

