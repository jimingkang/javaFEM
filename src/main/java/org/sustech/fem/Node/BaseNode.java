package org.sustech.fem.Node;

/**
 * Created by jimmy on 6/20/19.
 */import java.util.ArrayList;
import org.sustech.fem.Element.BaseElement;
public class BaseNode {
  public   int number;
  public  double x,y,z;
  public  double[] xx=new double[3];
public    double[] uvw=new double[3];

  public ArrayList<BaseElement>  elems=new ArrayList<BaseElement>();
  public BaseNode(double x,double y,double z){
    this.x=x;
    this.y=y;
    this.z=z;
    this.setXX();
  }
  public BaseNode(){

  }
  public void setXX(){
    xx[0]=x;
    xx[1]=y;
    xx[2]=z;

  }
}
