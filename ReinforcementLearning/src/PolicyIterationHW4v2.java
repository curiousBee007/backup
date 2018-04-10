
import java.util.Random;

import burlap.behavior.singleagent.learning.actorcritic.critics.TDLambda;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.Domain;

import burlap.oomdp.core.states.State;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;

public class PolicyIterationHW4v2 {
   
	
	DomainGenerator	dg;
	Domain domain;
	State initState;
	RewardFunction rf;
	TerminalFunction tf;
	//DiscretizingHashableStateFactory hashFactory;
	 DiscretizingHashableStateFactory	hashFactory; 
	int numStates;
	
	//ValueFunctionInitialization vinit;

	 public PolicyIterationHW4v2(double probAction1[][], 
			                   double[][] probAction2, 
			                   double[][] rewardAction1,double [][] rewardAction2) {
	        // add your code here
	     
		   // this.probability1 = probToState1;
		    //this.probability2 = (1 -probToState1);
		    this.numStates = 30;
		    
		   //Domain Generator is an interface implemented by different classes
		    
		    this.dg = new GraphDefinedDomain(numStates);
		  
		    for(int i = 0; i < numStates;i++){
		    	
		    	if(i == 0){
		    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,1.0);
		    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);
		    	 System.out.println("First if loop possible states form" +i +"  "
		    	 		+ "and  " +(i +1) + "  and  " +(i +2) + "  with probability " +1.0);
		    	
		    	 System.out.println();
		    	}
		    	
		    	
		    	//Definite action
		    	else if( (i%2 == 1) && (i <= (numStates -3)) 
		    			&& (i+2  < numStates) && (i+3  < numStates)){
		    		
		    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);
			    	((GraphDefinedDomain) this.dg).setTransition(i, 0, i+3,1.0);
		    		
			    	 System.out.println("First else if loop possible states form" +i +"  "
				    	 		+ "and  " +(i+2) + "  and  " +(i+3) + "  with probability  " +1.0);
			    	 System.out.println();
		    	}
		    	
		    	//Stochastic transition
		    	else if( (i%2 == 0) && (i <= (numStates -2))
		    			&& (i+2  < numStates) && (i+1  < numStates)){
		    		
		    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,0.5);
			    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,0.5);

			    	 System.out.println("Second else if loop possible states form" +i +"  "
				    	 		+ "and" +(i +1) + "  and  " +(i +2) + "  with probability  " + 0.5);
			    	 System.out.println();
		    		}
		    	
		    	else if (i == numStates -3){
		    		
		    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);	
		    		rewardAction1[i][i+2] = -1;
		    		
		    	System.out.println("Third else if loop possible states form" +i +"  "
				    	 		+ " are  " +(i +2) + "   with probability " +1.0);
		    	System.out.println();
		    	}
		    	
		    	else if (i == numStates -2){
		    		
		    		System.out.println("In else loop");
		    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,1.0);
		    		
		    		
			    	System.out.println("Fourth else if loop possible states form" +i +"  "
					    	 		+ " are  " +(i +1) + " with probability " +1.0);
		    		
			    	System.out.println();
		    	}
		    	
		    	else if (i == numStates -1){
		    	((GraphDefinedDomain) this.dg).setTransition(i, 0, i,1.0);
		    	System.out.println("Last else if loop possible states form" +i +"  "
		    	 		+ " are  " +(i) + "  with probability  " +1.0);	
		    	   System.out.println();
		    	}
		    	
		    	 }
		    
		    
		    /*for(int i =0;i < numStates;i++){
		    	for(int j = 0; j < numStates;j++){
		    		
		    		if(probAction1[i][j] != 0.0)
		    		((GraphDefinedDomain) this.dg).setTransition(i, 0, j,probAction1[i][j]);
		    		
		    		if(probAction2[i][j] != 0.0)
		    		((GraphDefinedDomain) this.dg).setTransition(i, 1, j,probAction2[i][j]);
		    	}
		    	
		    }*/
		    
		   /* ((GraphDefinedDomain) this.dg).setTransition(0, 0, 1,1.0);
		    ((GraphDefinedDomain) this.dg).setTransition(0, 1, 2,1.0);
		   // rewardAction1[0][1] =1.0;
		    //rewardAction2[0][2] =1.0;
		    
		    
		    ((GraphDefinedDomain) this.dg).setTransition(1, 0, 3,1.0);
		    ((GraphDefinedDomain) this.dg).setTransition(1, 1, 4,1.0);
		    
		   // rewardAction1[1][3] =1.0;
		    //rewardAction2[1][4] =1.5;
		    
		    ((GraphDefinedDomain) this.dg).setTransition(2, 0, 3,0.6);
		    ((GraphDefinedDomain) this.dg).setTransition(2, 1, 4,0.4);
		  
		    
		    
		    ((GraphDefinedDomain) this.dg).setTransition(3, 0, 5,1.0);
		    ((GraphDefinedDomain) this.dg).setTransition(3, 1, 6,1.0);
		    //rewardAction1[3][5] =1.0;
		    //rewardAction2[3][6] =1.0;
		    
		    ((GraphDefinedDomain) this.dg).setTransition(4, 0, 5,0.7);
		    ((GraphDefinedDomain) this.dg).setTransition(4, 1, 6,0.3);
		    
		    ((GraphDefinedDomain) this.dg).setTransition(5, 0, 7,1.0);
		    ((GraphDefinedDomain) this.dg).setTransition(5, 1, 8,1.0);
		    //rewardAction1[5][7] =1.0;
		    //rewardAction2[5][8] =1.0;
		    
		    
		    ((GraphDefinedDomain) this.dg).setTransition(6, 0, 7,0.8);
		    ((GraphDefinedDomain) this.dg).setTransition(6, 1, 8,0.2);
		    
		    ((GraphDefinedDomain) this.dg).setTransition(7, 0, 9,1.0);
		   // ((GraphDefinedDomain) this.dg).setTransition(7, 1, 1,0.5);
		    
		    ((GraphDefinedDomain) this.dg).setTransition(8, 0, 9,1.0);
		   // ((GraphDefinedDomain) this.dg).setTransition(8, 1, 1,0.5);
		    
		    ((GraphDefinedDomain) this.dg).setTransition(9, 0, 9,1.0);
		    //((GraphDefinedDomain) this.dg).setTransition(9, 1, 1,0.5);
		    
		  
		    
		   rewardAction1[7][9] = -1.0;*/
		   
		  
		    this.domain = this.dg.generateDomain();
		    
		    this.initState = GraphDefinedDomain.getState(this.domain,0);
		   
		   this.rf = new FourParamRF(rewardAction1,rewardAction2);
			
		    this.tf = new SingleStateTF(29);
		   
		   // this.tf = new NullTermination();
		    
		    this.hashFactory = new DiscretizingHashableStateFactory(35);
		    
		   
		    //this.vinit = new ValueFunctionInit(valueEstimates);
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
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public static class FourParamRF implements RewardFunction {
		 
		    double rewardAction0[][] = new double[30][30];
			double  rewardAction1[][] = new double[30][30] ;
			double reward1;
		
			public FourParamRF(double rewardAction1[][], double rewardAction2[][]) {
				
				for(int i = 0; i < 30;i++){
                 for(int j = 0; j < 30; j++){
						
						this.rewardAction0[i][j] = rewardAction1[i][j];
						this.rewardAction1[i][j] = rewardAction2[i][j];
					}
					
					}
				
				System.out.println("Inside reward function");
				
				for(int i = 0; i < 30; i++){
					
					for(int j = 0; j < 30; j++){
						
						System.out.print("   "+rewardAction0[i][j]);
						
					}
					System.out.println();
					
					}
				
				}
			
			
		@Override
			public double reward(State s, GroundedAction a, State sprime) { 
				
				int sid = GraphDefinedDomain.getNodeId(s);
				int targetId = GraphDefinedDomain.getNodeId(sprime);
				
				String actionName = a.actionName();
				int actionId = Integer.parseInt(actionName.substring(6,7));
				
				double reward1 = 0.0;
				if(sid == 27 && targetId == 29) reward1 = -1;
				
				
				/*//System.out.println("Reward for this transition " +rewardAction0[27][29]);
				
				if (actionId == 0){
					System.out.println("Inside action 0 " + "first state is"
				            +sid +"and target id is " + targetId);
					
					reward1 = rewardAction0[sid][targetId];
					
					if(reward1 == -1){
						System.out.println( "Reward is -1 when sid =  " +sid + 
								    "and the target id   " +targetId);
					}
				}
				
				else if (actionId == 1){
					
					System.out.println("Inside action 1 " + "first state is"
				            +sid +"and target id is " + targetId);
					
					reward1 = rewardAction1[sid][targetId];
					if(reward1 == -1){
						System.out.println( "Reward is 1 when sid =  " +sid + 
								    "and the target id   " +targetId);
					}
					
					
				}
				*/
				return reward1;
			
			}
				
		}
	 
	 
	 
	 
	
	 public void PolicyIterationExample(){
		 
		 double gamma = 0.75;
		 double maxDelta =  0.00001;
		 int maxEvaluationIterations = 10000;
		 int maxPolicyIterations = 1000;
		 
		 PolicyIteration pi = new PolicyIteration(this.domain, 
				 this.rf,this.tf,gamma,this.hashFactory, maxDelta, 
				maxEvaluationIterations, maxPolicyIterations);
		 
		pi.planFromState(this.initState);
		
		int totalPolIteration = pi.getTotalPolicyIterations();
		  
		  //System.out.println("total policy iteration " + totalPolIteration);
		 
		 
		  }
	 
	  private ValueIteration computeValue(double gamma) {
	    	double maxDelta = 0.001;
	    	int maxIterations = 1000;
	    	ValueIteration vi = new ValueIteration(this.domain, this.rf, this.tf, gamma, 
	    			this.hashFactory, maxDelta, maxIterations);
	    	vi.planFromState(this.initState);
	    	return vi;
	    }
	 
	 
	 
	 
	 
	 
	 
	 public static void main(String[] args){
		 
		 int n  = 30;
		 double probAction1[][] = new double[n][n]; 
		 double probAction2[][] = new double[n][n];
		 double reward1[][] = new double[n][n];
		 double reward2[][] = new double[n][n];
		 
		 for(int i =0; i < n;i++){
		
			 for(int j =0; j < n;j++){
				 probAction1[i][j] = 0.0;
				 probAction2[i][j] = 0.0;
				 reward1[i][j] = 0.0;
				 reward2[i][j] = 0.0;
				 
			 }
		 }
		
		
		   
		   
		 
		 
		 
	    
	    System.out.println("MDP created successfully");
	    PolicyIterationHW4v2 obj = new PolicyIterationHW4v2(probAction1,probAction2,
                reward1,reward2);
        obj.PolicyIterationExample();
        
        
        
	    //obj.computeValue(0.75);

		
		}
		 
		 
}	 
		 
		 
		 
		 
		
		 
	 
	 
	 
	



