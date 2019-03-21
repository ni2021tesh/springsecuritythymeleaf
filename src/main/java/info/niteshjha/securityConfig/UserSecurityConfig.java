package info.niteshjha.securityConfig;

import info.niteshjha.config.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public UserSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //@formatter:off

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {   //@formatter:off
        http.authorizeRequests()
                .antMatchers("/login*",
                                         "/",
                                         "/resetPassword*",
                                         "/signup*",
                                         "/forgotPassword*",
                                         "/js/**",
                                         "/img/**",
                                         "/css/**").permitAll()
                .anyRequest().authenticated()

                .and().formLogin()
                            .loginPage("/login")
                            .permitAll()
                            .loginProcessingUrl("/doLogin")
                            .successForwardUrl("/userList")
                .and().logout().permitAll()
                            .logoutUrl("/doLogout")
                            .logoutSuccessUrl("/login?logout")

                .and().csrf().disable();

        //@formatter:on
    }

}
