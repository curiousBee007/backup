import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.*;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.DiscretizingHashableStateFactory;


public class RewardShapingHW5 {
		
    DomainGenerator				dg;
    Domain						domain;
    State						initState;
    RewardFunction				rf;
    RewardFunction              rf1;
    TerminalFunction			tf;
    DiscretizingHashableStateFactory	hashFactory;
    int numStates; 
    double tsiFunc[];
    double shapeRewardFunc[][][];
    
    double finalval[];
    double [][][] transitionProb;
    
    
    public RewardShapingHW5(double[][][] probabilitiesOfTransitions,
    		                double[][][] rewards ,double gamma) {
        this.numStates = 9;
        this.dg = new GraphDefinedDomain(numStates);
        this.finalval = new double[9];
        
         tsiFunc = new double[9];
         shapeRewardFunc = new double[9][4][9];
         
         double totalVal;
         for(int i = 0; i < 9;i++){
        	totalVal = 0.0;
        	for(int j = 0; j < 4;j++){
        		for(int k = 0; k < 9;k++){
        			
        			
        			
        			if(probabilitiesOfTransitions[i][j][k] > 0.0) {	
        			/*System.out.println("Source node  is  " + i + "Action is  " 
        			                    +j+  " Target node is  " +k + 
        			                    "and probabilities of transition  is   "
        			                    +  probabilitiesOfTransitions[i][j][k] + "and reward is  "
        			                    +rewards[i][j][k]);*/
        			
        			
        			 ((GraphDefinedDomain) this.dg).setTransition(i, j, k, 
        					 probabilitiesOfTransitions[i][j][k]);
        			
        			// System.out.println();
        			 
        			}
        		}
          }
        	
        
       }
        
       
        this.domain = this.dg.generateDomain();
        
        this.initState = GraphDefinedDomain.getState(this.domain,0);
      
        this.rf = new SimpleRF(rewards);
        
        this.tf = new NullTermination();
        
        this.transitionProb = probabilitiesOfTransitions;
        
        this.hashFactory = new DiscretizingHashableStateFactory(35);
   
        
    }
    
    
    
    private void setRewardFun(double [][][]rewards, double gamma){
    	
    	double finVal[] = FuncValue();
    	
    	
    	for(int i = 0; i< 9 ; i++){
    		System.out.println("------------------------------------------------");
    	    System.out.println(" Value function at state " +i + "is"  + finVal[i]);
    	    System.out.println();
    	}
    	
    	this.rf1 = new RewardShapingRF(rewards,gamma,finVal);
    	
    	
    	
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
    
    
    public static class RewardShapingRF implements RewardFunction {
		
    	double[][][] rewards;
    	double gamma;
    	double [][][] transitionProb;
    	
    	double finalval[];
		
		public RewardShapingRF(double[][][] rewards,double gamma,
				                double finalval[] ) {
			this.rewards = rewards;
			this.gamma = gamma;
			this.transitionProb = transitionProb;
			
			this.finalval = finalval;
		}
		
		@Override
		public double reward(State s, GroundedAction a, State sprime) {
			
			int sid = GraphDefinedDomain.getNodeId(s);
			int targetid = GraphDefinedDomain.getNodeId(sprime);
			String actionName = a.actionName();
			int actionId = Integer.parseInt(actionName.substring(6,7));
			
			double r;
			
			double finalReward;
			
			double shapeVal = 0.0;
			
			r = rewards[sid][actionId][targetid];
			
			shapeVal = (gamma *(finalval[targetid] )  - (finalval[sid] ))  ;
			
		    finalReward = r   + shapeVal;
		    
		   /* if (r == 1.0){
		    	 finalReward = r   + 0.1;
		    	
		    }
			
			System.out.println("Original Reward " + r + "and final reward is  "+finalReward
					             +"and source id is  " +sid + "and target id is "+targetid);
			System.out.println();*/
			
		 return finalReward;
		 //return r;
		}
    }
    
    
   
   public void PolicyIterationExample(double gamma1){
		 
		 double gamma = gamma1;
		 double maxDelta = 0.00001;
		 int maxEvaluationIterations = 100;
		 int maxPolicyIterations = 100;
		 
		/* PolicyIteration obj = new PolicyIteration(this.domain, 
				 this.rf1,this.tf,gamma,this.hashFactory, maxDelta, 
				maxEvaluationIterations, maxPolicyIterations);
		 
		
		 
		 obj.planFromState(this.initState);*/
		 
		 
		 State state1 = GraphDefinedDomain.getState(this.domain,1);
		  PolicyIteration obj = new PolicyIteration(this.domain, 
					 this.rf1,this.tf,gamma,this.hashFactory, maxDelta, 
					maxEvaluationIterations, maxPolicyIterations);
			 
			
			 
			 obj.planFromState(state1);
			 int totalPolIteration = obj.getTotalPolicyIterations();
			  
			  System.out.println("total policy iteration " + totalPolIteration);
		 
		 
		  }
    
    
     private ValueIteration computeValue(double gamma ,State s) {
   	double maxDelta = 0.001;
   	int maxIterations = 1000;
   	ValueIteration vi = new ValueIteration(this.domain, this.rf, this.tf, gamma, 
   			this.hashFactory, maxDelta, maxIterations);
   	vi.planFromState(s);
   	
   	return vi;
      }
    
     
     private double[] FuncValue(){
    	 
        double finVal1[] = new double[9];
    	 
    	 for(int i = 0 ; i< 9;i++){
    		 
    		 State state1 = GraphDefinedDomain.getState(this.domain,i);
    		
    		 ValueIteration vi = computeValue(0.999,state1);
    		 
    		 finVal1[i] = vi.value(state1); 
    		 
    		 }
    	 
    	return finVal1;
    	
     }
    
    
	
    
	public static void main(String[] args) {
		
		double[][][] probabilitiesOfTransitions = {{{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
			{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
			{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},
				
			{{0.0,0.1,0.8,0.0,0.1,0.0,0.0,0.0,0.0},{0.0,0.9,0.1,0.0,0.0,0.0,0.0,0.0,0.0},
			{0.0,0.9,0.0,0.0,0.1,0.0,0.0,0.0,0.0},{0.0,0.1,0.1,0.0,0.8,0.0,0.0,0.0,0.0}},
			
			{{0.0,0.0,0.9,0.0,0.0,0.1,0.0,0.0,0.0},{0.0,0.1,0.9,0.0,0.0,0.0,0.0,0.0,0.0},
			{0.0,0.8,0.1,0.0,0.0,0.1,0.0,0.0,0.0},{0.0,0.1,0.1,0.0,0.0,0.8,0.0,0.0,0.0}},
			
			{{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},
			{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0}},
			
			{{0.0,0.1,0.0,0.0,0.0,0.8,0.0,0.1,0.0},{0.0,0.8,0.0,0.1,0.0,0.1,0.0,0.0,0.0},
			{0.0,0.1,0.0,0.8,0.0,0.0,0.0,0.1,0.0},{0.0,0.0,0.0,0.1,0.0,0.1,0.0,0.8,0.0}},
			
			{{0.0,0.0,0.1,0.0,0.0,0.8,0.0,0.0,0.1},{0.0,0.0,0.8,0.0,0.1,0.1,0.0,0.0,0.0},
			{0.0,0.0,0.1,0.0,0.8,0.0,0.0,0.0,0.1},{0.0,0.0,0.0,0.0,0.1,0.1,0.0,0.0,0.8}},
			
			{{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},
			{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0}},
			
			{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},
			{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0}},
			
			{{0.0,0.0,0.0,0.0,0.0,0.1,0.0,0.0,0.9},{0.0,0.0,0.0,0.0,0.0,0.8,0.0,0.1,0.1},
			{0.0,0.0,0.0,0.0,0.0,0.1,0.0,0.8,0.1},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.1,0.9}}};
		
		
		
		double[][][] rewards = {{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
		{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},
				
{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},
{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},

{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},
{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},

{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},

{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},
{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},

{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},
{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}},

{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},

{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},
{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}},

{{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},
{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1},{-0.1,-0.1,-0.1,-1.0,-0.1,-0.1,-0.1,1.0,-0.1}}};
		

		double gamma = 0.999;
		
		RewardShapingHW5 obj = new RewardShapingHW5(probabilitiesOfTransitions,rewards,gamma);
		//obj.FuncValue();
		obj.setRewardFun(rewards, gamma);
		obj.PolicyIterationExample(gamma);

		
		
		
		//mdp.computeValue(gamma);
		//System.out.println("Best initial action: " + mdp.bestFirstAction(gamma));
		//obj.PolicyIterationExample(gamma);
		
	}
}


