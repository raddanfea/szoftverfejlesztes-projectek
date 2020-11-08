package hu.szoftverprojekt.holdemfree.model.actions;

/**
 * Query class.
 * !!! Use this to create an instance from one of the child of the Action class.
 * For example: {@code setNextAction( Actions.FOLD )}
 *           or {@code setNextAction( Actions.RAISE_BY(100) )}
 *           where {@code void setNextAction(Action action)} expects an argument with Action type.
 *
 * Think about it as an Enum type, where the "Actions" is the name of the enum
 * and the "FOLD" is just a value.
 */
public class Actions {
  
  public static final Fold FOLD = new Fold();
  public static final Hold HOLD = new Hold();
  
  public static Raise RAISE_BY(int by) {
    return new Raise(by);
  }
  
}