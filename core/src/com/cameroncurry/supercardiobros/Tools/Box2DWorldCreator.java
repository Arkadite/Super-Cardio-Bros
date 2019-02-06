package com.cameroncurry.supercardiobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cameroncurry.supercardiobros.Sprites.Brick;
import com.cameroncurry.supercardiobros.Sprites.Coin;
import com.cameroncurry.supercardiobros.SuperCardioBros;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;


public class Box2DWorldCreator {

    public Box2DWorldCreator(IngameScreen screen){
        World gameWorld = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // This creates the ground, body, and fixtures. It ensures that the ground will not move when interacted with.
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            //set body position, scaled to given pixel
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / SuperCardioBros.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / SuperCardioBros.PIXELS_PER_METER);

            body = gameWorld.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SuperCardioBros.PIXELS_PER_METER, rect.getHeight() / 2 / SuperCardioBros.PIXELS_PER_METER);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //Creates the pipes body and fixtures and ensures that the pipes will not move when interacted with.
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            //set body position, scaled to given pixel
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / SuperCardioBros.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / SuperCardioBros.PIXELS_PER_METER);

            body = gameWorld.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SuperCardioBros.PIXELS_PER_METER, rect.getHeight() / 2 / SuperCardioBros.PIXELS_PER_METER);
            fdef.shape = shape;
            fdef.filter.categoryBits = SuperCardioBros.OBJECT_BIT;
            body.createFixture(fdef);
        }
        //Creates the body and fixtures for the brick layer and ensures the bricks will not move when interacted with
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rect);
        }
        //Creates coin body and fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
        }
    }
}
