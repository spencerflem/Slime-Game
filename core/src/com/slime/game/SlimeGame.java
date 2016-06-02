package com.slime.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/*
 * Todo list:
 * 	click+drag
 * 	aim prediction
 * 	rocket launcher
 * 	kryonet
 * 	hp/gameplay
 *
*/


/*
 * short term todo:
 * add world
 * add click n drag
 * add sticking to walls
 */

public class SlimeGame extends ApplicationAdapter {
	
	private Stage stage;
	private World world;
	private float accumulator = 0;
	
	private float GRAVITY = -10;
	private float BOX_TO_WORLD = 100f;
	private float WORLD_TO_BOX = 1/BOX_TO_WORLD;
	private float MAX_STEP_SIZE = 0.25f;
	private float STEP_SIZE = 1/60f;
	
	@Override
	public void create() {
		stage = new Stage(new StretchViewport(640,480));
		Gdx.input.setInputProcessor(stage);
		world = new World(new Vector2(0, GRAVITY), true);
		ContactPasser contactPasser = new ContactPasser(); //default as arg?
		world.setContactListener(contactPasser);
		Slime slime = new Slime();
		
		//move somewhere else?
		stage.addListener(new InputListener() {
		    public boolean keyDown (InputEvent event, int keyCode) {
		        if(slime.body.getGravityScale() != 0) {
		        	slime.body.setGravityScale(0);
		        }
		        else {
		        	slime.body.setGravityScale(1);
		        }
		        return true;
		    }
		});
		
		
		stage.addActor(slime);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();
		stepPhysics(delta);
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	private void stepPhysics(float delta) {
		float frameTime = Math.min(delta, MAX_STEP_SIZE);
		accumulator += frameTime;
		while (accumulator >= STEP_SIZE) {
			world.step(STEP_SIZE, 6, 2); //6, 2 are magic numbers twiddle at own risk
			accumulator -= STEP_SIZE;
		}
	}
}