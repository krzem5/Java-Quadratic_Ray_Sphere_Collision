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



	public void draw(GL2 gl){
		if (this._img==null){
			this._img=new BufferedImage(SPHERE_DETAIL*SPHERE_TEXTURE_RESOLUTION,SPHERE_DETAIL*SPHERE_TEXTURE_RESOLUTION,BufferedImage.TYPE_INT_RGB);
			this._g=(Graphics2D)this._img.createGraphics();
			this._g.setBackground(new Color(0,0,0));
			this._uv(this._g);
			// this._g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			// this._g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			// this._g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			// this._g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			// this._g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			// this._g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
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
		double[] vl=new double[(SPHERE_DETAIL+1)*(SPHERE_DETAIL+1)*3];
		short[] il=new short[(SPHERE_DETAIL+1)*SPHERE_DETAIL*2*3];
		double[] tl=new double[(SPHERE_DETAIL+1)*(SPHERE_DETAIL+1)*2];
		int vli=0;
		int ili=0;
		int tli=0;
		for (int i=0;i<=SPHERE_DETAIL;i++){
			for (int j=0;j<=SPHERE_DETAIL;j++){
				double a=i*Math.PI/SPHERE_DETAIL;
				double b=j*Math.PI*2/SPHERE_DETAIL;
				vl[vli++]=this.x+this.r*Math.sin(a)*Math.cos(b);
				vl[vli++]=this.y+this.r*Math.cos(a);
				vl[vli++]=this.z+this.r*Math.sin(a)*Math.sin(b);
				tl[tli++]=(double)j/(SPHERE_DETAIL+1);
				tl[tli++]=(double)i/(SPHERE_DETAIL+1);
				// System.out.printf("X: %f, Y: %f, Z: %f => U: %f, V: %f\n",this.x+this.r*Math.sin(a)*Math.cos(b),this.y+this.r*Math.cos(a),this.z+this.r*Math.sin(a)*Math.sin(b),(double)j/SPHERE_DETAIL,(double)i/SPHERE_DETAIL);
				if (j<SPHERE_DETAIL){
					il[ili++]=(short)(i*SPHERE_DETAIL+j);
					il[ili++]=(short)(i*SPHERE_DETAIL+j+1);
					il[ili++]=(short)((i+1)*SPHERE_DETAIL+j+1);
					il[ili++]=(short)(i*SPHERE_DETAIL+j);
					il[ili++]=(short)((i+1)*SPHERE_DETAIL+j);
					il[ili++]=(short)((i+1)*SPHERE_DETAIL+j+1);
				}
			}
		}
		if (this._vb==null){
			this._vb=DoubleBuffer.wrap(vl);
		}
		else if (vl!=null){
			this._vb.put(vl);
			this._vb.flip();
		}
		if (this._ib==null){
			this._ib=ShortBuffer.wrap(il);
		}
		else if (il!=null){
			this._ib.put(il);
			this._ib.flip();
		}
		if (this._tb==null){
			this._tb=DoubleBuffer.wrap(tl);
		}
		else if (tl!=null){
			this._tb.put(tl);
			this._tb.flip();
		}
		if (this._v_id==-1||this._i_id==-1||this._t_id==-1){
			int[] tmp=new int[3];
			gl.glGenBuffers(3,tmp,0);
			this._v_id=tmp[0];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._v_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._vb.capacity()*8,this._vb,GL2.GL_STATIC_DRAW);
			this._i_id=tmp[1];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._i_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._ib.capacity()*2,this._ib,GL2.GL_STATIC_DRAW);
			this._t_id=tmp[2];
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,this._t_id);
			gl.glBufferData(GL2.GL_ARRAY_BUFFER,this._tb.capacity()*8,this._tb,GL2.GL_STATIC_DRAW);
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
		try{
			javax.imageio.ImageIO.write(this._img,"PNG",new java.io.File("tmp.png"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}