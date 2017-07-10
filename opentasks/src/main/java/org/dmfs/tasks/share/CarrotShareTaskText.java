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
import android.net.Uri;
import android.util.Log;

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

//                String s = "file:///android_asset/carrot.template";
//                String path = "android.resource://" + context.getPackageName() + "/" + R.raw.video;
//                String path = "android.resource://" + context.getPackageName() + "/";

                Uri fileUri = Uri.parse("android.resource://org.dmfs.tasks/raw/carrot");

//                String path = "file:///android_asset/";
//                String path = "android.resource://org.dmfs.tasks/raw/";
//                String fileName = String.valueOf(R.raw.carrot);
                String fileName = "carrot";

//                config.setResourceLocater(
//                        new FileResourceLocater(config, path));

                config.setResourceLocater(new CarrotResourceLocator(context));
                try
                {
                    Map<String, Object> bindings = new TreeMap<>();
                    bindings.put("content", "summer day");
                    String output = engine.process(fileName, bindings);
                    Log.d("carrot", output);
                    return output;
                }
                catch (CarrotException e)
                {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
