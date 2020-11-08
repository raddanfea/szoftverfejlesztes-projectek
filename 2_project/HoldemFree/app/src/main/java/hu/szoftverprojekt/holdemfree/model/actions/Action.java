package hu.szoftverprojekt.holdemfree.model.actions;

/**
 * Base class for all action.
 * !!! Use this as a type when you want to store an action or request one as a function argument.
 * For example: {@code void setNextAction(Action action){}}
 */
public class Action {
  public String name;
  public Action() {
    name = this.getClass().getSimpleName();
  }
}
