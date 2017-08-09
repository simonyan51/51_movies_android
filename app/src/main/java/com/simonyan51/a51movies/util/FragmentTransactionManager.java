package com.simonyan51.a51movies.util;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * Created by simonyan51 on 07.08.2017.
 */

public class FragmentTransactionManager {

    public static void displayFragment(FragmentManager fragmentManager, Fragment fragment,
                                       int view, boolean mustAddToBackStack) {
        if (mustAddToBackStack) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();

        } else {
            fragmentManager.beginTransaction()
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
}