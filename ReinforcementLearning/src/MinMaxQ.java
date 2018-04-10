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
import burlap.behavior.stochasticgames.madynamicprogramming.SGBackupOperator;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.CorrelatedQ;
import burlap.behavior.stochasticgames.madynamicprogramming.backupOperators.MaxQ;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.ECorrelatedQJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyJointPolicy;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EGreedyMaxWellfare;
import burlap.behavior.stochasticgames.madynamicprogramming.policies.EMinMaxPolicy;
import burlap.behavior.stochasticgames.solvers.CorrelatedEquilibriumSolver.CorrelatedEquilibriumObjective;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.debugtools.DPrint;
import burlap.domain.stochasticgames.gridgame.GGVisualizer;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.legacy.StateParser;
import burlap.oomdp.legacy.StateYAMLParser;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;
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

public class MinMaxQ {
	//grid game domain generator
	protected ExpGridGame2 domainGen;

	//stochastic games domain
	protected SGDomain domain;

	//joint action model we want to use for grid game
	protected JointActionModel jam;

	//the type of agent for both agents (specifies their object class defining their personal state and their action set)
	protected  SGAgentType at;
	
	protected DiscretizingHashableStateFactory hashingFactory;
	
	//the joint reward function defining the objective
	protected JointReward rf;

	//a terminal function for defining end states of the gaableme
	protected TerminalFunction tf;

	//stochastic game discount factor
	protected double discount = 0.99;

	//initial state of the game
	protected State initialState;

	//a world of agents that will interact in a grid world
	protected World w;

	//Grid Game visualizer so we can watch examples of the policy
	Visualizer v;
	
     public static JointAction ja1;
	
	public static FileWriter writer ;
	
	public static int count = 0;
	
	LearningRate lr;
	
	public MinMaxQ(){

		this.domainGen = new ExpGridGame2();
		
		this.domain = (SGDomain)domainGen.generateDomain(); 
		
		this.jam = new ExpGridMechanics(this.domain);
		
		this.rf = new ExpGridGame2.GGJointRewardFunction1(this.domain,0.0,100.0,-100.0);
		
		this.tf = new ExpGridGame2.GGTerminalFunction(this.domain);
	
		this.initialState = ExpGridGame2.getTurkeyInitialState(this.domain);
		
        
		this.hashingFactory = new DiscretizingHashableStateFactory(0);
		
        this.at = new SGAgentType(ExpGridGame2.CLASSAGENT, domain.getObjectClass(ExpGridGame2.CLASSAGENT), domain.getAgentActions());
        
		this.w = new World(domain, this.rf, new ExpGridGame2.GGTerminalFunction(this.domain),
				new ConstantSGStateGenerator(this.initialState));

        this.v = GridGameVisualizer.getVisualizer(9, 9);
		
	}
	
	public void MinMaxLearning(){

		//double learningRate = 0.01;
		double defaultQ = 0.0;
		
		double epsilon = 0.2;
		
	    double initialLearningRate = 1.0;
	    
		double decayRate = 0.0001;
		
		double minimumlearningrate = 0.001;
		
		this.lr = new ExponentialDecayLR(initialLearningRate,decayRate,minimumlearningrate);
		//SGBackupOperator m1 = (SGBackupOperator) new MinMaxQ();
		
		
		
		ValueFunctionInitialization valueFuninit = new ValueFunctionInitialization.ConstantValueFunctionInitialization(0.0);
	
		MultiAgentQLearning a0 = new MultiAgentQLearning(this.domain, this.discount, this.lr, 
				   this.hashingFactory, valueFuninit, (SGBackupOperator) new MinMaxQ(), true);
		
		MultiAgentQLearning a1 = new MultiAgentQLearning(this.domain, this.discount, this.lr, 
				   this.hashingFactory, valueFuninit,(SGBackupOperator) new MinMaxQ(), true);
		

	   //have them joint the world
		a0.joinWorld(this.w, this.at);
		
		a1.joinWorld(this.w, this.at);
		//a0.getAgentType();
		SGAgent agentType0 = a0;
		
		
		SGAgent agentType1 = a1;
		
		List<SGAgent> multiAgentListObj = new ArrayList<SGAgent>();
		
		multiAgentListObj.add(agentType0);
		
		multiAgentListObj.add(agentType1);
		
		//this.qSourceMap = new AgentQSourceMap.MAQLControlledQSourceMap(multiAgentListObj);

		EMinMaxPolicy minAgent0 = new EMinMaxPolicy(a0,epsilon);
        
		EMinMaxPolicy minAgent1 = new EMinMaxPolicy(a1,epsilon);
		
	    a0.setLearningPolicy(new PolicyFromJointPolicy(a0.getAgentName(), minAgent0));
	    
		a1.setLearningPolicy(new PolicyFromJointPolicy(a1.getAgentName(),minAgent1));
        

		//we're going to run a lot of learning episodes, so lets disable the world debug console print outs
		DPrint.toggleCode(w.getDebugId(), false);
        
		//run 2500 learning episodes. We'll let these run without using our visualizer for speed.
		//However, we'll save each learning episode to disk so we can review them afterwards.

		//first create a standard state parser for saving data to disk
		StateParser sp = new StateYAMLParser(this.domain);

		//now run training; games will be saved in a directory
		//that will automatically be created called "sgTests"
		System.out.println("Starting learning");
		
		int ngames = 1000;
		
		WorldObs worldObj = new WorldObs( this.domain, this.initialState,a0,a1);
		
		w.addWorldObserver(worldObj);
		for(int i = 0; i < ngames; i++){

			GameAnalysis ga = w.runGame();
			
			if(i % 10 == 0){
				System.out.println("Game: " + i + ": " + ga.numTimeSteps());
				
			}
			
			ga.writeToFile(String.format("sgTests21/%4d", i));
		}

		System.out.println("Finished learning");

		//now load the game sequence visualizer so we can review what happened during learning
		new GameSequenceVisualizer(v, domain, "sgTests21/");
		//new GameSequenceVisualizer(v, domain, sp, "sgTests/");
       
}
	
	
	
	public static class  WorldObs implements WorldObserver{
		
		AgentQSourceMap.MAQLControlledQSourceMap qSourceMap1;
		
		SGDomain domain1;
		
		State initialState1;
		
		JointAction jaToBeQueried;
		
		MultiAgentQLearning agent0;
		
		MultiAgentQLearning agent1;
		//AgentQSourceMap.MAQLControlledQSourceMap qSourceMap1;
		
		
		public WorldObs(SGDomain domain,State initialState, MultiAgentQLearning a0, MultiAgentQLearning a1)
		     {
			    System.out.println("Inside world observer function");
			    
			    this.domain1 = domain;
			    
			    this.agent0 = a0;
			    
			    this.agent1 = a1;
			    this.initialState1 = initialState;
			   
			 
			   }
		
		public List<Double> getQValue() {
	        List<Double> values = new ArrayList<Double>();
	        List<GroundedSGAgentAction> actions = new ArrayList<GroundedSGAgentAction>();
	        SimpleSGAgentAction action0 = new SimpleSGAgentAction(this.domain1, "south");
	        SimpleSGAgentAction action1 = new SimpleSGAgentAction(this.domain1, "noop");

	        actions.add(new SimpleGroundedSGAgentAction("agent0", action0));
	        actions.add(new SimpleGroundedSGAgentAction("agent1", action1));
	        JointAction ja = new JointAction(actions);
	       
            
            
	        QSourceForSingleAgent source0 = this.agent0.getMyQSource();
	        
	        
	        JAQValue value1 = source0.getQValueFor(this.initialState1, ja);
	        values.add(value1.q);

	        QSourceForSingleAgent source1 = this.agent1.getMyQSource();
	        JAQValue value2 = source1.getQValueFor(this.initialState1, ja);
	        values.add(value2.q);

	        return values;
	}
		
		
		
	
		
			
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
			
			//System.out.println("Inside game ending");
			
			String sFileName ="MaxQResult";
			
			List<Double> qVal = new ArrayList<Double>();
			
			qVal = getQValue();
			double qValAgent0 = qVal.get(0);
			double qValAgent1 = qVal.get(1);
			
			
			System.out.println("agent0QValue  "+qValAgent0 + "          agent1QValue   "+qValAgent1);
			
			System.out.println();
			
			
			
			try {
				   File file = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/MinMaxQ0Val");
				   File file1 = new File("/Users/Anna/Documents/workspace/ReinforcementLearning/src/MinMaxQ1Val");

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					if (!file1.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
					
					FileWriter fw1 = new FileWriter(file1.getAbsoluteFile(),true);
					
					BufferedWriter bw = new BufferedWriter(fw);
					
					BufferedWriter bw1 = new BufferedWriter(fw1);
					
					count++;
					
					if(count %20 ==0)
						
					{
					bw.newLine();
					bw1.newLine();
					
				  }
					bw.write(String.valueOf(qValAgent0) +",");
					bw1.write(String.valueOf(qValAgent1) +",");
					
					bw1.flush();
					bw.flush();
					bw.close();
					bw1.close();
					
					
				    

					//System.out.println("Done");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			}
	}
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
