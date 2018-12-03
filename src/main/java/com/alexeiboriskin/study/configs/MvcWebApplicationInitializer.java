package com.alexeiboriskin.study.configs;

import com.alexeiboriskin.study.services.MyUserDetailsService;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { MvcWebConfig.class };
   }

   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] {MyUserDetailsService.class, SecSecurityConfig.class};
   }
}
