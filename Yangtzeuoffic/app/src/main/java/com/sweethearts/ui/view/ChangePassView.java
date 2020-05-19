package com.sweethearts.ui.view;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public interface ChangePassView {
    Toolbar getToolbar();

    TextInputEditText ACC();

    TextInputEditText PassOld();

    TextInputEditText PassNew();

    TextInputEditText PassDone();
}
