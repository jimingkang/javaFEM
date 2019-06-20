package org.sustech.fem;

import org.sustech.fem.Element.LinearElement2D;
import org.sustech.fem.Node.BaseNode;

public class Main {
    public static int NodeNum=4;
    public int EleNum=2;
     public static double[][] globalK=new double[NodeNum][NodeNum];

    public static void main(String[] args) {

        System.out.println("Hello World!");
        LinearElement2D[] elems=new LinearElement2D[2];

        BaseNode[] nodes=new BaseNode[3];
         nodes[0]=new BaseNode(0,0,1);
         nodes[1]=new BaseNode(1,1,1);
         nodes[2]=new BaseNode(0,1,1);
        nodes[0].uvw=new double[]{0.1,0.2,0};
        nodes[0].number=1;
        nodes[1].number=2;
        nodes[2].number=3;

        BaseNode[] nodes2=new BaseNode[3];
        nodes2[0]=new BaseNode(0,0,1);
        nodes2[1]=new BaseNode(1,0,1);
        nodes2[2]=new BaseNode(1,1,1);
        nodes2[0].uvw=new double[]{0.1,0.2,0};
        nodes2[0].number=1;
        nodes2[1].number=3;
        nodes2[2].number=4;

       elems[0]=new LinearElement2D(nodes);
     elems[1]=new LinearElement2D(nodes2);

        for (LinearElement2D ele:
            elems ) {
            for (BaseNode nod:
                    ele.getNodes()   ) {
              //  globalK[nod.number][] =;
            }

        }
        System.out.println(elems[1].getDisp(0.1,0.0)[0]);
       // System.out.println(ele.getLinearShape2Ds()[1].getValue(1,3));
       // System.out.println(ele.getLinearShape2Ds()[2].getValue(1,3));

    }
}
