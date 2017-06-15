package eu.elixir.aaimapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@EnableOAuth2Sso
@Order(1)
@Configuration
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

 @Autowired
 private ResourceServerTokenServices tokenServices;
	  
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.antMatcher("/**").authorizeRequests()
      .antMatchers("/map/**").authenticated()
      .and()
      .addFilterBefore(new ApiTokenAccessFilter(tokenServices), AbstractPreAuthenticatedProcessingFilter.class);
  }

}