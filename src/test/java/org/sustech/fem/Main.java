package org.sustech.fem;
import Juma.*;
import org.sustech.fem.Element.LinearElement2D;
import org.sustech.fem.Node.BaseNode;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.calculation.Calculation;

public class Main {
    public static int NodeNum=4;
    public int EleNum=2;
     public static BaseNode[] globalNode=new BaseNode[NodeNum];
  static  Matrix globalK = SparseMatrix.Factory.zeros(2*NodeNum, 2*NodeNum);

    static  Matrix globalF = SparseMatrix.Factory.zeros(2*NodeNum,1);
    static  Matrix globalBC = SparseMatrix.Factory.zeros(2*NodeNum,1);
   static LinearElement2D[] elems=new LinearElement2D[2];
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
        initGlobal();
        initGlobalBC();
        System.out.println(globalK.det());
        setBC();
        System.out.println(globalK);
        double[][] globalarray=globalK.toDoubleArray();
        Juma.Matrix jmatr= new Juma.Matrix(globalarray);
        Juma.Matrix subm=jmatr.getMatrix(4,7,4,7);
        System.out.println(subm);
        double[][] f=new double[][]{{5000},{0},{5000},{0}};
        //subm;
        Juma.Matrix rs=subm.solve(new Juma.Matrix(f));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++)
                System.out.println(rs.getArray()[i][j]);
        }

     /// Matrix rs=  globalK.inv().times(globalF.subMatrix(Calculation.Ret.LINK,4,7));
      //  System.out.println(rs);

        // System.out.println(ele.getLinearShape2Ds()[1].getValue(1,3));
        // System.out.println(ele.getLinearShape2Ds()[2].getValue(1,3));
    }
    public static void initGlobalBC(){
        globalBC.setAsInt(1,0,0);
        globalBC.setAsInt(2,1,0);
        globalBC.setAsInt(1,2,0);
        globalBC.setAsInt(2,3,0);
    }
    public static void setBC(){
        for (int i = 0; i < 2*NodeNum; i++) {
          if(  globalBC.getAsInt(i,0)>0){

              globalK.deleteRows(Calculation.Ret.NEW,i);
              globalK.deleteColumns(Calculation.Ret.NEW,i);
              globalF.deleteRows(Calculation.Ret.NEW,i);

          }
        }
    }
    public static void initGlobalF(){
        globalF.setAsFloat(5000,4,0);
        globalF.setAsFloat(0,5,0);
        globalF.setAsFloat(5000,6,0);
        globalF.setAsFloat(0,7,0);
    }

    public static void initGlobal(){
        BaseNode[] nodes=new BaseNode[3];

     /*   nodes[0]=new BaseNode(0,-1,1);
        nodes[1]=new BaseNode(2,0,1);
        nodes[2]=new BaseNode(0,1,1);
        nodes[0].uvw=new double[]{0.1,0.2,0};
        nodes[0].number=0;
        nodes[1].number=1;
        nodes[2].number=2;
        */

        nodes[0]=new BaseNode(0,0,1);
        nodes[1]=new BaseNode(20,10,1);
        nodes[2]=new BaseNode(0,10,1);
        nodes[0].uvw=new double[]{0.1,0.2,0};
        nodes[0].number=0;
        nodes[1].number=2;
        nodes[2].number=1;
        globalNode[nodes[0].number]=nodes[0];
        globalNode[nodes[1].number]=nodes[1];
        globalNode[nodes[2].number]=nodes[2];
        BaseNode[] nodes2=new BaseNode[3];
        nodes2[0]=new BaseNode(0,0,1);
        nodes2[1]=new BaseNode(20,0,1);
        nodes2[2]=new BaseNode(20,10,1);
        nodes2[0].uvw=new double[]{0.1,0.2,0};
        nodes2[0].number=0;
        nodes2[1].number=3;
        nodes2[2].number=2;
        globalNode[nodes[0].number]=nodes2[0];
        globalNode[nodes[1].number]=nodes2[1];
        globalNode[nodes[2].number]=nodes2[2];
        elems[0]=new LinearElement2D(nodes);
        elems[1]=new LinearElement2D(nodes2);

        for (LinearElement2D ele:
                elems ) {
             //   System.out.println(ele.getK().det());
             //ele.getK().print(1,1);
            BaseNode [] enodes=ele.getNodes();

            //int i=0;
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {


                    globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number, 2 * enodes[j].number)
                                    + ele.getK().get(2 * enodes[i].localnumber, 2 * enodes[j].localnumber),
                            2 * enodes[i].number, 2 * enodes[j].number);

                    globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number, 2 * enodes[j].number+1)
                                    + ele.getK().get(2 * enodes[i].localnumber, 2 * enodes[j].localnumber+1),
                            2 * enodes[i].number, 2 * enodes[j].number+1);

                    globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number+1, 2 * enodes[j].number)
                                    + ele.getK().get(2 * enodes[i].localnumber+1, 2 * enodes[j].localnumber),
                            2 * enodes[i].number+1, 2 * enodes[j].number);

                    globalK.setAsDouble(globalK.getAsDouble(2 * enodes[i].number + 1, 2 * enodes[j].number + 1)
                                    + ele.getK().get(2 * enodes[i].localnumber + 1, 2 * enodes[j].localnumber + 1),
                            2 * enodes[i].number + 1, 2 * enodes[j].number + 1);

                }
            }
System.out.print(globalK);
        }
    }
}
