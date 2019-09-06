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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.aries.cdi.extra.propertytypes.HttpWhiteboardContext;
import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * Servlet context for the guessing game
 */
@Bean
@SingleComponent
@Service(ServletContextHelper.class)
@HttpWhiteboardContext(name = AppServletContext.NAME, path = "/guessinggame")
public class AppServletContext extends ServletContextHelper {

    public static final String NAME = "org.osoco.software.samples.guessinggame";

    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        return super.handleSecurity(request, response);
    }
}
