
import burlap.behavior.singleagent.learning.actorcritic.ActorCritic;
import burlap.behavior.singleagent.learning.actorcritic.actor.BoltzmannActor;
import burlap.behavior.singleagent.learning.actorcritic.critics.TDLambda;
import burlap.behavior.valuefunction.ValueFunctionInitialization;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.Environment;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;

public class BestLambda {
	
	DomainGenerator	dg;
	Domain domain;
	State initState;
	RewardFunction rf;
	TerminalFunction tf;
	DiscretizingHashableStateFactory hashFactory;
	int numStates;
	double probability1;
	double probability2;
	ValueFunctionInitialization vinit;
	double gamma = 1.0;
	double lambda = 0.5;
	double learningRate = 1.0;
	TDLambda tdl;
	
	
	Environment env;
	
	public BestLambda(double probToState1, double[] valueEstimates, double[] rewards,double lambda) {
        // add your code here
     
	    this.probability1 = probToState1;
	    this.probability2 = (1 -probToState1);
	    this.numStates = 7;
	    this.lambda = lambda;
	    //Domain Generator is an interface implemented by different classes
	    
	    this.dg = new GraphDefinedDomain(numStates);
	  
	    
	    //transitions for node 0
	    ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1,probability1);
	    ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2,probability2);
	  
	    
	    //transitions for node 1
	    ((GraphDefinedDomain) this.dg).setTransition(1, 0, 3,1.);
	   
	    
	    //transitions for node 2
	    ((GraphDefinedDomain) this.dg).setTransition(2, 0, 3,1.);
	    
	    //transitions for node 3
	    ((GraphDefinedDomain) this.dg).setTransition(3, 0, 4,1.);
	    
	    //transitions for node 4
	    ((GraphDefinedDomain) this.dg).setTransition(4, 0, 5, 1.);
	    
	  //transitions for node 5
	    ((GraphDefinedDomain) this.dg).setTransition(5, 0, 6, 1.);
	    
		
	    this.domain = this.dg.generateDomain();
	    
	    
	   this.initState = GraphDefinedDomain.getState(this.domain,0);

		this.rf = new RF(rewards);
	    this.tf = new SingleStateTF(6);
	    this.hashFactory = new DiscretizingHashableStateFactory(1);
	    this.vinit = new ValueFunctionInit(valueEstimates);
	    }
	
	
	public static class SingleStateTF implements TerminalFunction{
		int terminalSid;
		
		public SingleStateTF(int sid){
			this.terminalSid = sid;
		}

		@Override
		public boolean isTerminal(State s) {
			int sid = GraphDefinedDomain.getNodeId(s);
			return sid == this.terminalSid;
		}
	}

	
	public double findBestLambda() {
		
		tdl = new TDLambda(rf,tf, gamma,hashFactory,learningRate,vinit, lambda);
		System.out.println("Lambda value is" +lambda);
        // add your code here
		
		// A Boltzmann actor chooses actions at each state based on a particular probability
		// distribution. If there is just one possible action per state (as in this example), 
		// it just takes the only action available to it.
		BoltzmannActor actor = new BoltzmannActor(this.domain, this.hashFactory, learningRate);

		ActorCritic ac = new ActorCritic(this.domain, gamma, actor, tdl);
		
		ac.runLearningEpisode((Environment) initState);
		
		
		   
		  for(int i = 0 ; i< 10;i++ ){
			  System.out.println("Inside i loop");
			  
			 
			  
		  }
		
		
		return tdl.value(initState); // Returns the estimated value of the initial state based on
		                                // the learning episode. If there are stochastic actions,
		                                // only the outcomes actually observed will contribute to the
		                                // TD estimate.
}
	
	

//ValueFunction Initialization
	
	public static class ValueFunctionInit implements ValueFunctionInitialization{
		
	   double initValueArray[];
		
		public ValueFunctionInit(double[] valueEstimates){
			this.initValueArray = valueEstimates;
			
		}
		
		

		@Override
		public double value(State s) {
			// TODO Auto-generated method stub
			int sid = GraphDefinedDomain.getNodeId(s);
			return initValueArray[sid]	;
		}

		@Override
		public double qValue(State s, AbstractGroundedAction a) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
 }
	
	
	
 public static class RF implements RewardFunction {
			
		 double[] reward1;
			
			public RF(double rewards[]) {
				this.reward1 = rewards;
				
			}
			
			@Override
			public double reward(State s, GroundedAction a, State sprime) { 
				
				int sid = GraphDefinedDomain.getNodeId(s);
				double r;
				int targetid = GraphDefinedDomain.getNodeId(sprime);
				
				
				if( sid == 0 && targetid == 1) { // initial state or c1
					r = this.reward1[0];
				}
				else if( sid == 0 && targetid == 2) { // a
					r = this.reward1[1];
				}
				else if( sid == 1 && targetid == 3) { // b1
					r = this.reward1[2];
				}
				else if( sid == 2 && targetid == 3) { // b2
					r = this.reward1[3];
				}
				else if( sid == 3 && targetid == 4) { // b2
					r = this.reward1[4];
				}
				else if( sid == 4 && targetid == 5) { // b2
				
					r = this.reward1[5];
		         }
				else if( sid == 5 && targetid == 6) { // b2
					r = this.reward1[6];
				}
				else {
					throw new RuntimeException("Unknown state: " + sid);
				}
				
				return r;
			}
	    }
	    
	 
	 public static void main(String[] args){
		 
		 double rewards[]= {7.9,-5.1,2.5,-7.2,9.0,0.0,1.6} ;
		 double[] initValue = {0.0,4.0,25.7,0.0,20.1,12.2,0.0};
		 double prob = 0.81;
		 double lambda1 = 0.622;
		 BestLambda obj = new BestLambda(prob,initValue,rewards,lambda1); 
		 System.out.println("Inside best lambda"+obj.findBestLambda());
		
	     
	  
	 
	 }
	
	}
