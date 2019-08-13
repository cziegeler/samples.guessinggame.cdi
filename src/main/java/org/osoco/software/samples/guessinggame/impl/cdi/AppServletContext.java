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
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.BeanPropertyType;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * Servlet context for the guessing game
 */
@Bean
@SingleComponent
@Service(ServletContextHelper.class)
@AppServletContext.ContextConfig
public class AppServletContext extends ServletContextHelper {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @BeanPropertyType
    public @interface ContextConfig {
        String osgi_http_whiteboard_context_name() default AppServletContext.NAME;

        String osgi_http_whiteboard_context_path() default "/guessinggame";
    }

    public static final String NAME = "org.osoco.software.samples.guessinggame";

    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        return super.handleSecurity(request, response);
    }
}
