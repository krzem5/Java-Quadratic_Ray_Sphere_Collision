package com.krzem.quadratic_ray_sphere_collision;



public class Vector extends Constants{
	public double x;
	public double y;



	public Vector(double x,double y){
		this.x=x;
		this.y=y;
	}



	@Override
	public Vector clone(){
		return new Vector(this.x,this.y);
	}
}
