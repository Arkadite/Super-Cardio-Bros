package com.cameroncurry.supercardiobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public class Goomba extends Enemy {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    public Goomba(IngameScreen screen, float x, float y){
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i<2; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / SuperCardioBros.PIXELS_PER_METER, 16 / SuperCardioBros.PIXELS_PER_METER);
    }

    public void update(float dt) {
        stateTime += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
        int rand = (int) (Math.random()* 125 ) + 1;
        if(rand <= 3){
            velocity = (b2body.getLinearVelocity().scl(new Vector2(-1,1)));
        }
        //b2body.setLinearVelocity(b2body.getLinearVelocity().scl(new Vector2(-1,1)));
                //give us 1,0
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperCardioBros.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = SuperCardioBros.ENEMY_BIT;
        fixtureDef.filter.maskBits = SuperCardioBros.GROUND_BIT |
                SuperCardioBros.COIN_BIT |
                SuperCardioBros.BRICK_BIT|
                SuperCardioBros.ENEMY_BIT|
                SuperCardioBros.OBJECT_BIT|
                SuperCardioBros.CARDIO_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }
}
