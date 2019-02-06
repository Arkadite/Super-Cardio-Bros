package com.cameroncurry.supercardiobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cameroncurry.supercardiobros.Screens.IngameScreen;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public class Bro extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    public World gameWorld;
    public Body b2body;
    //correct
    private Animation<TextureRegion> broJump;
    private Animation<TextureRegion> broRun;
    private boolean runningRight;
    private float stateTimer;
    public boolean isDead;
    public boolean redefineNow;
    //^^

    private TextureRegion broStand;
    private TextureRegion broDead;




    public Bro(IngameScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.gameWorld = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        redefineNow = false;
        //^^^
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
        }
        broRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
        }
        broJump = new Animation<TextureRegion>(0.1f, frames);


        establishBro();
        broStand = new TextureRegion(getTexture(), 0, 11, 16, 16);
        //broDead = new TextureRegion(getTexture(), 0, 11, 16, 16);
        broDead = new TextureRegion(getTexture(), 0, 11, 16, 16);

        // the following 16 is hardcoded until we fix the texture pack, then change it after.
        setBounds(0,0, 16 / SuperCardioBros.PIXELS_PER_METER, 16 / SuperCardioBros.PIXELS_PER_METER);
        setRegion(broStand);
    }
    public void update(float dt){
        if(isDead){

            setPosition((b2body.getPosition().x - getWidth() / 2), b2body.getPosition().y - getHeight() / 2 - 0.045f);
        } else{
            setPosition((b2body.getPosition().x - getWidth() / 2), b2body.getPosition().y - getHeight() / 2 );
        }
        setRegion(getFrame(dt));


    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch(currentState){
            case JUMPING:
                region = broJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = broRun.getKeyFrame(stateTimer, true);
                break;
            case DEAD:
                isDead = true;
                region = broDead;
                break;
            case FALLING:
            case STANDING:

            default:
                region = broStand;
                break;
        }


        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        } else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(isDead){
            if(b2body.getPosition().y < -0.5 || b2body.getPosition().x > 32){
                b2body.setLinearVelocity(new Vector2(0,0));
            }
            return State.DEAD;
        } else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0){
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        } else{
            return State.STANDING;
        }
    }

    public void establishBro(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((32 / SuperCardioBros.PIXELS_PER_METER) + 2, 32 / SuperCardioBros.PIXELS_PER_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = gameWorld.createBody(bdef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperCardioBros.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = SuperCardioBros.CARDIO_BIT;
        fixtureDef.filter.maskBits = SuperCardioBros.GROUND_BIT | SuperCardioBros.COIN_BIT | SuperCardioBros.BRICK_BIT|
        SuperCardioBros.ENEMY_BIT|
        SuperCardioBros.OBJECT_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / SuperCardioBros.PIXELS_PER_METER, 5 / SuperCardioBros.PIXELS_PER_METER), new Vector2(2 / SuperCardioBros.PIXELS_PER_METER, 5 / SuperCardioBros.PIXELS_PER_METER));
        fixtureDef.filter.categoryBits = SuperCardioBros.HEAD_BIT;
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void died(){
        if(isDead){

        } else{
            isDead = true;
            redefineNow = true;
            setBounds(0,0, 32 / SuperCardioBros.PIXELS_PER_METER, 6 / SuperCardioBros.PIXELS_PER_METER);

        }

    }
}
