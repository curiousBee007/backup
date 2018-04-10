
import burlap.behavior.singleagent.Policy;
import burlap.behavior.singleagent.planning.commonpolicies.GreedyQPolicy;
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
public class SecondMDPProblem {
	
	DomainGenerator				dg;
    Domain						domain;
    State						initState;
    RewardFunction				rf;
    TerminalFunction			tf;
    DiscreteStateHashFactory hashFactory;
    int numStates; 
    double p1;
    double p2;
    
    public SecondMDPProblem(double p1, double p2) {
    	
    	
        
    	this.p1 = p1;
    	this.p2 = p2;
    	
    	this.numStates = 6;
        this.dg = new GraphDefinedDomain(numStates);
    	
        ((GraphDefinedDomain) this.dg).setTransition(0, 0, 0, p1);
        ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1, 1-p1);
        ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2, 1);
        
        ((GraphDefinedDomain) this.dg).setTransition(1, 0, 5, p2);
        ((GraphDefinedDomain) this.dg).setTransition(1, 0, 3, 1-p2);
        ((GraphDefinedDomain) this.dg).setTransition(1, 1, 4, 1.0);
        
        ((GraphDefinedDomain) this.dg).setTransition(2, 0, 1, 1.0);
        
        ((GraphDefinedDomain) this.dg).setTransition(3, 0, 1, 1.0);
        
        ((GraphDefinedDomain) this.dg).setTransition(4, 0, 5, 1.0);
        
        
        
        this.domain = this.dg.generateDomain();
        
        this.initState = GraphDefinedDomain.getState(this.domain,0);
        
		this.rf = new SecondProblemRF();
		
        this.tf = new SingleStateTF(5);
        
        this.hashFactory = new DiscreteStateHashFactory();
        
        }
    
	
    public static class SingleStateTF implements TerminalFunction{
   
    	int stateId;
    	public SingleStateTF(int stateId){
    		this.stateId = stateId;
    		
    	}
    	
    	
		@Override
		public boolean isTerminal(State s) {
			
			boolean isTerminal = false;
			
			int sid = GraphDefinedDomain.getNodeId(s);
			if(sid == stateId)
				isTerminal = true;
			
			return isTerminal;
		}
		}
    
    
    public static class SecondProblemRF implements RewardFunction {
		
    	@Override
		public double reward(State s, GroundedAction a, State sprime) { 
			
			
			int sid = GraphDefinedDomain.getNodeId(s);
			
			int targetId = GraphDefinedDomain.getNodeId(sprime);
			
			double r =0.0;
			
			if(sid == 0)
			{
			if( targetId == 0 )  r = -1.0;
			
			if( targetId == 1 )  r = 3.0;
			
			if(targetId == 2 )  r = 1.0;
			
			
			}
			
			else if(sid == 1){
			
			if( targetId == 3 ) r = 1.0;
			
			if( targetId == 4 ) r = 2.0;
			
			if(targetId == 5 )  r = 0.0;
			
			}
			
			else if( 2 <= sid && sid <= 4) { 
				r = 0;
			}
			
			else if(sid == 5){
				throw new RuntimeException("No transition from state: " + sid);
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
    
    
    
    
    
    
    public String bestActions(double gamma) {
        // Return one of the following Strings
        // "a,c"
        // "a,d"
        // "b,c" 
        // "b,d"
        // based on the optimal actions at states S0 and S1. If 
        // there is a tie, break it in favor of the letter earlier in
        // the alphabet (so if "a,c" and "a,d" would both be optimal, 
        // return "a,c").
    	
    	 String actionName = null;
    	 ValueIteration vi = computeValue(gamma);
    	 
    	 Policy p = new GreedyQPolicy(vi);
    	 State s0 = GraphDefinedDomain.getState(this.domain,0);
    	 State s1 = GraphDefinedDomain.getState(this.domain,1);
    	 
    	 String s0Action = p.getAction(s0).actionName()
    			 .replaceAll("action0","a").replaceAll("action1", "b");
    	 String s1Action = p.getAction(s1).actionName()
    			 .replaceAll("action0","c").replaceAll("action1", "d");
		 
    	 String policy = s0Action  + "," + s1Action;
		 
		 return policy;
    	
    	
    	 }
    
    public static void main(String[] args) {
		double p1 = 0.5;
		double p2 = 0.5;
		SecondMDPProblem mdp = new SecondMDPProblem(p1,p2);
		
		double gamma = 0.5;
		System.out.println("Best actions: " + mdp.bestActions(gamma));
	}
    
 }
