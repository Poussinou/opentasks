/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.tasks.share;

import android.content.Context;
import android.util.Log;

import org.dmfs.tasks.R;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.Model;
import org.dmfs.tasks.utils.TaskText;
import org.dmfs.tasks.utils.charsequence.AbstractCharSequence;
import org.dmfs.tasks.utils.factory.Factory;

import java.util.Map;
import java.util.TreeMap;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;


/**
 * {@link TaskText} for sharing task information, uses <code>carrot</code> template engine.
 *
 * @author Gabor Keszthelyi
 */
public class CarrotShareTaskText extends AbstractCharSequence implements TaskText
{
    public CarrotShareTaskText(ContentSet contentSet, Model model, final Context context)
    {
        super(new Factory<CharSequence>()
        {
            @Override
            public CharSequence create()
            {
                CarrotEngine engine = new CarrotEngine();
                Configuration config = engine.getConfig();
                config.setResourceLocater(new RawIdResourceLocator(context));
                try
                {
                    Map<String, Object> bindings = new TreeMap<>();
                    String templateName = String.valueOf(R.raw.carrot);
                    String output = engine.process(templateName, bindings);
                    Log.v("CarrotShareTaskText", output);
                    return output;
                }
                catch (CarrotException e)
                {
                    throw new RuntimeException("Failed to process template with carrot", e);
                }
            }
        });
    }
}
