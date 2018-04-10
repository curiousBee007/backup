
				
//Full Q-Learning Code

import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.MDPSolver;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.valuefunction.QFunction;
import burlap.behavior.valuefunction.QValue;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.common.UniformCostRF;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.singleagent.environment.EnvironmentOutcome;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;
import burlap.oomdp.statehashing.HashableState;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;
import java.util.*;


public class QFunction1  {

	Map<HashableState, List<QValue>> qValues;
	ValueFunctionInitialization qinit;
	double learningRate;
	Policy learningPolicy;
	DomainGenerator				dg;
	Domain						domain;
	State						initState;
	RewardFunction				rf;
	RewardFunction              rf1;
	TerminalFunction			tf;
	QFunc2   qObj;
	
	DiscretizingHashableStateFactory	hashFactory;
	int numStates; 
	double finalval[];
	double [][][] transitionProb;
	
	
	public QFunction1(double[][][] probabilitiesOfTransitions,
            double[][][] rewards ,double gamma,int numStates, int numActions){
		
		this.numStates = numStates;
        this.dg = new GraphDefinedDomain(numStates);
        this.finalval = new double[numStates];
        
       
         //double totalVal;
         for(int i = 0; i < numStates;i++){
        	//totalVal = 0.0;
        	for(int j = 0; j < numActions;j++){
        		for(int k = 0; k < numStates;k++){
        			if(probabilitiesOfTransitions[i][j][k] > 0.0){
        			
        			/*System.out.println("Source node  is  " + i + "Action is  " 
        			                    +j+  " Target node is  " +k + 
        			                    "and probabilities of transition  is   "
        			                    +  probabilitiesOfTransitions[i][j][k] + "and reward is  "
        			                    +rewards[i][j][k]);*/
        			
        			
        			 ((GraphDefinedDomain) this.dg).setTransition(i, j, k, 
        					 probabilitiesOfTransitions[i][j][k]);
        		
        		}}
          }
        	
        
       }
        
       
        this.domain = this.dg.generateDomain();
        
        this.initState = GraphDefinedDomain.getState(this.domain,1);
      
        this.rf = new SimpleRF(rewards);
        
        int terminalStates[] = {0,3,6,7};
        
        this.tf = new ManyStateTF(terminalStates);
        
        this.transitionProb = probabilitiesOfTransitions;
        
        this.hashFactory = new DiscretizingHashableStateFactory(35);
        
       State state1 = GraphDefinedDomain.getState(this.domain,1);
       State state2 = GraphDefinedDomain.getState(this.domain,7);
      
       this.qObj = new QFunc2(this.domain,gamma, this.hashFactory,
    		   new ValueFunctionInitialization.ConstantValueFunctionInitialization(), 0.1,0.1);
       

		//create environment
		SimulatedEnvironment env = new SimulatedEnvironment(domain,rf, tf, state1);
 
        qObj.runLearningEpisode(env,100);
        
		System.out.println("Q Value of a state  " +qObj.value(state2));
		
		
	}
	
	
	public static class QFunc2 extends MDPSolver implements LearningAgent, QFunction {
		Map<HashableState, List<QValue>> qValues;
		ValueFunctionInitialization qinit;
		double learningRate;
		Policy learningPolicy;
		DomainGenerator				dg;
		Domain						domain;
		State						initState;
		RewardFunction				rf;
		RewardFunction              rf1;
		TerminalFunction			tf;
		DiscretizingHashableStateFactory	hashFactory;
		int numStates; 
		double finalval[];
		double [][][] transitionProb;
	

	public QFunc2(Domain domain, double gamma, HashableStateFactory hashingFactory,
					  ValueFunctionInitialization qinit, double learningRate,  double epsilon){

		this.solverInit(domain, null, null, gamma, hashingFactory);
		this.qinit = qinit;
		this.learningRate = learningRate;
		this.qValues = new HashMap<HashableState, List<QValue>>();
		this.learningPolicy = new EpsilonGreedy(this, epsilon);

	}

	@Override
	public EpisodeAnalysis runLearningEpisode(Environment env) {
		return this.runLearningEpisode(env, -1);
	}

	@Override
	public EpisodeAnalysis runLearningEpisode(Environment env, int maxSteps) {
		//initialize our episode analysis object with the initial state of the environment
		EpisodeAnalysis ea = new EpisodeAnalysis(env.getCurrentObservation());

		//behave until a terminal state or max steps is reached
		State curState = env.getCurrentObservation();
		int steps = 0;
		while(!env.isInTerminalState() && (steps < maxSteps || maxSteps == -1)){

			//select an action
			GroundedAction a = (GroundedAction)this.learningPolicy.getAction(curState);

			//take the action and observe outcome
			EnvironmentOutcome eo = a.executeIn(env);

			//record result
			ea.recordTransitionTo(a, eo.op, eo.r);

			//get the max Q value of the resulting state if it's not terminal, 0 otherwise
			double maxQ = eo.terminated ? 0. : this.value(eo.op);

			//update the old Q-value
			QValue oldQ = this.getQ(curState, a);
			oldQ.q = oldQ.q + this.learningRate * (eo.r + this.gamma * maxQ - oldQ.q);


			//move on to next state
			curState = eo.op;
			steps++;

		}

		return ea;
	}

	@Override
	public void resetSolver() {
		this.qValues.clear();
	}

	@Override
	public List<QValue> getQs(State s) {
		//first get hashed state
		HashableState sh = this.hashingFactory.hashState(s);

		//check if we already have stored values
		List<QValue> qs = this.qValues.get(sh);

		//create and add initialized Q-values if we don't have them stored for this state
		if(qs == null){
			List<GroundedAction> actions = this.getAllGroundedActions(s);
			qs = new ArrayList<QValue>(actions.size());
			//create a Q-value for each action
			for(GroundedAction ga : actions){
				//add q with initialized value
				qs.add(new QValue(s, ga, this.qinit.qValue(s, ga)));
			}
			//store this for later
			this.qValues.put(sh, qs);
		}

		return qs;
	}

	@Override
	public QValue getQ(State s, AbstractGroundedAction a) {
		//first get all Q-values
		List<QValue> qs = this.getQs(s);

		//translate action parameters to source state for Q-values if needed
		a = ((GroundedAction)a).translateParameters(s, qs.get(0).s);

		//iterate through stored Q-values to find a match for the input action
		for(QValue q : qs){
			if(q.a.equals(a)){
				return q;
			}
		}

		throw new RuntimeException("Could not find matching Q-value.");
	}

	@Override
	public double value(State s) {
		return QFunctionHelper.getOptimalValue(this, s);
	}


	}
	
	

    
public static class SimpleRF implements RewardFunction {
		
    	double[][][] rewards;
    	
		
		public SimpleRF(double[][][] rewards) {
			this.rewards = rewards;
			
		}
		
		@Override
		public double reward(State s, GroundedAction a, State sprime) {
			
			int sid = GraphDefinedDomain.getNodeId(s);
			int targetid = GraphDefinedDomain.getNodeId(sprime);
			String actionName = a.actionName();
			int actionId = Integer.parseInt(actionName.substring(6,7));
			
			double r;
			
			
			r = rewards[sid][actionId][targetid];
			return r;
		}
    }





public static class ManyStateTF implements TerminalFunction{
	   
	int[] stateId;
	public ManyStateTF(int [] stateId){
		this.stateId = stateId;
		
	}
	
	
	@Override
	public boolean isTerminal(State s) {
		
		boolean isTerminal = false;
		
		int sid = GraphDefinedDomain.getNodeId(s);
		for(int i = 0; i < stateId.length;i++){
			if(sid == stateId[i]){
				isTerminal = true;
			}
		}
		
		return isTerminal;
	}
	}

	
	
	public static void main(String[] args) {

		int numStates = 9;

		int numActions = 4;

		double[][][] probabilitiesOfTransitions = {{{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},{{0.0,0.1,0.8,0.0,0.1,0.0,0.0,0.0,0.0},{0.0,0.9,0.1,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.9,0.0,0.0,0.1,0.0,0.0,0.0,0.0},{0.0,0.1,0.1,0.0,0.8,0.0,0.0,0.0,0.0}},{{0.0,0.0,0.9,0.0,0.0,0.1,0.0,0.0,0.0},{0.0,0.1,0.9,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.8,0.1,0.0,0.0,0.1,0.0,0.0,0.0},{0.0,0.1,0.1,0.0,0.0,0.8,0.0,0.0,0.0}},{{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0}},{{0.0,0.1,0.0,0.0,0.0,0.8,0.0,0.1,0.0},{0.0,0.8,0.0,0.1,0.0,0.1,0.0,0.0,0.0},{0.0,0.1,0.0,0.8,0.0,0.0,0.0,0.1,0.0},{0.0,0.0,0.0,0.1,0.0,0.1,0.0,0.8,0.0}},{{0.0,0.0,0.1,0.0,0.0,0.8,0.0,0.0,0.1},{0.0,0.0,0.8,0.0,0.1,0.1,0.0,0.0,0.0},{0.0,0.0,0.1,0.0,0.8,0.0,0.0,0.0,0.1},{0.0,0.0,0.0,0.0,0.1,0.1,0.0,0.0,0.8}},{{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0}},{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0}},{{0.0,0.0,0.0,0.0,0.0,0.1,0.0,0.0,0.9},{0.0,0.0,0.0,0.0,0.0,0.8,0.0,0.1,0.1},{0.0,0.0,0.0,0.0,0.0,0.1,0.0,0.8,0.1},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.1,0.9}}};

		double[][][] rewards = {{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}}};

		double gamma = 0.999;
		
		DomainGenerator				dg ;
		Domain						domain;
		State						initState;
		RewardFunction				rf;
		DiscretizingHashableStateFactory	hashFactory;
		TerminalFunction            tf;
		double [][][] transitionProb;
		
		 dg = new GraphDefinedDomain(numStates);
		
		
		 for(int i = 0; i < numStates;i++){
	        	//totalVal = 0.0;
	        	for(int j = 0; j < numActions;j++){
	        		for(int k = 0; k < numStates;k++){
	        			if(probabilitiesOfTransitions[i][j][k] > 0.0){
	        			
	        			/*System.out.println("Source node  is  " + i + "Action is  " 
	        			                    +j+  " Target node is  " +k + 
	        			                    "and probabilities of transition  is   "
	        			                    +  probabilitiesOfTransitions[i][j][k] + "and reward is  "
	        			                    +rewards[i][j][k]);*/
	        			
	        			
	        			 ((GraphDefinedDomain) dg).setTransition(i, j, k, 
	        					 probabilitiesOfTransitions[i][j][k]);
	        		
	        		}}
	          }
	        	
	        
	       }
		
		    domain = dg.generateDomain();
	        
	        initState = GraphDefinedDomain.getState(domain,1);
	      
	        rf = new SimpleRF(rewards);
	        
	        int terminalStates[] = {0,3,6,7};
	        
	        tf = new ManyStateTF(terminalStates);
	        
	        transitionProb = probabilitiesOfTransitions;
	        
	        hashFactory = new DiscretizingHashableStateFactory(1);
	        
	        State state1 = GraphDefinedDomain.getState(domain,1);
	        
	        State state2 = GraphDefinedDomain.getState(domain,7);
		
		
		
		
		
		 
		 
		 QFunction1 obj = new QFunction1(probabilitiesOfTransitions,
           rewards , gamma, numStates, numActions);
		
		
		}
}
				
