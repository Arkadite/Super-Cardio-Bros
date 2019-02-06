package com.cameroncurry.supercardiobros.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cameroncurry.supercardiobros.SuperCardioBros;


public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTime;
    private float timeCounter;
    public static Integer scoreCounter;

    Label countdownLabel;
    static Label  playerScoreLabel;
    Label currentTimeLabel;
    Label currentLevelLabel;
    Label worldLabel;
    Label cardioLabel;

    public Hud(SpriteBatch sb){
        worldTime = 300;
        timeCounter = 0;
        scoreCounter = 0;

        viewport = new FitViewport(SuperCardioBros.VIRTUAL_WIDTH, SuperCardioBros.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel = new Label(String.format("%03d", worldTime), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerScoreLabel = new Label(String.format("%06d", scoreCounter), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentTimeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentLevelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        cardioLabel = new Label("BRO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(cardioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(currentTimeLabel).expandX().padTop(10);
        table.row();
        table.add(playerScoreLabel).expandX();
        table.add(currentLevelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }


    public void update(float dt){
        timeCounter += dt;
        if(timeCounter >= 1){
            worldTime--;
            countdownLabel.setText(String.format("%03d", worldTime));
            timeCounter = 0;
        }
    }

    public static void addScore(int value){
        scoreCounter += value;
        playerScoreLabel.setText(String.format("%06d", scoreCounter));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
