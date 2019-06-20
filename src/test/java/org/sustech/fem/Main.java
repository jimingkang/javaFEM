package org.sustech.fem;

import org.sustech.fem.Element.LinearElement2D;
import org.sustech.fem.Node.BaseNode;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;

public class Main {
    public static int NodeNum=4;
    public int EleNum=2;
    // public static double[][] globalK=new double[NodeNum][NodeNum];
  static  Matrix globalK = DenseMatrix.Factory.zeros(2*NodeNum, 2*NodeNum);

    public static void MatrixTest(){
    MatrixTest ma=new MatrixTest();
    ma.test();
}
    public static void main(String[] args) {
        
        System.out.println("Hello World!");
        //MatrixTest();
        triangletest();
    }
    public static void triangletest(){
        LinearElement2D[] elems=new LinearElement2D[2];

        BaseNode[] nodes=new BaseNode[3];
        nodes[0]=new BaseNode(0,0,1);
        nodes[1]=new BaseNode(20,10,1);
        nodes[2]=new BaseNode(0,10,1);
        nodes[0].uvw=new double[]{0.1,0.2,0};
        nodes[0].number=0;
        nodes[1].number=2;
        nodes[2].number=1;

        BaseNode[] nodes2=new BaseNode[3];
        nodes2[0]=new BaseNode(0,0,1);
        nodes2[1]=new BaseNode(20,0,1);
        nodes2[2]=new BaseNode(20,10,1);
        nodes2[0].uvw=new double[]{0.1,0.2,0};
        nodes2[0].number=0;
        nodes2[1].number=3;
        nodes2[2].number=2;

        elems[0]=new LinearElement2D(nodes);
        elems[1]=new LinearElement2D(nodes2);

        for (LinearElement2D ele:
                elems ) {
System.out.println(ele.number);
            BaseNode [] enodes=ele.getNodes();
          int i=0;
          //  for(int i=0;i<2;i++){
                
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number,2*enodes[i].number) +ele.getK().get(2*enodes[i].localnumber,2*enodes[i].localnumber),2*enodes[i].number,2*enodes[i].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number,2*enodes[i+1].number) +ele.getK().get(2*enodes[i+1].localnumber,2*enodes[i+1].localnumber),2*enodes[i+1].number,2*enodes[i+1].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number,2*enodes[i+2].number) +ele.getK().get(2*enodes[i+2].localnumber,2*enodes[i+2].localnumber),2*enodes[i+2].number,2*enodes[i+2].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number,2*enodes[i+1].number) +ele.getK().get(2*enodes[i].localnumber,2*enodes[i+1].localnumber),2*enodes[i].number,2*enodes[i+1].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number,2*enodes[i+2].number) +ele.getK().get(2*enodes[i].localnumber,2*enodes[i+2].localnumber),2*enodes[i].number,2*enodes[i+2].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number,2*enodes[i+2].number) +ele.getK().get(2*enodes[i+1].localnumber,2*enodes[i+2].localnumber),2*enodes[i+1].number,2*enodes[i+2].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number,2*enodes[i].number) +ele.getK().get(2*enodes[i+1].localnumber,2*enodes[i].localnumber),2*enodes[i+1].number,2*enodes[i].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number,2*enodes[i].number) +ele.getK().get(2*enodes[i+2].localnumber,2*enodes[i].localnumber),2*enodes[i+2].number,2*enodes[i].number);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number,2*enodes[i+1].number) +ele.getK().get(2*enodes[i+2].localnumber,2*enodes[i+1].localnumber),2*enodes[i+2].number,2*enodes[i+1].number);

            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number+1,2*enodes[i].number+1) +ele.getK().get(2*enodes[i].localnumber+1,2*enodes[i].localnumber+1),2*enodes[i].number+1,2*enodes[i].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number+1,2*enodes[i+1].number+1) +ele.getK().get(2*enodes[i+1].localnumber+1,2*enodes[i+1].localnumber+1),2*enodes[i+1].number+1,2*enodes[i+1].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number+1,2*enodes[i+2].number+1) +ele.getK().get(2*enodes[i+2].localnumber+1,2*enodes[i+1].localnumber+1),2*enodes[i+2].number+1,2*enodes[i+2].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number+1,2*enodes[i+1].number+1) +ele.getK().get(2*enodes[i].localnumber+1,2*enodes[i+1].localnumber+1),2*enodes[i].number+1,2*enodes[i+1].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i].number+1,2*enodes[i+2].number+1) +ele.getK().get(2*enodes[i].localnumber+1,2*enodes[i+2].localnumber+1),2*enodes[i].number+1,2*enodes[i+2].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number+1,2*enodes[i+2].number+1) +ele.getK().get(2*enodes[i+1].localnumber+1,2*enodes[i+2].localnumber+1),2*enodes[i+1].number+1,2*enodes[i+2].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+1].number+1,2*enodes[i].number+1) +ele.getK().get(2*enodes[i+1].localnumber+1,2*enodes[i].localnumber+1),2*enodes[i+1].number+1,2*enodes[i].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number+1,2*enodes[i].number+1) +ele.getK().get(2*enodes[i+2].localnumber+1,2*enodes[i].localnumber+1),2*enodes[i+2].number+1,2*enodes[i].number+1);
            globalK.setAsDouble(globalK.getAsDouble(2*enodes[i+2].number+1,2*enodes[i+1].number+1) +ele.getK().get(2*enodes[i+2].localnumber+1,2*enodes[i+1].localnumber+1),2*enodes[i+2].number+1,2*enodes[i+1].number+1);



        }
        System.out.println(elems[1].getDisp(0.1,0.0)[0]);
        System.out.println(globalK);
        // System.out.println(ele.getLinearShape2Ds()[1].getValue(1,3));
        // System.out.println(ele.getLinearShape2Ds()[2].getValue(1,3));
    }
}
