package inf.uie.Limux.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import inf.uie.Limux.R;

public class ShowRoomsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_show_rooms, container, false);

        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.rect);
        relativeLayout.addView(new Circle(getActivity()));

		return rootView;
	}



    private class Circle extends View {
        private Animation anim;
        Paint paint = new Paint();

        Bitmap bitmap;

        float centerX;
        float centerY;
        float offsetX;
        float offsetY;

        RotateAnimation rotate;
        AlphaAnimation blend;
        ScaleAnimation scale;
        AnimationSet spriteAnimation;

        public Circle (Context context) {
            super(context);


            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eating);
            offsetX = bitmap.getWidth() / 2;
            offsetY = bitmap.getHeight() / 2;
        }


        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 100;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#CD5C5C"));

            if (spriteAnimation == null) {
                centerX = canvas.getWidth() / 2;
                centerY = canvas.getHeight() / 2;
                createAnimation(canvas);
            }

            canvas.drawCircle(x / 2, y / 2, radius, paint);
//            canvas.drawText("Wohnzimmer", x / 2, y / 2, paint);
            canvas.drawBitmap(bitmap, centerX - offsetX, centerY - offsetY, paint);

        }

        private void createAnimation(final Canvas canvas) {

            rotate = new RotateAnimation(0, 360, centerX, centerY);
            rotate.setRepeatMode(Animation.REVERSE);
            rotate.setRepeatCount(Animation.INFINITE);
            scale = new ScaleAnimation(0, 2, 0, 2, centerX, centerY);
            scale.setRepeatMode(Animation.REVERSE);
            scale.setRepeatCount(Animation.INFINITE);
            scale.setInterpolator(new AccelerateDecelerateInterpolator());

            spriteAnimation = new AnimationSet(true);
            // spriteAnimation.addAnimation(rotate);
            spriteAnimation.addAnimation(scale);
            spriteAnimation.setDuration(10000L);

            startAnimation(spriteAnimation);

        }


    }
}
