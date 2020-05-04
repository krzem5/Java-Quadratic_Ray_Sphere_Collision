package com.krzem.quadratic_ray_sphere_collision;



import com.jogamp.opengl.GL2;
import java.lang.Math;



public class Ray{
	public Main.Main_ cls;
	public double x;
	public double y;
	public double z;
	public double dx;
	public double dy;
	public double dz;



	public Ray(Main.Main_ cls,double x,double y,double z,double rx,double ry){
		this.cls=cls;
		this.x=x;
		this.y=y;
		this.z=z;
		this.dx=Math.sin(rx)*Math.cos(ry);
		this.dy=Math.cos(rx);
		this.dz=Math.sin(rx)*Math.sin(ry);
	}



	public void update(){

	}



	public void draw(GL2 gl){
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(0,0,0);
		gl.glVertex3d(this.x,this.y,this.z);
		gl.glVertex3d(this.x+this.dx*3,this.y+this.dy*3,this.z+this.dz*3);
		gl.glEnd();
	}
}