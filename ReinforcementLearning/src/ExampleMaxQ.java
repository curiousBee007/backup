import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import burlap.behavior.learningrate.ExponentialDecayLR;
import burlap.behavior.learningrate.LearningRate;
import burlap.behavior.stochasticgames.GameAnalysis;
import burlap.behavior.stochasticgames.PolicyFromJointPolicy;
import burlap.behavior.stochasticgames.agents.maql.MultiAgentQLearning;
import burlap.behavior.stochasticgames.auxiliary.GameSequenceVisualizer;
import burlap.behavior.stochasticgames.madynamicprogramming.AgentQSourceMap;
import burlap.behavior.stochasticgames.madynamicprogramming.JAQValue;
import burlap.behavior.stochasticgames.madynamicprogramming.QSourceForSingleAgent;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CorrelatedQ;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.MaxQ;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.ECorrelatedQJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyMaxWellfare;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.legacy.StateParser;
import burlap.oomdp.legacy.StateYAMLParser;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.stochasticgames.JointAction;
import burlap.oomdp.stochasticgames.JointActionModel;
import burlap.oomdp.stochasticgames.JointReward;
import burlap.oomdp.stochasticgames.SGAgent;
import burlap.oomdp.stochasticgames.SGAgentType;
import burlap.oomdp.stochasticgames.SGDomain;
import burlap.oomdp.stochasticgames.World;
import burlap.oomdp.stochasticgames.WorldObserver;
import burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction;
import burlap.oomdp.stochasticgames.agentactions.SimpleGroundedSGAgentAction;
import burlap.oomdp.stochasticgames.agentactions.SimpleSGAgentAction;
import burlap.oomdp.stochasticgames.common.ConstantSGStateGenerator;
import burlap.oomdp.visualizer.Visualizer;

public class ExampleMaxQ  {
	
	//grid game domain generator
	protected ExpGridGame2 domainGen;

	//stochastic games domain
	protected SGDomain domain;

	//joint action model we want to use for grid game
	protected JointActionModel jam;

	//the type of agent for both agents (specifies their object class defining their personal state and their action set)
	protected  SGAgentType at;

	//protected SGAgent s1;
	
	//protected SGAgent s2;
	//discrete state hashing factory for state look up and equality checks
	protected SimpleHashableStateFactory hashingFactory;

	//the joint reward function defining the objective
	protected JointReward rf;

	//a terminal function for defining end states of the game
	protected TerminalFunction tf;

	//stochastic game discount factor
	protected double discount = 0.99;

	//initial state of the game
	protected State initialState;

	//a world of agents that will interact in a grid world
	protected World w;

	//Grid Game visualizer so we can watch examples of the policy
	Visualizer v;
	
	public static String sFileName = "MaxQResult";
	
	public static JointAction ja1;
	
	 public static File file = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/MaxQResult");
	
	 //public statiFileWriter fw = new FileWriter(file.getAbsoluteFile());
	
	public static FileWriter writer ;
	
	public static int count = 0;
	
	LearningRate lr;
	public AgentQSourceMap.MAQLControlledQSourceMap qSourceMap;


	public static void main(String[] args) {

		ExampleMaxQ esg = new ExampleMaxQ();

		//esg.MAValueIteration();
		esg.MAQLearning();

	}


	
	public static class  WorldObs implements WorldObserver{
		AgentQSourceMap.MAQLControlledQSourceMap qSourceMap1;
		SGDomain domain1;
		State initialState1;
		JointAction jaToBeQueried;
		//SGAgent agent0;
		//SGAgent agent1;
		MultiAgentQLearning agent0;
		MultiAgentQLearning agent1;
		//AgentQSourceMap.MAQLControlledQSourceMap qSourceMap1;
		
		
		public WorldObs(AgentQSourceMap.MAQLControlledQSourceMap qSourceMap,
				SGDomain domain,State initialState, MultiAgentQLearning a0, MultiAgentQLearning a1)
		     {
			    System.out.println("Inside world observer function");
			    this.domain1 = domain;
			    this.agent0 = a0;
			    this.agent1 = a1;
			   
			   List<GroundedSGAgentAction> actions =  addingAllJointFunctionsToList( domain);
			   System.out.println("Size of list of actions  "+actions.size());
			   
			   /*List<Double> qValues = new ArrayList<Double>();
			   qValues = getQValue();
			   System.out.println("agent0QValue  "+qValues.get(0) + "agent1QValue   "+qValues.get(1));*/
			   /*List<GroundedSGAgentAction> actionToBeQueried = new ArrayList<GroundedSGAgentAction>();
		        SimpleSGAgentAction action0 = new SimpleSGAgentAction(domain, "south");
		        SimpleSGAgentAction action1 = new SimpleSGAgentAction(domain, "noop");

		        actionToBeQueried.add(new SimpleGroundedSGAgentAction("agent0", action0));
		        actionToBeQueried.add(new SimpleGroundedSGAgentAction("agent1", action1));
		        this.jaToBeQueried = new JointAction(actionToBeQueried);
		        
		        this.agent0 = agent0;
		        this.agent1 = agent1;
			   
			   ja1 = new JointAction(actions);
		        //ja1.
		        this.domain1 = domain;
		        this.initialState1 = initialState;
		        if(qSourceMap == null){
		        	System.out.println("Null");
		        }
		        this.qSourceMap1 = qSourceMap;
		        */
			
			
		}
		
		public List<Double> getQValue() {
	        List<Double> values = new ArrayList<Double>();
	        List<GroundedSGAgentAction> actions = new ArrayList<GroundedSGAgentAction>();
	        SimpleSGAgentAction action0 = new SimpleSGAgentAction(this.domain1, "south");
	        SimpleSGAgentAction action1 = new SimpleSGAgentAction(this.domain1, "noop");

	        actions.add(new SimpleGroundedSGAgentAction("agent0", action0));
	        actions.add(new SimpleGroundedSGAgentAction("agent1", action1));
	        JointAction ja = new JointAction(actions);
	        if(ja == null){
	        	System.out.println("Joint Action created is null ");
	        }

	        QSourceForSingleAgent source0 = this.agent0.getMyQSource();
	        JAQValue value1 = source0.getQValueFor(this.initialState1, ja);
	        values.add(value1.q);

	        QSourceForSingleAgent source1 = this.agent1.getMyQSource();
	        JAQValue value2 = source1.getQValueFor(this.initialState1, ja);
	        values.add(value2.q);

	        return values;
	}
		
		
		
		
		public List<GroundedSGAgentAction> addingAllJointFunctionsToList(SGDomain domain){
			List<SimpleSGAgentAction> actionList0 = new ArrayList<SimpleSGAgentAction>();
			
			List<GroundedSGAgentAction> actions = new ArrayList<GroundedSGAgentAction>();
			
			//List<SimpleSGAgentAction> actionList1 = new ArrayList<SimpleSGAgentAction>();
			
			SimpleSGAgentAction action0 = new SimpleSGAgentAction(domain, "north");
		    SimpleSGAgentAction action1 = new SimpleSGAgentAction(domain, "south");
		    SimpleSGAgentAction action2 = new SimpleSGAgentAction(domain, "east");
		    SimpleSGAgentAction action3 = new SimpleSGAgentAction(domain, "west");
		    SimpleSGAgentAction action4 = new SimpleSGAgentAction(domain, "noop");
		    
		    actionList0.add(action0);
		    actionList0.add(action1);
		    actionList0.add(action2);
		    actionList0.add(action3);
		    actionList0.add(action4);
		    
		    
		    
		    	for(int j =0; j < 5; j++){
		    		actions.add(new SimpleGroundedSGAgentAction("agent0", actionList0.get(j)));
		    		actions.add(new SimpleGroundedSGAgentAction("agent1", actionList0.get(j)));
		    		}
		    	
		    	return actions;
		    	
		    }
			
			/*List<String>  simpleActionName= new ArrayList<String>();
			simpleActionName.add(0,"north");
			simpleActionName.add(1,"south");
			simpleActionName.add(2,"east");
			simpleActionName.add(3,"west");
			simpleActionName.add(4,"noop");*/
			
			
		
		
		
		

		@Override
		public void gameStarting(State s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void observe(State s, JointAction ja, Map<String, Double> reward, State sp) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void gameEnding(State s) {
			
			System.out.println("Inside game ending");
			
			String sFileName ="MaxQResult";
			
			/*QSourceForSingleAgent qVal0 = this.qSourceMap1.agentQSource("agent0");
			
			QSourceForSingleAgent qVal1 = this.qSourceMap1.agentQSource("agent1");
			
			
			
			
			 
			JAQValue jointQVal =qVal0.getQValueFor(s, jaToBeQueried);
			System.out.println();
			//System.out.println("Joint Action we are querying"+jaToBeQueried.actionName());
			
			JAQValue jointQVal1 =qVal1.getQValueFor(s, jaToBeQueried);
			//System.out.println(jaToBeQueried.actionName());
			
			 /*if(jointQVal.q != 0.0)
			 System.out.println(jointQVal.q +"  at iteration  "+count);*/
			 
			/* String qValStringval = Double.toString(jointQVal.q);
			 String qVal1StringVal = Double.toString(jointQVal1.q);*/
			// System.out.println("Value of qval0   " +qValStringval +"Value of qval1  " +qVal1StringVal);
			 
			 
			/* try {
				  // File file = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/MaxQResult");

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
					BufferedWriter bw = new BufferedWriter(fw);
				    //PrintWriter out = new PrintWriter(bw));
					//fw.append(qVal);
					//fw.append(",");
					count++;
					//BufferedWriter bw = new BufferedWriter(fw);
					//bw.write(qVal + ",");
					//bw.close();
					if(count %20 ==0){fw.write("\n");}
					fw.flush();
				    fw.close();

					//System.out.println("Done");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}*/
		
		
	}
	}
	public ExampleMaxQ(){

		this.domainGen = new ExpGridGame2();
		this.domain = (SGDomain)domainGen.generateDomain(); //type cast output to stochastic games domain
		this.jam = new ExpGridMechanics(this.domain);
		
		this.rf = new ExpGridGame2.GGJointRewardFunction1(this.domain,0.0,100.0,-100.0);
		this.tf = new ExpGridGame2.GGTerminalFunction(this.domain);
	

		//set the grid game agents will play to turkey
		//this.initialStste = GridGame.getTurkeyInitialState(this.domain);
		this.initialState = ExpGridGame2.getTurkeyInitialState(this.domain);
		//SGAgent s1 = this.domain.

		this.hashingFactory = new SimpleHashableStateFactory();
		//LearningRate lr = new Learning

		//both agents are the same type in grid games with the same action set available
		this.at = new SGAgentType(ExpGridGame2.CLASSAGENT, domain.getObjectClass(ExpGridGame2.CLASSAGENT), domain.getAgentActions());
        //this.w = new World(domain, new GridGameStandardMechanics(domain), this.rf, new GridGame.GGTerminalFunction(this.domain),
		//new ConstantSGStateGenerator(this.initialStste));
		//at.
		//this.s1 = new SGAgent();
		
		this.w = new World(domain, this.rf, new ExpGridGame2.GGTerminalFunction(this.domain),
				new ConstantSGStateGenerator(this.initialState));


		this.v = GridGameVisualizer.getVisualizer(9, 9);
		
		

	}
	
	
	public void MAQLearning(){

		//set parameters for our learning agents
		//we will use a semi-optimistic q-value initialization which we will then couple with an epsilon-greedy
		//policy to promote exploration, but not be overly conservative which takes a long time to learn
		//double learningRate = 0.01;
		double defaultQ = 0.0;
		double epsilon = 0.2;
		//SGAgent sg1 = 
		
		double initialLearningRate = 1.0;
		double decayRate = 0.001;
		double minimumlearningrate = 0.001;
		this.lr = new ExponentialDecayLR(initialLearningRate,decayRate,minimumlearningrate);
		ValueFunctionInitialization valueFuninit = new ValueFunctionInitialization.ConstantValueFunctionInitialization(0.0);
		
		//CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective objectiveType = UTILITARIAN ;
		
		MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount, this.lr, 
				   this.hashingFactory, valueFuninit, new MaxQ(), true);
		MultiAgentQLearning a1 = new MultiAgentQLearning(this.domain, this.discount, this.lr, 
				   this.hashingFactory, valueFuninit, new MaxQ(), true);
		

		//create CoCo-Q (promotes cooperation) learning agents
		/*MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount, learningRate,
				this.hashingFactory, defaultQ, new MaxQ(), true);*/
		
		
		
		//MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount,this.lr,this.hashingFactory, defaultQ, new MaxQ(), true);
		
		/*MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount, learningRate,
				this.hashingFactory, defaultQ, new MaxQ(), true);
		
		MultiAgentQLearning a1 = new MultiAgentQLearning(this.domain, this.discount, learningRate,
				this.hashingFactory, defaultQ, new MaxQ(), true);*/
		
		
       
		//have them joint the world
		a0.joinWorld(this.w, this.at);
		a1.joinWorld(this.w, this.at);
		//a0.getAgentType();
		SGAgent agent0 = a0;
		SGAgent agent1 = a1;
		
		
		List<SGAgent> multiAgentListObj = new ArrayList<SGAgent>();
		multiAgentListObj.add(agent0);
		multiAgentListObj.add(agent1);
		
		this.qSourceMap = new AgentQSourceMap.MAQLControlledQSourceMap(multiAgentListObj);


		//set their policies to be a epsilon greedy maxwelfare (which CoCo-Q uses) policy over the joint actions
		//with ties broken randomly
		EGreedyMaxWellfare ja0 = new EGreedyMaxWellfare(epsilon);
		EGreedyMaxWellfare ja1 = new EGreedyMaxWellfare(epsilon);
		//ja0.setBreakTiesRandomly(true);
		//ja1.setBreakTiesRandomly(true);

		a0.setLearningPolicy(new PolicyFromJointPolicy(a0.getAgentName(), ja0));
		a1.setLearningPolicy(new PolicyFromJointPolicy(a1.getAgentName(), ja1));
        

		//we're going to run a lot of learning episodes, so lets disable the world debug console print outs
		DPrint.toggleCode(w.getDebugId(), false);


		//run 2500 learning episodes. We'll let these run without using our visualizer for speed.
		//However, we'll save each learning episode to disk so we can review them afterwards.

		//first create a standard state parser for saving data to disk
		StateParser sp = new StateYAMLParser(this.domain);

		//now run training; games will be saved in a directory
		//that will automatically be created called "sgTests"
		System.out.println("Starting learning");
		int ngames = 100;
		
		WorldObs worldObj = new WorldObs(this.qSourceMap, this.domain, this.initialState,a0,a1);
		
		w.addWorldObserver(worldObj);
		for(int i = 0; i < ngames; i++){

			GameAnalysis ga = w.runGame();
			if(i % 10 == 0){
				System.out.println("Game: " + i + ": " + ga.numTimeSteps());
				//System.out.println(a1.getQSources());
			}
			//ga.writeToFile(String.format("sgTests/%4d", i));
			ga.writeToFile(String.format("sgTests21/%4d", i));
		}

		System.out.println("Finished learning");

		//now load the game sequence visualizer so we can review what happened during learning
		new GameSequenceVisualizer(v, domain, "sgTests21/");
		//new GameSequenceVisualizer(v, domain, sp, "sgTests/");
       
}
}
