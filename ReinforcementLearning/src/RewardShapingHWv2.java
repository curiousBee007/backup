
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;

public class RewardShapingHWv2 {

	
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
    
    
    public RewardShapingHWv2(double[][][] probabilitiesOfTransitions,
    		                double[][][] rewards ,double gamma,int numStates, int numActions) {
        this.numStates = numStates;
        this.dg = new GraphDefinedDomain(numStates);
        this.finalval = new double[numStates];
        
       
         //double totalVal;
         for(int i = 0; i < numStates;i++){
        	//totalVal = 0.0;
        	for(int j = 0; j < numActions;j++){
        		for(int k = 0; k < numStates;k++){
        			//if(probabilitiesOfTransitions[i][j][k] > 0.0){
        			
        			/*System.out.println("Source node  is  " + i + "Action is  " 
        			                    +j+  " Target node is  " +k + 
        			                    "and probabilities of transition  is   "
        			                    +  probabilitiesOfTransitions[i][j][k] + "and reward is  "
        			                    +rewards[i][j][k]);*/
        			
        			
        			 ((GraphDefinedDomain) this.dg).setTransition(i, j, k, 
        					 probabilitiesOfTransitions[i][j][k]);
        		
        		}}
         // }
        	
        
       }
        
       
        this.domain = this.dg.generateDomain();
        
        this.initState = GraphDefinedDomain.getState(this.domain,1);
      
        this.rf = new SimpleRF(rewards);
        
        int terminalStates[] = {0,3,6,7};
        
        this.tf = new ManyStateTF(terminalStates);
        
        this.transitionProb = probabilitiesOfTransitions;
        
        this.hashFactory = new DiscretizingHashableStateFactory(0);
   
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
 
    
  
 
 public double[] SimplePolicyIterationExample(double gamma1){
	 
	 double gamma = gamma1;
	 double maxDelta = 0.0001;
	 int maxEvaluationIterations = 100;
	 int maxPolicyIterations = 100;
	 State state1 = GraphDefinedDomain.getState(this.domain,1);
	 
	 PolicyIteration obj = new PolicyIteration(this.domain, 
			 this.rf1,this.tf,gamma,this.hashFactory, maxDelta, 
			maxEvaluationIterations, maxPolicyIterations);
	 
	
	
	 // obj.value(s)
	 double[] finalVal = new double[9];
	 
	 obj.planFromState(state1);
	 
	 for (int i = 0; i < 9;i++){
		 State stateVal = GraphDefinedDomain.getState(this.domain,i);
		 System.out.println(  "Value of state  "+i+"  in policy iteration object "
		                       +obj.value(stateVal));
		 finalVal[i] = obj.value(stateVal);
		 
	  }
	
	 int totalPolIteration = obj.getTotalPolicyIterations();
	 System.out.println("total policy iteration " + totalPolIteration);
	 return finalVal;
	 
	  }

    
    
    
    
    
    
    
    
    
    
   
	
	
}
