
/*I am trying to implement simple policy iteration example given in Littman 1996 paper.
 *The assumptions made for implementing this MDP problem
 *There are n = 30 states from(0,1,2,3....29)
 *State s0 ,s1 ,s3,s5 ,....s27 are decision states with two deterministic action each.
 *
 *State S0 move to state 1 on action 0 and state 2 on action 1.
 *
 *State s2,s4,.....s28 are random states which have stochastic transitions
 *
 *Decision state Si move to S(i+2)  on action 0 and S(i+3) on action 1 when i = 1,3,5,...25
 *
 *Random state Si move to S(i+1)  on action 0 and S(i+2) on action 1 when i = 2,4,8.....26
 *
 *State 27 (last decision state) move to absorbing state 29 with reward -1.
 *
 *State 28 (last random state) move to absorbing state 29 with reward -1.
 *
 *State 29 is defined as null terminal state
 *
 *Reward function returns -1 when there is a transition from state 27 to 29 else it returns 0
 *
 *
 **/
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.*;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;


public class PolicyIterationFreshStart {
	
	    DomainGenerator				dg;
	    Domain						domain;
	    State						initState;
	    RewardFunction				rf;
	    TerminalFunction			tf;
	    DiscretizingHashableStateFactory	hashFactory;
	    int numStates;
	    int noOfTransitions;
	
	public PolicyIterationFreshStart(){
		  
		this.numStates = 30;
	    this.dg = new GraphDefinedDomain(numStates);
	    this.noOfTransitions = 0;
	    
	    for(int i = 0; i < numStates;i++){
	    	
	    	//Action for 0 state
	    	if(i == 0){
	    		
	    	 ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1, 1.0);
	    	 ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2, 1.0);
	    	 noOfTransitions = noOfTransitions +2;
	         
	    	}
	    	
	    	
	    	//Action for  decision states
	    	else if( (i%2 == 1) && (i <= 25)){
	    		
	    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);
		    	((GraphDefinedDomain) this.dg).setTransition(i, 1, i+3,1.0);
		    	 noOfTransitions = noOfTransitions +2;
	    		}
	    	
	    	
	    	//Action for random classes
	    	else if( (i%2 == 0) && (i <= 26)){
	    		
	    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,0.5);
		    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,0.5);
		    	 noOfTransitions = noOfTransitions +2;

		    	}
	    	
	    	  
	    	  //Last decision state
	    	  else if (i == 27 || i == 28 || i == 29){
	    	  ((GraphDefinedDomain) this.dg).setTransition(i, 0, 29,1.0);	
	    	  noOfTransitions = noOfTransitions +1;
	    	  
	    	  }
	    		
	    	 }
	    
	      
         this.domain = this.dg.generateDomain();
      
        this.initState = GraphDefinedDomain.getState(this.domain,0);
        
		this.rf = new PolicyRF();
		
        this.tf = new NullTermination();
        
        this.hashFactory = new DiscretizingHashableStateFactory(0);
		
	  }
	
	
	
	/* Reward Function */
	public static class PolicyRF implements RewardFunction {
		 
	    @Override
		public double reward(State s, GroundedAction a, State sprime) { 
			
			int sid = GraphDefinedDomain.getNodeId(s);
			
			int targetId = GraphDefinedDomain.getNodeId(sprime);
			
			//String actionName = a.actionName();
			
			//int actionId = Integer.parseInt(actionName.substring(6,7));
			
			double reward = 0.0;
			
			if(sid == 27 && targetId == 29) 
			{reward = -5; 
			}
			
			return reward;
		
		}
			
	}

  /* Function to find total value iterations on the above defined MDP*/	
       public void PolicyIterationExample(){
		 
		 double gamma = 0.75;
		 double maxDelta =  0.00001;
		 int maxEvaluationIterations = 1000;
		 int maxPolicyIterations = 1000;
		 
		 PolicyIteration pi = new PolicyIteration(this.domain, 
				 this.rf,this.tf,gamma,this.hashFactory,maxDelta, 
				maxEvaluationIterations, maxPolicyIterations);
		 
		pi.planFromState(this.initState);
		
		System.out.println("Total policy iterations " + pi.getTotalPolicyIterations());
		
		}
       
	
      
       public static void main(String[] args){
  		 
  	    PolicyIterationFreshStart obj = new PolicyIterationFreshStart();
  	    
  	    System.out.println("Total no of transitions  " +obj.noOfTransitions);
  	    
        obj.PolicyIterationExample();
        
       }
  		 
}
