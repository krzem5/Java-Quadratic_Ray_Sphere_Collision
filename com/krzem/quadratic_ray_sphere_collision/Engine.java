package com.krzem.quadratic_ray_sphere_collision;



import com.jogamp.opengl.GL2;



public class Engine{
	public Main.Main_ cls;
	public Sphere s;



	public Engine(Main.Main_ cls){
		this.cls=cls;
		this.s=new Sphere(this.cls,0,0,0,1);
		// https://stackoverflow.com/a/1986458/10971851
	}



	public void update(){
		this.s.update();
	}



	public void draw(GL2 gl){
		this.s.draw(gl);
	}
}