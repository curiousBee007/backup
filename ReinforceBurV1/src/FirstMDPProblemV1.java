
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.behavior.statehashing.DiscreteStateHashFactory;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.State;
import burlap.oomdp.core.TerminalFunction;

import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.Environment;


public class FirstMDPProblemV1 {

	DomainGenerator				dg;
    Domain						domain;
    State						initState;
    RewardFunction				rf;
    TerminalFunction			tf;
    DiscreteStateHashFactory hashFactory;
    int numStates; 
    Environment env;
    
    
    public FirstMDPProblemV1(double p1, double p2, double p3, double p4) {
        this.numStates = 6;
        this.dg = new GraphDefinedDomain(numStates);
        
        //actions from initial state 0
        ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1, 1.);
        ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2, 1.);
        ((GraphDefinedDomain) this.dg).setTransition(0, 2, 3, 1.);
        
		//transitions from action "a" outcome state
		((GraphDefinedDomain) this.dg).setTransition(1, 0, 1, 1.);

		//transitions from action "b" outcome state
		((GraphDefinedDomain) this.dg).setTransition(2, 0, 4, 1.);
		((GraphDefinedDomain) this.dg).setTransition(4, 0, 2, 1.);

		//transitions from action "c" outcome state
		((GraphDefinedDomain) this.dg).setTransition(3, 0, 5, 1.);
		((GraphDefinedDomain) this.dg).setTransition(5, 0, 5, 1.);
        
        this.domain = this.dg.generateDomain();
        
        /*public DomainEnvironmentWrapper(Domain srcDomain,
                Environment env)*/
        this.initState = GraphDefinedDomain.getState(this.domain,0);
		this.rf = new FourParamRF(p1,p2,p3,p4);
        this.tf = new NullTermination();
        this.hashFactory = new DiscreteStateHashFactory();
      //  env.
        
    }
    
    
     public static class FourParamRF implements RewardFunction {
		double p1;
		double p2;
		double p3;
		double p4;
		
		public FourParamRF(double p1, double p2, double p3, double p4) {
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.p4 = p4;
		}
		
		@Override
		public double reward(State s, GroundedAction a, State sprime) { 
			int sid = GraphDefinedDomain.getNodeId(s);
			double r;
			
			if( sid == 0 || sid == 3 ) { // initial state or c1
				r = 0;
			}
			else if( sid == 1 ) { // a
				r = this.p1;
			}
			else if( sid == 2 ) { // b1
				r = this.p2;
			}
			else if( sid == 4 ) { // b2
				r = this.p3;
			}
			else if( sid == 5 ) { // c2
				r = this.p4;
			}
			else {
				throw new RuntimeException("Unknown state: " + sid);
			}
			
			return r;
		}
    }
    
    private ValueIteration computeValue(double gamma) {
    	double maxDelta = 0.001;
    	int maxIterations = 1000;
    	ValueIteration vi = new ValueIteration(this.domain, this.rf, this.tf, gamma, 
    			this.hashFactory, maxDelta, maxIterations);
    	vi.planFromState(this.initState);
    	return vi;
    }
    
    
    
    
    public void PolicyIterationExample(){
		 
		 double gamma = 0.75;
		 double maxDelta = 0.00001;
		 int maxEvaluationIterations = 100;
		 int maxPolicyIterations = 100;
		 
		 PolicyIteration pi = new PolicyIteration(this.domain, 
				 this.rf, this.tf,gamma,this.hashFactory, maxDelta, 
				maxEvaluationIterations, maxPolicyIterations);
		 
		  System.out.println(pi.getGamma());
		 
		 pi.planFromState(this.initState);
		 
		 
		  }
    
    
    

		
	public String bestFirstAction(double gamma){
        // Return "action a" if a is the best action based on the discount factor given.
		// Return "action b" if b is the best action based on the discount factor given.
		// Return "action c" if c is the best action based on the discount factor given.
		// If there is a tie between actions, give preference to the earlier action in the alphabet:
		//   e.g., if action a and action c are equally good, return "action a".
	
		 ValueIteration vi = computeValue(gamma);
		 double[] v = new double[this.numStates];
		 for(int i = 0;i < this.numStates;i++){
			 State s = GraphDefinedDomain.getState(this.domain, i);
			 v[i] = vi.value(s);
		 }
		 String actionName = null;
		 if(v[1] >= v[2] && v[1] >= v[3]) actionName = "action a";
		 else if(v[2] >= v[1] && v[2] >= v[3]) actionName = "action b";
		 else if(v[3] >= v[1] && v[3] >= v[2]) actionName = "action c";
		 
		 return actionName;
		 
	 }
    
    
	public static void main(String[] args) {
		double p1 = 5.;
		double p2 = 6.;
		double p3 = 3.;
		double p4 = 7.;
		FirstMDPProblemV1 mdp = new FirstMDPProblemV1(p1,p2,p3,p4);
		
		double gamma = 0.6;
		//mdp.computeValue(gamma);
		//System.out.println("Best initial action version 1: " + mdp.bestFirstAction(gamma));
		
		mdp.PolicyIterationExample();
	}
}
