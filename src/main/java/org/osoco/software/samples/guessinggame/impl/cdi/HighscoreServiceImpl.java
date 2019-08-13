/*
 * This file is licensed to you under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except 
 * in compliance with the License.  You may obtain a copy of the 
 * License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.osoco.software.samples.guessinggame.impl.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.BeanPropertyType;
import org.osgi.service.cdi.annotations.ComponentProperties;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;
import org.osoco.software.samples.guessinggame.HighscoreService;
import org.osoco.software.samples.guessinggame.Level;
import org.osoco.software.samples.guessinggame.Score;

@Bean
@SingleComponent
@Service(HighscoreService.class)
@HighscoreServiceImpl.Config
public class HighscoreServiceImpl implements HighscoreService {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @BeanPropertyType
    public @interface Config {
        int maxEntries() default 10;
    }

    @Inject
    @ComponentProperties
    private Config configuration;

    private final Map<Level, List<Score>> table = new HashMap<Level, List<Score>>();

    @Override
    public List<Score> getHighscores(final Level level) {
        synchronized (table) {
            return this.table.get(level);
        }
    }

    @Override
    public int addScore(final Level level, final Score score) {
        synchronized (table) {
            List<Score> highscores = table.get(level);
            if (highscores == null) {
                highscores = new ArrayList<Score>();
            } else {
                highscores = new ArrayList<Score>(highscores);
            }
            highscores.add(score);
            Collections.sort(highscores);
            if (highscores.size() > configuration.maxEntries()) {
                highscores.remove(configuration.maxEntries());
            }
            table.put(level, Collections.unmodifiableList(highscores));
            return highscores.indexOf(score);
        }
    }
}
