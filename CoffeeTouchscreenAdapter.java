package week5;

public class CoffeeTouchscreenAdapter implements CoffeeMachineInterface {

    OldCoffeeMachine theMachine;

    public CoffeeTouchscreenAdapter(OldCoffeeMachine newMachine) {
	theMachine = newMachine;
    }

    public String chooseFirstSelection() {
	return theMachine.selectA();
    }

    public String chooseSecondSelection() {
	return theMachine.selectB();
    }
}
