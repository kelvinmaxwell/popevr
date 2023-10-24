package com.example.popevr;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popevr.R;

public class PopUpClass {
    private PopupWindow popupWindow;
    private LinearLayout linearLayout;
    private float startY;
    private static final float SWIPE_THRESHOLD = 100;

    public void showPopupWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupwindow, null);

        // Find the LinearLayout in your popup layout
        linearLayout = popupView.findViewById(R.id.linerlayout);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleSwipe(event);
                return true;
            }
        });

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView test2 = popupView.findViewById(R.id.titleText);
        test2.setText("mytext");

        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    private void handleSwipe(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getRawY();
                float deltaY = currentY - startY;

                // Follow the finger movement (adjust the multiplier as needed)
                popupWindow.update(0, (int) deltaY, -1, -1, true);

                // Update the starting position
                startY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                // Perform closing animation here based on the final position
                float endY = event.getRawY();
                float finalDeltaY = endY - startY;

                if (Math.abs(finalDeltaY) > SWIPE_THRESHOLD) {
                    if (finalDeltaY < 0) {
                        // Swipe up, dismiss the popup
                        dismiss();
                    }

                    else if (finalDeltaY > 0) {
                        dismiss();
                    }

                }
                break;
        }
    }
}