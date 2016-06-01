package com.slime.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/*
 * Todo list:
 * 	box2d
 * 	click+drag
 * 	aim prediction
 * 	rocket launcher
 * 	kryonet
 * 	hp/gameplay
 *
*/

public class SlimeGame extends ApplicationAdapter {
	
	private Stage stage;
	private World world;
	private float accumulator = 0;
	
	private float BOX_TO_WORLD = 100f;
	private float WORLD_TO_BOX = 1/BOX_TO_WORLD;
	private float MAX_STEP_SIZE = 0.25f;
	private float STEP_SIZE = 1/60f;
	
	public class Slime extends Actor {
		TextureRegion region;
		Body body;
		Runnable flapRunnable;
		
	    public Slime() {
	        region = new TextureRegion(new Texture(Gdx.files.internal("slimeball.png")));
	        
	        //needs better place
	        setWidth(50); //use pixel or box_to_world shenanagains
	        setHeight(50);
	        
	        BodyDef bodyDef = new BodyDef(); //create new one every time?
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        bodyDef.position.set(300 * WORLD_TO_BOX, 300 * WORLD_TO_BOX);
	        body = world.createBody(bodyDef);
	        
	        CircleShape circle = new CircleShape();
	        circle.setRadius(6f);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = circle;
	        fixtureDef.density = 0.5f; 
	        fixtureDef.friction = 0.4f;
	        fixtureDef.restitution = 0.6f;

	        Fixture fixture = body.createFixture(fixtureDef);
	        
	        circle.dispose();
	        
	        //WOULD COLLISION BE AN ACTION?
			flapRunnable = new Runnable() { //move to other place
				@Override
				public void run() {
					body.setLinearVelocity(new Vector2(0, 3));
				}};
				
			//orginize better
			setBounds(0, 0, 50, 50); //height vs bounds?
	    }
	    
	    @Override
	    public void act(float delta) {
	    	super.act(delta);
	    	setX(body.getPosition().x * BOX_TO_WORLD);
	    	setY(body.getPosition().y * BOX_TO_WORLD);
	    	setRotation(MathUtils.radiansToDegrees * body.getAngle());
	    }
		
	    @Override
	    public void draw(Batch batch, float parentAlpha) {
	        Color color = getColor();
	        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
	            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	    }
	}
	
	@Override
	public void create() {
		stage = new Stage(new StretchViewport(640,480));
		Gdx.input.setInputProcessor(stage);
		world = new World(new Vector2(0, -10), true); //gravity as variable?
		Slime slime = new Slime();
		stage.addListener(new InputListener() {
		    public boolean keyDown (InputEvent event, int keyCode) {
		        slime.addAction(Actions.run(slime.flapRunnable));
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