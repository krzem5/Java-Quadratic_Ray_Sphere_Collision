package com.krzem.quadratic_ray_sphere_collision;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.nio.DoubleBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;



public class Sphere extends Constants{
	public Main.Main_ cls;
	public double x;
	public double y;
	public double z;
	public double r;
	private DoubleBuffer _vb=null;
	private ShortBuffer _ib=null;
	private DoubleBuffer _tb=null;
	private int _v_id=-1;
	private int _i_id=-1;
	private int _t_id=-1;
	private Texture _tx=null;
	private BufferedImage _img=null;
	private Graphics2D _g=null;



	public Sphere(Main.Main_ cls,int x,int y,int z,int r){
		this.cls=cls;
		this.x=x;
		this.y=y;
		this.z=z;
		this.r=r;
	}



	public void update(){

	}



	public void recalc_light(Ray r){
		double t=-(r.dx*(r.x-this.x)+r.dy*(r.y-this.y)+r.dz*(r.z-this.z))-Math.sqrt((r.dx*(r.x-this.x)+r.dy*(r.y-this.y)+r.dz*(r.z-this.z))*(r.dx*(r.x-this.x)+r.dy*(r.y-this.y)+r.dz*(r.z-this.z))-((r.x-this.x)*(r.x-this.x)+(r.y-this.y)*(r.y-this.y)+(r.z-this.z)*(r.z-this.z)-this.r*this.r));
		if (t>=0){
			double u=Math.atan2((r.z+r.dz*t-this.z)/this.r,(r.x+r.dx*t-this.x)/this.r)/(Math.PI*2);
			double v=0.5-Math.asin((r.y+r.dy*t-this.y)/this.r)/Math.PI;
			u=(u<0?1+u:u);
			this._g.setColor(new Color(0,0,0));
			this._g.fillRect((int)(u*(this._img.getWidth()-SPHERE_TEXTURE_RESOLUTION)),(int)(v*(this._img.getHeight()-SPHERE_TEXTURE_RESOLUTION)),1,1);
		}
	}



	public void reset_light(){
		this._img=new BufferedImage(SPHERE_DETAIL*SPHERE_TEXTURE_RESOLUTION,SPHERE_DETAIL*SPHERE_TEXTURE_RESOLUTION,BufferedImage.TYPE_INT_RGB);
		this._g=(Graphics2D)this._img.createGraphics();
		this._g.setBackground(new Color(0,0,0));
		this._g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		this._g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		this._g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		this._g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		this._g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		this._g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		this._uv(this._g);
	}



	public void draw(GL2 gl){
		if (this._img==null){
			this.reset_light();
		}
		if (this._tx==null){
			this._tx=AWTTextureIO.newTexture(gl.getGLProfile(),this._img,false);
		}
		else{
			this._tx.updateImage(gl,AWTTextureIO.newTextureData(gl.getGLProfile(),this._img,false));
		}
		if (this._vb==null){
			this._gen(gl);
		}
		gl.glColor3d(1,1,1);
		this._tx.enable(gl);
		this._tx.bind(gl);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MIN_FILTER,GL2.GL_NEAREST);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_MAG_FILTER,GL2.GL_NEAREST);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._v_id);
		gl.glVertexPointer(3,GL2.GL_DOUBLE,0,0);
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._t_id);
		gl.glTexCoordPointer(2,GL2.GL_DOUBLE,0,0);
		gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER,this._i_id);
		gl.glDrawElements(GL2.GL_TRIANGLES,this._ib.capacity(),GL2.GL_UNSIGNED_SHORT,0);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		this._tx.disable(gl);
	}



	private void _gen(GL2 gl){
		int SPHERE_SUBDIVISIONS=5;
		Sphere cls=this;
		List<Double> va=new ArrayList<Double>(){{
			double c=(1+Math.sqrt(5))/2;
			this.addAll(cls._norm(-1,c,0));
			this.addAll(cls._norm(1,c,0));
			this.addAll(cls._norm(-1,-c,0));
			this.addAll(cls._norm(1,-c,0));
			this.addAll(cls._norm(0,-1,c));
			this.addAll(cls._norm(0,1,c));
			this.addAll(cls._norm(0,-1,-c));
			this.addAll(cls._norm(0,1,-c));
			this.addAll(cls._norm(c,0,-1));
			this.addAll(cls._norm(c,0,1));
			this.addAll(cls._norm(-c,0,-1));
			this.addAll(cls._norm(-c,0,1));
		}};
		List<Short> ia=new ArrayList<Short>(){{
			this.add((short)0);
			this.add((short)11);
			this.add((short)5);
			this.add((short)0);
			this.add((short)5);
			this.add((short)1);
			this.add((short)0);
			this.add((short)1);
			this.add((short)7);
			this.add((short)0);
			this.add((short)7);
			this.add((short)10);
			this.add((short)0);
			this.add((short)10);
			this.add((short)11);
			this.add((short)1);
			this.add((short)5);
			this.add((short)9);
			this.add((short)5);
			this.add((short)11);
			this.add((short)4);
			this.add((short)11);
			this.add((short)10);
			this.add((short)2);
			this.add((short)10);
			this.add((short)7);
			this.add((short)6);
			this.add((short)7);
			this.add((short)1);
			this.add((short)8);
			this.add((short)3);
			this.add((short)9);
			this.add((short)4);
			this.add((short)3);
			this.add((short)4);
			this.add((short)2);
			this.add((short)3);
			this.add((short)2);
			this.add((short)6);
			this.add((short)3);
			this.add((short)6);
			this.add((short)8);
			this.add((short)3);
			this.add((short)8);
			this.add((short)9);
			this.add((short)4);
			this.add((short)9);
			this.add((short)5);
			this.add((short)2);
			this.add((short)4);
			this.add((short)11);
			this.add((short)6);
			this.add((short)2);
			this.add((short)10);
			this.add((short)8);
			this.add((short)6);
			this.add((short)7);
			this.add((short)9);
			this.add((short)8);
			this.add((short)1);
		}};
		for (int i=0;i<SPHERE_SUBDIVISIONS;i++){
			List<Double> nva=new ArrayList<Double>(){{
				for (Double e:va){
					this.add(e+0);
				}
			}};
			List<Short> nia=new ArrayList<Short>();
			for (int j=0;j<ia.size();j+=3){
				double[] v0=new double[]{va.get(ia.get(j)*3),va.get(ia.get(j)*3+1),va.get(ia.get(j)*3+2)};
				double[] v1=new double[]{va.get(ia.get(j+1)*3),va.get(ia.get(j+1)*3+1),va.get(ia.get(j+1)*3+2)};
				double[] v2=new double[]{va.get(ia.get(j+2)*3),va.get(ia.get(j+2)*3+1),va.get(ia.get(j+2)*3+2)};
				nva.addAll(this._norm(v0[0]/2+v1[0]/2,v0[1]/2+v1[1]/2,v0[2]/2+v1[2]/2));
				nva.addAll(this._norm(v1[0]/2+v2[0]/2,v1[1]/2+v2[1]/2,v1[2]/2+v2[2]/2));
				nva.addAll(this._norm(v2[0]/2+v0[0]/2,v2[1]/2+v0[1]/2,v2[2]/2+v0[2]/2));
				nia.add((short)(ia.get(j)));
				nia.add((short)(nva.size()/3-3));
				nia.add((short)(nva.size()/3-1));
				nia.add((short)(ia.get(j+1)));
				nia.add((short)(nva.size()/3-3));
				nia.add((short)(nva.size()/3-2));
				nia.add((short)(ia.get(j+2)));
				nia.add((short)(nva.size()/3-2));
				nia.add((short)(nva.size()/3-1));
				nia.add((short)(nva.size()/3-3));
				nia.add((short)(nva.size()/3-2));
				nia.add((short)(nva.size()/3-1));
			}
			va.clear();
			va.addAll(nva);
			ia.clear();
			ia.addAll(nia);
		}
		double[] vl=new double[va.size()];
		int i=0;
		for (Double e:va){
			vl[i]=e*this.r;
			i++;
		}
		short[] il=new short[ia.size()];
		i=0;
		for (Short e:ia){
			il[i]=e;
			i++;
		}
		double[] tl=new double[vl.length/3*2];
		for (i=0;i<tl.length;i+=2){
			tl[i]=0.5+Math.atan2(vl[i/2*3+2]/this.r,vl[i/2*3]/this.r)/(Math.PI*2);
			tl[i+1]=0.5-Math.asin(vl[i/2*3+1]/this.r)/Math.PI;
		}
		if (this._vb==null){
			this._vb=DoubleBuffer.wrap(vl);
		}
		else{
			this._vb.put(vl);
			this._vb.flip();
		}
		if (this._ib==null){
			this._ib=ShortBuffer.wrap(il);
		}
		else{
			this._ib.put(il);
			this._ib.flip();
		}
		if (this._tb==null){
			this._tb=DoubleBuffer.wrap(tl);
		}
		else{
			this._tb.put(tl);
			this._tb.flip();
		}
		if (this._v_id==-1||this._i_id==-1||this._t_id==-1){
			int[] tmp=new int[3];
			gl.glGenBuffers(3,tmp,0);
			this._v_id=tmp[0];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._v_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._vb.capacity()*((long)8),this._vb,GL2.GL_STATIC_DRAW);
			this._i_id=tmp[1];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._i_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._ib.capacity()*((long)2),this._ib,GL2.GL_STATIC_DRAW);
			this._t_id=tmp[2];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._t_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._tb.capacity()*((long)8),this._tb,GL2.GL_STATIC_DRAW);
		}
	}



	private void _uv(Graphics2D g){
		for (int i=0;i<SPHERE_DETAIL;i++){
			for (int j=0;j<SPHERE_DETAIL;j++){
				if ((i%2+j)%2==0){
					g.setColor(Color.getHSBColor((float)i/(SPHERE_DETAIL-1),255/255f,220/255f));
				}
				else{
					g.setColor(Color.getHSBColor((float)i/(SPHERE_DETAIL-1),255/255f,150/255f));
				}
				g.fillRect(i*SPHERE_TEXTURE_RESOLUTION,j*SPHERE_TEXTURE_RESOLUTION,SPHERE_TEXTURE_RESOLUTION,SPHERE_TEXTURE_RESOLUTION);
			}
		}
	}



	private List<Double> _norm(double x,double y,double z){
		return new ArrayList<Double>(){{
			double m=Math.sqrt(x*x+y*y+z*z);
			this.add(x/m);
			this.add(y/m);
			this.add(z/m);
		}};
	}
}
