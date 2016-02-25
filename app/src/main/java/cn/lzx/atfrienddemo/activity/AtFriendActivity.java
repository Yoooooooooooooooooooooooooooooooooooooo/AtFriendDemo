package cn.lzx.atfrienddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.widget.EditText;

import cn.lzx.atfrienddemo.R;

public class AtFriendActivity extends Activity
{

    private EditText mEditText;

    private static int REQUEST_CODE = 101;

    private static final String KEY_NAME = "name";

    private int mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atfriend);

        mEditText = (EditText) findViewById(R.id.edittext);

        mEditText.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mStart = start;

                if (TextUtils.isEmpty(s))
                {
                    return;
                }

                // 输入字符数为1（包括粘贴），且为@
                if (count == 1 && "@".equals(String.valueOf(s.charAt(start))))
                {
                    Intent intent = new Intent(AtFriendActivity.this, FriendListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE)
        {
            insertAtIntoEditText(data.getStringExtra(KEY_NAME),mStart);
        }
    }

    /**
     * 插入@
     * @param text
     */
    private void insertAtIntoEditText(String text,int index)
    {
        text = "@" + text;
        ImageSpan imageSpan = new ImageSpan(getTextBitmap(this, text));

        Editable et = mEditText.getText();// 先获取Edittext中的内容
        et.replace(index, index + 1, text + " "); // 替换"@"为"@text "
        et.setSpan(imageSpan, index, index + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // 替换"@text"为图片
        mEditText.setText(et);
        mEditText.setSelection(index + text.length() + 1);// 设置Edittext中光标插入点
    }

    /**
     * 文字生成图片
     * @param context
     * @param text
     * @return
     */
    public static Bitmap getTextBitmap(Context context, String text)
    {
        Bitmap bmp = null;// 最终生成的图片
        int text_size = 40;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(text_size);

        int padding = 5;
        int height = 50;
        int width = height * text.length() / 2 + padding;
        height = height + padding;

        bmp = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_green), width,
                height, false);

        Canvas canvas = new Canvas(bmp);

        canvas.drawText(text, width / 2, text_size, textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;

    }

}
