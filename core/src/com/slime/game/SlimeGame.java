package com.slime.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/*
 * Todo list:
 * 	box2d
 * 	click+drag
 * 	rocket launcher
 * 	kryonet
 * 	hp/gameplay
 *
*/

public class SlimeGame extends ApplicationAdapter {
	
	public class Slime extends Actor {
		TextureRegion region;
		
	    public Slime() {
	        region = new TextureRegion(new Texture(Gdx.files.internal("slimeball.png")));
	        setX(20);
	        setY(20);
	        setHeight(50);
	        setWidth(50);
	    }

	    @Override
	    public void draw (Batch batch, float parentAlpha) {
	        Color color = getColor();
	        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
	            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	    }
	}
	
	private Stage stage;
	
	@Override
	public void create() {
		stage = new Stage(new StretchViewport(640,480));
		Gdx.input.setInputProcessor(stage);
		
		Slime slime = new Slime();
		stage.addActor(slime);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}