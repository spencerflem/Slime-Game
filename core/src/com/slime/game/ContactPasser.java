package com.slime.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactPasser implements ContactListener{
	
	@Override
	public void beginContact(Contact contact) {
		boolean handled = true;
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureA().getBody().getUserData().getClass())) {
			CollidableActor actorA = (CollidableActor) contact.getFixtureA().getBody().getUserData();
			actorA.beginContact(contact);
		}
		else {
			handled = false;
		}
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureB().getBody().getUserData().getClass())) {
			CollidableActor actorB = (CollidableActor) contact.getFixtureB().getBody().getUserData();
			actorB.beginContact(contact);
		}
		else {
			handled = false;
		}
		
		if (!handled) {
			//SOME DEFAULT HANDLER
		}
	}

	@Override
	public void endContact(Contact contact) {
		boolean handled = true;
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureA().getBody().getUserData().getClass())) {
			CollidableActor actorA = (CollidableActor) contact.getFixtureA().getBody().getUserData();
			actorA.endContact(contact);
		}
		else {
			handled = false;
		}
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureB().getBody().getUserData().getClass())) {
			CollidableActor actorB = (CollidableActor) contact.getFixtureB().getBody().getUserData();
			actorB.endContact(contact);
		}
		else {
			handled = false;
		}
		
		if (!handled) {
			//SOME DEFAULT HANDLER
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		boolean handled = true;
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureA().getBody().getUserData().getClass())) {
			CollidableActor actorA = (CollidableActor) contact.getFixtureA().getBody().getUserData();
			actorA.preSolve(contact, oldManifold);
		}
		else {
			handled = false;
		}
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureB().getBody().getUserData().getClass())) {
			CollidableActor actorB = (CollidableActor) contact.getFixtureB().getBody().getUserData();
			actorB.preSolve(contact, oldManifold);
		}
		else {
			handled = false;
		}
		
		if (!handled) {
			//SOME DEFAULT HANDLER
		}
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		boolean handled = true;
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureA().getBody().getUserData().getClass())) {
			CollidableActor actorA = (CollidableActor) contact.getFixtureA().getBody().getUserData();
			actorA.postSolve(contact, impulse);
		}
		else {
			handled = false;
		}
		
		if(CollidableActor.class.isAssignableFrom(contact.getFixtureB().getBody().getUserData().getClass())) {
			CollidableActor actorB = (CollidableActor) contact.getFixtureB().getBody().getUserData();
			actorB.postSolve(contact, impulse);
		}
		else {
			handled = false;
		}
		
		if (!handled) {
			//SOME DEFAULT HANDLER
		}
	}

}
