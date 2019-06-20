package org.sustech.fem.Element;

/**
 * Created by jimmy on 6/20/19.
 */
import org.sustech.fem.Node.BaseNode;
public class BaseElement {
    private int number;
   public BaseNode[] nodes;
    public BaseNode[] getNodes(){
        return nodes;
    }

}
