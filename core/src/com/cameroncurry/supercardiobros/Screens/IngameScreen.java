package com.cameroncurry.supercardiobros.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.cameroncurry.supercardiobros.Scenes.Hud;
import com.cameroncurry.supercardiobros.Sprites.Bro;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cameroncurry.supercardiobros.Sprites.Goomba;
import com.cameroncurry.supercardiobros.SuperCardioBros;
import com.cameroncurry.supercardiobros.Tools.Box2DWorldCreator;
import com.cameroncurry.supercardiobros.Tools.WorldContactListener;

public class IngameScreen implements Screen{

    private SuperCardioBros game;
    private TextureAtlas textureAtlas;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private Hud hud;
    private Bro player;
    private Goomba goomba;
    private Goomba goomba1;
    private Goomba goomba2;
    private Goomba goomba3;
    private Goomba goomba4;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World gameWorld;
    private Box2DDebugRenderer b2dr;
    private Array<Goomba> goombaArray;


    public IngameScreen(SuperCardioBros game) {
        textureAtlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(SuperCardioBros.VIRTUAL_WIDTH / SuperCardioBros.PIXELS_PER_METER,
                SuperCardioBros.VIRTUAL_HEIGHT / SuperCardioBros.PIXELS_PER_METER, gameCamera);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        //map = mapLoader.load("Level1Edit.tmx");
        map = mapLoader.load("Level1Edit.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / SuperCardioBros.PIXELS_PER_METER);
        gameCamera.position.set((gameViewport.getWorldWidth() / 2) + 2, gameViewport.getWorldHeight() / 2, 0);

        gameWorld = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        player = new Bro(this);

        new Box2DWorldCreator(this);

        gameWorld.setContactListener(new WorldContactListener());
        goombaArray = new Array<Goomba>();

        goomba = new Goomba(this, 0.32f, 2.05f);
        goombaArray.add(goomba);



    }
    public TextureAtlas getAtlas(){
        return textureAtlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //allows user to input the Jump movement, which is W in this case
        if(player.isDead){} else{
            if(Gdx.input.isKeyJustPressed(Input.Keys.W))
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            //allows user to input the Right movement, which is the D key in this case
            if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity() .x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            //allows user to input the Left movement, which is A in this case
            if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity() .x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

        }

    }

    public void update(float dt){
        handleInput(dt);

        gameWorld.step(1/60f, 6, 2);
        player.update(dt);
        int rand = (int) (Math.random()* 125 ) + 1;
        if(rand <= 2){
            int rand2 = (int) (Math.random()* 3 ) + -1;
            goombaArray.add(new Goomba(this, (player.getX() + rand2), 2.05f));
            System.out.println("Goomba Added!!!!");
        }
        for(Goomba goomba:goombaArray){
            goomba.update(dt);
        }




        hud.update(dt);

        //When Bro moves, the game cam will track the movement
        gameCamera.position.x = player.b2body.getPosition().x;


        gameCamera.update();
        renderer.setView(gameCamera);

    }

    @Override
    public void render(float delta) {
        update(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        //renders the Box2DDebug

        //b2dr.render(gameWorld, gameCamera.combined);

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        if(goombaArray != null){
            for(Goomba goomba:goombaArray){
                goomba.draw(game.batch);
            }
        }


        game.batch.end();

        //draws the HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);

    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return gameWorld;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        gameWorld.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
