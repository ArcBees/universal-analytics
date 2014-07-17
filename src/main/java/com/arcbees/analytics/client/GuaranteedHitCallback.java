/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.analytics.client;

import com.arcbees.analytics.shared.HitCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

class GuaranteedHitCallback implements HitCallback {
    private final HitCallback callback;
    private boolean hasRun;

    GuaranteedHitCallback(final HitCallback callback) {
        this.callback = callback;
        Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {

            @Override
            public boolean execute() {
                callback.onCallback();
                return false;
            }
        }, 350);
    }

    @Override
    public void onCallback() {
        if (!hasRun) {
            hasRun = true;
            callback.onCallback();
        }
    }
}
