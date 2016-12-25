import java.util.List;

public class Parallel extends Subcircuit {

  public Parallel() {
    super(0, 2, 100, false);
  }

  @Override
  double eval(double[] box) {
    double sum = 0;
    for (int i = 0; i < children.size(); i++) {
      double r = children.get(i).eval(box);
      if (r <= 0) {
        return 0;
      }
      sum += 1.0 / r;
    }
    return 1.0 / sum;
  }

  @Override
  public String toString() {
    String res = "{";
    for (Subcircuit s : children) {
      res += s.toString();
    }
    return res + "}";
  }

  @Override
  public String toEq() {
    String res = "1/(";
    for (int i = 0; i < children.size(); i++) {
      if (i > 0) {
        res += "+";
      }
      res += "1/" + children.get(i).toEq();
    }
    return res + ")";
  }

  @Override
  protected Subcircuit clone() {
    return new Parallel();
  }

  @Override
  boolean wouldBeValid(List<Subcircuit> children) {
    if (children.size() < 2) {
      return false;
    }
    for (int i = 0; i < children.size(); i++) {
      Subcircuit c = children.get(i);
      if (c instanceof Parallel) {
        return false;
      }
      // if (!c.isValid()) {
      // return false;
      // }
      if (i > 0) {
        if (c.id < children.get(i - 1).id) {
          return false;
        }
      }
    }
    return true;
  }
}
