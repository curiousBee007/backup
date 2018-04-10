
import burlap.behavior.singleagent.ValueFunctionInitialization;
import burlap.behavior.statehashing.DiscreteStateHashFactory;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import org.json.JSONArray;
import org.json.JSONObject;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain.GraphAction;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain.NodeTransitionProbibility;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.State;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class PolicyIterationAttempt2 {

	 DomainGenerator				dg;
	    Domain						domain;
	    State						initState;
	    RewardFunction				rf;
	    TerminalFunction			tf;
	    DiscreteStateHashFactory	hashFactory;
	    int numStates;
	    int noOfTransitions;
	
	public PolicyIterationAttempt2(){
		  
		this.numStates = 30;
	    this.dg = new GraphDefinedDomain(numStates);
	    this.noOfTransitions = 0;
	    
	    for(int i = 0; i < numStates;i++){
	    	
	    	//Action for 0 state
	    	if(i == 0){
	    		
	    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,1.0);
	    	 ((GraphDefinedDomain) this.dg).setTransition(i, 1, i+2,1.0);
	    	 noOfTransitions = noOfTransitions +2;
	         
	    	}
	    	
	    	
	    	//Action for  decision states
	    	else if( (i%2 == 1) && (i < (numStates -3)) 
	    			&& (i+2  < numStates) && (i+3  < numStates)){
	    		
	    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);
		    	((GraphDefinedDomain) this.dg).setTransition(i, 1, i+3,1.0);
		    	 noOfTransitions = noOfTransitions +2;
	    		}
	    	
	    	
	    	//Action for random classes
	    	else if( (i%2 == 0) && (i <  (numStates -2))
	    			&& (i+2  < numStates) && (i+1  < numStates)){
	    		
	    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,0.5);
		    	 ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,0.5);
		    	 noOfTransitions = noOfTransitions +2;

		    	}
	    	
	    	  
	    	  //Last decision state
	    	  else if (i == numStates -3){
	    	  ((GraphDefinedDomain) this.dg).setTransition(i, 0, i+2,1.0);	
	    	  noOfTransitions = noOfTransitions +1;
	    	  
	    	  }
	    		//rewardAction1[i][i+2] = -1;
	    		
	    	
	    	  
	    	   //Last random state
	    	   else if (i == numStates -2){
	    		((GraphDefinedDomain) this.dg).setTransition(i, 0, i+1,1.0);
	    		 noOfTransitions = noOfTransitions +1;
	    	   }
	    		
	    		
	    	   //Last state or null state
	    	    else if (i == numStates -1){
	    	  ((GraphDefinedDomain) this.dg).setTransition(i, 0, i,1.0);
	    	   noOfTransitions = noOfTransitions +1;
	    	    }
	    	
	    	 }
	    
	      
      this.domain = this.dg.generateDomain();
   
     this.initState = GraphDefinedDomain.getState(this.domain,0);
     
		this.rf = new PolicyRF();
		
     this.tf = new NullTermination();
     
     this.hashFactory = new DiscreteStateHashFactory();
		
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
			
			if(sid == 27 && targetId == 29) reward = -5;
			
			return reward;
		
		}
			
	}
	
	
	public static String burlapToJson(GraphDefinedDomain gdd, Domain domain,
 			RewardFunction rf, double gamma) throws NoSuchFieldException,
 			SecurityException, IllegalArgumentException, IllegalAccessException {
 		JSONObject mdp = new JSONObject();
 		JSONArray states = new JSONArray();
 		mdp.put("states", states);
 		mdp.put("gamma", gamma);
 		Field f = GraphDefinedDomain.class.getDeclaredField("transitionDynamics");
 		f.setAccessible(true);
 		@SuppressWarnings("unchecked")
 		Map<Integer, Map<Integer, Set<NodeTransitionProbibility>>> transitionDynamics = (Map<Integer, Map<Integer, Set<NodeTransitionProbibility>>>) f
 				.get(gdd);
 		for (Integer fromId : transitionDynamics.keySet()) {
 			State fromState = GraphDefinedDomain.getState(domain, fromId);
 			JSONObject state = new JSONObject();
 			states.put(state);
 			state.put("id", fromId);
 			JSONArray actions = new JSONArray();
 			state.put("actions", actions);
 			Map<Integer, Set<NodeTransitionProbibility>> actionMap = transitionDynamics
 					.get(fromId);
 			for (Integer actionId : actionMap.keySet()) {
 				JSONObject action = new JSONObject();
 				actions.put(action);
 				action.put("id", actionId);
 				JSONArray transitions = new JSONArray();
 				action.put("transitions", transitions);
 				Set<NodeTransitionProbibility> transitionSet = actionMap
 						.get(actionId);
 				for (NodeTransitionProbibility ntp : transitionSet) {
 					JSONObject transition = new JSONObject();
 					transitions.put(transition);
 					transition.put("probability", ntp.probabiltiy);
 					transition.put("to", ntp.transitionTo);
 					State toState = GraphDefinedDomain.getState(domain,
 							ntp.transitionTo);
 					GraphAction graphAction = (GraphAction) domain
 							.getAction(GraphDefinedDomain.BASEACTIONNAME
 									+ actionId);
 					GroundedAction groundedAction = new GroundedAction(
 							graphAction, "");
 					transition.put("reward",
 							rf.reward(fromState, groundedAction, toState));
 				}
 			}
 		}
 		return mdp.toString();
 	}
	
	
	
	
	

/* Function to find total value iterations on the above defined MDP*/	
    public void PolicyIterationExample() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		 
		 double gamma = 0.75;
		 double maxDelta =  0.00001;
		 int maxEvaluationIterations = 1000;
		 int maxPolicyIterations = 1000;
		 
		 PolicyIteration pi = new PolicyIteration(this.domain, 
				 this.rf,this.tf,gamma,this.hashFactory, maxDelta, 
				maxEvaluationIterations, maxPolicyIterations);
		 
		pi.planFromState(this.initState);
		
		String mdpJSON = burlapToJson(((GraphDefinedDomain) this.dg),this.domain,
	 			this.rf, gamma);
		
		System.out.println(mdpJSON); 
		
		}
    
	
   
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		 
    	PolicyIterationAttempt2 obj = new PolicyIterationAttempt2();
	    
	    System.out.println("Total no of transitions  " +obj.noOfTransitions);
	    
       obj.PolicyIterationExample();
     
    }
		 
}
