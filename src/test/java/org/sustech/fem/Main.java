package org.sustech.fem;
import org.sustech.fem.Element.LinearElement2D;
import org.sustech.fem.Node.BaseNode;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.SparseMatrix2D;
import org.ujmp.core.calculation.Calculation;
import org.ujmp.core.util.DefaultSparseDoubleVector1D;

import java.io.*;
import java.math.BigInteger;

public class Main {
    public static int NodeNum=0;
    public static int ElementNum=0;
     public static BaseNode[] globalNode;//=new BaseNode[NodeNum]
  static  Matrix globalK ;//= SparseMatrix.Factory.zeros(2*NodeNum, 2*NodeNum);

    static  Matrix globalF ;//= SparseMatrix.Factory.zeros(2*NodeNum,1);
    static  Matrix globalBC ;//= SparseMatrix.Factory.zeros(2*NodeNum,1);
   static LinearElement2D[] elems;
    public static void MatrixTest(){
    MatrixTest ma=new MatrixTest();
    ma.test();
}
    public static void main(String[] args) {
        
        System.out.println("Hello World!");
        //MatrixTest();
        //initGlobalreadFile();
        triangletest();
    }
    public static void initGlobalreadFile(){
        File f=new File("data.txt");
        BufferedReader bf;
        String ss;
        try {
            bf = new BufferedReader(new FileReader(f));
            int i=0,j=0;
            while((ss=bf.readLine())!=null &&ss!=" "){

                if(ss.contains("node")){
                    NodeNum= Integer.parseInt(ss.split(" ")[1]);
                    System.out.println("NodeNum:"+NodeNum);
                    globalNode=new BaseNode[NodeNum];
                   globalK = SparseMatrix.Factory.zeros(2*NodeNum, 2*NodeNum);

                    globalF = SparseMatrix.Factory.zeros(2*NodeNum,1);
                    globalBC = SparseMatrix.Factory.zeros(2*NodeNum,1);
                }else if(i<NodeNum){
                    String [] nodestr= ss.split("\t");
                    globalNode[Integer.parseInt(nodestr[0])-1]=new BaseNode(Double.parseDouble(nodestr[1]),Double.parseDouble(nodestr[2]),1);
                    i++;
                }

                if(ss.contains("element")){
                    ElementNum= Integer.parseInt(ss.split(" ")[1]);
                    System.out.println(ElementNum);
                    elems=new LinearElement2D[ElementNum];
                }else if(j<ElementNum){
                    if(j==142)
                    System.out.println(j);
                    String [] elemstr= ss.split("\t");
                    BaseNode[] nodes=new BaseNode[3];
                    nodes[0]=globalNode[Integer.parseInt(elemstr[1])-1];
                    nodes[0].number=Integer.parseInt(elemstr[1])-1;
                    nodes[1]=globalNode[Integer.parseInt(elemstr[2])-1];
                    nodes[1].number=Integer.parseInt(elemstr[2])-1;
                    nodes[2]=globalNode[Integer.parseInt(elemstr[3])-1];
                    nodes[2].number=Integer.parseInt(elemstr[3])-1;
                    elems[Integer.parseInt(elemstr[0])-1]=new LinearElement2D(nodes);

                    j++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assemble();
    }
    public static void triangletest(){
       // initGlobal();
        initGlobalreadFile();
        initGlobalBC();
       System.out.println("det:"+globalK.det());
        setBC();
     //   System.out.println(globalK);
        double[][] globalarray=globalK.toDoubleArray();
        Juma.Matrix jmatr= new Juma.Matrix(globalarray);
       // Juma.Matrix subm=jmatr.getMatrix(NodeNum/2,2*NodeNum-1,NodeNum/2,2*NodeNum-1);
      //  System.out.println(subm);
        //double[][] f=new double[][]{{5000},{0},{5000},{0}};
        //Juma.Matrix f= Juma.Matrix.random(2*NodeNum-NodeNum/2,1);
        //subm;
      //  Juma.Matrix rs=subm.solve(f);


        Juma.Matrix subm=jmatr.getMatrix(0,2*NodeNum-1,0,2*NodeNum-1);
        Juma.Matrix f= Juma.Matrix.random(2*NodeNum,1);
        double [][] ff =f.getArray();
        Matrix fff=SparseMatrix.Factory.ones(2*NodeNum,1);
        //subm;
     // Juma.Matrix rs=subm.solve(f);
        Matrix rs=globalK.solve(fff);

        for (int i = 0; i < NodeNum; i++) {
            for (int j = 0; j < 1; j++)
                System.out.print(i+":"+rs.toDoubleArray()[i][j]+"\n");
        }
        System.out.println();

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
    public static double bigNum= 1e15;
    public static void setBC(){
        for (int i = 0; i < 2*NodeNum; i++) {
          if(  globalBC.getAsInt(i,0)>0){
              globalK.setAsDouble(bigNum,2*i,2*i);
              globalK.setAsDouble(bigNum,2*i+1,2*i+1);
             // globalK.deleteRows(Calculation.Ret.NEW,i);
            //  globalK.deleteColumns(Calculation.Ret.NEW,i);
            //  globalF.deleteRows(Calculation.Ret.NEW,i);

          }
        }
    }
    public static void initGlobalF(){
        globalF.setAsFloat(5000,4,0);
        globalF.setAsFloat(0,5,0);
        globalF.setAsFloat(5000,6,0);
        globalF.setAsFloat(0,7,0);
    }
public static void assemble(){
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
//System.out.print(globalK);
    }
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

assemble();
    }
}
