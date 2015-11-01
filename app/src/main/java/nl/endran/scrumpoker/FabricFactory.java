/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
public class FabricFactory {

    public Fabric create(Context context, Kit... kits) {
       return Fabric.with(context, kits);
    }
}
