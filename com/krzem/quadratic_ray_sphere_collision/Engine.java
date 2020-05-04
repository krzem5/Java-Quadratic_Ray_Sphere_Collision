package com.krzem.quadratic_ray_sphere_collision;



import com.jogamp.opengl.GL2;



public class Engine{
	public Main.Main_ cls;
	public Sphere s;
	public Ray r;
	private boolean _kd=false;
	private boolean _r=false;



	public Engine(Main.Main_ cls){
		this.cls=cls;
		this.s=new Sphere(this.cls,20,0,0,1);
		this.r=new Ray(this.cls,5,5,5,0,0);
	}



	public void update(){
		this.s.update();
		if (this._r==true){
			this.r=new Ray(this.cls,this.cls.cam.x,this.cls.cam.y,this.cls.cam.z,this.cls.cam.rx/180*Math.PI-Math.PI/2,-this.cls.cam.ry/180*Math.PI+Math.PI/2);
			this.s.reset_light();
			this.s.recalc_light(this.r);
		}
		if (this.cls.KEYBOARD.pressed(81)){
			if (this._kd==false){
				this._r=!this._r;
			}
			this._kd=true;
		}
		else{
			this._kd=false;
		}
	}



	public void draw(GL2 gl){
		this.s.draw(gl);
		this.r.draw(gl);
	}
}