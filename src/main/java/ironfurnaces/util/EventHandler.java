/*
 * Copyright 2025 pizzaatime and XenoMustache
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.util;


import ironfurnaces.init.Registration;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ExplosionKnockbackEvent;

public class EventHandler {


    @SubscribeEvent
    public static void explosionEvent(ExplosionKnockbackEvent event)
    {
        // 1.21.2 no longer exposes affected block positions in ExplosionKnockbackEvent.
        // Keep this hook compiled until block protection is reimplemented against the new explosion API.
    }

}
