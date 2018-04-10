import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import burlap.debugtools.RandomFactory;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.SGDomain;

public class ExpGridMechanics extends JointActionModel {
	
	Random rand;
	Domain domain;
	double pMoveThroughSWall;
	boolean bothHaveBall = false;
	
	/**
	 * Initializes the mechanics for the given domain and sets the semi-wall pass through probability to 0.5;
	 * @param d the domain object
	 */
	public ExpGridMechanics(Domain d){
		rand = RandomFactory.getMapped(0);
		domain = d;
		pMoveThroughSWall = 0.5;
	}

	
	@Override
public List<TransitionProbability> transitionProbsFor(State s, JointAction ja) {
		
		System.out.println("Inside transition probability");
		// TODO Auto-generated method stub
		boolean bHasBall = true;
		
		List <TransitionProbability> tps = new ArrayList<TransitionProbability>();
		List <GroundedSGAgentAction> gsas = ja.getActionList();
		
		//need to force no movement when trying to enter space of a noop agent
		List <Location2> previousLocations = new ArrayList<ExpGridMechanics.Location2>();
		List <Location2> noopLocations = new ArrayList<ExpGridMechanics.Location2>();
		
		for(GroundedSGAgentAction gsa : gsas){
			Location2 loc = this.getLocation(s, gsa.actingAgent);
			
			previousLocations.add(loc);
			
			if(gsa.action.actionName.equals(ExpGridGame2.ACTIONNOOP)){
				noopLocations.add(loc);
			}
			
		}
		List<ObjectInstance> agentObjectList= s.getObjectsOfClass(ExpGridGame2.CLASSAGENT);
		ObjectInstance agentAObject  = agentObjectList.get(0);
		Value hasBall = agentAObject.getValueForAttribute(ExpGridGame2.ATTBALLOWNER);
		String stringValHasBall = hasBall.getStringVal();
		//System.out.println(s1);
		boolean aHasBall = Boolean.parseBoolean(stringValHasBall);
		if(aHasBall){
			bHasBall = false;
		}
		
		
		//List <List<Location2>> possibleOutcomes = new ArrayList<List<Location2>>();
		List<Location2> newLocation = new ArrayList<Location2>();
		
		for(int i = 0; i < ja.size(); i++){
			Location2 loc = previousLocations.get(i);
			GroundedSGAgentAction gsa = gsas.get(i);
			if(i == 0){
				Location2 loc1 = this.attemptedDelta(gsa.action.actionName, aHasBall);
				newLocation.add(loc1);
			}
			
			else {
				Location2 loc2 = this.attemptedDelta(gsa.action.actionName, bHasBall);
				newLocation.add(loc2);
			}
			//possibleOutcomes.add(this.getPossibleLocationsFromWallCollisions(s, loc, this.attemptedDelta(gsa.action.actionName,), noopLocations));
		}
		
		List<Location2> finalPositions = resolveCollisions(previousLocations, newLocation);
		State ns = s.copy();
		for(int i=0; i <2;i++){
			
			GroundedSGAgentAction gsa = gsas.get(i);
			ObjectInstance agent = ns.getObject(gsa.actingAgent);
			agent.setValue(ExpGridGame2.ATTX, (finalPositions.get(i)).x);
			agent.setValue(ExpGridGame2.ATTY, (finalPositions.get(i)).y);
			agent.setValue(ExpGridGame2.ATTBALLOWNER, (finalPositions.get(i)).ballOwnerShip);
			
		}
		TransitionProbability tp = new TransitionProbability(s, 0.5);
		tps.add(tp);
		
		/*ObjectInstance agent = ns.getObject(gsa.actingAgent);
		agent.setValue(GridGame.ATTX, loc.x);
		agent.setValue(GridGame.ATTY, loc.y);*/
		
		/*TransitionProbability tp = new TransitionProbability(s, 0.25);
		tps.add(tp);*/
		
		return tps;
	
	}
	
	
	
	
	/**
	 * A class for holding a location and a probability associated with that location.
	 * @author James MacGlashan
	 *
	 */
	class Location2Prob{
		
		/**
		 * The location
		 */
		public Location2 l;
		
		/**
		 * The probability
		 */
		public double p;
		
		
		/**
		 * Initializes with the given location and probability
		 * @param l the location
		 * @param p the probability
		 */
		public Location2Prob(Location2 l, double p){
			this.l = l;
			this.p = p;
		}
		
	}
	
	
	/**
	 * A class for holding the joint probability for a particular set of location outcomes for each agent.
	 * @author James MacGlashan
	 *
	 */
	class LocationSetProb{
		
		/**
		 * The location outcomes for each agent
		 */
		public List <Location2> locs;
		
		/**
		 * The probability for this outcome sequence
		 */
		public double p;
		
		
		/**
		 * Initializes with a list of outcome locations for each agent and the probability of that joint outcome
		 * @param locs the location outcomes
		 * @param p the joint probability
		 */
		public LocationSetProb(List <Location2> locs, double p){
			this.locs = locs;
			this.p = p;
		}
		
	}


/**
	 * Returns the x-y position of an agent stored in a Location2 object.
	 * @param s the state in which the agent exists
	 * @param agentName the name of the agent.
	 * @return a {@link GridGameStandardMechanics.Location2} object containing the agents position in the world.
	 */
	protected Location2 getLocation(State s, String agentName){
		
		ObjectInstance a = s.getObject(agentName);
		//boolean ballFlag;
		
		int x = a.getIntValForAttribute(ExpGridGame2.ATTX);
		//System.out.println("X attribute val for " +agentName +"is" +x);
		boolean ballOwnerShip = a.getBooleanValForAttribute(ExpGridGame2.ATTBALLOWNER);
		//System.out.println("ballOwnership " +agentName +"is" +ballOwnerShip);
		
		//int playerNo = a.getIntValForAttribute(ExpGridGame2.ATTPN);
	
		
		Location2 loc = new Location2(a.getIntValForAttribute(ExpGridGame2.ATTX), 
				  a.getIntValForAttribute(ExpGridGame2.ATTY),ballOwnerShip);
		
		return loc;
	}
	
	
	
	
	/**
	 * A class for storing 2 dimensional position information. Add and subtract operations are defined for it.
	 * @author James MacGlashan
	 *
	 */
	class Location2{
		
		/**
		 * The x position
		 */
		public int x;
		
		/**
		 * The y position
		 */
		public int y;
	    public boolean ballOwnerShip;
		/**
		 * Constructs with the given position
		 * @param x the x position
		 * @param y the y position
		 */
		public Location2(int x, int y,boolean ballOwnerShip){
			this.x = x;
			this.y = y;
			this.ballOwnerShip = ballOwnerShip;
			
		}
		
		/**
		 * Constructs a new instance from a previous {@link Location2} instance
		 * @param l the {@link Location2} instance to copy.
		 */
		public Location2(Location2 l){
			this.x = l.x;
			this.y = l.y;
			this.ballOwnerShip = l.ballOwnerShip;
		}
		
		
		@Override
		public boolean equals(Object o){
			if(!(o instanceof Location2)){
				return false;
			}
			
			Location2 ol = (Location2)o;
			
			return x == ol.x && y == ol.y;
			
		}
		
	}
	
	
	
	
	@Override
	protected State actionHelper(State s, JointAction ja) {
		/*System.out.println("Inside action helper");
	    System.out.println("Size of joint action"+ja.size());*/
		List <GroundedSGAgentAction> gsas = ja.getActionList();
		List<String> list1 = ja.getAgentNames();
		boolean bTakesFirstStep = false;
		boolean noOpsAgent0 = false;
		boolean noOpsAgent1 = false;
		
		GroundedSGAgentAction actionToBeTakenByAgent0 = gsas.get(0);
		GroundedSGAgentAction actionToBeTakenByAgent1 = gsas.get(1);
		
		Location2 prevLocAgent0 = this.getLocation(s,"agent0");
		Location2 prevLocAgent1 = this.getLocation(s,"agent1");
		
		int x = randomInteger(0,1);
		
		List<Location2> newLocations =  findingNewPositions(x, prevLocAgent0,
				prevLocAgent1,actionToBeTakenByAgent0,
				actionToBeTakenByAgent1);
		Location2 newLocationAgent0 = newLocations.get(0);
		Location2 newLocationAgent1 = newLocations.get(1);
		
		if(newLocationAgent0.ballOwnerShip == true && newLocationAgent1.ballOwnerShip == true){
			
			if(prevLocAgent0.ballOwnerShip == true){
				newLocationAgent0.ballOwnerShip = true;
				newLocationAgent1.ballOwnerShip = false;
				}
			if(prevLocAgent0.ballOwnerShip == false){
				newLocationAgent0.ballOwnerShip = false;
				newLocationAgent1.ballOwnerShip = true;
				}
			
			
		}
		
		
		ObjectInstance agent0 = s.getObject("agent0");
		agent0.setValue(ExpGridGame2.ATTX, newLocationAgent0.x);
		agent0.setValue(ExpGridGame2.ATTY, newLocationAgent0.y);
		agent0.setValue(ExpGridGame2.ATTBALLOWNER, newLocationAgent0.ballOwnerShip);
		
		ObjectInstance agent1 = s.getObject("agent1");
		agent1.setValue(ExpGridGame2.ATTX, newLocationAgent1.x);
		agent1.setValue(ExpGridGame2.ATTY, newLocationAgent1.y);
		agent1.setValue(ExpGridGame2.ATTBALLOWNER, newLocationAgent1.ballOwnerShip);
		
		return s;
		
		
	}

	
	
	
public List<Location2> findingNewPositions(int agentTakingFirstAction, Location2 prevAgent0Loc,
		                                  Location2 prevAgent1Loc,GroundedSGAgentAction actionToBeTakenByAgent0,
		                                  GroundedSGAgentAction actionToBeTakenByAgent1){
	
	
	Location2 firstActorPrevLoc = prevAgent0Loc;
	Location2 secondActorPrevLoc = prevAgent1Loc;
	GroundedSGAgentAction firstActorAction = actionToBeTakenByAgent0;
	GroundedSGAgentAction secondActorAction = actionToBeTakenByAgent1;
	
	Location2 newLocationFirstActor = prevAgent0Loc;
	Location2  newLocationSecondActor= prevAgent1Loc;
	
	
	
	
	if(agentTakingFirstAction == 1)
	{
		firstActorPrevLoc = prevAgent0Loc;
		secondActorPrevLoc = prevAgent1Loc;
		firstActorAction = actionToBeTakenByAgent0;
		secondActorAction = actionToBeTakenByAgent1;
		newLocationFirstActor = prevAgent1Loc;
		newLocationSecondActor= prevAgent0Loc;
		
	}
	
	boolean noOpsFirstAgent = false;
	
	Location2 changeForFirstAgent = attemptedDelta(firstActorAction.actionName(),firstActorPrevLoc.ballOwnerShip);
		
		if(firstActorAction.actionName().equalsIgnoreCase("noop")){
			noOpsFirstAgent = true;
		}
		
		if (noOpsFirstAgent){
			
			newLocationFirstActor = firstActorPrevLoc;
			
			Location2 changeForSecondAgent = attemptedDelta(secondActorAction.actionName(),secondActorPrevLoc.ballOwnerShip);
			
			newLocationSecondActor = sampleBasicMovement(secondActorPrevLoc, changeForSecondAgent);
			
			
			if( newLocationSecondActor.equals(newLocationFirstActor)){
				
				newLocationSecondActor = secondActorPrevLoc;
				
				if(secondActorPrevLoc.ballOwnerShip){
					newLocationFirstActor.ballOwnerShip = true;
					
				}
			}
		
		   }
		
		
		
		else if (!noOpsFirstAgent) {
			
			newLocationFirstActor = sampleBasicMovement( firstActorPrevLoc, changeForFirstAgent);
			Location2 changeForSecondAgent = attemptedDelta(secondActorAction.actionName(),secondActorPrevLoc.ballOwnerShip);
			
			 
			if(newLocationFirstActor.equals(secondActorPrevLoc)){
					
					if(secondActorAction.actionName().equalsIgnoreCase("noop")){
						
						boolean firstActorHasBall = firstActorPrevLoc.ballOwnerShip;
						// newLocationFirstActor = 
						 
						 if(firstActorHasBall){
							 
							 secondActorPrevLoc.ballOwnerShip = true;
							 firstActorPrevLoc.ballOwnerShip = false;
							 
							// Location2 changeForAgent1 = attemptedDelta(actionToBeTakenByAgent1.actionName(),prevLocAgent0.ballOwnerShip);
							
							//newPositionForAgent1 = prevLocAgent1;
						 }
						 newLocationSecondActor = secondActorPrevLoc;
						 newLocationFirstActor = firstActorPrevLoc;
					 }
					
					if(!secondActorAction.actionName().equalsIgnoreCase("noop")){
					
					
					newLocationSecondActor =sampleBasicMovement( secondActorPrevLoc, changeForSecondAgent);	
					if (newLocationSecondActor.equals(newLocationFirstActor)){
						newLocationFirstActor = firstActorPrevLoc;
						newLocationSecondActor = secondActorPrevLoc;
					   
					  }
					   
				
					}
					
				}
				
			//Case where both are going on athird cell instead of colliding	
			if(!newLocationFirstActor.equals(secondActorPrevLoc)  ){
				    
				  newLocationSecondActor =sampleBasicMovement( secondActorPrevLoc, changeForSecondAgent);
				  if(newLocationSecondActor.equals(newLocationFirstActor)){
					  
					  newLocationSecondActor = secondActorPrevLoc;
				  
				  }
				  
				}
			 
			}
		
	    
	/*if(newLocationSecondActor.ballOwnerShip = newLocationFirstActor.ballOwnerShip ){
			if(prevAgent0Loc.ballOwnerShip){
				newLocationSecondActor.ballOwnerShip = false;
				newLocationFirstActor.ballOwnerShip = true;
				}
			if(prevAgent1Loc.ballOwnerShip){
				newLocationSecondActor.ballOwnerShip = true;
				newLocationFirstActor.ballOwnerShip = false;
				}
			
			
			
		}*/

		
		
	
		List<Location2> newStateList = new ArrayList<Location2>();
		 if( agentTakingFirstAction == 0){
			 newStateList.add(0,newLocationFirstActor);
			 newStateList.add(1,newLocationSecondActor);
			 
		 }
		 else{
			 
			 newStateList.add(0,newLocationSecondActor);
			 newStateList.add(1,newLocationFirstActor);
			 
		 }
	
	
	return newStateList;
	
}
	
	
	
	
	
	/**
	 * Returns the attempted change in position by the agent for the given action. For instance, if the action is north,
	 * it would result in an attempted position change of (0, +1).
	 * @param actionName the action taken.
	 * @return the attempted change in position for the given action.
	 */
	protected Location2 attemptedDelta(String actionName,boolean ballOwnerShip){
		
		if(actionName.equals(GridGame.ACTIONNORTH)){
			return new Location2(0, 1,ballOwnerShip);
		}
		else if(actionName.equals(GridGame.ACTIONSOUTH)){
			return new Location2(0, -1,ballOwnerShip);
		}
		else if(actionName.equals(GridGame.ACTIONEAST)){
			return new Location2(1, 0,ballOwnerShip);
		}
		else if(actionName.equals(GridGame.ACTIONWEST)){
			return new Location2(-1, 0,ballOwnerShip);
		}
		else if(actionName.equals(GridGame.ACTIONNOOP)){
			return new Location2(0, 0,ballOwnerShip);
		}
		
		throw new RuntimeException("Error: Unknown action named '" + actionName + "' that GridGameStandardMechanics cannot handle");
	}
	
	
	
	
	/**
	 * Returns a movement result of the agent. If the agent tries to pass through a semi-wall, then it is randomly
	 * selected whether the agent succeeds or not. If the agent tries to move through a solid wall or to a location
	 * where there is another agent who is not moving, then no change occurs.
	 * @param s the state containing the agent
	 * @param p0 the initial position of the agent
	 * @param delta the desired change of position.
	 * @param agentNoOpLocs the locations occupied by agents who are not moving.
	 * @return The resulting location of this agents movement.
	 */
	protected Location2 sampleBasicMovement(Location2 p0, Location2 delta){
		
		boolean ballOwner = p0.ballOwnerShip;
		boolean ballChangeFlag = false;
		 boolean feasibleFlag = false;
		//Location2 p1 = p0.add(delta);
		
		int deltax = delta.x;
		int deltay = delta.y;
		
		int p1XVal =  p0.x +deltax;
		int p1YVal =  p0.y +deltay;
		
		if(p1XVal >= 0 && p1XVal <=3 && p1YVal >=0 && p1YVal <=1){
			feasibleFlag = true;
		}
		
		
		if(feasibleFlag){
		Location2 p1 = new Location2(p1XVal, p1YVal, ballOwner);
		return p1;
		}
		
		return p0;
		
		
		
		
		/*boolean reset = false;
		
		for(Location2 anl : agentNoOpLocs){
			if(p1.equals(anl)){
				p1.ballOwnerShip = false;
				ballChangeFlag = true;
				reset = true;
				break;
			}
		}
		
		//Reset will be false if state change is feasible
		if( !reset){
			reset = !checkingStateIsFeasibleOrNot(p1);
		}
		
		
		
		if(reset){
			p1.x = p1.x - delta.x;
			p1.y = p1.y - delta.y;
		}*/
		
		
	}
	
	
	
public boolean checkingStateIsFeasibleOrNot(Location2 loc){
		
		boolean feasibleFlag = false;
		
		if(loc.x >= 0 && loc.x <=3 && loc.y >=0 && loc.y <=1){
			feasibleFlag = true;
		}
		
		return feasibleFlag;
	}
	
	
	
public static int randomInteger(int min, int max) {
     
		Random rand = new Random();
		// nextInt excludes the top value so we have to add 1 to include the top value
	    int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
	}
	/**In our case since there are two players then only two location in original position and two in desired position
	 * 
	 * 
	 * 
	 * 
	 * Resolves collisions that occur when two or more agents try to enter the same cell, in which case only one
	 * agent will make it into the position and the rest will stay in place
	 * @param originalPositions the positions of the agents in the original state before their actions were taken
	 * @param desiredPositions the desired locations of the agents
	 * @return a list of the resulting positions having accounted for collisions.
	 */
	protected List<Location2> resolveCollisions(List<Location2> originalPositions, List <Location2> desiredPositions){
		
		List <Location2> finalPoses = new ArrayList<ExpGridMechanics.Location2>();
		//get movement collisions
		Map <Integer, List <Integer>> collissionSets = this.getColissionSets(desiredPositions);
		
		if(collissionSets.size() == 0){
			return desiredPositions; //no resolutions needed
		}
		
		/*for(int i =0; i < originalPositions.size();i++){
			Location2 agentLocationFutureObj = desiredPositions.get(i);
			Location2 agent1PrevLocObj = originalPositions.get(i);
			
			
		}*/
		
		//resolve attempted movement collisions
				
				Map <Integer, Integer> winners = this.getWinningAgentMovements(collissionSets);
				
				for(int i = 0; i < originalPositions.size(); i++){
					if(winners.containsKey(i)){
						if(winners.get(i) != i){
							//this player lost and stays in place
							finalPoses.add(originalPositions.get(i));
						}
						else{
							//this player wins and gets to go to desired location
							finalPoses.add(desiredPositions.get(i));
						}
					}
					else{
						//no competitors so the agent goes where it wants
						finalPoses.add(desiredPositions.get(i));
					}
				}
				
				//it's possible that a losing collision means the agent's spot is no longer available, causing another collision
				//in this case all agents affected by loser are pushed back
				collissionSets = this.getColissionSets(finalPoses);
				/*while(collissionSets.size() > 0){
					
					Set <Integer> pushedBackAgents = collissionSets.keySet();
					for(Integer aid : pushedBackAgents){
						finalPoses.set(aid, originalPositions.get(aid));
					}
					
					collissionSets = this.getColissionSets(finalPoses);
					
				}*/
				
				return finalPoses;
	}
	


	/**
	 * Takes as input the set of collisions and randomly selects a winner
	 * @param collissionSets the set of collisions; maps from one agent index to a list of the agents with whom he is competing for a cell
	 * @return A map from each agent involved in a collision to the winning agent of that collision.
	 */
	protected Map <Integer, Integer> getWinningAgentMovements(Map <Integer, List <Integer>> collissionSets){
		
		Map <Integer, Integer> winners = new HashMap<Integer, Integer>();
		
		Set <Integer> keySet = collissionSets.keySet();
		for(Integer agentId : keySet){
			if(winners.containsKey(agentId)){
				continue; //already resolved winner
			}
			List <Integer> competitors = collissionSets.get(agentId);
			int winner = competitors.get(rand.nextInt(competitors.size()));
			for(Integer a2 : competitors){
				winners.put(a2, winner);
			}
		}
		
		
		return winners;
		
	}
		
	
/**
 * Returns with whom each agent is in a collision competition for a cell.
 * @param candidatePositions a list of the positions to which each agent would like to go; the Location2 in index 1 indicates the location the 1th index agent
 * wants to go to.
 * @return A map from the index of each agent to a list of the index of agents with whom there is competition for a cell
 */
protected Map <Integer, List <Integer>> getColissionSets(List <Location2> candidatePositions){
	
	Map <Integer, List <Integer>> collisionSets = new HashMap<Integer, List<Integer>>();
	
	for(int i = 0; i < candidatePositions.size(); i++){
		
		Location2 candLoc = candidatePositions.get(i);
		List <Integer> collisions = new ArrayList<Integer>();
		collisions.add(i);
		for(int j = i+1; j < candidatePositions.size(); j++){
			if(collisionSets.containsKey(j)){
				continue; //already found collisions with this agent
			}
			Location2 cLoc = candidatePositions.get(j);
			if(candLoc.equals(cLoc)){
				collisions.add(j);
			}
		}
		
		if(collisions.size() > 1){ //greater than one because an agent always "collides" with itself
			//set the collision set for each agent involved
			for(Integer aid : collisions){
				collisionSets.put(aid, collisions);
			}
		}
	}
	
	
	return collisionSets;
	
}

/*public static void main(String[] args){
	
	 ExpGridGame2 domainGen;
     SGDomain domain1;
     domainGen = new ExpGridGame2();
	domain1 = (SGDomain)domainGen.generateDomain();
	ExpGridGame2 obj = new ExpGridGame2();
	State s = obj.getTurkeyInitialState(domain1);
	ExpGridMechanics obj1 = new ExpGridMechanics(domain1);
	
	//JointAction obj2 = new JointAction();
	//obj1.actionHelper(s, obj2);
	//obj1.
	

}*/

}

