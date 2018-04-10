
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

public class PolicyIterationv2SecondAttempt {
   
	
	DomainGenerator	dg;
	Domain domain;
	State initState;
	RewardFunction rf;
	TerminalFunction tf;
	//DiscretizingHashableStateFactory hashFactory;
	 DiscretizingHashableStateFactory	hashFactory; 
	int numStates;
	
	

	 public PolicyIterationv2SecondAttempt(double probAction1[][], 
			                   double[][] probAction2, 
			                   double[][] rewardAction1,double [][] rewardAction2) {
	        // add your code here
	     
		   // this.probability1 = probToState1;
		    //this.probability2 = (1 -probToState1);
		    this.numStates = 30;
		   
		    //Domain Generator is an interface implemented by different classes
		    
		    this.dg = new GraphDefinedDomain(numStates);
		  
		    
		    for(int i =0;i < 30;i++){
		    	for(int j = 0; j < 30;j++){
		    		if(probAction1[i][j] != 0.0)
			    		((GraphDefinedDomain) this.dg).setTransition(i, 0, j,probAction1[i][j]);
			    		
			    		if(probAction2[i][j] != 0.0)
			    		((GraphDefinedDomain) this.dg).setTransition(i, 1, j,probAction2[i][j]);
		    	}
		    	
		    }
		    
		    
		    
		   this.domain = this.dg.generateDomain();
		    
		   this.initState = GraphDefinedDomain.getState(this.domain,0);
		   
		   if (this.initState.equals(null)){
			   System.out.println("Anu");
		   }

			this.rf = new FourParamRF(rewardAction1,rewardAction2);
			
		    //this.tf = new SingleStateTF(6);
		   
		    this.tf = new NullTermination();
		    
		    //this.hashFactory = new DiscreteStateHashFactory();
		   
		    this.hashFactory = new DiscretizingHashableStateFactory(35);
		    //this.vinit = new ValueFunctionInit(valueEstimates);
		    
		    
		    
		    
		    }
	 
	 
	 
	 
	public static class FourParamRF implements RewardFunction {
			double rewardAction1[][];
			double  rewardAction2[][];
			
			double reward1;
		
			
			public FourParamRF(double rewardAction1[][], double rewardAction2[][]) {
				this.rewardAction1 = rewardAction1;
				this.rewardAction2 = rewardAction2;

			}
			
			@Override
			public double reward(State s, GroundedAction a, State sprime) { 
				int sid = GraphDefinedDomain.getNodeId(s);
				int targetId = GraphDefinedDomain.getNodeId(sprime);
				String actionName = a.actionName();
				int actionId = Integer.parseInt(actionName.substring(6,7));
				
				if (actionId == 0){
					reward1 = rewardAction1[sid][targetId];
				}
				
				else if (actionId == 1){
					reward1 = rewardAction2[sid][targetId];
				}
				
				return reward1;
			
			}
				
		}
	 
	
	 public void PolicyIterationExample() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		 
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
	 
	 
	 
	 
	 	
	
	 
	 private ValueIteration computeValue(double gamma) {
	    	double maxDelta = 0.001;
	    	int maxIterations = 1000;
	    	ValueIteration vi = new ValueIteration(this.domain, this.rf, this.tf, gamma, 
	    			this.hashFactory, maxDelta, maxIterations);
	    	vi.planFromState(this.initState);
	    	return vi;
	    }
	 
 public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		 
		 double probAction1[][] = new double[30][30]; 
		 double probAction2[][] = new double[30][30];
		 double reward1[][] = new double[30][30];
		 double reward2[][] = new double[30][30];
		 
		 for(int i =0; i < 30;i++){
		
			 for(int j =0; j < 30;j++){
				 probAction1[i][j] = 0.0;
				 probAction2[i][j] = 0.0;
				 reward1[i][j] = 0.0;
				 reward2[i][j] = 0.0;
				 
			 }
		 }
		
		double action2Prob[] = new double[30];
		 
		double action1Prob[] ={0.6, 0.3, 0.1, 0.4, 0.5,
				               0.3, 0.9, 0.3, 0.7, 0.6, 
				               0.3, 0.3, 0.4, 0.8, 0.5,
				               0.7 ,0.2, 0.1 ,0.6, 0.8, 
				               0.5 ,0.7, 0.5 , 0.1, 0.3,
				               0.8 ,0.2, 0.5, 0.1, 1.0
				               }; 
			 
		
		for(int j = 0; j < 30; j ++){
			
			action2Prob[j] = 1.0 - action1Prob[j];
			}
			 
			 
		int action1TransitionState[] = {0,2,1,2,6,
				                        7,12,4,16,11,
				                        2,8,7,17,19,
				                        21,23,7,9,26,
				                        21,27,22,29,13,
				                        29,29,15,4,29};
				
		
		int action2TransitionState[] = {1,3,5,4,8,
				                        9,9,10,3,5
				                       ,13,14,15,16,20,
				                        22,24,25,5,12,
				                        13,11,28,23,29,
				                        4,11,11,29,29};
		
		
		double rewardAction1[] = {3.0 ,4.0,1.0,1.6,1.2,
				                 7.2, 1.2, .4, 4.2, 3.6,
				                 -13,-1.2, .2, 1.0, 1.2,
				                 -1.4, .5,-2.0,-2.0,1.3,
				                  .2,6.0,-.2,-1.0,-1.5,
				                   -1.7,-4.0,-3.6,.2,-1.7};
		
		
		
	    double rewardAction2[] = { 2.5,6.5,1.7,2.1,1.4,
	    		                   4.7,1.0,4.0,1.2,0.6,
	    		                   1.2,.2,2.5,.2,.2,
	    		                   1.2,1.7,8.0,1.0,-1.2,
	    		                   -7.5,-2.0,1.0,-2.0,-4.0,
	    		                   1.2,-1.5,-4.0,-3,-4.0};
		
		
		
	    for(int k = 0 ; k < 30; k++){
	    	int rowIndex = k;
	    	int colIndex1 = action1TransitionState[k];
	    	int colIndex2 = action2TransitionState[k];
	    	
	    	probAction1[rowIndex][colIndex1] = action1Prob[k];
	    	probAction2[rowIndex][colIndex2] = action2Prob[k];
	    	reward1[rowIndex][colIndex1] = rewardAction1[k];
	    	reward2[rowIndex][colIndex2] = rewardAction2[k];
	    	
	    	
	    }
	   
	    
	    
	 System.out.println("Reward value for first action ---------------");   
	 
	 for(int i =0; i < 30;i++){
		 
		 for(int j = 0; j< 30;j++){
			 
			 if( reward1[i][j] != 0){
			 
			 System.out.print(" State " +i + " transitioned to "+ " State " +j+
			                       "   under action 0 with  probability"+  probAction1[i][j] +
			                         "getting reward " +reward1[i][j]); 
			 System.out.println();
			 }
			 
			 if( reward2[i][j] != 0){
			 System.out.println();
			 
			 System.out.print(" State " +i + " transitioned to "+ " State " +j+
                     "   under action 1 with  probability"+  probAction2[i][j] +
                       "getting reward " +reward2[i][j]); 
			 
			 }
		 }
		 
		 System.out.println();
		 System.out.println();
	  }
	 
	 System.out.println();  
	    
     System.out.println("MDP created successfully for version 1");
		
	    PolicyIterationv2SecondAttempt obj = new PolicyIterationv2SecondAttempt(probAction1,probAction2,
                reward1,reward2);
        obj.PolicyIterationExample();
	   // obj.computeValue(0.75);

		
		}
		 
	 
	 
	}



