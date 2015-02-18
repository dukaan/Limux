/**
 * 工程名: colorpicker
 * 文件名: ColorPickerView.java
 * 包名: com.style.colorpicker
 * 日期: 2014-3-30上午10:14:15
 * Copyright (c) 2014
 *
*/

package inf.uie.Limux.activity;

import inf.uie.Limux.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout; 
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 类名: ColorPickerView <br/>
 * 功能: 颜色选择器. <br/>
 * 日期: 2014-3-30 上午10:14:15 <br/>
 *
 * @author   msl
 * @version  	 
 */
public class ColorPickerView extends FrameLayout {
	
	private ImageView iv_color_range;//颜色选择盘
	
	private ImageView iv_color_picker;//颜色选择器

	private RelativeLayout rl_root;//根布局
	
	private int range_radius ;//圆盘半径
	private int picker_radius;//颜色选择器半径
	private int centreX;//圆盘中心X坐标
	private int centreY;//圆盘中心Y坐标
	private int picker_centreX;//颜色选择器中心X坐标
	private int picker_centreY;//颜色选择器中心Y坐标
	private Bitmap bitmap;//颜色选择盘图片
	private onColorChangedListener colorChangedListener;//颜色变换监听
	
	final static int IMAGE_COLOR_RANGE_DIMENSION = 260;
	final float scale = getContext().getResources().getDisplayMetrics().density;
	final int screenHeight = (int) (getContext().getResources().getDisplayMetrics().heightPixels - (144 * scale + 0.5)); // substrate height of actionbar
	final int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
	
	public ColorPickerView(Context context) {
		super(context); 	
	}

	public ColorPickerView(Context context, AttributeSet attrs) {
		super(context, attrs); 
		init(context); 
	}
	 

	/**
	 * 
	 * init:(初始化). <br/>
	 * @author msl
	 * @param context
	 * @since 1.0
	 */
	private void init(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.color_picker, this);
		iv_color_range = (ImageView) view.findViewById(R.id.iv_color_range);
		iv_color_picker = (ImageView) view.findViewById(R.id.iv_color_picker);
		rl_root = (RelativeLayout)view.findViewById(R.id.rl_root);
		
		//选择器触摸监听		
		iv_color_picker.setOnTouchListener(new OnTouchListener() {
			int lastX, lastY;//上次触摸坐标	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int ea=event.getAction(); 
				
				if(range_radius==0){//未初始化
					range_radius =iv_color_range.getWidth()/2;//圆盘半径
					picker_radius = iv_color_picker.getWidth()/2;//选择器半径
					centreX =iv_color_range.getRight()-range_radius;
					centreY =iv_color_range.getBottom()-iv_color_range.getHeight()/2; 
				    bitmap =((BitmapDrawable)iv_color_range.getDrawable()).getBitmap();//获取圆盘图片
				}
				
				switch(ea){
					case MotionEvent.ACTION_DOWN://按下
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						getParent().requestDisallowInterceptTouchEvent(true);//通知父控件勿拦截本控件touch事件
						break;
					case MotionEvent.ACTION_MOVE://拖动
						//拖动距离
						int dx =(int)event.getRawX() - lastX;
						int dy =(int)event.getRawY() - lastY;	
						//相对于父控件的新坐标
						int left = v.getLeft() + dx;
						int top = v.getTop() + dy;
						int right = v.getRight() + dx;
						int bottom = v.getBottom() + dy;	
						//选择器圆心坐标
						picker_centreX = right-picker_radius;
						picker_centreY = bottom-picker_radius;
	
						//选择器圆心与圆盘圆心距离
						float diff = FloatMath.sqrt((centreY-picker_centreY)*(centreY-picker_centreY)+(centreX-picker_centreX)*
								(centreX-picker_centreX))+picker_radius/2;//两个圆心距离+颜色选择器半径
						//在边距内，则拖动
						if(diff<=range_radius){
							v.layout(left, top, right, bottom); //sets new position for picker
							
							int dipInPx = (int) (260 * scale + 0.5f);
							int xOffset = (int) ( (screenWidth - (IMAGE_COLOR_RANGE_DIMENSION*scale+0.5f) ) / 2 ) + 50;
							int yOffset = (int) ( (screenHeight - (IMAGE_COLOR_RANGE_DIMENSION*scale+0.5f) ) / 2 ) - 50;
							
							int pixel = bitmap.getPixel(picker_centreX + xOffset, picker_centreY + yOffset);//125, 200获取选择器圆心像素
							if(colorChangedListener!=null){//读取颜色
								colorChangedListener.colorChanged(Color.red(pixel), Color.blue(pixel), Color.green(pixel));
							} 
							
							//Log.d("TAG", "radValue="+Color.red(pixel)+"  blueValue="+Color.blue(pixel)+"  greenValue"+Color.green(pixel));
							Log.d("ScreenDimension","Height: " +  screenHeight + ", " + yOffset + ", Width: " + screenWidth + ", " + xOffset + " Pixel" + dipInPx);
							lastX = (int) event.getRawX();
							lastY = (int) event.getRawY();
						}
						getParent().requestDisallowInterceptTouchEvent(true);//通知父控件勿拦截本控件touch事件
						break;
					default:
						break;
				}
				
				return false;
			}
		});
		
	
	
	}
	
	
	
    public onColorChangedListener getColorChangedListener() {
		return colorChangedListener;
	}

	public void setColorChangedListener(onColorChangedListener colorChangedListener) {
		this.colorChangedListener = colorChangedListener;
	}



	/**
	 * 颜色变换监听接口
	 */
	interface onColorChangedListener{
    	public void colorChanged(int red,int blue,int green);
    }
	 
	

}

