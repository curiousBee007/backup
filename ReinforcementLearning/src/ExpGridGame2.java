import java.util.HashMap;
import java.util.List;
import java.util.Map;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.GroundedProp;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.stochasticgames.SGAgentType;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointReward;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.agentactions.SimpleSGAgentAction;
import burlap.oomdp.stochasticgames.explorers.SGVisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

/**
 * The GridGame domain is much like the GridWorld domain, except for arbitrarily many agents in
 * a stochastic game. Each agent in the world has an OO-MDP object instance of OO-MDP class "agent"
 * which is defined by an x position, a y position, and a player number. Agents can either move north, south, east,
 * west, or do nothing, therefore the game is symmetric for all agents. To get a standard {@link burlap.oomdp.stochasticgames.SGAgentType}
 * to use with this game, use the {@link #getStandardGridGameAgentType(burlap.oomdp.core.Domain)} static method.
 * <br/><br/>
 * In this domain, there is also an OO-MDP object class for 1-dimensional walls (both for horizontal
 * walls or vertical walls). Each wall can take on a different type; a solid wall that can never be passed (type 0),
 * and a semi-wall, can be passed with some stochastic probability (type 1). Finally, there is also an OO-MDP
 * class for goal locations, which also have different types. There is a type that can be indicated
 * as a universal goal/reward location for all agents (type 0), and type that is only useful to each individual
 * agent (type i is a personal goal for player i-1).
 * <br/><br/>
 * The {@link burlap.oomdp.stochasticgames.JointActionModel} set for the domain is {@link burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics},
 * with a default semi-wall probability of passing through of 0.5, which is changeable with the
 *
 * @author James MacGlashan
 *
 */
public class ExpGridGame2 implements DomainGenerator {

	/**
	 * A constant for ballowner
	 * 
	 */
	public static final String  ATTBALLOWNER = "ballOwner" ;
	
	/**
	 * A constant for the name of the x position attribute
	 */
	public static final String	ATTX = "x";
	
	/**
	 * A constant for the name of the y position attribute
	 */
	public static final String	ATTY = "y";
	
	/**
	 * A constant for the name of the player number attribute. The first player number is 0.
	 */
	public static final String	ATTPN = "playerNum";
	
	/**
	 * A constant for the name of the goal type attribute. Type 0 corresponds to a universal goal. Type i corresponds to a personal goal for player i-1.
	 */
	public static final String	ATTGT = "gt";
	
	/**
	 * A constant for the name of the first wall end position attribute. For a horizontal wall,
	 * this attribute represents the left end point; for a vertical wall, the bottom end point.
	 */
	public static final String	ATTE1 = "end1";
	
	/**
	 * A constant for the name of the second wall end position attribute. For a horizontal wall,
	 * this attribute represents the right end point; for a vertical wall, the top end point.
	 */
	public static final String	ATTE2 = "end2";
	
	/**
	 * A constant for the name of the attribute for defining the walls position along its orthogonal direction.
	 * For a horizontal wall, this attribute represents the y position of the wall; for a vertical wall,
	 * the x position.
	 */
	public static final String	ATTP = "pos";
	
	/**
	 * A constant for the name of the wall type attribute.
	 */
	public static final String ATTWT = "wallType";
	
	/**
	 * A constant name for the ball owner class
	 */
	
	 public static final String	CLASSBALLOWNER = "ballOwnerClass";
	
	
	/**
	 * A constant for the name of the agent class.
	 */
	public static final String	CLASSAGENT = "agent";
	
	/**
	 * A constant for the name of the goal class.
	 */
	public static final String	CLASSGOAL = "goal";
	
	/**
	 * A constant for the name of the horizontal wall class.
	 */
	public static final String	CLASSDIMHWALL = "dimensionlessHorizontalWall";
	
	/**
	 * A constant for the name of the vertical wall class.
	 */
	public static final String	CLASSDIMVWALL = "dimensionlessVerticalWall";
	
	
	/**
	 * A constant for the name of the north action.
	 */
	public static final String	ACTIONNORTH = "north";
	
	/**
	 * A constant for the name of the south action.
	 */
	public static final String	ACTIONSOUTH = "south";
	
	/**
	 * A constant for the name of the east action.
	 */
	public static final String	ACTIONEAST = "east";
	/**
	 * A constant for the name of the west action.
	 */
	public static final String	ACTIONWEST = "west";
	
	/**
	 * A constant for the name of the no operation (do nothing) action.
	 */
	public static final String	ACTIONNOOP = "noop";
	
	
	/**
	 * A constant for the name of a propositional function that evaluates whether an agent is in a universal goal location.
	 */
	public static final String	PFINUGOAL = "agentInUniversalGoal";
	
	/**
	 * A constant for the name of a propositional function that evaluates whether an agent is in a personal goal location for just them.
	 */
	public static final String	PFINPGOAL = "agentInPersonalGoal";
	
	
	/**
	 * A constant for the name of a propositional function that evaluates whether an agent is 
	 * has the ball.
	 */
	public static final String	PFHASBALL = "agentHasBall";
	
	
	/**
	 * A constant for the name of a propositional function that evaluates whether an agent is 
	 * in the wrong goal.
	 */
	public static final String	PFWRONGGOAL = "agentInWrongGoal";
	
	
	/**
	 * The width and height of the world.
	 */
	protected int maxDim = 10;
	
	/**
	 * The maximum number of players that can be in the game
	 */
	protected int maxPlyrs = 2;
	
	/**
	 * The number of goal types
	 */
	protected int maxGT = 2;
	
	/**
	 * The number of wall types
	 */
	protected int maxWT = 2;
   /**
	 * The probability that an agent will pass through a semi-wall.
	 */
	protected double semiWallProb = 0.5;
	

	
	/**
	 * Creates a visual explorer for a simple domain with two agents in it. The
	 * w-a-s-d keys control the movement of the first agent; the i-k-j-l keys control
	 * the movement direction of the second agent. q sets the first agent to do nothing
	 * and u sets the second agent to do nothing. When the actions for both agents have been set,
	 * the actions can be committed to affect the world by pressing the c key.
	 * 
	 * <p/>
	 * If "t" is passed as an argument then a terminal explorer is used instead.
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		
		ExpGridGame2 gg = new ExpGridGame2();
		SGDomain d = (SGDomain)gg.generateDomain();
		
		/*
		State s = getCleanState(d, 2, 3, 3, 2, 5, 5);
		
		setAgent(s, 0, 0, 0, 0);
		setAgent(s, 1, 4, 0, 1);
		
		setGoal(s, 0, 0, 4, 1);
		setGoal(s, 1, 2, 4, 0);
		setGoal(s, 2, 4, 4, 2);
		
		setHorizontalWall(s, 2, 4, 1, 3, 1);
		*/
		
		//State s = GridGame.getCorrdinationGameInitialState(d);
		State s = ExpGridGame2.getTurkeyInitialState(d);
		//System.out.println(s.getCompleteStateDescriptionWithUnsetAttributesAsNull());
		
		/*List<ObjectInstance> agentObjectList= s.getObjectsOfClass(ExpGridGame2.CLASSAGENT);
		ObjectInstance agentAObject  = agentObjectList.get(0);
		Value hasBall = agentAObject.getValueForAttribute(ExpGridGame2.ATTBALLOWNER);
		System.out.println("Value of has ball = " +hasBall);
		String s1 = hasBall.getStringVal();
		System.out.println(s1);
		boolean bvar = Boolean.parseBoolean(s1);
		
		if (bvar == true)
		{
			System.out.println("Anu");
		}*/
		//System.out.println(s.getCompleteStateDescription());
		Visualizer v = GridGameVisualizer.getVisualizer(9, 9);
		
		SGVisualExplorer exp = new SGVisualExplorer(d, v, s);
		
		exp.setJAC("c"); //press c to execute the constructed joint action
		
		exp.addKeyAction("w", CLASSAGENT+"0:"+ACTIONNORTH);
		exp.addKeyAction("s", CLASSAGENT+"0:"+ACTIONSOUTH);
		exp.addKeyAction("d", CLASSAGENT+"0:"+ACTIONEAST);
		exp.addKeyAction("a", CLASSAGENT+"0:"+ACTIONWEST);
		exp.addKeyAction("q", CLASSAGENT+"0:"+ACTIONNOOP);
		
		exp.addKeyAction("i", CLASSAGENT+"1:"+ACTIONNORTH);
		exp.addKeyAction("k", CLASSAGENT+"1:"+ACTIONSOUTH);
		exp.addKeyAction("l", CLASSAGENT+"1:"+ACTIONEAST);
		exp.addKeyAction("j", CLASSAGENT+"1:"+ACTIONWEST);
		exp.addKeyAction("u", CLASSAGENT+"1:"+ACTIONNOOP);
		
		exp.initGUI();
		
  }
	
	
	@Override
	public Domain generateDomain() {
		
		SGDomain domain = new SGDomain();
		Attribute xatt = new Attribute(domain, ATTX, Attribute.AttributeType.INT);
		xatt.setLims(0, maxDim);
		//System.out.println(maxDim);
		
		Attribute yatt = new Attribute(domain, ATTY, Attribute.AttributeType.INT);
		yatt.setLims(0, maxDim);
		
		Attribute e1att = new Attribute(domain, ATTE1, Attribute.AttributeType.INT);
		e1att.setLims(0, maxDim);
		
		Attribute e2att = new Attribute(domain, ATTE2, Attribute.AttributeType.INT);
		e2att.setLims(0, maxDim);
		
		Attribute patt = new Attribute(domain, ATTP, Attribute.AttributeType.INT);
		patt.setLims(0, maxDim);
		
		Attribute pnatt = new Attribute(domain, ATTPN, Attribute.AttributeType.INT);
		pnatt.setLims(0, maxPlyrs);
		
		Attribute gtatt = new Attribute(domain, ATTGT, Attribute.AttributeType.INT);
		gtatt.setLims(0, maxGT);
		
		Attribute wtatt = new Attribute(domain, ATTWT, Attribute.AttributeType.INT);
		wtatt.setLims(0, maxWT);
		
		Attribute ballatt = new Attribute(domain, ATTBALLOWNER, Attribute.AttributeType.BOOLEAN);
		//ballatt.setLims(0, 2);
		
		
		ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);
		agentClass.addAttribute(pnatt);
		agentClass.addAttribute(ballatt);
		
		ObjectClass goalClass = new ObjectClass(domain, CLASSGOAL);
		goalClass.addAttribute(xatt);
		goalClass.addAttribute(yatt);
		goalClass.addAttribute(gtatt);
		
		
		ObjectClass ballOwnerClassObj = new ObjectClass(domain, CLASSBALLOWNER);
		ballOwnerClassObj.addAttribute(xatt);
		ballOwnerClassObj.addAttribute(yatt);
		ballOwnerClassObj.addAttribute(ballatt);
		ballOwnerClassObj.addAttribute(pnatt);
		
		ObjectClass horWall = new ObjectClass(domain, CLASSDIMHWALL);
		horWall.addAttribute(e1att);
		horWall.addAttribute(e2att);
		horWall.addAttribute(patt);
		horWall.addAttribute(wtatt);
		
		ObjectClass vertWall = new ObjectClass(domain, CLASSDIMVWALL);
		vertWall.addAttribute(e1att);
		vertWall.addAttribute(e2att);
		vertWall.addAttribute(patt);
		vertWall.addAttribute(wtatt);
		
		
		new SimpleSGAgentAction(domain, ACTIONNORTH);
		new SimpleSGAgentAction(domain, ACTIONSOUTH);
		new SimpleSGAgentAction(domain, ACTIONEAST);
		new SimpleSGAgentAction(domain, ACTIONWEST);
		new SimpleSGAgentAction(domain, ACTIONNOOP);
		
		//new AgentHasBall(PFHASBALL, domain);
		//new AgentInUGoal(PFINUGOAL, domain);
		new AgentInPGoal(PFINPGOAL, domain);
		new AgentInWrongGoal(PFWRONGGOAL, domain);
		

		domain.setJointActionModel(new ExpGridMechanics(domain));
		
		return domain;
	}

	
	
	/**
	 * Returns a state with with the specified number of objects for each object class and with the specified boundary of
	 * the playing area. The number of walls *MUST* include the world boundary walls; that is, there must be at least 2 horizontal walls and 2 vertical walls.
	 * @param d the domain object of the grid world
	 * @param na the number of agents/players
	 * @param ng the number of goal objects
	 * @param nhw the number of horizontal walls
	 * @param nvw the number of vertical walls
	 * @param width the width of the playing area
	 * @param height the height of the playing area
	 *@param ballOwner the id of ball owner 0 for player1 and 1 for player2(there can be only one ball owner at a time)
	 * @return A state with the specified number of objects
	 */
	public static State getCleanState(Domain d, int na, int ng, int nhw, int nvw, int width, int height){
		
		/*System.out.println("Width of playing area " +width);
		System.out.println("height of playing area " +height);
		System.out.println("Number of goal objects " +ng);
		System.out.println("Number of horizontal walls " +nhw);
		System.out.println("Number of vertical walls " +nvw);*/
		
		
		State s = new MutableState();
		addNObjects(d, s, CLASSGOAL, ng);
		addNObjects(d, s, CLASSAGENT, na);
		addNObjects(d, s, CLASSDIMHWALL, nhw);
		addNObjects(d, s, CLASSDIMVWALL, nvw);
		//addNObjects(d, s, CLASSBALLOWNER, ballOwner);
		
		setBoundaryWalls(s, width, height);
		
		
		return s;
	}


	

	
	public static State getTurkeyInitialState(Domain d){
		
		//State s = GridGame.getCleanState(d, 2, 3, 4, 2, 3, 4);
		State s = ExpGridGame2.getCleanState(d, 2, 4, 2, 2, 4, 2);
		//s.s
		//s.getCompleteStateDescription();
		//System.out.println(s.getCompleteStateDescription());
		
		//A is green and B is blue,
		ExpGridGame2.setAgent(s, 0, 1, 1, 1,true);
		//ExpGridGame2.setAgent(s, 1, 2, 1, 0,false);
		ExpGridGame2.setAgent(s, 1, 2, 1, 0,false);
		
		//Last is ball owner id , it can be either 1 or 0
		//ExpGridGame2.setballOwnerVal(s,1,1,1,1,1);
		
		/*Sets a goal objects attribute values

Parameters:
s the state in which the goal exists
i indicates the ith goal object whose values should be set
x the x position of the goal
y the y position of the goal
gt the goal type (0 personal goal for 0 player, 1 personal goal for personal)*/
		
		//A goal
		ExpGridGame2.setGoal(s, 0, 0, 0, 1);
		ExpGridGame2.setGoal(s, 1, 0, 1, 1);
		
		//B goal
		ExpGridGame2.setGoal(s, 2, 3, 0, 2);
		ExpGridGame2.setGoal(s, 3, 3, 1, 2);
		
		return s;
		
	}
	
	/**
	 * AddsN objects of a specific object class to a state object
	 * @param d the domain of the object classes
	 * @param s the state to which the objects of the specified class should be added
	 * @param className the name of the object class for which to create object instances
	 * @param n the number of object instances to create
	 */
	protected static void addNObjects(Domain d, State s, String className, int n){
		for(int i = 0; i < n; i++){
			ObjectInstance o = new MutableObjectInstance(d.getObjectClass(className), className+i);
			s.addObject(o);
		}
	}
	
	
	/**
	 * Sets an agent's attribute values
	 * @param s the state in which the agent exists
	 * @param i indicates the ith agent object whose values should be set
	 * @param x the x position of the agent
	 * @param y the y position of the agent
	 * @param pn the player number of the agent
	 * @param ballOwner it shows whether the player owns the ball
	 */
	public static void setAgent(State s, int i, int x, int y, int pn, boolean agentHasBall){
		ObjectInstance agent = s.getObjectsOfClass(CLASSAGENT).get(i);
		agent.setValue(ATTX, x);
		agent.setValue(ATTY, y);
		agent.setValue(ATTPN, pn);
		agent.setValue(ATTBALLOWNER,agentHasBall);
	}
	
	
	/**
	 * Sets ball ownerVal values
	 * @param s the state in which the agent exists
	 * @param i indicates the ith agent object whose values should be set
	 * @param x the x position of the agent
	 * @param y the y position of the agent
	 * @param pn the player number of the agent
	 */
	/*public static void setballOwnerVal(State s, int i, int x, int y, int pn,int ballOwner){
		ObjectInstance agent1 = s.getObjectsOfClass(CLASSBALLOWNER).get(0);
		agent1.setValue(ATTX, x);
		agent1.setValue(ATTY, y);
		agent1.setValue(ATTPN, pn);
		agent1.setValue(ATTBALLOWNER, ballOwner);
	}*/
	
	
	
	
	/**
	 * Sets a goal objects attribute values
	 * @param s the state in which the goal exists
	 * @param i indicates the ith goal object whose values should be set
	 * @param x the x position of the goal
	 * @param y the y position of the goal
	 * @param gt the goal type
	 */
	public static void setGoal(State s, int i, int x, int y, int gt){
		ObjectInstance goal = s.getObjectsOfClass(CLASSGOAL).get(i);
		goal.setValue(ATTX, x);
		goal.setValue(ATTY, y);
		goal.setValue(ATTGT, gt);
	}
	
	
	/**
	 * Sets boundary walls of a domain. This method will add 4 solid walls (top left bottom right) to create
	 * a playing field in which the agents can interact.
	 * @param s the state in which the walls should be added
	 * @param w the width of the playing field
	 * @param h the height of the playing field
	 */
	public static void setBoundaryWalls(State s, int w, int h){
		
		List<ObjectInstance> verticalWalls = s.getObjectsOfClass(CLASSDIMVWALL);
		List<ObjectInstance> horizontalWalls = s.getObjectsOfClass(CLASSDIMHWALL);
		
		ObjectInstance leftWall = verticalWalls.get(0);
		ObjectInstance rightWall = verticalWalls.get(1);
		
		ObjectInstance bottomWall = horizontalWalls.get(0);
		ObjectInstance topWall = horizontalWalls.get(1);
		
		setWallInstance(leftWall, 0, 0, h-1, 0);
		setWallInstance(rightWall, w, 0, h-1, 0);
		setWallInstance(bottomWall, 0, 0, w-1, 0);
		setWallInstance(topWall, h, 0, w-1, 0);
		
		
	}
	
	
	/**
	 * Sets the attribute values for a wall instance
	 * @param w the wall instance to set
	 * @param p the orthogonal position of the wall instance
	 * @param e1 the first end point of the wall
	 * @param e2 the second end point of the wall
	 * @param wt the type of the wall
	 */
	public static void setWallInstance(ObjectInstance w, int p, int e1, int e2, int wt){
		w.setValue(ATTP, p);
		w.setValue(ATTE1, e1);
		w.setValue(ATTE2, e2);
		w.setValue(ATTWT, wt);
	}

	
	/**
	 * Sets the attribute values for a vertical wall
	 * @param s the state in which the wall exits
	 * @param i indicates the ith vertical wall instance whose values should be set
	 * @param p the x position of the vertical wall
	 * @param e1 the bottom end point of the wall
	 * @param e2 the top end point of the wall
	 * @param wt the type of the wall
	 */
	public static void setVerticalWall(State s, int i, int p, int e1, int e2, int wt){
		setWallInstance(s.getObjectsOfClass(CLASSDIMVWALL).get(i), p, e1, e2, wt);
	}
	
	
	
	/**
	 * Sets the attribute values for a horizontal wall
	 * @param s the state in which the wall exits
	 * @param i indicates the ith horizontal wall instance whose values should be set
	 * @param p the y position of the vertical wall
	 * @param e1 the left end point of the wall
	 * @param e2 the right end point of the wall
	 * @param wt the type of the wall (0 is solid, 1 is semi)
	 */
	public static void setHorizontalWall(State s, int i, int p, int e1, int e2, int wt){
		setWallInstance(s.getObjectsOfClass(CLASSDIMHWALL).get(i), p, e1, e2, wt);
	}


	/**
	 * Creates and returns a standard {@link burlap.oomdp.stochasticgames.SGAgentType} for grid games. This {@link burlap.oomdp.stochasticgames.SGAgentType}
	 * is assigned the type name "agent", grid game OO-MDP object class for "agent", and has its action space set to all possible actions in the grid game domain.
	 * Typically, all agents in a grid game should be assigned to the same type.
	 *
	 * @param domain the domain object of the grid game.
	 * @return An {@link burlap.oomdp.stochasticgames.SGAgentType} that typically all {@link burlap.oomdp.stochasticgames.SGAgent}'s of the grid game should play as.
	 */
	public static SGAgentType getStandardGridGameAgentType(Domain domain){
		return new SGAgentType(ExpGridGame2.CLASSAGENT, domain.getObjectClass(ExpGridGame2.CLASSAGENT), domain.getAgentActions());
	}
	
	
	
	//---------------------Propositional functions------------------------------------
	
	/**
	 * Defines a propositional function that evaluates to true when a given agent is in any of its personal goals with the 
	 * ball
	 * @author James MacGlashan
	 *
	 */
	static class AgentInPGoal extends PropositionalFunction{

		
		/**
		 * Initializes with the given name and domain and is set to evaluate on agent objects
		 * @param name the name of the propositional function
		 * @param domain the domain for this propositional function
		 */
		public AgentInPGoal(String name, Domain domain) {
			super(name, domain, new String[]{CLASSAGENT});
		}

		@Override
		public boolean isTrue(State s, String... params) {
			
			ObjectInstance agent = s.getObject(params[0]);
			int ax = agent.getIntValForAttribute(ATTX);
			int ay = agent.getIntValForAttribute(ATTY);
			int apn = agent.getIntValForAttribute(ATTPN);
			boolean HasBall = agent.getBooleanValForAttribute(ATTBALLOWNER);
			
			//find all universal goals
			List <ObjectInstance> goals = s.getObjectsOfClass(CLASSGOAL);
			for(ObjectInstance goal : goals){
				
				int gt = goal.getIntValForAttribute(ATTGT);
				if(gt == apn+1 && HasBall ){
				
					int gx = goal.getIntValForAttribute(ATTX);
					int gy = goal.getIntValForAttribute(ATTY);
					if(gx == ax && gy == ay){
						return true;
					}
					
				}
				
				
			}
			
			return false;
			}

		}



/**
 * defines  a PFINOGOAL propositional function that returns true if the agent is in the opponents goal,with the ball
 * Check this one
 *
 */
static class AgentInWrongGoal extends PropositionalFunction{

	/**
	 * Initializes with the given name and domain and is set to evaluate on agent objects
	 * @param name the name of the propositional function
	 * @param domain the domain for this propositional function
	 */
	public AgentInWrongGoal(String name, Domain domain) {
		super(name, domain, new String[]{CLASSAGENT});
	}

	@Override
	public boolean isTrue(State s, String... params) {
		
		ObjectInstance agent = s.getObject(params[0]);
		int ax = agent.getIntValForAttribute(ATTX);
		int ay = agent.getIntValForAttribute(ATTY);
		int apn = agent.getIntValForAttribute(ATTPN);
		boolean HasBall = agent.getBooleanValForAttribute(ATTBALLOWNER);
		//Player 0 should be in goal 1 and player 1 should be in goal 2
		//player 0 is in goal 2 or player 1 is in goal 1 then our function will return true.
		
		//find all universal goals
		List <ObjectInstance> goals = s.getObjectsOfClass(CLASSGOAL);
		for(ObjectInstance goal : goals){
			
			int gt = goal.getIntValForAttribute(ATTGT);
			//Wrong goal
			if (apn == 0 && gt == 2 && HasBall){
				int gx = goal.getIntValForAttribute(ATTX);
				int gy = goal.getIntValForAttribute(ATTY);
				if(gx == ax && gy == ay){
					return true;
				}
				
				}
			
			if (apn == 1 && gt == 1 && HasBall){
				int gx = goal.getIntValForAttribute(ATTX);
				int gy = goal.getIntValForAttribute(ATTY);
				if(gx == ax && gy == ay){
					return true;
				}
				
				}
			}
		
		return false;
	}

	
	
}

//------------------------Propositional function ends-----------------------------


//Helper function to change ball ownership (Reward function starts)
	
	
public static class GGJointRewardFunction1 implements JointReward {

PropositionalFunction agentInPersonalGoal;
PropositionalFunction agentInWrongGoal;
//PropositionalFunction agentHasBall;

double stepCost = 0.;
double pGoalReward = 100.;
double wrongGoalPenalty = -100.;

//double uGoalReward = 1.;
boolean noopIncursCost = false;
Map<Integer, Double> personalGoalRewards = null;


/**
 * Initializes for a given domain, step cost reward, personal goal reward, and universal goal reward.
 * @param ggDomain the domain
 * @param stepCost the reward returned for all transitions except transtions to goal locations
 * @param personalGoalReward the reward returned for transitions to a personal goal
 * @param universalGoalReward the reward returned for transitions to a universal goal
 * @param noopIncursStepCost if true, then noop actions also incur the stepCost reward; 
 * if false, then noops always return 0 reward.
 * @param wrongGoalPenalty : gives -100 when the player is in the wrong goal
 */
public GGJointRewardFunction1(Domain ggDomain, double stepCost, double personalGoalReward,double wrongGoalPenalty){

	agentInPersonalGoal = ggDomain.getPropFunction(ExpGridGame2.PFINPGOAL);
	agentInWrongGoal = ggDomain.getPropFunction(ExpGridGame2.PFWRONGGOAL);
	//agentHasBall = ggDomain.getPropFunction(ExpGridGame2.PFHASBALL);
	//agentInUniversalGoal = ggDomain.getPropFunction(GridGame.PFINUGOAL);
	this.stepCost = stepCost;
	this.pGoalReward = personalGoalReward;
	//this.uGoalReward = universalGoalReward;
	this.wrongGoalPenalty = wrongGoalPenalty;
	//this.noopIncursCost = noopIncursStepCost;

}


@Override
public Map<String, Double> reward(State s, JointAction ja, State sp) {
	
	Map <String, Double> rewards = new HashMap<String, Double>();
	//get all agents and initialize reward to default
	List <ObjectInstance> obs = sp.getObjectsOfClass(ExpGridGame2.CLASSAGENT);
	for(ObjectInstance o : obs){
		rewards.put(o.getName(), 0.0);
	 }

	double initialRewardAgent0 =0.0;
	double initialRewardAgent1 =0.0;
	//check for any agents that reached a personal goal location and give them a goal reward if they did
	//List<GroundedProp> ipgps = sp.getAllGroundedPropsFor(agentInPersonalGoal);
	
	String agentName2 = null,agentName3 = null;
	boolean HasBallFlag = false;
	boolean inPersonalGoal = false;
	boolean inWrongGoal1 = false;
	
	
	/*List<GroundedProp> hasBallps = agentHasBall.getAllGroundedPropsForState(sp);
	for(GroundedProp gp : hasBallps){
		agentName1 = gp.params[0];
		if(gp.isTrue(sp)){
			
			HasBallFlag = true;
		}
	}*/
	
	List<GroundedProp> ipgps = agentInPersonalGoal.getAllGroundedPropsForState(sp);
	for(GroundedProp gp : ipgps){
		 agentName2 = gp.params[0];
		if(gp.isTrue(sp)){
			inPersonalGoal = true;
			rewards.put(agentName2, 100.0);
			
			if(agentName2.equals("agent0")){
				rewards.put("agent1", -100.0);
				}
			else
				rewards.put("agent0", -100.0);
		}
	}
	
	List<GroundedProp> inWrongGl = agentInWrongGoal.getAllGroundedPropsForState(sp);
	for(GroundedProp gp : inWrongGl){
		 agentName3 = gp.params[0];
		if(gp.isTrue(sp)){
			inWrongGoal1= true;
			
			rewards.put(agentName3, -100.0);
			if(agentName3.equals("agent0")){
				rewards.put("agent1", 100.0);
				}
			else
				rewards.put("agent0", 100.0);
			
			
			//rewards.put(agentName, this.getPersonalGoalReward(sp, agentName));
		}
	}
	double agent0Reward = rewards.get("agent0");
	double agent1Reward = rewards.get("agent1");
	
	//System.out.println("Agent0 Reward  " +agent0Reward +" Agent1 reward" + agent1Reward);

	
  return rewards;
	
}

}




//----------------------------Terminal class definition starts here-----------------

/**
 * Causes termination when any agent reaches a personal or universal goal location.
 * @author James MacGlashan
 *
 */
public static class GGTerminalFunction implements TerminalFunction {

	PropositionalFunction agentInPersonalGoal;
	//PropositionalFunction agentInUniversalGoal;
	PropositionalFunction agentInWrongGoal;
	PropositionalFunction agentHasBall;
	
	
	/**
	 * Initializes for the given domain
	 * @param ggDomain the specific grid world domain.
	 */
	public GGTerminalFunction(Domain ggDomain){
		agentInPersonalGoal = ggDomain.getPropFunction(ExpGridGame2.PFINPGOAL);
		//agentInUniversalGoal = ggDomain.getPropFunction(GridGame.PFINUGOAL);
		agentInWrongGoal = ggDomain.getPropFunction(ExpGridGame2.PFWRONGGOAL);
		agentHasBall = ggDomain.getPropFunction(ExpGridGame2.PFHASBALL);
	}
	
	
	
	
	
//-----------------------------Terminal function definition ---------------------------
	@Override
	public boolean isTerminal(State s) {
		
		//check personal goals; if anyone reached their personal goal, it's game over
		//List<GroundedProp> ipgps = s.getAllGroundedPropsFor(agentInPersonalGoal);
		
		//boolean hasBallFlag = agentHasBall.somePFGroundingIsTrue(s);
		//if(agentHasBall.isTrue(s, null))
	//	boolean hasBall = agentHasBall.somePFGroundingIsTrue(s);
		
		
		//System.out.println(hasBall);
		
		//if(hasBall){
		List<GroundedProp> ipgps = agentInPersonalGoal.getAllGroundedPropsForState(s);
		for(GroundedProp gp : ipgps){
			if(gp.isTrue(s)){
				//System.out.println("Terminal state reached");
				return true;
			}
		}
		
		List<GroundedProp> ipwg = agentInWrongGoal.getAllGroundedPropsForState(s);
		for(GroundedProp gp1 : ipwg){
			if(gp1.isTrue(s)){
				//System.out.println("Terminal state reached");
				return true;
			}
		}
		//}
		return false;
	}

}

}





	

