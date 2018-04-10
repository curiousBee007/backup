import burlap.domain.stochasticgames.gridgame.GridGameStandardMechanics;
//import burlap.domain.stochasticgames.gridgame.GridGame.AgentInPGoal;
//import burlap.domain.stochasticgames.gridgame.GridGame.AgentInUGoal;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.GroundedProp;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.TerminalFunction;
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

//Added an ATTBOWNER attribute for ball owner


public class ExpGridGame1 implements DomainGenerator {
	
	//Added an ATTBOWNER attribute for ball owner
	
		public static final String	ATTBOWNER = "ballOwner";
		
		
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
		public static final String				ATTE2 = "end2";
		
		/**
		 * A constant for the name of the attribute for defining the walls position along its orthogonal direction.
		 * For a horizontal wall, this attribute represents the y position of the wall; for a vertical wall,
		 * the x position.
		 */
		public static final String	ATTP = "pos";
		
		/**
		 * A constant for the name of the wall type attribute.
		 */
		public static final String				ATTWT = "wallType";
		
		
		/**
		 * A constant for the name of the agent class.
		 */
		public static final String		CLASSAGENT = "agent";
		
		/**
		 * A constant for the name of the goal class.
		 */
		public static final String		CLASSGOAL = "goal";
		
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
		public static final String		PFINUGOAL = "agentInUniversalGoal";
		
		/**
		 * A constant for the name of a propositional function that evaluates whether an agent is in a personal goal location for just them.
		 */
		public static final String	PFINPGOAL = "agentInPersonalGoal";
		
		
		
		
		/**
		 * The width and height of the world.
		 */
		protected int maxHeight = 2;
		
		protected int maxWidth = 4;
		
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


	
		@Override
		public Domain generateDomain() {
			
			SGDomain domain = new SGDomain();
			
			
			Attribute xatt = new Attribute(domain, ATTX, Attribute.AttributeType.INT);
			xatt.setLims(0, 3);
			
			Attribute yatt = new Attribute(domain, ATTY, Attribute.AttributeType.INT);
			yatt.setLims(0, 2);
			
			/*Attribute e1att = new Attribute(domain, ATTE1, Attribute.AttributeType.INT);
			e1att.setLims(0, maxDim);
			
			Attribute e2att = new Attribute(domain, ATTE2, Attribute.AttributeType.INT);
			e2att.setLims(0, maxDim);
			
			Attribute patt = new Attribute(domain, ATTP, Attribute.AttributeType.INT);
			patt.setLims(0, maxDim);
			
			Attribute pnatt = new Attribute(domain, ATTPN, Attribute.AttributeType.INT);
			patt.setLims(0, maxPlyrs);
			
			Attribute gtatt = new Attribute(domain, ATTGT, Attribute.AttributeType.INT);
			gtatt.setLims(0, maxGT);
			
			Attribute wtatt = new Attribute(domain, ATTWT, Attribute.AttributeType.INT);
			wtatt.setLims(0, maxWT);
			
			
			
			ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
			agentClass.addAttribute(xatt);
			agentClass.addAttribute(yatt);
			agentClass.addAttribute(pnatt);
			
			ObjectClass goalClass = new ObjectClass(domain, CLASSGOAL);
			goalClass.addAttribute(xatt);
			goalClass.addAttribute(yatt);
			goalClass.addAttribute(gtatt);
			
			ObjectClass horWall = new ObjectClass(domain, CLASSDIMHWALL);
			horWall.addAttribute(eH1att);
			horWall.addAttribute(eH2att);
			horWall.addAttribute(pHatt);
			horWall.addAttribute(wtatt);
			
			ObjectClass vertWall = new ObjectClass(domain, CLASSDIMVWALL);
			vertWall.addAttribute(e1att);
			vertWall.addAttribute(e2att);
			vertWall.addAttribute(patt);
			vertWall.addAttribute(wtatt);
			*/
			
			new SimpleSGAgentAction(domain, ACTIONNORTH);
			new SimpleSGAgentAction(domain, ACTIONSOUTH);
			new SimpleSGAgentAction(domain, ACTIONEAST);
			new SimpleSGAgentAction(domain, ACTIONWEST);
			new SimpleSGAgentAction(domain, ACTIONNOOP);
			
			
			//new AgentInUGoal(PFINUGOAL, domain);
			//new AgentInPGoal(PFINPGOAL, domain);

			domain.setJointActionModel(new GridGameStandardMechanics(domain, this.semiWallProb));
			
			return domain;
		}
	
	
	
	

	

}
