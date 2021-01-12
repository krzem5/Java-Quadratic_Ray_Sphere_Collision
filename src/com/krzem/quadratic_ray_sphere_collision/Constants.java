package com.krzem.quadratic_ray_sphere_collision;



import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();

	public static final double CAMERA_MOVE_SPEED=0.5d;
	public static final double CAMERA_ROT_SPEED=0.075d;
	public static final double CAMERA_MIN_EASE_DIFF=0.05d;
	public static final double CAMERA_EASE_PROC=0.45d;
	public static final double CAMERA_CAM_NEAR=0.05d;
	public static final double CAMERA_CAM_FAR=1000d;

	public static final int SPHERE_DETAIL=101;
	public static final int SPHERE_TEXTURE_RESOLUTION=3;
}