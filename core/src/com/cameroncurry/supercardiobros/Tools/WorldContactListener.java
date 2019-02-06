package com.cameroncurry.supercardiobros.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.cameroncurry.supercardiobros.Sprites.Bro;
import com.cameroncurry.supercardiobros.Sprites.InteractiveTileObject;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int collisionDefinition = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        System.out.println(fixA.getFilterData().categoryBits);
        System.out.println(fixB.getFilterData().categoryBits);
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch(collisionDefinition){
            case SuperCardioBros.HEAD_BIT | SuperCardioBros.BRICK_BIT:
            case SuperCardioBros.HEAD_BIT | SuperCardioBros.COIN_BIT:
                if(fixA.getFilterData().categoryBits == SuperCardioBros.HEAD_BIT){
                    System.out.println("Hit Head!");
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                }
                else{
                    System.out.println("Hit Head!");
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                }
                break;
            case SuperCardioBros.CARDIO_BIT | SuperCardioBros.ENEMY_BIT:
                System.out.println("MARIO DIED!");
                if(fixA.getFilterData().categoryBits == SuperCardioBros.CARDIO_BIT){
                    ((Bro) fixA.getUserData()).died();


                } else{
                    ((Bro) fixB.getUserData()).died();

                }


        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
