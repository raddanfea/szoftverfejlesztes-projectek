package hu.szoftverprojekt.holdemfree.model;

/**
 * Query class.
 * !!! Use this to create an instance from one of the child of the Action class.
 * For example: {@code nextTurn( Actions.FOLD )}
 *           or {@code nextTurn( Actions.RAISE_BY(100) )}
 *           where {@code void nextTurn(Action action)} expects an argument with Action type.
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

/**
 * Base class for all action.
 * !!! Use this as a type when you want to store an action or request one as a function argument.
 * For example: {@code void nextTurn(Action action){}}
 */
class Action {}

class Fold extends Action {}

class Hold extends Action {}

class Raise extends Action {
  public int by;
  
  public Raise(int amount) {
    by = amount;
  }
}