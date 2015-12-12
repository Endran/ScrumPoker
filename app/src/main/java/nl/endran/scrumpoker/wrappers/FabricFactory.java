/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.wrappers;

import android.content.Context;

import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class FabricFactory {

    public Fabric create(Context context, Kit... kits) {
        return Fabric.with(context, kits);
    }

    public Answers getAnswers() {
        return Answers.getInstance();
    }
}
