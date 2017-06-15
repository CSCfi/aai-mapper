package eu.elixir.aaimapper;

import java.security.Principal;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AaiMapperApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(AaiMapperApplication.class, args);
	}

  	@RequestMapping("/map/identities")
  	public String user(@RequestParam("X-ELIXIR") String accessToken, Principal principal) {

		return getElixirUserId(accessToken);
   	}  	

  	@Value("${elixir.userinfo}") String userInfoEndpoint;
  	
    private String getElixirUserId(String accessToken) {

        UserInfoTokenServices tokenService = new UserInfoTokenServices(this.userInfoEndpoint, "");
        OAuth2Authentication auth = tokenService.loadAuthentication(accessToken);
        LinkedHashMap<String,String> details = (LinkedHashMap<String, String>) auth.getUserAuthentication().getDetails();
        String id = details.get("sub");

        return id;
    }
  	
}
