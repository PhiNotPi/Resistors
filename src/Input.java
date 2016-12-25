import java.util.List;

public class Input extends Subcircuit {

  int type;

  public Input(int type) {
    super(1, 0, 0, false);
    this.type = type;
  }

  @Override
  double eval(double[] box) {
    return box[type];
  }

  @Override
  public String toString() {
    return "" + "XYZW".charAt(type);
  }

  @Override
  public String toEq() {
    return "" + "XYZW".charAt(type);
  }

  @Override
  protected Subcircuit clone() {
    return new Input(type);
  }

  @Override
  boolean wouldBeValid(List<Subcircuit> children) {
    return true;
  }

}
