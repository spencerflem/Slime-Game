package com.slime.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Slime extends CollidableActor {
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
        body.setUserData(this);
        
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
        //ACTIONS FOR VISUALS?
        //OR NO ACTIONS AT ALL?
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
    
    public void beginContact(Contact contact) {
    	
    }
}
