package com.mxt.anitrend.base.custom.view.editor;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mxt.anitrend.R;
import com.mxt.anitrend.base.interfaces.view.CustomView;
import com.mxt.anitrend.util.ApplicationPrefs;
import static com.mxt.anitrend.util.KeyUtils.*;

/**
 * Created by max on 2017/08/14.
 */

public class MarkdownInputEditor extends TextInputEditText implements CustomView, ActionMode.Callback {

    private ApplicationPrefs applicationPrefs;

    public MarkdownInputEditor(Context context) {
        super(context);
        onInit();
    }

    public MarkdownInputEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public MarkdownInputEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    /**
     * Optionally included when constructing custom views
     */
    @Override
    public void onInit() {
        applicationPrefs = new ApplicationPrefs(getContext());
        setVerticalScrollBarEnabled(true);
        setTextColor(ContextCompat.getColor(getContext(),applicationPrefs.isLightTheme()?
                R.color.colorPrimaryDark_dark:R.color.colorPrimaryText));
        setCustomSelectionActionModeCallback(this);
    }

    /**
     * Called when action mode is first created. The menu supplied will be used to
     * generate action buttons for the action mode.
     *
     * @param mode ActionMode being created
     * @param menu Menu used to populate action buttons
     * @return true if the action mode should be created, false if entering this
     * mode should be aborted.
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        return true;
    }

    /**
     * Called to refresh an action mode's action menu whenever it is invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    /**
     * Called to report a user click on an action button.
     *
     * @param mode The current ActionMode
     * @param item The item that was clicked
     * @return true if this callback handled the event, false if the standard MenuItem
     * invocation should continue.
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int start = getSelectionStart();
        int end = getSelectionEnd() + 2;
        if(end > getSelectionEnd() + 1)
            end -= 1;
        switch (item.getItemId()) {
            case R.id.menu_bold:
                getText().insert(start, MD_BOLD);
                getText().insert(end, MD_BOLD, 0, MD_BOLD.length());
                mode.finish();
                return true;
            case R.id.menu_italic:
                getText().insert(start, MD_ITALIC);
                getText().insert(end, MD_ITALIC, 0, MD_ITALIC.length());
                mode.finish();
                return true;
            case R.id.menu_strike:
                getText().insert(start, MD_STRIKE);
                getText().insert(end, MD_STRIKE, 0, MD_STRIKE.length());
                mode.finish();
                return true;
            case R.id.menu_list:
                getText().insert(start, MD_NUMBER);
                mode.finish();
                return true;
            case R.id.menu_bullet:
                getText().insert(start, MD_BULLET);
                mode.finish();
                return true;
            case R.id.menu_heading:
                getText().insert(start, MD_HEADING);
                mode.finish();
                return true;
            case R.id.menu_center:
                getText().insert(start, MD_CENTER_ALIGHN);
                getText().insert(end, MD_CENTER_ALIGHN, 0, MD_CENTER_ALIGHN.length());
                mode.finish();
                return true;
            case R.id.menu_quote:
                getText().insert(start, MD_QOUTE);
                mode.finish();
                return true;
            case R.id.menu_code:
                getText().insert(start, MD_CODE);
                getText().insert(end, MD_CODE, 0, MD_CODE.length());
                mode.finish();
                return true;
        }
        return false;
    }

    /**
     * Called when an action mode is about to be exited and destroyed.
     *
     * @param mode The current ActionMode being destroyed
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
