package animus;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class MagicClassifier
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public MagicClassifier(){
		super();
	}

  public double[] classify(String[] w) {
    System.out.println("CLASSIFY");
    for(int i=0; i<w.length; i++) {
      System.out.println(w[i]);
    }
    double[] r = {0.4, 0.5, 0.3};
    return r;
  }
}

