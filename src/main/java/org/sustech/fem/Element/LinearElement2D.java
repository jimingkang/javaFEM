package org.sustech.fem.Element;

import org.sustech.fem.Node.BaseNode;
import org.sustech.fem.Shape.LinearShape2D;

/**
 * Created by jimmy on 6/20/19.
 */
public class LinearElement2D extends BaseElement {

    private LinearShape2D[] linearShape2Ds;
    private double [] disp=new double[2];
    private double [] strain=new double[3];
    private double [][] stiffmatrix=new double[3][6];

    public LinearElement2D(BaseNode[] nodes){
        this.nodes=nodes;
        for (BaseNode node:
             nodes) {
            node.elems.add(this);

        }
        LinearShape2D linearShape2D=new LinearShape2D(this.nodes);
        linearShape2Ds=linearShape2D.getLinearShape2Ds();
    }
    public LinearShape2D[] getLinearShape2Ds(){

        return linearShape2Ds;
    }
    public double[] getDisp(double x,double y){
        double[] dis=new double[2];
        int i=0;
        for (BaseNode node: this.nodes             ) {

            dis[0]+=linearShape2Ds[i].getValue(x,y)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();
            dis[1]+=linearShape2Ds[i].getValue(x,y)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal();

            i++;
        }
        return this.disp=dis;
    }
    public double[] getStrain(double x,double y){
        double[] strain=new double[3];
        int i=0;
        for (BaseNode node: this.nodes             ) {

            strain[0]+=linearShape2Ds[i].getBelta(i)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();
            strain[1]+=linearShape2Ds[i].getGamma(i)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal();
            strain[3]+=linearShape2Ds[i].getBelta(i)*linearShape2Ds[i].nodes[i].uvw[1]/linearShape2Ds[i].getNormal()+
                    linearShape2Ds[i].getGamma(i)*linearShape2Ds[i].nodes[i].uvw[0]/linearShape2Ds[i].getNormal();

            i++;
        }
        return this.strain=strain;
    }
    public double[] getStiffmatrix(double x,double y){
        double[] strain=new double[3];
        int i=0;
        for (BaseNode node: this.nodes             ) {

            stiffmatrix[0][2*i]=linearShape2Ds[i].getBelta(i)/linearShape2Ds[i].getNormal();
            stiffmatrix[0][2*i+1]=0;

            stiffmatrix[1][2*i]=0;
            stiffmatrix[1][2*i+1]=linearShape2Ds[i].getGamma(i)/linearShape2Ds[i].getNormal();

            stiffmatrix[2][2*i]=linearShape2Ds[i].getGamma(i)/linearShape2Ds[i].getNormal();
            stiffmatrix[2][2*i+1]=linearShape2Ds[i].getBelta(i)/linearShape2Ds[i].getNormal();

            i++;
        }
        return this.strain=strain;
    }

}
