/*
 * Copyright (C) 2013 Slimroms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.SysUIToast;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;

import android.widget.Toast;

import java.util.Random;
import javax.inject.Inject;

public class HornsTile extends QSTileImpl<BooleanState> {

    private final ActivityStarter mActivityStarter;


   // Random Strings
   public final static java.lang.String[] insults = {
             "Hahaha, n00b!",
             "What are you doing??",
             "n00b alert!",
             "What is this...? Amateur hour!?",
             "This is not Windows",
             "Please step away from the device!",
             "error code: 1D10T",
             "Go outside",
             "¯\\_(ツ)_/¯",
             "Pro tip: Stop doing this!",
             "Y u no speak computer???",
             "Why are you so stupid?!",
             "Perhaps this Android thing is not for you...",
             "Don't you have anything better to do?!",
             "This is why nobody likes you...",
             "Are you even trying?!",};

    @Inject
    public HornsTile(QSHost host, ActivityStarter activityStarter) {
        super(host);
	mActivityStarter = activityStarter;
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public void handleClick() {
        refreshState();
        // Collapse the panels, so the user can see the toast.
        mHost.collapsePanels();
        Random randomInsult = new Random();
        final int toasts = randomInsult.nextInt(insults.length);
        SysUIToast.makeText(mContext, insults[toasts],
                      Toast.LENGTH_LONG).show();
    }

    @Override
    protected void handleLongClick() {
	mHost.collapsePanels();
	startHornsActivity();
    }

    @Override
    public Intent getLongClickIntent() {
	return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.horns_label);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.HORNS;
    }

    @Override
    public void handleSetListening(boolean listening) {
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        state.icon = ResourceIcon.get(R.drawable.ic_qs_horns);
        state.label = mContext.getString(R.string.horns_label);
    }

    private void startHornsActivity() {
        Intent nIntent = new Intent(Intent.ACTION_MAIN);
        nIntent.setClassName("com.android.settings",
            "com.android.settings.Settings$HornsActivity");
        mActivityStarter.startActivity(nIntent, true /* dismissShade */);
    }
}
