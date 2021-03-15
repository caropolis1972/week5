package week5;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class CoffeeTouchscreenAdapterTest {

    @Test
    public void testCreateAccountHolder() {
	OldCoffeeMachine machine = new OldCoffeeMachine();
	CoffeeTouchscreenAdapter adapter = new CoffeeTouchscreenAdapter(machine);

	assertEquals(machine, adapter.theMachine);
	assertEquals("A - Selected", adapter.chooseFirstSelection());
	assertEquals("B - Selected", adapter.chooseSecondSelection());
    }

}
