package com.herkiusdev.dicemaster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;

public class EditTextDialog extends Dialog implements View.OnClickListener {

    TextView title;
    TextView text;
    public TextInputLayout textInputLayout;
    public EditText editText;
    public Button negativeButton;
    public Button positiveButton;

    Context context;
    View view;

    public EditTextDialog(Context context) {

        super(context);

        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_text);
        view = getWindow().getDecorView();
        view.setBackgroundResource(android.R.color.transparent);

        title = (TextView) findViewById(R.id.dialog_edit_text_title);
        text = (TextView) findViewById(R.id.dialog_edit_text_text);
        textInputLayout = (TextInputLayout) findViewById(R.id.dialog_edit_text_text_input);
        editText = (EditText) findViewById(R.id.dialog_edit_text_edit);
        negativeButton = (Button) findViewById(R.id.dialog_edit_text_neg_button);
        positiveButton = (Button) findViewById(R.id.dialog_edit_text_pos_button);

        negativeButton.setOnClickListener(this);
        positiveButton.setOnClickListener(this);

        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        this.title.setText(this.context.getResources().getString(titleId));
    }

    public void setText(CharSequence text) {
        this.text.setText(text);
    }

    public void setText(int textId) {
        this.text.setText(this.context.getResources().getString(textId));
    }

    public void setNegativeButtonText(CharSequence text) {
        this.negativeButton.setText(text);
    }

    public void setNegativeButtonText(int textId) {
        this.negativeButton.setText(this.context.getText(textId));
    }

    public void setPositiveButtonText(CharSequence text) {
        this.positiveButton.setText(text);
    }

    public void setPositiveButtonText(int textId) {
        this.positiveButton.setText(this.context.getText(textId));
    }

    public String getInputText() {
        return this.editText.getText().toString();
    }

    @Override
    public void onClick(View v) {

    }
}
