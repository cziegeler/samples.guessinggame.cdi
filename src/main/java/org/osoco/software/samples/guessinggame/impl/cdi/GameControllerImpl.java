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

import java.util.Random;

import javax.inject.Inject;

import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.BeanPropertyType;
import org.osgi.service.cdi.annotations.ComponentProperties;
import org.osgi.service.cdi.annotations.PID;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osoco.software.samples.guessinggame.Game;
import org.osoco.software.samples.guessinggame.GameController;
import org.osoco.software.samples.guessinggame.Level;

@Bean
@Service
@PID("org.osoco.software.samples.guessinggame.impl.GameControllerImpl")
@ServiceDescription("This is the core game component")
public class GameControllerImpl implements GameController {

    @BeanPropertyType
    @ObjectClassDefinition(name = "Game Configuration", description = "The configuration for the famous guessing game.")
    public @interface Config {
        @AttributeDefinition(name = "Easy", description = "Maximum value for easy")
        int easy_max() default 10;

        @AttributeDefinition(name = "Medium", description = "Maximum value for medium")
        int medium_max() default 50;

        @AttributeDefinition(name = "Hard", description = "Maximum value for hard")
        int hard_max() default 100;
    }

    @Inject
    @ComponentProperties
    private Config configuration;

    private final Random r = new Random();

    @Override
    public Game startGame(final String name, final Level level) {
        return new Game(name, level, r.nextInt(getMax(level)) + 1);
    }

    @Override
    public int getMax(final Level level) {
        int max = 0;
        switch (level) {
        case EASY:
            max = configuration.easy_max();
            break;
        case MEDIUM:
            max = configuration.medium_max();
            break;
        case HARD:
            max = configuration.hard_max();
            break;
        }
        return max;
    }

    @Override
    public int nextGuess(final Game status, final int guess) {
        status.incAttempts();
        if (status.getNumber() == guess) {
            return 0;
        }
        if (status.getNumber() > guess) {
            return -1;
        }
        return +1;
    }
}
