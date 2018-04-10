import java.util.Random;

public class RandomShuffle {

	public int getRandom(int floor, int ceiling) {
	    Random rand = new Random();
	    return rand.nextInt((ceiling - floor) + 1) + floor;
	}

	public void shuffle(int[] theArray) {
	    // if it's 1 or 0 items, just return
	    if (theArray.length <= 1) return;

	    // walk through from beginning to end
	    for (int indexWeAreChoosingFor = 0;indexWeAreChoosingFor < theArray.length - 1; indexWeAreChoosingFor++) {

	        // choose a random not-yet-placed item to place there
	        // (could also be the item currently in that spot)
	        // must be an item AFTER the current item, because the stuff
	        // before has all already been placed
	        int randomChoiceIndex = getRandom(indexWeAreChoosingFor, theArray.length - 1);

	        // place our random choice in the spot by swapping
	        if (randomChoiceIndex != indexWeAreChoosingFor) {
	            int valueAtIndexWeChoseFor = theArray[indexWeAreChoosingFor];
	            theArray[indexWeAreChoosingFor] = theArray[randomChoiceIndex];
	            theArray[randomChoiceIndex] = valueAtIndexWeChoseFor;
	        }
	    }
	}
}
