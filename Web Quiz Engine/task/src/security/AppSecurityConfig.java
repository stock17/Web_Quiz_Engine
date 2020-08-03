package security;


import entities.User;
import entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/register**")
                .permitAll()
                .antMatchers("/actuator/shutdown")
                .permitAll()
                .antMatchers("/api/users")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        for (User user : userRepository.findAll()) {
//            auth.inMemoryAuthentication()
//                    .withUser(user.getEmail())
//                    .password("{noop}" + user.getPassword())
//                    .roles(("USER"));
//
//        }
        auth.userDetailsService(service);
    }
}
